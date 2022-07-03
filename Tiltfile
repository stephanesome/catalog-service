# Deploy
k8s_yaml(['k8s/deployment.yml', 'k8s/service.yml'])

# Build
docker_build('stephanesome/catalog-service', '.')

# Manage
k8s_resource('catalog-service', port_forwards=['9001'])
