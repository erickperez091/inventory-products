package com.example.products.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket usersApi ( ) {
        return new Docket( DocumentationType.SWAGGER_2 )
                .apiInfo( usersApiInfo( ) )
                .select( )
                .paths( PathSelectors.regex( "/(product|category)/v1.*" ) )
                .apis( RequestHandlerSelectors.any( ) )
                .build( )
                .useDefaultResponseMessages( false );
    }

    private ApiInfo usersApiInfo ( ) {
        return new ApiInfoBuilder( )
                .title( "Inventory Microservice System" )
                .version( "1.0" )
                .license( "Apache License Version 2.0" )
                .build( );
    }

}
