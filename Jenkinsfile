pipeline {
    agent any

    environment {
        AWS_REGION = "ap-south-1"
        AWS_ACCOUNT_ID = "091113170910"

        BACKEND_ECR = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/ib-backend"
        FRONTEND_ECR = "${AWS_ACCOUNT_ID}.dkr.ecr.${AWS_REGION}.amazonaws.com/ib-frontend"
    }

    stages {

        stage('Checkout Code') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/omkarkrishna/Capstone_InvestmentBankingDealPipeline.git'
            }
        }

        // 🔹 NEW: Build & Test Backend (Required for SonarQube)
        stage('Build & Test Backend') {
            steps {
                sh '''
                cd InvestmentBanking-dealpipeline
                mvn clean verify
                '''
            }
        }

        // 🔹 NEW: SonarQube Analysis
        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh '''
                    cd InvestmentBanking-dealpipeline
                    mvn sonar:sonar \
                      -Dsonar.projectKey=deal-pipeline \
                      -Dsonar.projectName="Investment Banking Deal Pipeline"
                    '''
                }
            }
        }

        // 🔹 OPTIONAL BUT RECOMMENDED: Quality Gate
        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Backend Image') {
            steps {
                sh '''
                docker build -t ib-backend ./InvestmentBanking-dealpipeline
                '''
            }
        }

        stage('Build Frontend Image') {
            steps {
                sh '''
                docker build -t ib-frontend ./frontend
                '''
            }
        }

        stage('Login to ECR & Push Images') {
            steps {
                withCredentials([
                    string(credentialsId: 'aws-access-key', variable: 'AWS_ACCESS_KEY_ID'),
                    string(credentialsId: 'aws-secret-key', variable: 'AWS_SECRET_ACCESS_KEY')
                ]) {
                    sh '''
                    aws ecr get-login-password --region $AWS_REGION |
                    docker login --username AWS --password-stdin $AWS_ACCOUNT_ID.dkr.ecr.$AWS_REGION.amazonaws.com

                    docker tag ib-backend:latest $BACKEND_ECR:latest
                    docker push $BACKEND_ECR:latest

                    docker tag ib-frontend:latest $FRONTEND_ECR:latest
                    docker push $FRONTEND_ECR:latest
                    '''
                }
            }
        }
    }

    post {
    always {
        junit allowEmptyResults: true,
              testResults: 'InvestmentBanking-dealpipeline/target/surefire-reports/*.xml'
    }
}

}
