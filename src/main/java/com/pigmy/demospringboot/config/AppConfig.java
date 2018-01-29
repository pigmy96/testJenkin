package com.pigmy.demospringboot.config;

import org.hibernate.SessionFactory;
import org.hibernate.jpa.HibernateEntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import javax.persistence.EntityManagerFactory;

@Configuration
public class AppConfig {

	@Bean(name = "viewResolver")
	  public ViewResolver getViewResolver() {
	      UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();

	      // TilesView 3
	      viewResolver.setViewClass(TilesView.class);

	      return viewResolver;
	  }
	  @Bean(name = "tilesConfigurer")
	  public TilesConfigurer getTilesConfigurer() {
	      TilesConfigurer tilesConfigurer = new TilesConfigurer();

	      // TilesView 3
	      tilesConfigurer.setDefinitions("/tiles.xml");

	      return tilesConfigurer;
	  }

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();

		return commonsMultipartResolver;
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource rb = new ResourceBundleMessageSource();
		// Load property in message/validator.properties
		rb.setBasenames(new String[] { "messages/validator" });
		return rb;
	}

	@Bean
	public SessionFactory sessionFactory(HibernateEntityManagerFactory hemf) {
		return hemf.getSessionFactory();
	}
}
