pipeline {
    agent any

    stages {

        stage('Unit Tests') {
           steps {
              sh 'mvn test'
           }
        }
        
        stage('Build & Package spring app') {
           steps {
             sh 'mvn clean'
             sh 'mvn install -DskipTests '
           }
        }
    }
}
