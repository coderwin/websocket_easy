package com.example.websocketeasy.websocket;

import jakarta.websocket.EncodeException;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(
    value = "/chat/{username}",
    decoders = {MessageDecoder.class},
    encoders = {MessageEncoder.class}
)

public class ChatEndpoint {

    private Session session;
    private static Set<ChatEndpoint> chatEndpointSet = new CopyOnWriteArraySet<>();
    private static HashMap<String, String> users = new HashMap<>();


    // 3-way-handshaek로 먼저 통신
    @OnOpen
    public void onOpen(Session session, @PathVariable String username) {

        this.session = session;
        chatEndpointSet.add(this);
        users.put(session.getId(), username);

        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        // client로 메시지 전달
        broadcast(message);


    }



    // 웹소켓 통신으로 메시지 전달
    @OnMessage
    public void onMessage(Session session, Message message) {

        message.setFrom(users.get(session.getId()));
        broadcast(message);
    }

    // 통신 닫기
    @OnClose
    public void onClose(Session session) {

        chatEndpointSet.remove(this);

        Message message = new Message();
        message.setFrom(users.get(session.getId()));
        message.setContent("Disconnected!");
        broadcast(message);

    }

    // 에러 발생
    @OnError
    public void onError(Session session, Throwable throwable) {


    }

    private void broadcast(Message message) {
        chatEndpointSet.forEach((endpoint) -> {
            synchronized (endpoint) {
                try {
                    endpoint.session.getBasicRemote()
                            .sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }






}
