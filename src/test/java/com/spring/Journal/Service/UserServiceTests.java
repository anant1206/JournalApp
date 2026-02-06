package com.spring.Journal.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Test
    void add(){
        assertEquals(5,2+3);
    }

    @Test
    public void findByUserNameTest(){
        assertNotNull(userService.findByUserName("ram"),"User is Present");
    }
}
