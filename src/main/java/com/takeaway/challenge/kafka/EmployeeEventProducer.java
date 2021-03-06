package com.takeaway.challenge.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class EmployeeEventProducer {

    private static final Logger log = LoggerFactory.getLogger(EmployeeEventProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void produceMessage(String topic,String message) {
        log.debug(String.format("Producing message -> {}", message));
        this.kafkaTemplate.send(topic, message);
    }
}
