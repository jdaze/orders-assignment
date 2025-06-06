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

**2. How can you make the service redundant? What considerations should you do?**
- service should be stateless
- load balancing with autoscaling
- db replicas
- health checks, monitoring and alarms
- add observability (logs, metrics, traces)


## Assumptions
- Price can be 0 or more and should have at most 2 fractional digits
- Order Products contain original product price + complete price for this product, complete price = product price * quantity
- There's only mysql db. It would be better to keep orders in NOSQL db, like MongoDB because it's immutable (except user email)
- User email can change, order api will return order with updated email
- Create order API can consume duplicated data (multiple entries for the same product) -> all of them will result in single entry and quantities will be summed up (check ProductQuantityMerger)
- There are default products and default user
- Default user is automatically authenticated
- Exception handling is really basic :)
- Integeration tests are using testcontainers, there's only one unit test for ProductQuantityMerger