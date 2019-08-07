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

        DATASOURCE_URL = "jdbc:postgresql://test-pg-bdp.co90ybcr8iim.eu-west-1.rds.amazonaws.com:5432/webcompstore"
        DATASOURCE_USERNAME = credentials('webcompstore-test-postgres-username')
        DATASOURCE_PASSWORD = credentials('webcompstore-test-postgres-password')

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
