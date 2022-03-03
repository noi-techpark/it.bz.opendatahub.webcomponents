# webcompstore cli tool
A command line tool to interact with the API of the Webcomponent-Store.  
It's mainly used in the webcomp-push GithubAction to push webcomponents to the store:  
[github.com/noi-techpark/github-actions/tree/main/webcomp-push](https://github.com/noi-techpark/github-actions/tree/main/webcomp-push)

- [webcompstore cli tool](#webcompstore-cli-tool)
  - [Prerequisite](#prerequisite)
  - [Setup](#setup)
  - [Usage](#usage)

## Prerequisite
To run this cli tool you need the following software installed:  
- Python version 3
- Pythons "pip3" package manager
- Optionally: [venv](https://docs.python.org/3/library/venv.html) (to have a virtual python environment and prevent dependencies-hell on your machine) 

## Setup
If you want to use venv please follow first [these steps](https://docs.python.org/3/library/venv.html#creating-virtual-environments) to create a virtual environment.  
To install the required libraries in *requirements.txt* running:  
```
pip3 install -r requirements.txt
```


## Usage
You can get the following help message running `python3 webcompstore-cli.py --help`

```
usage: webcompstore-cli.py [-h] [-l] [-y] [--push VERSION_TAG] [--delete UUID] [--production] [--lighthouse] [--size] [--version]

Webcomponent-Store cli program to interact with the API.

optional arguments:
  -h, --help          show this help message and exit
  -l, --list          Prints list of all webcomponents.
  -y, --yes           Don't ask for confirmation on critical actions like deleting.
  --push VERSION_TAG  Push a webcomponent to the store with the given version-tag (usually the git commit sha).
  --delete UUID       Deletes a webcomponent with the give uuid.
  --production        Use production URL for API: api.webcomponents.opendatahub.bz.it
  --lighthouse        Refetches the lighthouse stats for every webcomponent.
  --size              Recalculates the size for every webcomponent.
  --version           Output version information and exits.
```