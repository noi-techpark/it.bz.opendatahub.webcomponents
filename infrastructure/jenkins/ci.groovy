pipeline {
    agent any

    stages {
        stage('Backend - Test') {
            agent {
                dockerfile {
                    dir 'backend'
                    filename 'infrastructure/docker/java-ci.dockerfile'
                    additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
                }
            }
            steps {
                sh 'cd backend && mvn -B -U clean test'
            }
        }
        stage('Frontend - Test') {
            agent {
                dockerfile {
                    dir 'frontend'
                    filename 'docker/dockerfile-node'
                    additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
                }
            }
            steps {
                sh '''
                    cd frontend
                    yarn install
                    yarn run test --passWithNoTests
                '''
            }
        }
    }
}
