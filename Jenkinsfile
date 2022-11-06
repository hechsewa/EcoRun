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
                    junit "build/reports/tests/**/*.html"
                }
            }
        }
    }
}