# e-store
## Software architecture mini project 2

Team members: Munkh-Undral Erdenebayar, Tumurjin Arildii, Saruulgun Chinzorigt

Microservices:
- Account service
- Product service
- Order service
- Payment service
- Transaction service(s)
- Shipping service
- Cloud Gateway
- Cloud registry (Eureka)

Eureka is used as Load balancer, service discovery. All credentials are stored as Kubernetes secret. All other non-credential configurations are stored in Kubernetes Configmap.

## Configure/Deploy on Kubernetes

All Kubernetes configuration files are located in `Springboot-k8s` directory.
Create *Configmaps and Secrets*: 
```sh
kubectl create -f ./*-secret*.yml
kubectl create -f ./*-configmap.yml
```
Start MySQL dbs:
```sh
kubectl apply -f ./*-mysql.yml
```
Start Springboot applications:
```sh
kubectl apply -f ./*-service.yml
```
Start Eureka server:
```sh
kubectl apply -f service-registry.yml
```
Start config server, cloud gateway, hystrix dashboard:
```sh
kubectl apply -f cloud-gateway.yml
kubectl apply -f config-server.yml
kubectl apply -f hystrix-dashboard.yml
```

Check status:
```sh
kubectl get all
```

## Docker

All docker images are pushed to https://hub.docker.com/u/undral23. 

Rebuild docker images from source code:
```sh
cd <PROJECT>
mvn clean -DskipTests spring-boot:build-image -Dspring-boot.build-image.imageName=<NAME>
```

## Test

Test API using port-forward by forwarding API gateway port to local machine port.

```sh
kubectl port-forward service/cloud-gateway-svc 9191:80
```

To go to Eureka dashboard, run command below and browse localhost:8761 from your browser.
```sh
kubectl port-forward service/eureka-lb 8761:80
```
Test API services:
```sh
curl -X GET http://localhost:9191/products -H 'content-type: application/json'
```


