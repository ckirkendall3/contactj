package com.ckirkendall3.contacts.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Contacts Spring boot application
 */
@ComponentScan("com.ckirkendall3.contacts")
@SpringBootApplication
public class ContactsApp {

    /**
     * Application entry point.
     *
     * @param args application command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ContactsApp.class, args);
    }
}
