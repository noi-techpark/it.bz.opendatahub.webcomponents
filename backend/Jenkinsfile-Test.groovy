pipeline {
    agent {
        dockerfile {
            dir 'backend'
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        TESTSERVER_TOMCAT_ENDPOINT_API = "http://api.webcomponents.tomcat02.testingmachine.eu:8080/manager/text"
        TESTSERVER_TOMCAT_ENDPOINT_CDN = "http://cdn.webcomponents.tomcat02.testingmachine.eu:8080/manager/text"
        TESTSERVER_TOMCAT_CREDENTIALS = credentials('testserver-tomcat8-credentials')

        POSTGRES_URL = "jdbc:postgresql://test-pg-bdp.co90ybcr8iim.eu-west-1.rds.amazonaws.com:5432/webcompstore"
        POSTGRES_USERNAME = credentials('webcompstore-test-postgres-username')
        POSTGRES_PASSWORD = credentials('webcompstore-test-postgres-password')

        WORKSPACE_PATH = "/var/data/webcomponents-store"
        GITHUB_TOKEN = credentials('webcompstore-test-github-token')
        ORIGIN_URL = "https://github.com/uhufa/odh-web-components-store-demo-origins.git"
        ORIGIN_BRANCH = "master"
    }

    stages {
        stage('Configure') {
            steps {
                sh 'sed -i -e "s/<\\/settings>$//g\" ~/.m2/settings.xml'
                sh 'echo "    <servers>" >> ~/.m2/settings.xml'
                sh 'echo "        ${TESTSERVER_TOMCAT_CREDENTIALS}" >> ~/.m2/settings.xml'
                sh 'echo "    </servers>" >> ~/.m2/settings.xml'
                sh 'echo "</settings>" >> ~/.m2/settings.xml'

                sh 'sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/data-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/data-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/data-service/src/main/resources/application.properties'
                
                sh 'sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/crawler-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/crawler-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/crawler-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(application.workspace.path\\s*=\\).*\\$%\\1${WORKSPACE_PATH}%" backend/crawler-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(application.repository.github.token\\s*=\\).*\\$%\\1${GITHUB_TOKEN}%" backend/crawler-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(application.repository.origin.url\\s*=\\).*\\$%\\1${ORIGIN_URL}%" backend/crawler-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(application.repository.origin.branch\\s*=\\).*\\$%\\1${ORIGIN_BRANCH}%" backend/crawler-service/src/main/resources/application.properties'

                sh 'sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/delivery-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/delivery-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/delivery-service/src/main/resources/application.properties'
                sh 'sed -i -e "s%\\(application.workspace.path\\s*=\\).*\\$%\\1${WORKSPACE_PATH}%" backend/delivery-service/src/main/resources/application.properties'
            }
        }
        stage('Test') {
            steps {
                sh 'cd backend && mvn -B -U clean test verify'
            }
        }
        stage('Deploy') {
            steps{
                sh 'cd backend/data-service && mvn -B -U tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT_API} -Dmaven.tomcat.server=testServer -Pdeploy'
                sh 'cd backend/crawler-service && mvn -B -U tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT_API} -Dmaven.tomcat.server=testServer -Pdeploy'
                sh 'cd backend/delivery-service && mvn -B -U tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT_CDN} -Dmaven.tomcat.server=testServer -Pdeploy'
            }
        }
    }
}
