package ru.kpfu.MotivationAppBackend.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeforcesServiceTest {

    @Autowired
    CodeForcesServiceImpl codeForcesService;
    @Test
    public void t(){
        System.out.println(codeForcesService.getTaskFromSubmissions("Dmitry_"));
    }
}
