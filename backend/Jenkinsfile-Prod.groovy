pipeline {
    agent {
        dockerfile {
            dir 'backend'
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    environment {
        POSTGRES_URL = "jdbc:postgresql://postgres-prod.co90ybcr8iim.eu-west-1.rds.amazonaws.com:5432/webcompstore"
        POSTGRES_USERNAME = credentials('webcompstore-prod-postgres-username')
        POSTGRES_PASSWORD = credentials('webcompstore-prod-postgres-password')

        WORKSPACE_PATH = "/var/data/webcomponents-store"
        GITHUB_TOKEN = credentials('webcompstore-github-token')
        ORIGIN_URL = "https://github.com/noi-techpark/odh-web-components-store-origins.git"
        ORIGIN_BRANCH = "master"
        CRAWLER_FETCH_DELAY_MS = 1800000

        DELIVERY_BASE_URL = "https://cdn.webcomponents.opendatahub.bz.it/dist"
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

                    sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.deliveryBaseUrl\\s*=\\).*\\$%\\1${DELIVERY_BASE_URL}%" backend/data-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.workspace.path\\s*=\\).*\\$%\\1${WORKSPACE_PATH}%" backend/data-service/src/main/resources/application.properties

                    sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.workspace.path\\s*=\\).*\\$%\\1${WORKSPACE_PATH}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.repository.github.token\\s*=\\).*\\$%\\1${GITHUB_TOKEN}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.repository.origin.url\\s*=\\).*\\$%\\1${ORIGIN_URL}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.repository.origin.branch\\s*=\\).*\\$%\\1${ORIGIN_BRANCH}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.schedule.origin\\s*=\\).*\\$%\\1${CRAWLER_FETCH_DELAY_MS}%" backend/crawler-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.schedule.component\\s*=\\).*\\$%\\1${CRAWLER_FETCH_DELAY_MS}%" backend/crawler-service/src/main/resources/application.properties

                    sed -i -e "s%\\(spring.datasource.url\\s*=\\).*\\$%\\1${POSTGRES_URL}%" backend/delivery-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.username\\s*=\\).*\\$%\\1${POSTGRES_USERNAME}%" backend/delivery-service/src/main/resources/application.properties
                    sed -i -e "s%\\(spring.datasource.password\\s*=\\).*\\$%\\1${POSTGRES_PASSWORD}%" backend/delivery-service/src/main/resources/application.properties
                    sed -i -e "s%\\(application.workspace.path\\s*=\\).*\\$%\\1${WORKSPACE_PATH}%" backend/delivery-service/src/main/resources/application.properties
                '''
            }
        }
        stage('Install Dependencies & Package Backend Services') {
            steps {
                sh 'cd backend && mvn -B -U clean install test verify package'
            }
        }
        stage('Archive') {
            steps {
                archiveArtifacts artifacts: 'backend/data-service/target/dataservice.war', onlyIfSuccessful: true
                archiveArtifacts artifacts: 'backend/crawler-service/target/crawlerservice.war', onlyIfSuccessful: true
                archiveArtifacts artifacts: 'backend/delivery-service/target/deliveryservice.war', onlyIfSuccessful: true
            }
        }
    }
}
