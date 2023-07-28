def call ()
{
     try {
withAWS(credentials: 'Tweeny_IAM', region: 'ap-south-1') {
        sh ('aws eks update-kubeconfig --name t-cluster-1 --region ap-south-1')
        sh 'helm upgrade --install pluto-frontend ./pluto_app_config/pluto_helm_chart/ --set frontend.enabled=true --set frontend.image.repository=927491280662.dkr.ecr.ap-south-1.amazonaws.com/pluto-frontend'
}
}
catch(e) {}
}
