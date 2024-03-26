# SPDX-FileCopyrightText: NOI Techpark <digital@noi.bz.it>
#
# SPDX-License-Identifier: AGPL-3.0-or-later

import argparse
import requests
import json
import base64
from datetime import datetime
import copy
import os
from keycloak import KeycloakOpenID

VERSION = 0.1

API_URL_TEST = os.getenv("API_URL_TEST", "http://api:5001")
API_URL_PROD = os.getenv("API_URL_PROD")

WC_PATH = os.getenv("WC_PATH", "./")

# check that WC_PATH terminates with /
if WC_PATH[-1] != "/":
    WC_PATH += "/"

KEYCLOAK_URL_TEST = os.getenv("KEYCLOAK_URL_TEST")
KEYCLOAK_REALM_TEST = os.getenv("KEYCLOAK_REALM_TEST")
KEYCLOAK_CLIENT_ID_TEST = os.getenv("KEYCLOAK_CLIENT_ID_TEST")

KEYCLOAK_URL_PROD = os.getenv("KEYCLOAK_URL_PROD")
KEYCLOAK_REALM_PROD = os.getenv("KEYCLOAK_REALM_PROD")
KEYCLOAK_CLIENT_ID_PROD = os.getenv("KEYCLOAK_CLIENT_ID_PROD")

KEYCLOAK_CLIENT_SECRET = os.getenv("KEYCLOAK_CLIENT_SECRET", None)

# use --production or set env variable PRODUCTION to true, to push to production store
PRODUCTION = os.getenv("PRODUCTION")

keycloak_url = KEYCLOAK_URL_TEST
keycloak_realm = KEYCLOAK_REALM_TEST
keycloak_client_id = KEYCLOAK_CLIENT_ID_TEST

api_url = API_URL_TEST


def get_token():
    keycloak_openid = KeycloakOpenID(server_url=keycloak_url,
                                     client_id=keycloak_client_id,
                                     realm_name=keycloak_realm,
                                     client_secret_key=KEYCLOAK_CLIENT_SECRET,
                                     verify=True)

    return keycloak_openid.token("", "", "client_credentials")["access_token"]


def get_file_as_base64(file_path):
    try:
        with open(WC_PATH + file_path, "rb") as file:
            return base64.b64encode(file.read()).decode('utf-8')
    except IOError:
        print('Error while reading file: ' + file_path)
        exit(6)


def get_file_as_json(file_path):
    try:
        with open(WC_PATH + file_path, 'r') as file:
            data = file.read()
        return json.loads(data)
    except IOError:
        print('Error while reading file: ' + file_path)
        exit(6)


def lighthouse():
    token = get_token()
    url = api_url + "admin/webcomponent/refetch-lighthouse"
    headers = {
        "Authorization": "Bearer " + token
    }
    response = requests.patch(url, headers=headers)
    print("Lighthouse Status Code", response.status_code)


def size():
    token = get_token()
    url = api_url + "admin/webcomponent/recalculate-size"
    headers = {
        "Authorization": "Bearer " + token
    }
    response = requests.patch(url, headers=headers)
    print("Recalculate size Status Code", response.status_code)


def post(token, wcs_manifest, image):
    url = api_url + "admin/webcomponent"

    headers = {
        "Content": "application/json",
        "Authorization": "Bearer " + token
    }

    # prepare wcs_manifest data for post
    del wcs_manifest["image"]
    del wcs_manifest["configuration"]
    del wcs_manifest["dist"]
    wcs_manifest["imagePngBase64"] = image

    response = requests.post(url, headers=headers, json=wcs_manifest)
    
    print("POST " + url)
    print("Status Code", response.status_code)
    if response.status_code != 200:
        print("Error message", response.text)
        exit(1)
        
    data = response.json()
    return data["uuid"]

def patch_image(token, uuid, image):
    url = api_url + "admin/webcomponent/" + uuid + "/logo"

    headers = {
        "Content": "application/json",
        "Authorization": "Bearer " + token
    }

    data = {
        "logoPngBase64": image
    }


    response = requests.patch(url, headers=headers, json=data)
    
    print("PATCH " + url)
    print("Status Code", response.status_code)
    if response.status_code != 200:
        print("Error message", response.text)
        exit(1)
        

def post_version(token, uuid, wcs_manifest, dist_files, version_tag):
    wcs_manifest["versionTag"] = version_tag
    wcs_manifest["releaseTimestamp"] = datetime.now().strftime(
        "%Y-%m-%dT%H:%M:%SZ")
    wcs_manifest["distFiles"] = dist_files

    url = api_url + "admin/webcomponent/" + uuid

    headers = {
        "Content": "application/json",
        "Authorization": "Bearer " + token
    }

    response = requests.post(url, headers=headers, json=wcs_manifest)
    print("POST " + url)
    print("Status Code", response.status_code)
    if response.status_code != 200:
        print("Error message", response.text)
        exit(2)
    # print("JSON Response ", response.json())

