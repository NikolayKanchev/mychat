package com.project.mychat.models;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig extends AbstractWebSocketMessageBrokerConfigurer
{
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/chat").withSockJS();
        registry.addEndpoint("/listUsers").withSockJS();
        registry.addEndpoint("/visible").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        registry.setApplicationDestinationPrefixes("/app").enableSimpleBroker( "/topic", "/queue");
    }
}

//region simple WS try
//package com.project.mychat.models;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.concurrent.CopyOnWriteArrayList;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer
//{
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry)
//    {
//        registry.addHandler(new ChatHandler(),"/chat");
//    }
//
//    class ChatHandler extends TextWebSocketHandler
//    {
//
//        private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
//
//        @Override
//        public void afterConnectionEstablished(WebSocketSession session) throws Exception
//        {
//            sessions.add(session);
//
//        }
//
//        @Override
//        protected void handleTextMessage(WebSocketSession session, TextMessage message)
//        {
//            for (WebSocketSession s: sessions)
//            {
//                try
//                {
//                    s.sendMessage(message);
//
//                }catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//}
//endregion