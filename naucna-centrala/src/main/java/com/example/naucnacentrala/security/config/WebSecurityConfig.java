package com.example.naucnacentrala.security.config;

import com.example.naucnacentrala.security.CustomUserDetailsService;
import com.example.naucnacentrala.security.TokenUtils;
import com.example.naucnacentrala.security.auth.RestAuthenticationEntryPoint;
import com.example.naucnacentrala.security.auth.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled=true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

		// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
		// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}

		@Autowired
		private CustomUserDetailsService jwtUserDetailsService;
		
		// Neautorizovani pristup zastcenim resursima
		@Autowired
		private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
		
		@Autowired
		TokenUtils tokenUtils;
		
		@Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}

		// Definisemo nacin autentifikacije
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
		}
		
		
		// Definisemo prava pristupa odredjenim URL-ovima
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
			
				// komunikacija izmedju klijenta i servera je stateless
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				
				// za neautorizovane zahteve posalji 401 gresku
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
				
				.authorizeRequests()
					.antMatchers("/registration/**").permitAll()
					.antMatchers("/auth/**").permitAll()
					.antMatchers("/casopis/**").permitAll()
					// svaki zahtev mora biti autorizovan
					.anyRequest().authenticated().and()
				// presretni svaki zahtev filterom
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);
			
			http.headers().contentSecurityPolicy("script-src 'self' https://localhost:4200; object-src https://localhost:4200");
		}
		
		
		// Generalna bezbednost aplikacije
		@Override
		public void configure(WebSecurity web) throws Exception {
			// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
			web.ignoring().antMatchers(HttpMethod.POST, "/auth/**");
			web.ignoring().antMatchers(HttpMethod.GET, "/auth/**");
//			web.ignoring().antMatchers(HttpMethod.OPTIONS, "/auth/**");
			// nije potrebna autentifikacija za obicne user metode
//			web.ignoring().antMatchers(HttpMethod.POST, "/registration/**");
//			web.ignoring().antMatchers(HttpMethod.GET, "/registration/**");
//			web.ignoring().antMatchers(HttpMethod.PUT, "/registration/**");
//			web.ignoring().antMatchers(HttpMethod.OPTIONS, "/registration/**");
			web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js");
		}
		
		@Bean
		public DeviceResolverHandlerInterceptor 
		        deviceResolverHandlerInterceptor() {
		    return new DeviceResolverHandlerInterceptor();
		}

		@Bean
		public DeviceHandlerMethodArgumentResolver 
		        deviceHandlerMethodArgumentResolver() {
		    return new DeviceHandlerMethodArgumentResolver();
		}

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
		    registry.addInterceptor(deviceResolverHandlerInterceptor());
		}

		@Override
		public void addArgumentResolvers(
		        List<HandlerMethodArgumentResolver> argumentResolvers) {
		    argumentResolvers.add(deviceHandlerMethodArgumentResolver());
		}
	
}
