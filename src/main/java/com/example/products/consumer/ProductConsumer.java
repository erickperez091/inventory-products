package com.example.products.consumer;

import com.example.common.entitty.EnumUtil.EventType;
import com.example.common.entitty.MessageEvent;
import com.example.products.consumer.processor.CategoryProcessor;
import com.example.products.consumer.processor.ProductProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ProductConsumer {

    private static final Logger logger = LoggerFactory.getLogger( ProductConsumer.class );
    private ProductProcessor productProcessor;
    private CategoryProcessor categoryProcessor;

    @Autowired
    public ProductConsumer( ProductProcessor productProcessor, CategoryProcessor categoryProcessor ) {
        this.productProcessor = productProcessor;
        this.categoryProcessor = categoryProcessor;
    }

    @KafkaListener( topics = { "${topic-name}" } )
    public void handleProductEvent( @Payload final MessageEvent messageEvent ) {
        logger.info( "Message received: {}", messageEvent.getEventName() );
        EventType eventType = messageEvent.getEventName();
        switch ( eventType ) {
            case CREATE_PRODUCT: {
                productProcessor.store( messageEvent.getPayload() );
                break;
            }
            case UPDATE_PRODUCT: {
                productProcessor.refresh( messageEvent.getPayload() );
                break;
            }
            case DELETE_PRODUCT: {
                productProcessor.delete( messageEvent.getPayload() );
                break;
            }
            case UPDATE_PRODUCT_STOCK: {
                productProcessor.updateProductsStock( messageEvent.getPayload() );
                break;
            }
            case CREATE_CATEGORY: {
                categoryProcessor.store( messageEvent.getPayload() );
                break;
            }
            case DELETE_CATEGORY: {
                categoryProcessor.delete( messageEvent.getPayload() );
                break;
            }
        }
    }
}
