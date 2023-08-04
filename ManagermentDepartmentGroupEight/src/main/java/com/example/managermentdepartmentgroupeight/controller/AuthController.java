package com.example.managermentdepartmentgroupeight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.managermentdepartmentgroupeight.authen.AccountPrincipal;
import com.example.managermentdepartmentgroupeight.entity.Account;
import com.example.managermentdepartmentgroupeight.entity.Token;
import com.example.managermentdepartmentgroupeight.serviceimp.AccountImp;
import com.example.managermentdepartmentgroupeight.serviceimp.TokenImp;
import com.example.managermentdepartmentgroupeight.util.JwtUtil;

@Controller
public class AuthController {
	@Autowired
	private AccountImp accountImp;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private TokenImp tokenImp;
	
	@PostMapping("/authenticateTheAccount")
	public ResponseEntity<?> login(@RequestBody Account account) {
		AccountPrincipal accountPrincipal = accountImp.findByUsername(account.getUsername());
		
		if(null == account || !new BCryptPasswordEncoder()
				.matches(account.getPassword(), accountPrincipal.getPassword())) {
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("Account or password is not valid!");
				}
		
		Token token = new Token();
		token.setTokenName(jwtUtil.generateToken(accountPrincipal));
		
		token.setTokenExpDate(jwtUtil.generateExpirationDate());
		token.setCreatedBy(accountPrincipal.getAccountId());
		tokenImp.createToken(token);
		
		return ResponseEntity.ok(token.getTokenName());
	}
	
	@GetMapping("/hello")
	@PreAuthorize("hasAnyAuthority('USER_READ')")
	public ResponseEntity hello() {
		return ResponseEntity.ok("hello");
	}
}