def put_webcomp(token, uuid, wcs_manifest):

    url = api_url + "admin/webcomponent/" + uuid

    headers = {
        "Content": "application/json",
        "Authorization": "Bearer " + token
    }

    response = requests.put(url, headers=headers, json=wcs_manifest)
    print("PUT " + url)
    print("Status Code", response.status_code)
    if response.status_code != 200:
        print("Error message", response.text)
        exit(3)

def put_version(token, uuid, wcs_manifest, dist_files, version_tag):

    url = api_url + "admin/webcomponent/" + uuid + "/" + version_tag

    headers = {
        "Content": "application/json",
        "Authorization": "Bearer " + token
    }

    # wcs_manifest["readMe"] = None
    # wcs_manifest["licenseAgreement"] = None
    wcs_manifest["versionTag"] = version_tag
    wcs_manifest["releaseTimestamp"] = datetime.now().strftime(
        "%Y-%m-%dT%H:%M:%SZ")

    wcs_manifest["distFiles"] = dist_files

    response = requests.put(url, headers=headers, json=wcs_manifest)
    print("PUT " + url)
    print("Status Code", response.status_code)
    if response.status_code != 200:
        print("Error message", response.text)
        exit(3)


def find_webcomp(repository_url):
    for element in get_list()["content"]:
        if repository_url == element["repositoryUrl"]:
            return element
    return None


def get_list():
    url = api_url + "webcomponent?pageSize=1000000"
    response = requests.get(url)
    return response.json()


def confirm(text=""):
    while True:
        choice = input(f"Confirm {text}[y/N]?").lower()
        if choice in 'no' or not choice:
            exit()
        elif choice in 'yes':
            return True


if __name__ == '__main__':
    parser = argparse.ArgumentParser(
        description="Webcomponent-Store cli program to interact with the API.")

    # parser.add_argument('-v', '--verbose', help="Produce verbose output.", action="store_true")
    parser.add_argument(
        '-l', '--list', help="Prints list of all webcomponents.", action="store_true")
    parser.add_argument(
        '-y', '--yes', help="Don't ask for confirmation on critical actions like deleting.", action="store_true")
    parser.add_argument('--push', metavar='VERSION_TAG',
                        help="Push a webcomponent to the store with the given version-tag (usually the git commit sha).")
    parser.add_argument('--delete', metavar='UUID',
                        help="Deletes a webcomponent with the give uuid.")
    parser.add_argument(
        '--production', help="Use production URL for API: api.webcomponents.opendatahub.com", action="store_true")
    parser.add_argument(
        '--lighthouse', help="Refetches the lighthouse stats for every webcomponent.", action="store_true")
    parser.add_argument(
        '--size', help="Recalculates the size for every webcomponent.", action="store_true")
    parser.add_argument(
        '--version', help="Output version information and exits.", action="store_true")

    args = parser.parse_args()

    if(args.version):
        print(f"webcompstore-cli version {VERSION}")
        exit()

    if(args.production or str(PRODUCTION).upper() == 'TRUE'):
        api_url = API_URL_PROD
        keycloak_url = KEYCLOAK_URL_PROD
        keycloak_realm = KEYCLOAK_REALM_PROD
        keycloak_client_id = KEYCLOAK_CLIENT_ID_PROD
        print("Pushing to production...")

    if(args.list):
        list = get_list()
        print(json.dumps(list, indent=4))

    if(args.push):

        wcs_manifest = get_file_as_json("wcs-manifest.json")
        image = get_file_as_base64("wcs-logo.png")

        # iterate over files and create array of files with dist filenames and base64 encoded files
        dist_files = []
        for file_name in wcs_manifest["dist"]["files"]:
            dist_file_path = wcs_manifest["dist"]["basePath"] + \
                "/" + file_name
            dist_file = get_file_as_base64(dist_file_path)
            dist_files.append({
                "fileName": file_name,
                "fileDataBase64": dist_file
            })
        
        # check that repositoryUrl has .git in it
        if not ".git" in wcs_manifest["repositoryUrl"]:
            print("repositoryUrl in wcs-manifest.json needs '.git' ending.")
            print("example: https://github.com/noi-techpark/webcomp-carsharing.git")
            exit(5)

        webcomp = find_webcomp(wcs_manifest["repositoryUrl"])

        token = get_token()

        version_tag = str(args.push)[0:8]

        if webcomp == None:
            # post for first time
            uuid = post(token, copy.deepcopy(wcs_manifest), image)
            post_version(token, uuid, copy.deepcopy(
                wcs_manifest), dist_files, version_tag)
        else:
            # update webcomp
            put_webcomp(
                token, webcomp["uuid"], wcs_manifest,)
            # put version
            put_version(
                token, webcomp["uuid"], wcs_manifest, dist_files, version_tag)
            patch_image(token, webcomp["uuid"],image)

    if(args.lighthouse):
        lighthouse()

    if(args.size):
        size()

    if(args.delete):
        if not args.yes:
            confirm("deletion ")
        token = get_token()
        url = api_url + "admin/webcomponent/" + args.delete

        headers = {
            "Authorization": "Bearer " + token
        }
        response = requests.delete(url, headers=headers)
        print("Status Code", response.status_code)
        if response.status_code != 200:
            print("Error message", response.text)
            exit(4)
