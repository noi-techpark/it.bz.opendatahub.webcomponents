pipeline {
    agent {
        dockerfile {
            filename 'infrastructure/docker/webcomp-deploy.dockerfile'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=$(id -u jenkins) --build-arg JENKINS_GROUP_ID=$(id -g jenkins)'
        }
    }

    options {
        ansiColor('xterm')
    }

    environment {
		DB_HOST = "test-pg-bdp.co90ybcr8iim.eu-west-1.rds.amazonaws.com"
		DB_PORT = "5432"
        DB_USER = credentials('webcompstore-test-postgres-username')
        DB_PASS = credentials('webcompstore-test-postgres-password')
        SSH_CDN_ADDR = "172.31.19.96"  // docker test 1
        SSH_CDN_USER = "admin"
		GITHUB_ORGANIZATION = "noi-techpark"
		GITHUB_ORIGINS_REPO = "odh-web-components-store-origins"
		GITHUB_ORIGINS_BRANCH = "development"
		GITHUB_ORIGINS_FILE = "origins.json"
        CDN_WORKSPACE = "/var/docker/wcstore-api/shared/workspace"
        CDN_ADMINPATH = "/home/admin/$CDN_WORKSPACE"
    }

    parameters {
        string(
            name: 'WC_NAME',
            defaultValue: 'webcomp-mobility-traffic',
            description: 'Web component git repository name',
            trim: true
        )
        string(
            name: 'WC_TAG',
            defaultValue: 'v0.3.2',
            description: 'Web component git tag',
            trim: true
        )
    }

    stages {
        stage('Configure') {
            steps {
                sh '''
					echo 'DB_USER=$DB_USER' >> infrastructure/utils/.env
					echo 'DB_PASS=$DB_PASS' >> infrastructure/utils/.env
				'''
				sh """
					echo 'DB_HOST=$DB_HOST' >> infrastructure/utils/.env
					echo 'DB_PORT=$DB_PORT' >> infrastructure/utils/.env
					echo 'GITHUB_ORGANIZATION=$GITHUB_ORGANIZATION' >> infrastructure/utils/.env
					echo 'GITHUB_ORIGINS_REPO=$GITHUB_ORIGINS_REPO' >> infrastructure/utils/.env
					echo 'GITHUB_ORIGINS_BRANCH=$GITHUB_ORIGINS_BRANCH' >> infrastructure/utils/.env
					echo 'GITHUB_ORIGINS_FILE=$GITHUB_ORIGINS_FILE' >> infrastructure/utils/.env
					echo 'CDN_WORKSPACE=$CDN_WORKSPACE' >> infrastructure/utils/.env
					echo 'CDN_ADMINPATH=$CDN_ADMINPATH' >> infrastructure/utils/.env

                    mkdir -p ~/.ssh
                    ssh-keyscan -H $SSH_CDN_ADDR >> ~/.ssh/known_hosts
					ssh-keyscan -H github.com >> ~/.ssh/known_hosts
                    echo 'Host testcdnhost' >> ~/.ssh/config
                    echo '  User $SSH_CDN_USER' >> ~/.ssh/config
                    echo '  Hostname $SSH_CDN_ADDR' >> ~/.ssh/config

					git config --global user.email "info@opendatahub.bz.it"
					git config --global user.name "Jenkins"
					git remote set-url origin $GIT_URL
				"""
            }
        }
        stage('Deploy') {
            steps {
                sshagent (credentials: ['tomcatkey', 'jenkins_github_ssh_key']) {
                    sh """
                        cd infrastructure/utils
                        ./wcstorecli.sh -d "$WC_NAME" "$WC_TAG"
                    """
                }
            }
        }
    }
}
