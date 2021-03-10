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
        GITHUB_TOKEN = credentials('webcompstore-github-token')
        ORIGIN_URL = "https://github.com/noi-techpark/odh-web-components-store-origins.git"
        ORIGIN_BRANCH = "development"
        CRAWLER_FETCH_DELAY_MS = 1800000

        DELIVERY_BASE_URL = "https://cdn.webcomponents.opendatahub.testingmachine.eu/dist"

        DEBUG_LEVEL = "DEBUG"
    }

    stages {
        stage('Configure') {
            steps {
                sh '''
                    sed -i -e "s/<\\/settings>$//g\" ~/.m2/settings.xml
                    echo "    <servers>" >> ~/.m2/settings.xml
                    echo "        ${TESTSERVER_TOMCAT_CREDENTIALS}" >> ~/.m2/settings.xml
                    echo "    </servers>" >> ~/.m2/settings.xml
                    echo "</settings>" >> ~/.m2/settings.xml

                    cp backend/data-service/src/main/resources/application-dev.properties backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.flyway.baselineOnMigrate\\s*=\\).*\\$%\\1false%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.deliveryBaseUrl\\s*=\\).*\\$%\\1${DELIVERY_BASE_URL}%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.workspace.path\\s*=\\).*\\$%\\1${WORKSPACE_PATH}%" backend/data-service/src/main/resources/application.properties

                    cp backend/crawler-service/src/main/resources/application-dev.properties backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.workspace.path\\s*=\\).*\\$%\\1${WORKSPACE_PATH}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.repository.github.token\\s*=\\).*\\$%\\1${GITHUB_TOKEN}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.repository.origin.url\\s*=\\).*\\$%\\1${ORIGIN_URL}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.repository.origin.branch\\s*=\\).*\\$%\\1${ORIGIN_BRANCH}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.schedule.origin\\s*=\\).*\\$%\\1${CRAWLER_FETCH_DELAY_MS}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.schedule.component\\s*=\\).*\\$%\\1${CRAWLER_FETCH_DELAY_MS}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(logging.level.org.slf4j\\s*=\\).*\\$%\\1${DEBUG_LEVEL}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(logging.level.it.bz.opendatahub.webcomponents\\s*=\\).*\\$%\\1${DEBUG_LEVEL}%" backend/crawler-service/src/main/resources/application.properties

                    cp backend/delivery-service/src/main/resources/application-dev.properties backend/delivery-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/delivery-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/delivery-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/delivery-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.workspace.path\\s*=\\).*\\$%\\1${WORKSPACE_PATH}%" backend/delivery-service/src/main/resources/application.properties
                '''
            }
        }
        stage('Test') {
            steps {
                sh 'cd backend && mvn -B -U clean test verify'
            }
        }
        stage('Deploy') {
            steps{
                sh 'cd backend && mvn install -Dmaven.test.skip=true -B -U'
                sh 'cd backend/data-service && mvn -Dmaven.test.skip=true -B -U tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT_API} -Dmaven.tomcat.server=testServer -Pdeploy -Dmaven.tomcat.path=/'
                /*  We no longer use the crawler on testing, since we deploy it directly with our CLI script for now!
				 *  sh 'cd backend/crawler-service && mvn -Dmaven.test.skip=true -B -U tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT_API} -Dmaven.tomcat.server=testServer -Pdeploy -Dmaven.tomcat.path=/crawler'
				 */
                sh 'cd backend/delivery-service && mvn -Dmaven.test.skip=true -B -U tomcat:redeploy -Dmaven.tomcat.url=${TESTSERVER_TOMCAT_ENDPOINT_CDN} -Dmaven.tomcat.server=testServer -Pdeploy -Dmaven.tomcat.path=/'
            }
        }
    }
}
