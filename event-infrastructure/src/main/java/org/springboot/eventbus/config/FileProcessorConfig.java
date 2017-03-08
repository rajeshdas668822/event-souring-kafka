package org.springboot.eventbus.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.ThrottlingInflightRoutePolicy;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springboot.eventbus.route.CoreRoute;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * Created by rdas on 1/15/2017.
 */

//@Configuration
public class FileProcessorConfig extends CamelConfiguration {
    @Override
    public List<RouteBuilder> routes() {


        return Arrays.asList(new CoreRoute());
    }
}
