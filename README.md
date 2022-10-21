# Programvaruproduktion 2022 Grupp 5 <!-- omit in toc -->  

This is a project made for the course Software Engineering at Åbo Akademi University it is a [PoS system](https://en.wikipedia.org/wiki/Point_of_sale) and it consists of the following parts.  

- Client handles the user interface.  
- Card Reader simulates card transactions.  
- Cash Box simulates a cash box.  
- Backend handles data persistence and client integration with the Customer Register and Product Catalog.  
- Customer Register handles customer information.  
- Product Catalog handles product information.  

![System diagram](docs/diagram/pvpgrp5%20system%20diagram.png)

## Table of Contents <!-- omit in toc -->  

- [Setup](#setup)
    - [Requirements](#requirements)
    - [Quick Start](#quick-start)
    - [Configuration](#configuration)

## Setup  

### Requirements  

- Java 8/10 for the [external jars](/Jars/).  
- Client and Backend was made with Java 18 so you should use that to be sure it works correctly.  
- PostgreSQL database (tested with v14.5).

### Quick Start  

1. Setup a PostgreSQL database wherever you like, you can use the [provided docker compose](/backend/database/compose.yaml) if you have docker and you can use `docker compose up` to start it.    
2. Modify the database settings (this step can be ignored if you use the provided docker compose or same settings).  
    2.1 Modify the database info in the [application.yaml](/backend/src/main/resources/application.yaml) with your database info if you plan on starting the backend with an IDE.
    2.2 Create a application.yaml file in the same directory as the backend-1.0.0.jar and it will override default settings when you run the jar file.  
3. Start all the [external jars](/Jars/), you can use the [provided batch file](/Jars/startall.bat) by first editing it and setting the location to java8/10 then you can run it and it should start all of them.
4. Start the client and backend, there are multiple ways to start these you can:  
    4.1 Load the project with an IDE and maven should install all dependencies and then you can run the main classes [BackendApplication.java](/backend/src/main/java/app/BackendApplication.java) and [AdminClientApplication.java](/client/src/main/java/app/AdminClientApplication.java).  
    4.2 Start the provided jar files using Java 18 using `<path to Java 18 java.exe> -jar <jar name>.jar`.  

### Configuration  

[Backend](/docs/backend.md)  
[Client](/docs/client.md)  