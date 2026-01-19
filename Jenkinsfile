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

        stage('Build Backend Image') {
            steps {
                sh 'docker build -t ib-backend ./InvestmentBanking-dealpipeline'
            }
        }

        stage('Build Frontend Image') {
            steps {
                sh 'docker build -t ib-frontend ./frontend'
            }
        }

        stage('Push to ECR') {
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
       stage('Deploy to EC2 using Docker Compose') {
    steps {
        withCredentials([
            string(credentialsId: 'aws-access-key', variable: 'AWS_ACCESS_KEY_ID'),
            string(credentialsId: 'aws-secret-key', variable: 'AWS_SECRET_ACCESS_KEY')
        ]) {
            sh '''
            cd /home/ubuntu/Capstone_InvestmentBankingDealPipeline

            aws ecr get-login-password --region ap-south-1 |
            docker login --username AWS --password-stdin 091113170910.dkr.ecr.ap-south-1.amazonaws.com

            docker compose down
            docker compose pull
            docker compose up -d
            '''
        }
    }
}




    }
}
