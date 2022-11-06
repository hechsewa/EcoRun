pipeline {
    agent any

    stages {
        stage('Build'){
            steps {
                echo 'Building...'
                sh './gradlew build'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
                sh './gradlew test'
            }
            post {
                always {
                    //junit "core/build/reports/tests/**/*.html"
                    junit "core/build/test-results/**/TEST*.xml"
                }
            }
        }
    }
}