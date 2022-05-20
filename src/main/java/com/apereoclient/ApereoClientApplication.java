package com.apereoclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring boot middle-man service that connects non-spring applications and other services
 * to Central Authentication Service (CAS) implemented by Apereo. In this capacity
 * REST endpoints are offered.<br>
 * The service is also used as SSO (Single-Sign-On and Single-Sing-Out) platform. <br>
 *
 * @author Kamen Nikolov & Svetomir Shepelyaviy
 * @version 1.0
 * @implNote Created on 05.2022
 */
@SpringBootApplication
public class ApereoClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApereoClientApplication.class, args);
    }

    // TODO Put versions on Dependencies or download the libraries
}
