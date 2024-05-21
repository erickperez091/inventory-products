package com.example.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@ComponentScan( { "com.example" } )
@EnableJpaRepositories( basePackages = { "com.example.products.repository" } )
@EnableKafka
@EnableDiscoveryClient
public class ProductApp {
    public static void main( String[] args ) {
        SpringApplication.run( ProductApp.class, args );
    }

}
