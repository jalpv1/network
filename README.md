# how to run application
* clone repository in folder
```
git clone https://github.com/jalpv1/network
```
* install PostgreSql database
* run dbcreate.sql to create database
* run dbinit.sql to add test data in tables.
* Do this commands in project folder:
```

          mvn clean
          mvn install
          mvn spring-boot:run
```
*  swagger - http://localhost:8080/swagger-ui.html#
# project structure

###  config
This package contains configurations for app , swagger , custom JSON parser.
###  controllers
This package contains controllers layer that serves as facade  for entire application.
###  services
this package contains services which constitute
business logic layer.

### repositories
- this package contains repositories implements data access layer

### validation
 classes which verifying that Network is in consistent state  
## exceptions
 Custom exceptions
### entities
 Main Entities of application.
### queries
 queries which use to get and modify data in database

# Design decisions
1. Network is mapped to relational structure using Child-parent model
2. API is deigned to prevent loading entire network into memory for performance reasons
3. Validation is based on Command pattern from GoF
