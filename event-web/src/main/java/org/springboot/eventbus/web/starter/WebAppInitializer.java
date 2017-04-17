package org.springboot.eventbus.web.starter;

import org.apache.camel.CamelContext;
import org.springboot.eventbus.route.CoreRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@ComponentScan("org.springboot.eventbus")
public class WebAppInitializer {

    public static void main(String[] args) throws Exception{

        SpringApplication app = new SpringApplication(WebAppInitializer.class);
        ConfigurableApplicationContext ctx = app.run(args);

    }

}
