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
public class EmployeeKafkaProducer {

    private static final Logger log = LoggerFactory.getLogger(EmployeeKafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;


    public void produceMessage(String topic,String message) {
        log.info(String.format("Producing message -> %s", message));
        this.kafkaTemplate.send(topic, message);
    }
}
