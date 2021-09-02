package com.example.products.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;

@Configuration
public class HibernateConfig {

    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter ( ) {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter( );
        ObjectMapper mapper = converter.getObjectMapper( );
        Hibernate5Module hibernate5Module = new Hibernate5Module( );
        //hibernate5Module.configure(Hibernate5Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true);
        hibernate5Module.configure( Hibernate5Module.Feature.FORCE_LAZY_LOADING, true );
        mapper.registerModule( hibernate5Module );
        mapper.enable( SerializationFeature.INDENT_OUTPUT );
        mapper.setDateFormat( new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ) );
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return converter;
    }
}
