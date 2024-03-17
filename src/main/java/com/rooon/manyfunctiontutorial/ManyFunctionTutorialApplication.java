package com.rooon.manyfunctiontutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ManyFunctionTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManyFunctionTutorialApplication.class, args);
    }

}
