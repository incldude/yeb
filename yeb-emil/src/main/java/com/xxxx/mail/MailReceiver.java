package com.xxxx.mail;

import com.rabbitmq.client.Channel;
import com.xxxx.server.pojo.Employee;
import com.xxxx.server.pojo.MailConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Date;

/**
 * @author Mr.Z
 * @title: MailReceiver
 * @projectName yeb
 * @description: TODO
 * @date 2022/5/2516:44
 */
@SuppressWarnings({"all"})
@Component
public class MailReceiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailReceiver.class);

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = MailConstants.MAIL_QUEUE_NAME)
    public void handler(Message message, Channel channel) {
        Employee employee = (Employee) message.getPayload();
        MessageHeaders headers = message.getHeaders();
        //消息序号
        long tag = ((long) headers.get(AmqpHeaders.DELIVERY_TAG));
        String msgId = (String) headers.get("spring_returned_message_correlation");
        HashOperations hashOperations = redisTemplate.opsForHash();


        try {
if (hashOperations.entries("mail_log").containsKey(msgId)){
    LOGGER.error("消息已被消费",msgId);
    /**
     * 手动确认
     * tag:消息序号
     * multiple:是否确认多条
     */
    channel.basicAck(tag,false);
}
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg);
            //发件人
            helper.setFrom(mailProperties.getUsername());
            //收件人
            helper.setTo(employee.getEmail());
            System.out.println(employee.getEmail());
            //主题
            helper.setSubject("入职欢迎邮件");
            //发送日期
            helper.setSentDate(new Date());
            //邮件内容
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("posName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJoblevel().getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            helper.setText(mail, true);
            //发送
            javaMailSender.send(msg);
            LOGGER.info("邮件发送成功");
            //将消息id 存入redis
            hashOperations.put("mail_log",msgId,"ok");
            //手动确认消息
            channel.basicAck(tag,false);
        } catch (Exception e) {
            /**
             *  手动确认多条
             *  tag：消息序号
             *  multiple:是否确认多条
             *  requeue:是否退回队列
             */
            try {
                channel.basicNack(tag,false,true);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            LOGGER.error("邮件发送======>{}", e.getMessage());
        }

    }


}
