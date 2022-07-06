package com.xxxx.mail;

import com.xxxx.server.pojo.MailConstants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Queue;

/**
 * @author Mr.Z
 * @title: YebApplication
 * @projectName yeb
 * @description: 启动类
 * @date 2022/4/1216:20
 */
@SuppressWarnings({"all"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MailApplication {
    public static void main(String[] args) {

        SpringApplication.run(MailApplication.class,args);
    }
    @Bean
    public Queue queue(){
        return new Queue(MailConstants.MAIL_QUEUE_NAME);
    }
}
