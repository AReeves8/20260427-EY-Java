package com.skillstorm.profiles;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// this bean will ONLY become part of the application context when we're in the prod profile
@Component
@Profile("prod")
// we're implementing CommandLineRunner to run the code in the run() method when this Bean comes to life
public class ProdClass implements CommandLineRunner {

    // the @Value annotation allows us to draw from a properties file and use those values in a class
    @Value("#{'${duplicate.key}'}")
    private String valueFromPropertiesFile;

    @Override
    public void run(String... args) {
        System.out.println("Now utilizing the *** prod *** profile...");
        System.out.println("The value in the duplicate.key variable is...");
        System.out.println(valueFromPropertiesFile);
    }

}
