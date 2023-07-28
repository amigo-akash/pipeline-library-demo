def call ()
{
   git url: 'https://gitlab.com/dev-products1/pluto-poc-backend.git', branch: 'docker', credentialsId: 'my-gitlab-repo-creds'
   try {
withAWS(credentials: 'Tweeny_IAM', region: 'ap-south-1') {
        sh ('aws eks update-kubeconfig --name t-cluster-1 --region ap-south-1')
        sh 'helm upgrade --install pluto-backend ./pluto_app_config/pluto_helm_chart/ --set backend.enabled=true --set backend.image.repository=927491280662.dkr.ecr.ap-south-1.amazonaws.com/pluto-app'
}
}
catch(e) {}
}
