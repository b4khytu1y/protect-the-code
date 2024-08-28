package com.github.app.rest;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.app.api.Flower;
import com.github.app.rest.FlowerController;
import com.github.domain.service.FlowerService;

/*
    Необходимо протестировать MessageController используя моки
    TODO
        создайте мок для FlowerService
        создайте мок для FlowerController
        напишите метод для тестирования flowerController.isAFlower, создав мок для flowerService.isABigFlower, который возвращает true
        напишите метод для тестирования flowerController.isABigFlower, создав мок для flowerService.isABigFlower, который возвращает true
*/
@RunWith(MockitoJUnitRunner.class)
public class FlowerControllerUnitTest {
    @Mock
    private FlowerService flowerService;
    @InjectMocks
    private FlowerController flowerController;

    @Test
    @Order(1)
    public void testIsAFlower(){
        when(flowerService.analize(anyString())).thenAnswer(invocation -> {
            String arg = invocation.getArgument(0);
            if(Arrays.asList("Poppy", "Ageratum", "Carnation", "Diascia", "Lantana").contains(arg))
                return "flower";
            return null;
        });

        assertNull(flowerController.isAFlower("nonExistingFlower"));
        assertEquals("flower", flowerController.isAFlower("Poppy"));
    }

    @Test
    public void testIsABigFlower(){
        when(flowerService.isABigFlower(anyString(),anyInt())).thenAnswer(invocation -> {
            String arg1 = invocation.getArgument(0);
            int arg2 = invocation.getArgument(1);
            return (Arrays.asList("Poppy", "Ageratum", "Carnation", "Diascia", "Lantana").contains(arg1) && arg2 > 10);
        });
        Flower bigFlower = new Flower("Carnation",15);
        Flower smallFlower = new Flower("Poppy",5);

        assertTrue(flowerController.isABigFlower(bigFlower));
        assertFalse(flowerController.isABigFlower(smallFlower));
    }
    
}