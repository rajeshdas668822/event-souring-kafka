package org.springboot.eventbus.config;

import org.restlet.Component;
import org.restlet.ext.spring.SpringServerServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.Properties;


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getMultipartResolver() {
		return new CommonsMultipartResolver();
	}



	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		//registry.addViewController("/").setViewName("home");

		registry.addViewController("/").setViewName("forward:/index.html");
		registry.addViewController("/error").setViewName("forward:/error.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		// TODO Auto-generated method stub
		super.addViewControllers(registry);
	}


	@Bean
	// Only used when running in embedded servlet
	public DispatcherServlet dispatcherServlet() {
		return new DispatcherServlet();
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();

	}




	/*@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("RestletServlet","/rs*//*");
		super.addResourceHandlers(registry);
	}
*/

  /*@Bean(name = "messageSource")
	protected SpringServerServlet createSpringServerServlet() {

		SpringServerServlet springServerServlet = new SpringServerServlet();

       *//*JaxRsApplication jaxRsApplication = new JaxRsApplication(parentContext.createChildContext());
		jaxRsApplication.setObjectFactory(new ApplicationContextObjectFactory(getServletContext()));
		jaxRsApplication.add(config);*//*

		return springServerServlet;
	}*/

	/*@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getMultipartResolver() {
		return new CommonsMultipartResolver();
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename("classpath:messages");
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}*/

/*

	@Bean(name = "messageSource")
	protected SpringServerServlet createSpringServerServlet() {

		SpringServerServlet springServerServlet = new SpringServerServlet();
		*/
/*JaxRsApplication jaxRsApplication = new JaxRsApplication(parentContext.createChildContext());
		jaxRsApplication.setObjectFactory(new ApplicationContextObjectFactory(getServletContext()));
		jaxRsApplication.add(config);*//*

		return springServerServlet;
	}
*/








/*
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");
		registry.addViewController("/error").setViewName("forward:/error.html");
		registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
		// TODO Auto-generated method stub
		super.addViewControllers(registry);
	}
*/


	/*@Bean
	public ViewResolver getViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setSuffix(".html");
		return resolver;
	}

	@Override
	public void configureDefaultServletHandling(
			DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
*/

/*
	@Bean(name = "simpleMappingExceptionResolver")
	public SimpleMappingExceptionResolver
	createSimpleMappingExceptionResolver() {
		SimpleMappingExceptionResolver r =
				new SimpleMappingExceptionResolver();

		Properties mappings = new Properties();
		mappings.setProperty("DatabaseException", "databaseError");
		mappings.setProperty("InvalidCreditCardException", "creditCardError");

		r.setExceptionMappings(mappings);  // None by default
		r.setDefaultErrorView("error");    // No default
		r.setExceptionAttribute("ex");     // Default is "exception"
		r.setWarnLogCategory("example.MvcLogger");     // No default
		return r;
	}*/
}
