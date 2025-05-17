package com.example.products.consumer;

import com.example.common.entity.EnumUtil.EventType;
import com.example.common.entity.MessageEvent;
import com.example.products.consumer.processor.CategoryProcessor;
import com.example.products.consumer.processor.ProductProcessor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConsumer {

    private static final Logger logger = LoggerFactory.getLogger( ProductConsumer.class );
    private final ProductProcessor productProcessor;
    private final CategoryProcessor categoryProcessor;

    @KafkaListener( topics = { "${topic-name}" } )
    public void handleProductEvent( @Payload final MessageEvent messageEvent ) {
        logger.info( "Message received: {}", messageEvent.getEventName() );
        EventType eventType = messageEvent.getEventName();
        switch ( eventType ) {
            case CREATE_PRODUCT -> {
                productProcessor.store( messageEvent.getPayload() );
            }
            case UPDATE_PRODUCT -> {
                productProcessor.refresh( messageEvent.getPayload() );
            }
            case DELETE_PRODUCT -> {
                productProcessor.delete( messageEvent.getPayload() );
            }
            case UPDATE_PRODUCT_STOCK -> {
                productProcessor.updateProductsStock( messageEvent.getPayload() );
            }
            case CREATE_CATEGORY -> {
                categoryProcessor.store( messageEvent.getPayload() );
            }
            case DELETE_CATEGORY -> {
                categoryProcessor.delete( messageEvent.getPayload() );
            }
        }
    }
}
