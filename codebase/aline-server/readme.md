##Development:

##### Layers
`Repositories - Database access  
Services - Business logic  
Controller - HTTP endpoints (and authorization)`  
Layers should not be skipped. A controller should use a repository

##### Lombok
This Project uses Lombok which auto-generates boilerplate code (like getter and setter). The dependency is in the gradle file but you also need a IDE plugin. There is one for every major IDE and a quick google search will give you all the infos you need to get Lombok running for your IDE of joice. 

##### Database
Spring Boot uses by default (if nothing else is configured) a embedded H2 database.
You can access the DB console via `${serveraddress}/${contextPath}/console `. Default JDBC URL is `jdbc:h2:mem:testdb`, user is `sa` with empty password

##### Swagger
Url: ${serveraddress}/${contextPath}/swagger-ui.html

##### Project structure:



    |-- java
        |-- AlineServerApplication.java  <- Main
        |-- controller                   <- Rest Endpoints   
        |-- domain                       <- Domain models
        |-- model                        <- Technical models
        |-- repository                   <- Data access
        |-- security                     <- Authentication/Authorication
        |-- service                      <- Buisness Logic
    |-- resources
        |-- application.yml              <- Configuration
The content of "security" could have been split between the other packages (i.e. services under "service" etc.) but I liked having all of the security code separate from the domain / business code. Only exception is the AuthenticationController since Swagger wants all of the controllers to be in the same package. 

##Production:
- Change the secret in the properties
- Add CORS urls as necessary in the corsConfigurer function
- The PersistenceConfiguration adds dummy data - You probably don't want that going live
- Check the WebSecurityConfig
