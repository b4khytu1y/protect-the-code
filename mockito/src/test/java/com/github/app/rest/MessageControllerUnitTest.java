package com.github.app.rest;

import static org.mockito.Mockito.times;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.app.api.MessageApi;
import com.github.domain.model.Message;
import com.github.domain.service.MessageService;
import com.github.domain.util.MessageMatcher;

/*
    Необходимо протестировать MessageController используя моки
    TODO
        создайте мок для MessageService
        создайте мок для MessageController
        протестируйте создание нового сообщения
*/
@RunWith(MockitoJUnitRunner.class)
public class MessageControllerUnitTest {
    @InjectMocks
    private MessageController messageController;
    @Mock
    private MessageService messageService;


    @Test
    public void testCreateMessage(){
        Message message = new Message();
        message.setText("hello");
        message.setFrom("pi");
        message.setTo("pi");
        MessageApi messageApi = new MessageApi();
        messageApi.setText(message.getText());
        messageApi.setFrom(message.getFrom());
        messageApi.setTo(message.getTo());

        Mockito.when(messageService.deliverMessage(any())).thenReturn(message);

        assertEquals(message, messageController.createMessage(messageApi));
    }
    
}