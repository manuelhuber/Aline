####Development:

This Project uses Lombok which auto-generates boilerplate code (like getter and setter). The dependency is in the gradle file but you also need a IDE plugin. There is one for every major IDE and a quick google search will give you all the infos you need to get Lombok running for your IDE of joice. 

####Production:
- Change the secret in the properties
- Add CORS urls as necessary in the corsConfigurer function
- The PersistenceConfiguration adds users (for development purposes) - You probably don't want those going live


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
