package com.lianzi.text2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者1
 */
public class ReceiveLogs1 {
    //交换机
    private  static  final  String EXCHANG_NAME= "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANG_NAME,"fanout");

        //产生随机队列
        String queue = channel.queueDeclare().getQueue();
        //绑定随机队列
        channel.queueBind(queue,EXCHANG_NAME,"");
        //haiwei

    }
}
