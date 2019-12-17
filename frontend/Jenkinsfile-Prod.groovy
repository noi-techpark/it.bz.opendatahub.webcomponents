pipeline {
    agent {
        dockerfile {
            dir 'frontend'
            filename 'docker/dockerfile-node'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        AWS_ACCESS_KEY_ID = credentials('AWS_ACCESS_KEY_ID')
        AWS_SECRET_ACCESS_KEY = credentials('AWS_SECRET_ACCESS_KEY')

        BASE_URL = "https://api.webcomponents.opendatahub.bz.it"
        CDN_URL = "https://cdn.webcomponents.opendatahub.bz.it"
    }

    stages {
        stage('Dependencies') {
            steps {
                sh 'cd frontend && yarn install'
            }
        }
        stage('Test') {
            steps {
                sh 'cd frontend && yarn run test --passWithNoTests'
            }
        }
        stage('Build') {
            steps {
                sh 'cd frontend && yarn run build'
            }
        }
        stage('Upload') {
            steps {
                s3Upload(bucket: 'webcompstore-prod', acl: 'PublicRead', file: './frontend/dist')
            }
        }
    }
}
