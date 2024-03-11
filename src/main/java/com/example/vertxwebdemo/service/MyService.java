package com.example.vertxwebdemo.service;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;

@Service
public class MyService extends AbstractVerticle implements ApplicationRunner {

    @Resource
    private Router router;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    private final DefaultRedisScript<String> script = new DefaultRedisScript<>();


    @Override
    public void start() throws Exception {
        router.route(HttpMethod.GET, "/api/test").handler(ctx -> {
            // 所有的请求都会调用这个处理器处理
            HttpServerResponse response = ctx.response();
            response.putHeader("content-type", "text/plain");
            String execute = redisTemplate.execute(script, Collections.singletonList("k"));
            // 写入响应并结束处理
            response.end(execute);
        });
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ResourceScriptSource pathResource = new ResourceScriptSource(new ClassPathResource("redis/lua.lua"));

        script.setResultType(String.class);

        script.setScriptSource(pathResource);
    }
}
