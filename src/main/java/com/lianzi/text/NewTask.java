package com.lianzi.text;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//分发消息
public class NewTask {
    private static final String TASK_QUEUE_NAME="task_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);
        //分发消息
        for (int i = 0; i < 10; i++ ){
            String message = "Hello RabbitMQ" + i;
            /**
             *  发送消息到队列中
             *  第一个参数为交换机名称
             *  第二个参数为队列映射的路由key
             *  第三个参数为消息的其他属性
             *  第四个参数为发送信息的主体
             */
            channel.basicPublish("",TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("发送了的"+i);
        }
        channel.close();
        connection.close();
    }

}
