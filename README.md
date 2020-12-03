# bankapp
spring boot java banking app

this java app is a maven project. it can be easily converted to gradle project if required. 

this java app is a spring boot app which exposed REST API endpoints for doing CRUD operations on domain model objects for a bank. currently supporting Customer and BankAccount domain objects. 

we have a spring CRUD repository to enable the CRUD operations on the entities or documents. 

we have a REST controller to define the API endpoints for doing GET, POST , PATCH, and DELETE operations on the domain objects. we use @PathVariable and @RequestBody annotations to capture the request input 

the app can be tested with POSTMAN REST API client. and the app currently uses a in-memory mongodb can be evolved to depend on docker image for mysql or mongodb for persistence of the data.

the app can be further enhanced to add spring security using JWT token to secure the REST API endpoints. 
