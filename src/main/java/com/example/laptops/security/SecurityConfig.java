/*package com.example.laptops.security;

import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
        // Cấu hình quyền truy cập
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
            	.requestMatchers("/css/**", "/js/**", "/img/**","/img1/**","/scss/**","/vendor/**").permitAll() 
            	.requestMatchers("/shared/register").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")  // tk : admin mk : admin
                .requestMatchers("/User/**").hasRole("KHACHHANG")    
                .anyRequest().authenticated()               // 
        )
        .formLogin(formLogin ->
            formLogin
                .loginPage("/shared/login")                       
                .successHandler(successHandler())            
                .permitAll()                                 
        )
        .logout(logout ->
            logout
                .logoutUrl("/logout")                       
                .logoutSuccessUrl("/login")                 
                .permitAll()                                 
        )
        .csrf(csrf ->
            csrf.disable() 
        );
        return http.build();
    }
	@Bean
	AuthenticationSuccessHandler successHandler() {
	    return (request, response, authentication) -> {
	        String role = authentication.getAuthorities().stream()
	                                    .map(GrantedAuthority::getAuthority)
	                                    .collect(Collectors.joining());
	        if (role.contains("ROLE_ADMIN")) {
	            response.sendRedirect("/admin/dashboard");
	            System.out.println("Logged in as: " + authentication.getName() + " with roles: " + role);
	        } else {
	            response.sendRedirect("/User/home");
	            System.out.println("Logged in as: " + authentication.getName() + " with roles: " + role);
	        }
	    };
	}
    
	@Bean
	AuthenticationManager authenticationManager(HttpSecurity http, JdbcUserDetailsManager userDetailsService) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authenticationManagerBuilder.userDetailsService(userDetailsService);
	    return authenticationManagerBuilder.build();
	}
	@Bean
	JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
	    JdbcUserDetailsManager manager = new JdbcUserDetailsManager();
	    manager.setDataSource(dataSource);
	    // Cấu hình các truy vấn để lấy thông tin người dùng và quyền từ cơ sở dữ liệu
	    manager.setUsersByUsernameQuery("SELECT username, password, enabled FROM account WHERE username = ?");
	    manager.setAuthoritiesByUsernameQuery("SELECT username, role FROM account WHERE username = ?");
	    return manager;
	}
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}*/
