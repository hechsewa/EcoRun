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
                    junitReporter: {
                        outputDir: 'test-reports',
                        outputFile: 'unit-test-report.xml',
                        useBrowserName: false,
                        xmlVersion: null
                    }
                    junit "core/build/reports/tests/**/*.html"
                }
            }
        }
    }
}