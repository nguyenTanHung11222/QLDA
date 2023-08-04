package com.example.managermentdepartmentgroupeight.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.managermentdepartmentgroupeight.authen.AccountPrincipal;
import com.example.managermentdepartmentgroupeight.dto.RoleDTO;
import com.example.managermentdepartmentgroupeight.entity.Token;
import com.example.managermentdepartmentgroupeight.serviceimp.AccountImp;
import com.example.managermentdepartmentgroupeight.serviceimp.TokenImp;
import com.example.managermentdepartmentgroupeight.util.JwtUtil;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AccountImp accountImp;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private TokenImp tokenImp;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AccountPrincipal accountPrincipal = accountImp.findByUsername(username);
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (RoleDTO role : accountPrincipal.getRoles()) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		}
		
		Token token = new Token();
		token.setTokenName(jwtUtil.generateToken(accountPrincipal));
		
		token.setTokenExpDate(jwtUtil.generateExpirationDate());
		token.setCreatedBy(accountPrincipal.getAccountId());
		tokenImp.createToken(token);
		
		return new org.springframework.security.core.userdetails.User(accountPrincipal.getUsername(), accountPrincipal.getPassword(),
				grantedAuthorities);
	}

}