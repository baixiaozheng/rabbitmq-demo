package com.baixiaozheng.rabbitmqproducer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class RabbitTemplateConfig implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate() {
        // 设置生产者消息确认
        rabbitTemplate.setConfirmCallback(this::confirm);

        rabbitTemplate.setReturnsCallback(this::returnedMessage);

    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info("message id {}",correlationData.getId());
        if(!ack){
            log.error("message id {} 未送达，cause {}", correlationData.getId(), cause);
            //TODO 相应补偿
        }
    }


    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("returnedMessage is {}", returnedMessage.toString());
        //TODO 补偿
    }
}
