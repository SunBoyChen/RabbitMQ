package com.lianzi.text3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者代码
 */
public class ReceiveLogsDirect1 {
    private  static  final  String EXCHANG_NAME= "direct_logs";

    //路由关键字
    private static final String[] routingKeys = new String[]{"info" ,"warning"};

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANG_NAME,"direct");  //注意是direct

        //获取随机队列
        String queue = channel.queueDeclare().getQueue();


    }
}
