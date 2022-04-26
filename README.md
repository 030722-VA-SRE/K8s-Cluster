## K8s-Cluster
## Project Description
Card-shop is an application programming interface that allows users to create an account and add their own collection of basketball trading cards for easy management and access to player statistics. Card-shop is deployed on a Kubernetes cluster and utilizes continuous delivery through the implementation of Jenkins, to ensure quick application update times.

## Technologies Used
- Java
- Maven
- Spring
- JUnit
- Mockito
- JWT
- Docker
- Promtail
- Loki
- Grafana
- Prometheus
- Kubernetes
- Jenkins

## Features
Users can: 
   - login as customer or admin
   - register for a customer account
   - see cards they own

Admin users can: 
   - see all cards
   - add new cards
   - update cards
   - delete cards
   - assign users to a card

## To-do list:
- Add Alerting Rules
- Implement more custom metrics

## Getting Started
- Clone Repository using Git Bash

`git clone https://github.com/030722-VA-SRE/K8s-Cluster.git`

- Install Kubernetes and create deployment
- Install Docker and Jenkins onto EC2 
- Navigate to repository and create deployment
   `kubectl apply -f ./deployment`

## Usage
- Use Postman or web browser to send requests 
* Login
> /auth/login
* Register
> /auth/register
* Get all cards
> /cards
* Get/create/update/delete card by id
> /cards/{id}
* Get card by name
> /cards/{name}
* Get user list
> /users
* Get/create/update/delete user by id
> /users/{id}
* All POST, PUT, and DELETE requests can only be performed when logged in as admin. After login, copy JWT token from response header into Authorization in request header.
* Use grafana to view logs and metrics using Loki and Prometheus
> /grafana

## Contributors
Henry Gay, Fentry Martin, Kaitlyn Moore and Kyle Pfunder
