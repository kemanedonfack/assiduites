pipeline {
    environment {
        def gitCommit = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
        def artifactName = "your-app-${gitCommit}.jar"
    }
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
             sh 'ls'
             sh 'aws s3 cp target/your-app.jar s3://jenkins-bucket-i-0121489bb6176b65f/${artifactName}'
           }
        }
    }
}
