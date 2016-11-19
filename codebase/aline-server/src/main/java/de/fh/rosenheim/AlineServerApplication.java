package de.fh.rosenheim;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AlineServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AlineServerApplication.class, args);
    }

    @Bean
    /**
     * Allows the inspection of the h2 database via browser
     */
    public ServletRegistrationBean h2servletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
        registration.addUrlMappings("/console/*");
        return registration;
    }
}
