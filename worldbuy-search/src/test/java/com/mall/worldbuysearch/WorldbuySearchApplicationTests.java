package com.mall.worldbuysearch;

import javafx.application.Application;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@SpringBootTest
public class WorldbuySearchApplicationTests {

    @Resource
    private RestHighLevelClient client;

    @Test
    public void esTest() {
        System.out.println(client);
    }

}
