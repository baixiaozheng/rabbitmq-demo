package com.baixiaozheng.rabbitmqproducer.config;

import com.baixiaozheng.rabbitmqcommon.common.RabbitConstant;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    /**
     * 定义一个队列
     * name:队列的名称
     * durable：设置是否持久化。持久化的队列在RabbitMQ服务重启的时候可以保证不丢失相关信息
     *
     * @return
     */
    @Bean
    public Queue demoQueue() {
        //定义队列并绑定死信队列
        return  QueueBuilder.durable(RabbitConstant.QUEUE)
                .deadLetterExchange(RabbitConstant.DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(RabbitConstant.DEAD_LETTER_ROUTING_KEY)
                .build();
    }

    /**
     * 定义交换机
     * name：交换机的名称
     * durable：设置是否持久化。持久化的交换机在RabbitMQ服务重启的时候不会丢失相关信息
     * autoDelete：在所在消费者都解除订阅的情况下自动删除
     */
    @Bean
    public DirectExchange demoExchanger() {
        return new DirectExchange(RabbitConstant.EXCHANGE, true, false);
    }

    @Bean
    public Binding directBinding() {
        return BindingBuilder.bind(demoQueue()).to(demoExchanger()).with(RabbitConstant.ROUTING_KEY);
    }


    /* 下面是定义死信队列 */
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(RabbitConstant.DEAD_LETTER_QUEUE, true);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(RabbitConstant.DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Binding deadLetterBindings() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(RabbitConstant.DEAD_LETTER_ROUTING_KEY);
    }
    /* 死信队列定义结束 */


    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
