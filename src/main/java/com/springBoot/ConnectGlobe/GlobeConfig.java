package com.springBoot.ConnectGlobe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class GlobeConfig  extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userService;
	
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.cors().and()
		.authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/save").permitAll()
			.antMatchers("/login").permitAll()
			.antMatchers("/MyPosts/upload").hasRole("USER")
			.antMatchers("/MyPosts").hasRole("USER")
			.antMatchers("/home").permitAll()
			.antMatchers("/AllReports").permitAll()
			.antMatchers("/MyReports").permitAll()
			.antMatchers("/uploadReport").permitAll()
			.antMatchers("/Profile").permitAll()
			.antMatchers("/comment").permitAll()
			.antMatchers("/AllComments").permitAll()
			.antMatchers("/back").permitAll()
			.antMatchers("/suggest").permitAll()
			.antMatchers("/getAllSuggest").permitAll()
			.antMatchers("/return").permitAll()
			.antMatchers("/delete").permitAll()
			.antMatchers("/deletePost").permitAll()
			.antMatchers("/Profile").permitAll()
			.antMatchers("/Edit").permitAll()
			.antMatchers("/getUserProfile").permitAll()
			.antMatchers("/deleteUserProfile").permitAll()
			.antMatchers("/adminHome").permitAll()
//			.anyRequest().authenticated()
			.and()
			.logout().invalidateHttpSession(true)
			.clearAuthentication(true)
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/").permitAll()
		 	.and().sessionManagement()
		 	.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	http.csrf().disable();
	http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}

}
