package com.example.restapiadvcdbdemo.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//1)all requests should be authenticated
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated());
		
		//2)if req not auth, a webpage is shown
		http.httpBasic(withDefaults());
		
		//3)disable csrf
		http.csrf().disable();
		
		return http.build();
	}

}
