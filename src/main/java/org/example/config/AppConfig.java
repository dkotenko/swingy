package org.example.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppConfig {
    @Value("${swingy.window.width}")
    private int windowWidth;
    @Value("${swingy.window.height}")
    private int windowHeight;
}
