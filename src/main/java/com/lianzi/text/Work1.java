package com.lianzi.text;
import com.rabbitmq.client.*;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work1 {
    private static final String TASK_QUEUE_NAME="task_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME,true,false,false,null);

        //每次从队列获取的数量
        channel.basicQos(1);

        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body,"utf-8");
                System.out.println("work1收到消息"+message);

                try {
                   // doWork();
                    throw new Exception();
                } catch (Exception e) {
                   channel.abort();
                }finally {
                    System.out.println("work1 done");
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }

            }
        };
        //消息应答
        Boolean autoAck = false;        //是否自动应答,trueautoAck是否自动回复，如果为true的话，每次生产者只要发送信息就会从内存中删除
        channel.basicConsume(TASK_QUEUE_NAME,autoAck,consumer);
    }

    private static void doWork() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            //当线程阻塞时，调用interrupt方法后，该线程会得到一个interrupt异常，可以通过对该异常的处理而退出线程
            Thread.currentThread().interrupt();
        }
    }
}
