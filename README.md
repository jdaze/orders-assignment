# orders-assignment

## How to start the service with the database
```bash
  docker compose up -d
```
## Tests
```bash
  ./gradlew test
```

## Postman collection
You can find postman collection with all requests in **postman-collections** directory

## API documentation
URL: http://localhost:8080/swagger-ui/index.html

OpenAPI YAML: http://localhost:8080/v3/api-docs.yaml

## Questions and answers

**1. You do not need to add authentication to your web service, but propose a protocol / method and
justify your choice.**

Currently I'm using fake authentication mechanism which always authenticates as default user from the db.
What we can do:
- TLDR; instead of keeping users in our database, I'd use external provider + OIDC spring security configuration, eg. Amazon Cognito
- we need to store user passwords as hash with salt 
- we can setup basic authentication and create proper filter to authentication
- add some public login endpoint which will generate JWT token and we can replace DefaultUserAuthenticationFilter with JWTAuthenticationFilter
- we can setup OAuth2 with Keycloak, Amazon Cognito or other providers
- 

**2. How can you make the service redundant? What considerations should you do?**
- service should be stateless
- load balancing with autoscaling
- db replicas
- health checks, monitoring and alarms
- add observability (logs, metrics, traces)