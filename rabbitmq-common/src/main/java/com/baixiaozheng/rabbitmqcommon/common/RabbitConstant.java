package com.baixiaozheng.rabbitmqcommon.common;

public class RabbitConstant {

    public static final String EXCHANGE = "demoExchanger";
    public static final String QUEUE = "demoQueue";
    public static final String ROUTING_KEY = "pushToDemo";

    //下面是死信队列的配置
    public static final String DEAD_LETTER_EXCHANGE = "deadLetterExchanger";
    public static final String DEAD_LETTER_QUEUE = "deadLetterQueue";
    public static final String DEAD_LETTER_ROUTING_KEY = "deadLetterRoutingKey";
}
