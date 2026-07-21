package com.example.products.messaging;

import com.example.common.aspect.AddCreatedBy;
import com.example.common.entity.MessageEvent;
import com.example.common.service.messaging.MessagingProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductPublisher {

    @Value("${messaging.destination.products}")
    private String destination;

    private final MessagingProducer messagingProducer;

    @AddCreatedBy(addCreatedAt = true)
    public void sendEvent(MessageEvent messageEvent) {
        messagingProducer.send(destination, messageEvent);
    }

}
