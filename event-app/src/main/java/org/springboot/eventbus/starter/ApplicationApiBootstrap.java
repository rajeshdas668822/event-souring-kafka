package org.springboot.eventbus.starter;


import org.apache.camel.CamelContext;
import org.springboot.eventbus.config.ActivitiConfig;
import org.springboot.eventbus.config.FileProcessorConfig;
import org.springboot.eventbus.route.CoreRoute;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan("org.springboot.eventbus")
public class ApplicationApiBootstrap {
	
	  public static void main(String[] args) {		 
	      SpringApplication app = new SpringApplication(ApplicationApiBootstrap.class);
	         app.setWebEnvironment(false);
	         ConfigurableApplicationContext ctx = app.run(args);



	    }

}
