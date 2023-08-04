package com.example.managermentdepartmentgroupeight.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.managermentdepartmentgroupeight.authen.AccountPrincipal;
import com.example.managermentdepartmentgroupeight.entity.Account;
import com.example.managermentdepartmentgroupeight.repository.AccountRepository;
import com.example.managermentdepartmentgroupeight.serviceimp.AccountImp;

@Service
public class AccountService implements AccountImp {

	private static final String LOCALHOST = "http://MANAGERMENT-EMPLOYEES-GROUP-EIGHT/";

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public AccountPrincipal findById(Long id) {
		Account account = accountRepository.findById(id).get();
		AccountPrincipal accountPrincipal = new AccountPrincipal();
		
		if(null != account) {
			Set<String> authorities = new HashSet<>();
			
			if(null != account.getRoles()) {
				account.getRoles().forEach(
							r -> {
								authorities.add(r.getRoleKey());
								r.getPermissions().forEach(
											p -> authorities.add(p.getPermissionKey())
										);
							}
						);
			}
			accountPrincipal.setAccountId(account.getId());
			accountPrincipal.setUsername(account.getUsername());
			accountPrincipal.setPassword(account.getPassword());
			accountPrincipal.setAuthorities(authorities);
		}
		
		return accountPrincipal;
	}

	@Override
	public Account createAccount(Account account) {
		// TODO Auto-generated method stub
		return accountRepository.saveAndFlush(account);
	}

	@Override
	public AccountPrincipal findByUsername(String username) {
		Account account = accountRepository.findByUsername(username);
		AccountPrincipal accountPrincipal = new AccountPrincipal();
		
		if(null != account) {
			Set<String> authorities = new HashSet<>();
			
			if(null != account.getRoles()) {
				account.getRoles().forEach(
						r -> {
							authorities.add(r.getRoleKey());
							r.getPermissions().forEach(
										p -> authorities.add(p.getPermissionKey())
									);
						});
			}
			accountPrincipal.setAccountId(account.getId());
			accountPrincipal.setUsername(account.getUsername());
			accountPrincipal.setPassword(account.getPassword());
			accountPrincipal.setAuthorities(authorities);
		}
		
		return accountPrincipal;
	}

	@Override
	public AccountPrincipal sendRegister(AccountPrincipal account) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		final HttpEntity<AccountPrincipal> request = new HttpEntity<>(account,headers);
		ResponseEntity<String> response = restTemplate.exchange(LOCALHOST+"/employees", HttpMethod.POST, request, String.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			System.out.println("got response succsessfully");
		}
		return account;
	}

}
