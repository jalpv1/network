# how to run application
* clone repository in folder git clone (https://github.com/jalpv1/network)
* project uses PosgreSql dateBase
* run dbcreate.sql to create dateBase
* run dbinit.sql to add test data in tables.
* Do this commants in project folder:
```

          mvn clean
          mvn install
          mvn spring-boot:run
```
#project structure
### package config
- app configuration, swagger configuration, custom Json parser.
### package controllers
- network controller - operations with whole network.
- node controller - operations with a node.
### package services
business logic layer
### repositories
- database layer
###validation
-classes verifying that Network in consistent state  
## exception
-Custom exceptions
### Entity
-Entity - node
### Queries
-queries to get and modify data in database
