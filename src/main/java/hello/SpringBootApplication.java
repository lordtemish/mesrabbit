package hello;

import hello.Listener.SimpleListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;



/**
 * Created by lordtemich on 12/5/17.
 */
@EnableRabbit
@EnableAutoConfiguration
@org.springframework.boot.autoconfigure.SpringBootApplication
public class SpringBootApplication {
    public final static String MESSAGE_NAME="simple queue";

    @Bean
    Queue queue(){
        return new Queue(MESSAGE_NAME,false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange("spring-boot-exchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(MESSAGE_NAME);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter adapter){
        SimpleMessageListenerContainer container=new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(MESSAGE_NAME);
        container.setMessageListener(adapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(SimpleListener receiver){
        return new MessageListenerAdapter(receiver,"receiveMessage");
    }

    public static void main(String[] args){
        SpringApplication.run(SpringBootApplication.class,args);
    }
}
