#!/bin/bash
set -xeuo pipefail

#
# Configuration
#
set -o allexport
if [ -f .env ]; then
    source .env
else
    echo ".env file not found in $PWD"
fi
set +o allexport

PSQL="psql -h $DB_HOST -p $DB_PORT -U $DB_USER"
GIT_BASEURL="https://raw.githubusercontent.com/noi-techpark"
GITHUBURL="https://github.com/noi-techpark"

#
# Prepare local temporary folders and files, download the manifest file and parse information
#
rm -rf "tmp/${WC_NAME:?}/${WC_TAG:?}"*
mkdir -p "tmp/$WC_NAME/$WC_TAG/dist"

cd tmp
wget --no-verbose "$GIT_BASEURL/odh-web-components-store-origins/development/origins.json"
UUID=$(jq -r '.[] | select(.url | test(".*$WC_NAME.*")) | .uuid' origins.json)

cd "$WC_NAME/$WC_TAG"
wget --no-verbose "$GIT_BASEURL/$WC_NAME/$WC_TAG/wcs-manifest.json" 

WCS_IMAGE=$(jq -r '.image' ./wcs-manifest.json)
wget --no-verbose "$GIT_BASEURL/$WC_NAME/$WC_TAG/$WCS_IMAGE"

cd dist
DIST_PATH=$(jq -r '.dist.basePath' ../wcs-manifest.json)
jq -r '.dist.files[]' ../wcs-manifest.json \
    | xargs -I '{}' wget --no-verbose "$GIT_BASEURL/$WC_NAME/$WC_TAG/$DIST_PATH/{}"

#
# Update the database records
#

$PSQL <<SQL

    insert into origin (uuid, url, api, deleted) 
    values (
        'TEST-$UUID',
        '$GITHUBURL/$WC_NAME.git',
        'github',
        false
    ) on conflict (uuid) do 
        update set 
            url = excluded.url, 
            api = excluded.api, 
            deleted = excluded.deleted;

SQL

MF_TITLE=$(jq -r '.title' ../wcs-manifest.json)
MF_TITLE=${MF_TITLE//\'/\'\'}

MF_DESCRIPTION=$(jq -r '.description' ../wcs-manifest.json)
MF_DESCRIPTION=${MF_DESCRIPTION//\'/\'\'}

MF_DESCRIPTION_ABSTRACT=$(jq -r '.descriptionAbstract' ../wcs-manifest.json)
MF_DESCRIPTION_ABSTRACT=${MF_DESCRIPTION_ABSTRACT//\'/\'\'}

MF_AUTHORS=$(jq -r '.authors' ../wcs-manifest.json)
MF_AUTHORS=${MF_AUTHORS//\'/\'\'}

MF_COPYRIGHTHOLDERS=$(jq -r '.copyrightHolders' ../wcs-manifest.json)
MF_COPYRIGHTHOLDERS=${MF_COPYRIGHTHOLDERS//\'/\'\'}

$PSQL <<SQL

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
        'TEST-$UUID',
        '$MF_TITLE',
        '$MF_DESCRIPTION',
        '$MF_DESCRIPTION_ABSTRACT',
        '$(jq -r '.license' ../wcs-manifest.json)',
        '$MF_AUTHORS',
        '$(jq -r '.searchTags' ../wcs-manifest.json)',
        '$(jq -r '.image' ../wcs-manifest.json)',
        '$GITHUBURL/$WC_NAME.git',
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
            copyright_holde = excluded.copyright_holders

SQL

MF_CONFIGURATION=$(jq -r '.configuration' ../wcs-manifest.json)
MF_CONFIGURATION=${MF_CONFIGURATION//\'/\'\'}

MF_DIST=$(jq -r '.dist' ../wcs-manifest.json)
MF_DIST=${MF_DIST//\'/\'\'}


$PSQL <<SQL

    insert into webcomponent_version (
        webcomponent_uuid, 
        version_tag, 
        release_timestamp, 
        configuration, 
        dist, 
        deleted 
    ) values (
        'TEST-$UUID',
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
            deleted = excluded.deleted 

SQL


#
# Upload to the CDN
#
ssh tomcattest2 "mkdir -p /home/admin/var/data/webcomponents-store/$UUID/$WC_TAG/dist"
scp -r ./* "tomcattest2:/home/admin/var/data/webcomponents-store/$UUID/$WC_TAG/dist"
scp "../wcs-manifest.json" "tomcattest2:/home/admin/var/data/webcomponents-store/$UUID/$WC_TAG"
scp "../$WCS_IMAGE" "tomcattest2:/home/admin/var/data/webcomponents-store/$UUID/$WC_TAG"

ssh tomcattest2 bash -c "'
    set -xeuo pipefail
    sudo rm -rf /var/data/webcomponents-store/$UUID/$WC_TAG
    sudo mkdir -p /var/data/webcomponents-store/$UUID/$WC_TAG
    sudo cp -R /home/admin/var/data/webcomponents-store/$UUID/$WC_TAG/* /var/data/webcomponents-store/$UUID/$WC_TAG
    sudo chown -R tomcat8: /var/data/webcomponents-store/$UUID
'"



exit 0