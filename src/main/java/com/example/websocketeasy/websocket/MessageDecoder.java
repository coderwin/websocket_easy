package com.example.websocketeasy.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.DecodeException;
import jakarta.websocket.Decoder;
import jakarta.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String s) throws DecodeException {

        ObjectMapper mapper = new ObjectMapper();

        Message message = null;
        try {
            message = mapper.readValue(s, Message.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return s != null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        Text.super.init(endpointConfig);
    }

    @Override
    public void destroy() {
        Text.super.destroy();
    }
}
