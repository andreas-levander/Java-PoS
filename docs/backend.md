[back to readme](https://github.com/it-teaching-abo-akademi/project-pvp22-grupp-fem#readme)

# Backend  

The backend is made with mainly Spring Boot and handles all the [CRUD](https://en.wikipedia.org/wiki/Create,_read,_update_and_delete) operations for the system.  

## Configuration  

The main configuration file is the [application.yaml](/backend/src/main/resources/application.yaml).  

Here you can set the following:
- Database url, username and password.  
- Port
- Product catalog and Customer register baseurls.  
- spring.jpa.hibernate.ddl-auto will set if the backend will create and delete the database on startup/shutdown. (create-drop) will create on startup and drop on shutdown