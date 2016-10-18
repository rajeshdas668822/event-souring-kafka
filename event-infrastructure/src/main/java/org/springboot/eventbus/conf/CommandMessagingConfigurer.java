/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Eslam Nawara
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.springboot.eventbus.conf;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.eventbus.handler.CommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

import com.google.gson.Gson;

/**
 * Spring boot configuration class for registering the queues on Rabbit for command bus
 */
@Configuration
public class CommandMessagingConfigurer {

    private static final Logger log = LoggerFactory.getLogger(CommandMessagingConfigurer.class);

    @Value("${apache.kafka.consumer.properties}")
    private String kafkaConsumerProperties;

    @Autowired
    private ApplicationContext context;

   // @Autowired
    private Gson gson;
    



    @Autowired
    private TaskExecutor consumerTaskExecutor;

    @Value("${kafka.command.topic}")
    private String topic;

    @Value("${command.handler.package.base}")
    private String commandHandlerPackageBase;

    @Value("${kafka.consumer.timeout}")
    private Long timeout;



    @Bean
    public ActiveMqCommandConsumer activeMqCommandConsumer(){
        final Map<String, CommandHandler> registry = CommandHandlerUtils.buildCommandHandlersRegistry(this.commandHandlerPackageBase, this.context);        
        ActiveMqCommandConsumer eventConsumer = new ActiveMqCommandConsumer(this.timeout,this.gson, registry);
        return eventConsumer;
    }


    @Bean
    public Gson gson() {
    	 gson = new Gson();    	
    	return gson;
    }
    
    
    




    @Bean
    public TaskExecutor consumerTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
