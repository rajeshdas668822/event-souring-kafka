package org.springboot.eventbus.config;

import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.catalina.filters.CorsFilter;
import org.restlet.ext.spring.SpringServerServlet;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import java.util.HashMap;
import java.util.Map;

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

       FilterRegistration.Dynamic corsFilter =  servletContext.addFilter("CorsFilter", CorsFilter.class);
        Map<String,String> inParamMaps = new HashMap<>();
        inParamMaps.put("cors.allowed.origins","*");
        inParamMaps.put("cors.allowed.methods","GET,POST,HEAD,OPTIONS,PUT");
        inParamMaps.put("cors.allowed.methods","GET,POST,HEAD,OPTIONS,PUT");
        inParamMaps.put("cors.allowed.headers","Content-Type,X-Requested-With,accept,Origin,Access-Control-Request-Method,Access-Control-Request-Headers");
        inParamMaps.put("cors.exposed.headers","Access-Control-Allow-Origin,Access-Control-Allow-Credentials");
        inParamMaps.put("cors.support.credentials","false");
        inParamMaps.put("cors.preflight.maxage","10");
        corsFilter.setInitParameters(inParamMaps);
       // camelServlet.setLoadOnStartup(1);

        corsFilter.getUrlPatternMappings().add("*//*");

    }
}
