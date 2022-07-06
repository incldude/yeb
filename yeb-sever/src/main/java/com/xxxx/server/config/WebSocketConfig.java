package com.xxxx.server.config;

import com.xxxx.server.config.security.component.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Mr.Z
 * @title: WebSocketConfig
 * @projectName yeb
 * @description: TODO
 * @date 2022/5/2715:38
 */
@SuppressWarnings({"all"})
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private UserDetailsService userDetailsServicel;
    /**
     * 添加Endpoints 这样在网页里面就可以通过webSocket 链接服务
     * 也就是我们配置webSocket的服务地址，并且可以指定是否使用 socketJS
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 1.将ws/ep注册为stomp的端点
         *
         * 用户链接此端点就可以使用websocket通讯，只是socketJS
         *"*"允许跨域
         */
        registry.addEndpoint("/ws/ep").setAllowedOrigins("*").withSockJS();

    }

    /**
     * 输入通道的配置
     *
     * @param registration
     */
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                //判断是不是一个链接如果是获取token 并设置用户对象
                if (StompCommand.COMMIT.equals(accessor.getCommand())) {
                    String token = accessor.getFirstNativeHeader("Auth-Token");
                    if (!StringUtils.isEmpty(token)) {
                        String authToken = token.substring(tokenHead.length());
                        String username = jwtTokenUtils.getUserNameFromTokem(authToken);
                        //token 存在用户名
                        if (!StringUtils.isEmpty(username)){
                            //登录
                            UserDetails userDetails = userDetailsServicel.loadUserByUsername(username);
                            //验证token有效
                            if (jwtTokenUtils.validateToken(authToken,userDetails)){
                                UsernamePasswordAuthenticationToken authenticationToken =
                                        new UsernamePasswordAuthenticationToken
                                                (userDetails, null, userDetails.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                                accessor.setUser(authenticationToken);
                            }
                        }
                    }
                }
                return message;
            }
        });
    }


    /**
     * 配置消息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //配置代理，可以配置多个 配置代理目的地前缀为/queue,可以在配置与上向客户短推动消息
        registry.enableSimpleBroker("/queue");
    }
}
