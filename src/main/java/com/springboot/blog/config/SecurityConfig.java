package com.springboot.blog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.springboot.blog.security.CustomUserDetailsService;
import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig{
	
	@Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(){
        return  new JwtAuthenticationFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
    			.cors()
    		.and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests((authorize) -> authorize
                        .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .antMatchers("/api/auth/**").permitAll()
                        .anyRequest()
                        .authenticated()
                );
        
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);        
        return http.build();
    }
    
    @Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:9001"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
    
    /*
   	.authorizeRequests((authorize) -> authorize
                        .antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
                        .antMatchers("/api/v1/auth/**").permitAll()
                        .antMatchers("/v2/api-docs/**").permitAll()
                        .antMatchers("/swagger-ui/**").permitAll()
                        .antMatchers("/swagger-resources/**").permitAll()
                        .antMatchers("/swagger-ui.html").permitAll()
                        .antMatchers("/webjars/**").permitAll()
                        .anyRequest()
                        .authenticated()
    */
    
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
	
    /* -- DELETE THIS SPACE OF CODE
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint)
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			.antMatchers("/api/auth/**").permitAll() 
			.anyRequest()
			.authenticated();
		http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	*/
	
	/* 
	// Manual creation of users for testing the API without DB validation
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		
		UserDetails jordi = User.builder()
				.username("jordi")
				.password(passwordEncoder().encode("password"))
				.roles("USER")
			.build();
		
		UserDetails admin = User.builder()
				.username("admin")
				.password(passwordEncoder().encode("admin"))
				.roles("ADMIN")
			.build();
		
		return new InMemoryUserDetailsManager(jordi, admin);
	}
	*/
}
