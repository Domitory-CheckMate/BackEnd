package org.gachon.checkmate.global.config;

import com.p6spy.engine.spy.P6SpyOptions;
import jakarta.annotation.PostConstruct;
import org.gachon.checkmate.global.common.CustomP6spySqlFormatter;
import org.springframework.context.annotation.Configuration;

@Configuration
public class P6spyConfig {
    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(CustomP6spySqlFormatter.class.getName());
    }
}

