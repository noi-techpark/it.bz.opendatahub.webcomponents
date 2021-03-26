#!/bin/bash
set -euo pipefail
SCRIPTNAME=${0##*/}

GIT_BASEURL="https://github.com"
GIT_BASEURL_RAW="https://raw.githubusercontent.com"

# All output should be in English
export LC_ALL=C


function showHelp {
    echo "
USAGE:
    $SCRIPTNAME [OPTIONS]

    This is the CLI tool to the web component store. It gets configured through a
    .env file. See .env.example for details.

OPTIONS:
  -h                    Show this help message
  -d WEBCOMP TAG        Deploy a web component to the store
  -o WEBCOMP            Add a new web component to the origins file
"
}

function outError {
    echo -e "\e[31m>> $SCRIPTNAME | $(date -Is) | ERROR | $*\e[0m" >&2
    exit 1
}

function outInfo {
    echo -e "\e[32m>> $SCRIPTNAME | $(date -Is) | INFO  | $*\e[0m" >&2
}

function loadDotEnv {
    set -o allexport
    if [ -f .env ]; then
        source .env
    else
        outError ".env file not found in $PWD"
    fi
    set +o allexport

	_GITHUBRAW="$GIT_BASEURL_RAW/$GITHUB_ORGANIZATION"
	_GITHUBURL="$GIT_BASEURL/$GITHUB_ORGANIZATION"

    return 0
}

function jsonGetPermissive {
    FILEPATH="$1"
    shift
    RES=$(jq -r "$@" "$FILEPATH")
    RES=${RES//\'/\'\'}
    if [ -z "${RES}" ] || [ "$RES" = "null" ]; then
        echo ""
    else
		echo "$RES"
	fi
}

function jsonGet {
    RES=$(jsonGetPermissive "$@")
    if [ -z "${RES}" ]; then
        outError "JSON query '$*' did not produce a result (empty or null)"
    fi
	echo "$RES"
}


function updateOrigin {
	outInfo ">> GIT clone"
	mkdir -p tmp/
	cd tmp
	if [ ! -d "$GITHUB_ORIGINS_REPO" ]; then
		git clone "git@github.com:$GITHUB_ORGANIZATION/$GITHUB_ORIGINS_REPO.git"
	fi
	outInfo ">> SUCCESS"
	outInfo ">> GIT checkout/pull"
	cd "$GITHUB_ORIGINS_REPO"
	git checkout "$GITHUB_ORIGINS_BRANCH"
	git pull
	outInfo ">> SUCCESS"
	outInfo ">> Updating $GITHUB_ORIGINS_FILE"
	UUID=$(uuidgen)
	jq '. += [ {"uuid": "'"$UUID"'", "url": "'"$_GITHUBURL/$WC_NAME"'.git", "api": "github" } ]' "$GITHUB_ORIGINS_FILE" > tmp-origins.json
	mv tmp-origins.json "$GITHUB_ORIGINS_FILE"
	outInfo ">> SUCCESS"
	outInfo ">> GIT commit/push changes"
	git add "$GITHUB_ORIGINS_FILE"
	git commit -m "Add $WC_NAME"
	git push
	outInfo ">> SUCCESS"
	cd ../..
}

################################################################################
## MAIN
################################################################################

# Handling of script arguments...
# Each short option character in shortopts may be followed by one colon to
# indicate it has a required argument, and by two colons to indicate it has
# an optional argument.
ARGS=$(getopt -o "hd:o:" -n "$SCRIPTNAME" -- "$@")
eval set -- "$ARGS"

while true; do
    case "$1" in

        ## HELP ###############################################################
        -h)
            showHelp
            exit 0
        ;;
        ## HELP ###############################################################

		## UPDATE ORIGINS #####################################################
		-o)
			loadDotEnv
			WC_NAME="${2:?"Parameter #1 WEBCOMP null or not set"}"
			updateOrigin
			exit 0
		;;
		## UPDATE ORIGINS #####################################################

        ## DEPLOY #############################################################
        -d)
            loadDotEnv

            #
            # Test SSH connection before doing anything
            #
            outInfo "# Testing SSH connection"
            ssh testcdnhost "date" > /dev/null || {
                outError "Could not establish SSH connection. Exiting..."
            }
            outInfo "> SUCCESS"

            WC_NAME="${2:?"Parameter #1 WEBCOMP null or not set"}"
            WC_TAG="${4:?"Parameter #2 TAG null or not set"}"

            PSQL="psql --set AUTOCOMMIT=off --set ON_ERROR_STOP=on -h $DB_HOST -p $DB_PORT -U $DB_USER"

            #
            # Prepare local temporary folders and files, download the manifest file and parse information
            #
            PATH_LOCAL_WC="tmp/${WC_NAME:?}/${WC_TAG:?}"
			PATH_LOCAL_WC=$(pwd)
            #rm -rf "$PATH_LOCAL_WC"*
            #mkdir -p "$PATH_LOCAL_WC/dist"

			#
			# Get manifest file and parse metadata
			#
            outInfo "# Get wcs-manifest.json and parse it"
            PATH_WCS_MANIFEST_JSON="$PATH_LOCAL_WC/wcs-manifest.json"
            #wget --no-verbose "$_GITHUBRAW/$WC_NAME/$WC_TAG/wcs-manifest.json" -O "$PATH_WCS_MANIFEST_JSON"
            MF_WCS_IMAGE=$(jsonGetPermissive "$PATH_WCS_MANIFEST_JSON" '.image')
            MF_DIST_PATH=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.dist.basePath')
            MF_TITLE=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.title')
            MF_DESCRIPTION=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.description')
            MF_DESCRIPTION_ABSTRACT=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.descriptionAbstract')
            MF_AUTHORS=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.authors')
            MF_COPYRIGHTHOLDERS=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.copyrightHolders')
            MF_CONFIGURATION=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.configuration')
            MF_DIST=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.dist')
			MF_LICENSE=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.license')
			MF_SEARCH_TAGS=$(jsonGet "$PATH_WCS_MANIFEST_JSON" '.searchTags')
            outInfo "> SUCCESS"

            outInfo "# Get the image file"
			if [ -z "$MF_WCS_IMAGE" ]; then
				outInfo ">> No image path defined... skipping."
				MF_WCS_IMAGE_SQL="null"
			else
            	#wget --no-verbose "$_GITHUBRAW/$WC_NAME/$WC_TAG/$MF_WCS_IMAGE" -O "$PATH_LOCAL_WC/$MF_WCS_IMAGE"
				MF_WCS_IMAGE_SQL="'$MF_WCS_IMAGE'"
			fi
            outInfo "> SUCCESS"

            #outInfo "# Get all dist files"
            #jq -r '.dist.files[]' "$PATH_WCS_MANIFEST_JSON" \
            #    | xargs -I '{}' wget --no-verbose "$_GITHUBRAW/$WC_NAME/$WC_TAG/$MF_DIST_PATH/{}" -O "$PATH_LOCAL_WC/dist/{}"
            #outInfo "> SUCCESS"

			#
            # Get origins.json and parse UUID, create it if absent
            #
			outInfo "# Get origins.json and parse UUID"
			PATH_ORIGINS_JSON="tmp/origins.json"
			wget --no-verbose "$_GITHUBRAW/$GITHUB_ORIGINS_REPO/$GITHUB_ORIGINS_BRANCH/$GITHUB_ORIGINS_FILE" -O "$PATH_ORIGINS_JSON"
			UUID=$(jsonGetPermissive "$PATH_ORIGINS_JSON" '.[] | select(.url | test(".*'"$WC_NAME"'.*")) | .uuid')
			if [ -z "${UUID}" ]; then
				outInfo "> No entry for $WC_NAME present, creating..."
				## UUID will be set by updateOrigin
	            updateOrigin
			fi
			outInfo ">> UUID is '$UUID'"
			outInfo "> SUCCESS"

            #
            # Update the database records
            #
            outInfo "# Update the database records"
            PGPASSWORD="$DB_PASS" $PSQL <<SQL

                begin;

                insert into origin (uuid, url, api, deleted)
                values (
                    '$UUID',
                    '$_GITHUBURL/$WC_NAME.git',
                    'github',
                    false
                ) on conflict (url) do
                    update set
                        uuid = excluded.uuid,
                        url = excluded.url,
                        api = excluded.api,
                        deleted = excluded.deleted;

                insert into webcomponent (
                    uuid,
                    title,
                    description,
                    description_abstract,
                    license,
                    authors,
                    search_tags,
                    image,
                    repository_url,
                    deleted,
                    copyright_holders
                ) values (
                    '$UUID',
                    '$MF_TITLE',
                    '$MF_DESCRIPTION',
                    '$MF_DESCRIPTION_ABSTRACT',
                    '$MF_LICENSE',
                    '$MF_AUTHORS',
                    '$MF_SEARCH_TAGS',
                    $MF_WCS_IMAGE_SQL,
                    '$_GITHUBURL/$WC_NAME.git',
                    false,
                    '$MF_COPYRIGHTHOLDERS'
                ) on conflict (uuid) do
                    update set
                        title = excluded.title,
                        description = excluded.description,
                        description_abstract = excluded.description_abstract,
                        license = excluded.license,
                        authors = excluded.authors,
                        search_tags = excluded.search_tags,
                        image = excluded.image,
                        repository_url = excluded.repository_url,
                        deleted = excluded.deleted,
                        copyright_holders = excluded.copyright_holders;

                insert into webcomponent_version (
                    webcomponent_uuid,
                    version_tag,
                    release_timestamp,
                    configuration,
                    dist,
                    deleted
                ) values (
                    '$UUID',
                    '$WC_TAG',
                    '$(date -Iseconds)',
                    '$MF_CONFIGURATION',
                    '$MF_DIST',
                    false
                ) on conflict (webcomponent_uuid, version_tag) do
                    update set
                        release_timestamp = excluded.release_timestamp,
                        configuration = excluded.configuration,
                        dist = excluded.dist,
                        deleted = excluded.deleted;

                commit;
