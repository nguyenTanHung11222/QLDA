package com.example.managermentdepartmentgroupeight.filter;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.managermentdepartmentgroupeight.authen.AccountPrincipal;
import com.example.managermentdepartmentgroupeight.entity.Token;
import com.example.managermentdepartmentgroupeight.serviceimp.TokenImp;
import com.example.managermentdepartmentgroupeight.util.JwtUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private TokenImp vertificationToken;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		final String authorizationHeader = request.getHeader("Authorization");
		
		AccountPrincipal account = null;
		Token token = null;
		
		if(StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Token ")) {
			String jwt = authorizationHeader.substring(6);
			
			account = jwtUtil.getAccountFromToken(jwt);
			token = vertificationToken.findByToken(jwt);
		}
		
		if(null != account && null != token && token.getTokenExpDate().after(new Date())) {
			Set<GrantedAuthority> authorities = new HashSet<>();
			
			account.getAuthorities().forEach(
					p -> authorities.add(new SimpleGrantedAuthority(String.valueOf(p))));
					
					UsernamePasswordAuthenticationToken authentication = 
							new UsernamePasswordAuthenticationToken(account, null, authorities);
					
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
	
	
}