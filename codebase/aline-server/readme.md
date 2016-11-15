##Development:

##### Lombok
This Project uses Lombok which auto-generates boilerplate code (like getter and setter). The dependency is in the gradle file but you also need a IDE plugin. There is one for every major IDE and a quick google search will give you all the infos you need to get Lombok running for your IDE of joice. 

##### Database
Spring Boot uses by default (if nothing else is configured) a embedded H2 database.
You can access the DB console via `${serveraddress}/${contextPath}/console `. Default JDBC URL is `jdbc:h2:mem:testdb`, user is `sa` with empty password

##### Project structure:
- Swagger Url: ${serveraddress}/${contextPath}/swagger-ui.html


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

##Production:
- Change the secret in the properties
- Add CORS urls as necessary in the corsConfigurer function
- The PersistenceConfiguration adds dummy data - You probably don't want that going live
- Check the WebSecurityConfig