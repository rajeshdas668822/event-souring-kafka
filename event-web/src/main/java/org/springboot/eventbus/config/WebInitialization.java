package org.springboot.eventbus.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.restlet.ext.spring.SpringServerServlet;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * Created by rdas on 3/16/2017.
 */

/**
 *  This is the replacement of Web.xml
 *
 */
@Configuration
public class WebInitialization implements ServletContextInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

       /* ServletRegistration.Dynamic restletServlet =  servletContext.addServlet("RestletServlet",SpringServerServlet.class);
        restletServlet.setInitParameter("org.restlet.component","RestletComponent");
        restletServlet.addMapping("/rs*//*");
        
        System.out.println("!!!!!!!!!!!!!!!!!!!!!-- Inside Sevlet Init Section  "+restletServlet.getMappings());
         */
         Dynamic camelServlet =  servletContext.addServlet("camelServlet",CamelHttpTransportServlet.class);         
         camelServlet.addMapping("/camel/*");
        camelServlet.setLoadOnStartup(1);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!-- Inside Sevlet Init Section  "+camelServlet.getMappings());

    }
}
