package de.fh.rosenheim.aline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AlineServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlineServerApplication.class, args);
    }

//    /**
//     * Allows the inspection of the h2 database via browser
//     */
//    @Bean
//    public ServletRegistrationBean h2servletRegistration() {
//        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
//        registration.addUrlMappings("/console/*");
//        return registration;
//    }
}
