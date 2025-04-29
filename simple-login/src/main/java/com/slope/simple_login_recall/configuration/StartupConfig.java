package com.slope.simple_login_recall.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartupConfig {

    /**
     * auto launch browser on startup
     * @return 
     * @throws IOException processbuilder.start()
     * @throws InterruptedException proccess.waitFor()
     */
    @Bean
    Process autoLaunchBrowserOnStartupBean() throws IOException, InterruptedException {
        String browserPath = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
        String url = "http://localhost:8086";
        // use processbuilder instead of Runtime.getRuntime().exec(str[]{"cmd"}) for it to work
        ProcessBuilder builder = new ProcessBuilder(browserPath, url);
        var process = builder.start();
        // process.waitFor(); // if using @PostConstruct
        return process;
    }
}
