package com.pigmy.demospringboot.config;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

  private static final Charset UTF8 = Charset.forName("UTF-8");

 
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
      StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
      stringConverter.setSupportedMediaTypes(Arrays.asList(new MediaType("text", "plain", UTF8)));
      converters.add(stringConverter);
  }

 

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
      registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/").setCachePeriod(31556926);
      registry.addResourceHandler("/fonts/**").addResourceLocations("/resources/fonts/").setCachePeriod(31556926);
      registry.addResourceHandler("/bootstrap/**").addResourceLocations("/resources/bootstrap/").setCachePeriod(31556926);
      registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/").setCachePeriod(31556926);
      registry.addResourceHandler("/plugins/**").addResourceLocations("/resources/plugins/").setCachePeriod(31556926);
      registry.addResourceHandler("/stylesheets/**").addResourceLocations("/resources/stylesheets/").setCachePeriod(31556926);
      registry.addResourceHandler("/javascripts/**").addResourceLocations("/resources/javascripts/").setCachePeriod(31556926);
  }

  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
      configurer.enable();
  }

}