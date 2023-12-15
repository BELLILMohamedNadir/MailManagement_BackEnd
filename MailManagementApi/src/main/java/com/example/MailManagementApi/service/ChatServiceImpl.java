package com.example.MailManagementApi.service;

import com.example.MailManagementApi.helper_classes.CommentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
@Slf4j
@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    @Lazy
    SimpMessagingTemplate messagingTemplate;

        // Map to store the groups with the variable as key
    private Map<String, Set<String>> subscribers = new HashMap<>();
    private Map<String,String> trackSubscriberTopic=new HashMap<>();


    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        if (trackSubscriberTopic.containsKey(headerAccessor.getSessionId())){
            unsubscribeFromTopic(event.getSessionId());
        }
    }

    @Override
    public void subscribeToTopic(String variable ,String subscriberId,CommentResponse commentResponse) {
        //subscriberId==sessionId

        String topic = "/topic/message/" + variable;

        if (!trackSubscriberTopic.containsKey(subscriberId)){
            trackSubscriberTopic.put(subscriberId,topic);
        }

        subscribers.computeIfAbsent(topic, k -> new HashSet<>());

        Set<String> subscriber=subscribers.get(topic);

        // create a new subscriber map in the topic if it doesn't exist
        if (subscriber == null || subscriber.isEmpty()) {
            subscriber = new HashSet<>();
            subscribers.put(topic, subscriber);
        }

        //add the subscriberId to the subscriber topic
        if (!subscriber.contains(subscriberId)) {
            subscriber.add(subscriberId);
            subscribers.put(topic, subscriber);
        }

        sendToGroup(topic,commentResponse);
    }

    @Override
    public void unsubscribeFromTopic(String subscriberId) {

        String topic=trackSubscriberTopic.get(subscriberId);
        trackSubscriberTopic.remove(subscriberId);
        Set<String> subscriber = subscribers.get(topic);
        if (subscriber != null) {
            subscriber.remove(subscriberId);
            if (subscriber.isEmpty()) {
                // No more subscribers in the topic, remove it from the maps
                subscribers.remove(topic);
            }else {
                //set the new subscriber set to the topic in the map
                subscribers.put(topic, subscriber);
            }
        }
    }

    @Override
    public void sendToGroup(String destination, CommentResponse commentResponse) {

        if (destination!=null)
            messagingTemplate.convertAndSend(destination, commentResponse);
    }

}
