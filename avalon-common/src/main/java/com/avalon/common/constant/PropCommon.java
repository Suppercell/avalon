package com.avalon.common.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.avalon.common.spring.SpringContext;

@Component
public class PropCommon {

    @Value("${application.env}")
    private String env;

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.port}")
    private String redisPort;

    @Value("${redis.max_active}")
    private String redisMaxActive;

    @Value("${redis.max_wait}")
    private String redisMaxWait;

    @Value("${redis.time_out}")
    private String redisTimeOut;

    @Value("${redis.max_idle}")
    private String redisMaxIdle;

    public static PropCommon getProp() {
        return SpringContext.getBean(PropCommon.class);
    }

    public String getEnv() {
        return env;
    }

    public String getRedisHost() {
        return redisHost;
    }

    public String getRedisPassword() {
        return redisPassword;
    }

    public String getRedisPort() {
        return redisPort;
    }

    public String getRedisMaxActive() {
        return redisMaxActive;
    }

    public String getRedisMaxWait() {
        return redisMaxWait;
    }

    public String getRedisTimeOut() {
        return redisTimeOut;
    }

    public String getRedisMaxIdle() {
        return redisMaxIdle;
    }

}
