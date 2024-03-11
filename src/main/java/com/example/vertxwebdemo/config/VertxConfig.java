package com.example.vertxwebdemo.config;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertxConfig {


    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }


    @Bean
    public Router router() {
        return Router.router(vertx());
    }

    @Bean
    public HttpServer server() {
        HttpServer server = vertx().createHttpServer();
        server.requestHandler(router()).listen(8080);
        return server;
    }
}
