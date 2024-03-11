package com.example.vertxwebdemo.config;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ServiceRegister implements ApplicationRunner {


    @Resource
    private Vertx vertx;

    @Resource
    private List<AbstractVerticle> verticles;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        verticles.forEach(x -> vertx.deployVerticle(x));
    }
}
