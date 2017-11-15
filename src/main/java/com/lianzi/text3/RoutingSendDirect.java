package com.lianzi.text3;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 使用采用路由的方式对不同的消息进行过滤
 */
public class RoutingSendDirect {
    private  static  final  String EXCHANG_NAME= "direct_logs";

    //路由关键字
    private static final String[] routingKeys = new String[]{"info" ,"warning", "error"};

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANG_NAME,"direct");  //注意是direct
        //发送信息
        for (String routingKey : routingKeys){
            String message = "发送消息" + routingKey;
            channel.basicPublish(EXCHANG_NAME,routingKey,null,message.getBytes());
            System.out.println(message);
        }
        channel.close();
        connection.close();
    }

}
