pipeline {
    agent any

    stages {
        
        stage('Build & Package spring app') {
            steps {
               sh 'mvn clean'
               sh 'mvn install -DskipTests '
            }

        }

        stage('Unit Tests') {
           steps {
              sh 'mvn test'
           }
        }
    }
}
