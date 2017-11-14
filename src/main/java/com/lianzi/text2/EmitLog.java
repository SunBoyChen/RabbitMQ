package com.lianzi.text2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 信息发送端(使用交换机接受信息,推送)
 */
public class EmitLog {

    private  static  final  String EXCHANG_NAME= "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANG_NAME,"fanout");  //fanout表示分发，所有的消费者得到同样的队列信息
        //分发信息
        for (int i = 0; i<5 ; i++) {
            String message = "Hello"+ i;
            /**
             *
             */
            channel.basicPublish(EXCHANG_NAME, "", null, message.getBytes("utf-8"));
            System.out.println("发送了"+ i);
        }
        channel.close();
        connection.close();
    }
}
