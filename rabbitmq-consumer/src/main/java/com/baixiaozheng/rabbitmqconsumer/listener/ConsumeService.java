package com.baixiaozheng.rabbitmqconsumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.baixiaozheng.rabbitmqcommon.common.RabbitConstant;
import com.baixiaozheng.rabbitmqcommon.vo.MessageVo;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class ConsumeService {


    @RabbitListener(queues = RabbitConstant.QUEUE)
    public void receive(MessageVo messageVo, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        try {
            log.info("receive message : {}", JSONObject.toJSONString(messageVo));
            if (messageVo.getMessage().startsWith("2")) { //通过发送的消息是否以2开头来模拟异常
                System.out.println(1 / 0);
            }
            channel.basicAck(deliveryTag, false);
        } catch (Exception e) {
            log.error(e.getMessage());
            try {
                //消息确认失败后，requeue=false，消息不会放回队列，而是会发进入死信队列
                channel.basicNack(deliveryTag, false, false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
