package com.example.websocketeasy.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

/**
 * 인코더는 Java 개체를 가져와 JSON, XML 또는 이진 표현과 같은 메시지로 전송하기에 적합한 일반적인 표현을 생성합니다.
 * 인코더는 Encoder.Text<T> 또는 Encoder.Binary<T> 인터페이스를 구현하여 사용할 수 있습니다.
 * 아래 코드 에서 인코딩할 Message 클래스를 정의하고 encode 메서드 에서 Java 개체를 JSON으로 인코딩 하기 위해 Gson을 사용합니다.
 */
public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) throws EncodeException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
//        Text.super.init(endpointConfig);
    }

    @Override
    public void destroy() {
//        Text.super.destroy();
    }
}
