//package com.example.vertxwebdemo.service;
//
//import io.vertx.core.AbstractVerticle;
//import io.vertx.core.Vertx;
//import io.vertx.core.http.HttpMethod;
//import io.vertx.core.http.HttpServerResponse;
//import io.vertx.ext.web.Router;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//@Service
//public class MyService2 extends AbstractVerticle {
//
//    @Resource
//    private Router router;
//
//
//    @Override
//    public void start() throws Exception {
//        router.route(HttpMethod.GET, "/api/test1")
//                .handler(ctx -> {
//                    HttpServerResponse response = ctx.response();
//                    response.putHeader("content-type", "text/plain");
//                    System.out.println("集成成功111");
//                    // 写入响应并结束处理
//                    response.end("Hello World from Vert.x-Web!");
//                });
//    }
//
//}
