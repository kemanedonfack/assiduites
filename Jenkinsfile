pipeline {
    agent any

    stages {
        
        stage('Build & Package spring app') {
            sh 'mvn clean '
            sh 'mvn install -DskipTests '
        }
    }
}
