pipeline{
    agent any
    environment{
        registry= 'fentrymartin7/fentrymartin'
        dockerHubCreds = 'dockerhub'
        dockerImage =''
    }
    stages{
        stage('Code quality analysis'){
            steps{
                withSonarQubeEnv(credentialsId: 'sonar-token', installationName: 'sonar'){
                    sh 'mvn -f project2/ verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=030722-VA-SRE_K8s-Cluster'
                }
            }
        }
        stage("Maven clean package"){
            steps{
                sh 'mvn -f project2/pom.xml clean package -Dmaven.test.skip'
            }
        }
        stage("Docker build"){
            steps{
                script{
                    dockerImage = docker.build "$registry"
                }
            }
        }
        stage("Sending image to DockerHub"){
            steps{
                script{
                    docker.withRegistry('', dockerHubCreds){
                        dockerImage.push("$currentBuild.number")
                        dockerImage.push("latest")
                    }
                    
                }
            }
        }
        stage("Waiting for approval"){
            steps{
                script{
                    // Prompt, if yes build, if no abort
                    try {
                        timeout(time: 1, unit: 'MINUTES'){
                            approved = input message: 'Deploy to production?', ok: 'Continue',
                                parameters: [choice(name: 'approved', choices: 'Yes\nNo', description: 'Deploy this build to production')]
                            if(approved != 'Yes'){
                                error('Build not approved')
                            }
                        }
                    } catch (error){
                        error('Build not approved in time')
                    }
                }
            }
        }
        stage("Deploy to production"){
            steps{
                script{
                    withAWS(credentials: 'aws-creds', region: 'us-east-1'){
                        sh 'curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"'
                        sh 'chmod u+x ./kubectl'
                        sh 'aws eks update-kubeconfig --name k8s-cluster'
                        sh './kubectl get pods -n k8s-cluster'
                        sh "echo $registry:$currentBuild.number"
                        sh "./kubectl set image -n k8s-cluster deployment/card-shop-deployment card-shop-container=$registry:$currentBuild.number"
                    }
                }
            }
        }
    }
    // post{
        // always{
        //     echo "========always========"
        // }
        // success{
        //     echo "========pipeline executed successfully ========"
        // }
        // failure{
        //     echo "========pipeline execution failed========"
        // }
    // }
}