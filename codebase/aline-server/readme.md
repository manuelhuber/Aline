##Development:

##### Layers
Repositories - Database access
Services - Business logic  
Controller - HTTP endpoints
Authorization is whenever possible in the controller. More complex authorization is done in the services.

##### Lombok
This Project uses Lombok which auto-generates boilerplate code (like getter and setter). The dependency is in the gradle file but you also need a IDE plugin. There is one for every major IDE and a quick google search will give you all the infos you need to get Lombok running for your IDE of joice. 

##### Database
Spring Boot uses by default (if nothing else is configured) a embedded H2 database.
You can access the DB console via `${serveraddress}/${contextPath}/console `. Default JDBC URL is `jdbc:h2:mem:testdb`, user is `sa` with empty password

##### Swagger
Url: ${serveraddress}/${contextPath}/swagger-ui.html
Use the login endpoint to generate a token. Enter this token at the top right into the api_key field to automatically attach it to every call.

#### Tests
Execute the gradle jacocoTestReport task to generate test coverage. It can be found under  
 `build\reports\jacoco\test\html\index.html`
 
#### Test Data
Dummy data is created in the `PersistenceConfiguration.java` and resets with every server restart.

##### Project structure:

    |-- java
        |-- AlineServerApplication.java  <- main
        |-- controller                   <- HTTP config / endpoints   
        |-- model                        <- domain & technical models
        |-- repository                   <- data access
        |-- security                     <- authentication / authorication
        |-- service                      <- buisness Logic
    |-- resources
        |-- application.yml              <- Configuration

The content of "security" could have been split between the other packages (i.e. services under "service" etc.) but I liked having all of the security code separate from the domain / business code. Only exception is the AuthenticationController since Swagger wants all of the controllers to be in the same package. 

##Production:
- The token received from the login endpoint has to be put in the header of requests with the key `X-Auth-Token`.
- Change the secret in the properties
- Add CORS urls as necessary in the `corsConfigurer` function
- The `PersistenceConfiguration.java` adds dummy data - You probably don't want that going live
- Check the `WebSecurityConfig.java` if everything is up to your standards
- There is no option to create users since most people will want to integrate it with their existing LDAP / whatever 
