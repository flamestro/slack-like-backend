package de.tub.ise.anwsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ServletComponentScan
@ComponentScan("de.tub.ise.anwsys")
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
