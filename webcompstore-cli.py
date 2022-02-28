import argparse
import requests
import json


VERSION = 0.1
API_URL_DEV = "https://api.webcomponents.opendatahub.testingmachine.eu/"
API_URL_PROD = "https://api.webcomponents.opendatahub.bz.it/" # TODO replace with .com when migration is done

api_url = API_URL_DEV

if __name__=='__main__':
     
    #Initialize
    parser=argparse.ArgumentParser(description="Webcomponent-Store cli program to interact with the API.")
     
    #Adding optional parameters
    parser.add_argument('-v',
                        '--version',
                        help="Prints the version and exits the program.",action="store_true")

        #Adding optional parameters
    parser.add_argument('-s',
                        '--secret',
                        help="The Keycloak client secret.")

    parser.add_argument('-l',
                        '--list',
                        help="Get a list of all webcomponents.",action="store_true")

    parser.add_argument('-p',
                        '--production',
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