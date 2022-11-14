def deployMyApp(service,env,tag,repo,ip_address,pem_file) {
                pushTOECR(repo,tag)
                deployToInstance(repo,tag,ip_address,pem_file)
                echo 'Deployed ${service} app to the ${env} environment successfully'
            }

def pushTOECR(repo,tag) {
  withEnv(["AWS_ACCESS_KEY_ID=${env.AWS_ACCESS_KEY_ID}", "AWS_SECRET_ACCESS_KEY=${env.AWS_SECRET_ACCESS_KEY}", "AWS_DEFAULT_REGION=${env.AWS_DEFAULT_REGION}"]) {
     sh 'docker login -u AWS -p $(aws ecr get-login-password --region eu-west-2) 927491280662.dkr.ecr.eu-west-2.amazonaws.com'
     sh 'docker build -t 927491280662.dkr.ecr.eu-west-2.amazonaws.com/"${repo}":"${tag}" .'
     sh 'docker push 927491280662.dkr.ecr.eu-west-2.amazonaws.com/"${repo}":"${tag}"'

  }
}


def deployToInstance(repo,tag,ip_address,pem_file)
{
          sh 'ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/"${pem_file}" ec2-user@ec2-"${ip_address}".eu-west-2.compute.amazonaws.com aws ecr get-login-password --region eu-west-2 | docker login --username AWS --password-stdin 927491280662.dkr.ecr.eu-west-2.amazonaws.com'
          sh 'ssh -o StrictHostKeyChecking=no -i /var/lib/jenkins/"${pem_file}" ec2-user@ec2-"${ip_address}".eu-west-2.compute.amazonaws.com docker run -d -p 8081:4000 927491280662.dkr.ecr.eu-west-2.amazonaws.com/"${repo}":"${tag}"'
}
