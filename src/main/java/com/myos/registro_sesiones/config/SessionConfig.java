package com.myos.registro_sesiones.config;

import org.springframework.session.FlushMode;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 900, flushMode = FlushMode.IMMEDIATE)
public class SessionConfig {
    //instalar redis -> Redis-x64-2.8.2104.exe si es en windows
}
