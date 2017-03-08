package org.springboot.eventbus.route;

import org.apache.camel.builder.RouteBuilder;
/**
 * Created by rdas on 1/15/2017.
 */
public class CoreRoute extends RouteBuilder {



    @Override
    public void configure() throws Exception {
       from("seda:queue:fileprocessor")
               .to("bean:coreFileProcessor");
    }
}
