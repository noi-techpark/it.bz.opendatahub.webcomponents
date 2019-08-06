pipeline {
    stages {
        stage('Backend - Test') {
            agent {
                dockerfile {
                    dir 'backend'
                    filename 'docker/dockerfile-java'
                    additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
                }
            }
            steps {
                sh 'cd backend && mvn -B -U clean test verify'
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
                sh 'cd frontend && yarn install'
                sh 'cd frontend && yarn run test'
            }
        }
    }
}
