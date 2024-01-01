package com.sheryians.major.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.sheryians.major.model.CustomUserDetail;
import com.sheryians.major.service.CustomUserDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
   @Autowired
   GoogleOAuth2SuccessHandler googleOAuth2SuccessHandler;
   @Autowired
   CustomUserDetailService customUserDetailService;

   
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//"/admin/** means admin se jud hua koi bhi ek route
		http
		.authorizeRequests()
		.antMatchers("/","/shop/**","/register","/h2-console/**").permitAll()
		.antMatchers("/admin/**").hasRole("ADMIN")
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.failureUrl("/login?error=true")
		.defaultSuccessUrl("/")
		.usernameParameter("email")
		.passwordParameter("password")
		.and()
		.oauth2Login()
		.loginPage("/login")
		.successHandler(googleOAuth2SuccessHandler)
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login")
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID")
		.and()
		.exceptionHandling()
		.and()
		.csrf()
		.disable();
		
		//for h2 console
		http.headers().frameOptions().disable();
		
	}
	
	//
	@Bean
	public BCryptPasswordEncoder  bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//ek custom user of object build kro and use pass kro bs
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(customUserDetailService);
	}
	
	//ek aur security check for static content

	@Override
	public void configure(WebSecurity web) throws Exception {
		// TODO Auto-generated method stub
		//meaans itne routes ko igonre kr dena hai security se
		web.ignoring().antMatchers("/resources/**","/static/**","/images/**","/productImages/**","/css/**","/js/**");
	}
	
	

	
	
	
	
	
}
