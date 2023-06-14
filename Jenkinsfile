pipeline {
    environment {
        def gitCommit = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
        def artifactName = "assiduite-${gitCommit}.jar"
    }
    agent any

    stages {

        stage('Unit Tests') {
           steps {
              sh 'mvn test'
           }
        }
        
         stage('Build & Package & store artefact to s3') {
            steps {
              sh 'mvn clean'
              sh 'mvn install -DskipTests '
              sh 'aws s3 cp target/*.jar s3://jenkins-bucket-i-07bb8b9d87698e757/${artifactName}'
            }
         }
        
        stage('Build Docker image') {
           steps {
             sh 'docker build -t lugar2020/assiduites:${gitCommit} .'
           }
        }
        
        stage('Vulnerability scan') {
           steps {
              sh 'trivy image --scanners vuln lugar2020/assiduites:${gitCommit}'
           }
        }
        
    }
}
