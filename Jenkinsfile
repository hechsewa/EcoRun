pipeline {
    agent any

    stage('Test') {
        steps {
			echo 'Testing..'
    				
    		script {
	            sh './gradlew test'
            }
        }
    }

}