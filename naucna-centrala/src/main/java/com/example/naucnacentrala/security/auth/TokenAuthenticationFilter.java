package com.example.naucnacentrala.security.auth;

import com.example.naucnacentrala.security.CustomUserDetailsService;
import com.example.naucnacentrala.security.TokenUtils;
import com.example.naucnacentrala.security.CustomUserDetailsService;
import com.example.naucnacentrala.security.TokenUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;


//Filter koji ce presretati svaki zahtev klijenta ka serveru
//Sem nad putanjama navedenim u WebSecurityConfig.configure(WebSecurity web)
public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private TokenUtils tokenUtils;

	private CustomUserDetailsService userDetailsService;

	public TokenAuthenticationFilter(TokenUtils tokenHelper, CustomUserDetailsService userDetailsService) {
		this.tokenUtils = tokenHelper;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		System.out.println("usao u doFilterInternal");
		String username;
		//izvuci token iz request-a
		String authToken = tokenUtils.getToken(request);
//		System.out.println("authToken: " + authToken);

		if (authToken != null) {
			// uzmi username iz tokena
			username = tokenUtils.getUsernameFromToken(authToken);
//			System.out.println("username: " + username);
			
			if (username != null) {
				// uzmi user-a na osnovu username-a
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				
				// proveri da li je prosledjeni token validan
				if (tokenUtils.validateToken(authToken, userDetails)) {
					// kreiraj autentifikaciju
					TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
					authentication.setToken(authToken);
					SecurityContextHolder.getContext().setAuthentication(authentication);

//					Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
//                	SecurityContextHolder.getContext().getAuthentication().getAuthorities();
//
//					for (GrantedAuthority authority : authorities) {
//						System.out.println("Authority: " + authority.getAuthority());
//					}
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}