package com.example.products.consumer;

import com.example.common.entity.EnumUtil.EventType;
import com.example.common.entity.MessageEvent;
import com.example.common.service.messaging.MessagingConsumer;
import com.example.products.consumer.processor.CategoryProcessor;
import com.example.products.consumer.processor.ProductProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class ProductConsumer implements MessagingConsumer {

    @Value("${messaging.destination.products}")
    private String destination;

    private final ProductProcessor productProcessor;
    private final CategoryProcessor categoryProcessor;

    @Override
    public String destination() {
        return destination;
    }

    @Override
    public void consume(MessageEvent messageEvent) {
        logger.info("Message received: {}", messageEvent.getEventName());
        EventType eventType = messageEvent.getEventName();
        switch (eventType) {
            case CREATE_PRODUCT -> this.productProcessor.store(messageEvent.getPayload());
            case UPDATE_PRODUCT -> this.productProcessor.refresh(messageEvent.getPayload());
            case DELETE_PRODUCT -> this.productProcessor.delete(messageEvent.getPayload());
            case UPDATE_PRODUCT_STOCK -> this.productProcessor.updateProductsStock(messageEvent.getPayload());
            case CREATE_CATEGORY -> this.categoryProcessor.store(messageEvent.getPayload());
            case DELETE_CATEGORY -> this.categoryProcessor.delete(messageEvent.getPayload());
        }
    }
}
