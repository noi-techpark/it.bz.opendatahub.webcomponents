import argparse
import requests
import json
from keycloak import KeycloakOpenID
from dotenv import dotenv_values


env = dotenv_values(".env") 

VERSION = env["VERSION"]
API_URL_DEV = env["API_URL_DEV"]
API_URL_PROD = env["API_URL_PROD"]

KEYCLOAK_URL = env["KEYCLOAK_URL"]
KEYCLOAK_REALM = env["KEYCLOAK_REALM"]
KEYCLOAK_CLIENT_ID = env["KEYCLOAK_CLIENT_ID"]
KEYCLOAK_CLIENT_SECRET = env["KEYCLOAK_CLIENT_SECRET"]

api_url = API_URL_DEV



# Configure client
keycloak_openid = KeycloakOpenID(server_url=KEYCLOAK_URL,
                    client_id=KEYCLOAK_CLIENT_ID,
                    realm_name=KEYCLOAK_REALM,
                    client_secret_key=KEYCLOAK_CLIENT_SECRET)


if __name__=='__main__':
     
    #Initialize
    parser=argparse.ArgumentParser(description="Webcomponent-Store cli program to interact with the API.")
     
    #Adding optional parameters
    parser.add_argument('-v',
                        '--version',
                        help="Prints the version and exits the program.",action="store_true")

    parser.add_argument('-s',
                        '--secret',
                        help="The Keycloak client secret.")

    parser.add_argument('-l',
                        '--list',
                        help="Get a list of all webcomponents.",action="store_true")


    parser.add_argument('-p',
                        '--post',
                        help="Post a webcomponent to the store.",action="store_true")

    parser.add_argument('--production',
                        help="Use production URL for API: api.webcomponents.opendatahub.bz.it",action="store_true")




   #Parsing the argument
    args=parser.parse_args()
    print(args)

    if(args.version):
        print(f"webcompstore-cli version {VERSION}")
        exit()

    if(args.secret):
        print(f"webcompstore-cli {args.secret}")

    if(args.production):
        api_url = API_URL_PROD


    if(args.list):
        url = api_url + "webcomponent" 
        response = requests.get(url)
        data = response.json()
        print(json.dumps(data,indent=4))