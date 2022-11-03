pipeline {
    agent any

    stage('Test') {
        steps {
			echo 'Testing..'	script {
    				
    		script {
	            sh './gradlew test'
            }
        }
    }

}