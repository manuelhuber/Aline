package de.fh.rosenheim;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
public class AlineServerApplication {

    @RequestMapping("/foo")
    public String foo() {
        return "foo";
    }


    public static void main(String[] args) {
        SpringApplication.run(AlineServerApplication.class, args);
    }
}
