package com.example.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan( { "com.example" } )
@EnableJpaRepositories( basePackages = { "com.example.products.repository" } )
@EnableKafka
@EnableEurekaClient
public class ProductApp {
    public static void main ( String[] args ) {
        SpringApplication.run( ProductApp.class, args );
    }

}
