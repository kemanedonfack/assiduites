pipeline {
    environment {
        def gitCommit = sh(returnStdout: true, script: 'git rev-parse --short HEAD').trim()
        def artifactName = "assiduite-${gitCommit}.jar"
        def s3buckect = "s3://jenkins-bucket-for-artifact-devsecops-pipeline"
        def ecrRepo="625243961866.dkr.ecr.eu-north-1.amazonaws.com/assiduite"
        def imageTag="${ecrRepo}:${gitCommit}"
        def imageLatest="${ecrRepo}:latest"
        def region="eu-north-1"
        def ip="13.49.46.99"
    }

    agent any

    stages {
        stage('SonarQube Analysis') {
            steps{
                withSonarQubeEnv('SonarQube-server') {
                    sh '''mvn clean verify sonar:sonar \
                      -Dsonar.projectKey=devsecops-pipeline \
                      -Dsonar.host.url=http://16.171.112.206:9000 \
                      -Dsonar.login=sqp_552e69feffb678c365381e5ef2eb1222e29e094a'''
                }
            }
        }
        
        stage('Unit Tests') {
           steps {
              sh 'mvn test'
           }
        }
        
         stage('Build & Package & store artefact to s3') {
            steps {
              sh 'mvn clean'
              sh 'mvn install -DskipTests '
              sh 'aws s3 cp target/*.jar ${s3buckect}/${artifactName}'
            }
         }
        
        stage('Build Docker image') {
           steps {
             sh 'docker build -t assiduites:${gitCommit} .'
           }
        }

        stage('Vulnerability scan & copy report to s3') {
           steps {
              //sh 'trivy image --format template --template "@/usr/local/share/trivy/templates/html.tpl" -o report.html --no-progress --exit-code 1 --severity HIGH,CRITICAL lugar2020/assiduites:${gitCommit}'
              sh 'trivy image --format template --template "@/usr/local/share/trivy/templates/html.tpl" -o report-${gitCommit}.html --no-progress --exit-code 0 --severity MEDIUM,HIGH,CRITICAL assiduites:${gitCommit}'
              sh 'aws s3 cp report-${gitCommit}.html ${s3buckect}/'
           }
        }

        stage('owasp zap scan & save report') {
            steps {
                sh ''' final_tag=$(echo ${gitCommit} | tr -d ' ')
                    sed -i "s/docker_tag/$final_tag/g" docker-compose.yml
                '''
                sh 'bash zap.sh'
                sh 'aws s3 cp zap_report-${gitCommit}.html ${s3buckect}/'
            }
        }

        stage('Push image to ecr') {
           steps {
              sh 'aws ecr get-login-password --region ${region} | docker login --username AWS --password-stdin ${ecrRepo}'
              sh 'docker tag assiduites:${gitCommit} ${imageTag}'
              sh 'docker tag assiduites:${gitCommit} ${imageLatest}'
              sh 'docker push ${imageTag}'
              sh 'docker push ${imageLatest}'
           }
        }
        
    }
}
