package com.pigmy.demospringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
 

@Configuration
//@EnableWebSecurity = @EnableWebMVCSecurity + Extra features
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserDetailsServiceImpl userDetailsServiceImpl;

  
  @Autowired
  public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsServiceImpl);
      auth.authenticationProvider(authenticationProvider());
  }
   
   
  @Bean
  public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
  }
   
   
  @Bean
  public DaoAuthenticationProvider authenticationProvider() {
      DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
      authenticationProvider.setUserDetailsService(userDetailsServiceImpl);
      authenticationProvider.setPasswordEncoder(passwordEncoder());
      return authenticationProvider;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

      http.csrf().disable();

      // redirect tới trang login nếu chưa login
      http.authorizeRequests().antMatchers("/accountInfo", "/logout", "/cart/checkout", "/order/orderHistory",
    		  "/order/orderDetail", "/cart/success")//
              .access("hasAnyRole('ROLE_CUSTOMER', 'ROLE_ADMIN')");

 
      // Các quyền chỉ dành chho Admin
      http.authorizeRequests().antMatchers("/album/manage", "/album/manage/add", "/album/manage/edit",
              "/album/manage/delete", "/genre/add", "/artist/add").access("hasRole('ROLE_ADMIN')");
      // Ngoại lệ AccessDeniedException sẽ ném lỗi khi truy cập vào link không được cấp quyền
      http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

 
      // Cấu hình cho Login Form.
      http.authorizeRequests().and().formLogin()//
         
              .loginProcessingUrl("/j_spring_security_check") // Submit URL
              .loginPage("/login")//
              .defaultSuccessUrl("/accountInfo")//
              .failureUrl("/login?error=true")//
              .usernameParameter("userName")//
              .passwordParameter("password")
              .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

  }
}