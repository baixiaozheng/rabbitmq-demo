package com.baixiaozheng.rabbitmqproducer.controller;

import com.alibaba.fastjson.JSONObject;
import com.baixiaozheng.rabbitmqcommon.common.RabbitConstant;
import com.baixiaozheng.rabbitmqcommon.vo.MessageVo;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


@RestController
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @RequestMapping("send")
    public void send(String message) {
        MessageVo vo = new MessageVo();
        vo.setMessage(message)
                .setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(RabbitConstant.EXCHANGE, RabbitConstant.ROUTING_KEY, JSONObject.toJSON(vo), new CorrelationData(vo.getId()));
    }
}