SQL
            outInfo "> SUCCESS"

            #
            # Upload to the CDN
            #
            outInfo "# Upload dist files to the CDN"
            ssh testcdnhost "mkdir -p /home/admin/var/data/webcomponents-store/$UUID/$WC_TAG/dist"
            scp -r "$PATH_LOCAL_WC/$MF_DIST_PATH/"* "testcdnhost:/home/admin/var/data/webcomponents-store/$UUID/$WC_TAG/dist"
            scp "$PATH_WCS_MANIFEST_JSON" "testcdnhost:/home/admin/var/data/webcomponents-store/$UUID/$WC_TAG"
			if [ -n "$MF_WCS_IMAGE" ]; then
            	scp "$PATH_LOCAL_WC/$MF_WCS_IMAGE" "testcdnhost:/home/admin/var/data/webcomponents-store/$UUID/$WC_TAG"
			fi
            outInfo "> SUCCESS"

            outInfo "# Set permissions and paths of the uploaded dist files inside the CDN server"
            ssh testcdnhost bash -c "'
                set -xeuo pipefail
                sudo rm -rf /var/data/webcomponents-store/$UUID/$WC_TAG
                sudo mkdir -p /var/data/webcomponents-store/$UUID/$WC_TAG
                sudo cp -R /home/admin/var/data/webcomponents-store/$UUID/$WC_TAG/* /var/data/webcomponents-store/$UUID/$WC_TAG
                sudo chown -R tomcat8: /var/data/webcomponents-store/$UUID
            '"
            outInfo "> SUCCESS"

            exit 0
        ;;
        ## DEPLOY #############################################################

        -- )
            shift
            break
        ;;
    esac
done

# Show help, if now arguments given...
showHelp
outError "No arguments given"
