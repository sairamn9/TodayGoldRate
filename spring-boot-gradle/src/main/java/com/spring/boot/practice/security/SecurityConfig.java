package com.spring.boot.practice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.spring.boot.practice.service.UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserService userDetailsService;
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		/*http.authorizeRequests().antMatchers("/","/home","/public/**").permitAll().antMatchers("/users/**").hasAuthority("ADMIN").
		anyRequest().fullyAuthenticated().and().formLogin().loginPage("/login").failureUrl("/login?error").usernameParameter("email").permitAll().
		and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();*/
		
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests = http.authorizeRequests();
		authorizeRequests.antMatchers("/","/login","/logOut").permitAll();
		authorizeRequests.antMatchers("/userInfo").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')");
		authorizeRequests.antMatchers("/admin").access("hasRole('ROLE_ADMIN')");
		authorizeRequests.and().exceptionHandling().accessDeniedPage("/403");
		
		authorizeRequests.and().formLogin().loginProcessingUrl("/jspring_security_check").loginPage("/login")//
        .defaultSuccessUrl("/userAccountInfo")//
        .failureUrl("/login?error=true")//
        .usernameParameter("username")//
        .passwordParameter("password")
        // Config for Logout Page
        .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");
		
		
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
}
