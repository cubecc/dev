package com.example.springcloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@SpringBootApplication
public class SpringCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewayApplication.class, args);
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private String clientName="test";
    private String clientSecret="ccc";
    
    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange()
                   .anyExchange().authenticated()
                   .and()
                   .httpBasic()
                   .and()
                   .csrf().disable()
                   .build();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    	System.out.println("---C----" + clientName);
    	System.out.println("---P----" + clientSecret);
    	System.out.println("---P2----" + passwordEncoder.encode(clientSecret));
        UserDetails webclient = User
              .withUsername(clientName)
              .password(passwordEncoder.encode(clientSecret))
              .roles("account", "message", "email")
              .build();

        return new MapReactiveUserDetailsService(webclient);
    }
}
