package com.example.managermentdepartmentgroupeight.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.managermentdepartmentgroupeight.entity.Token;
import com.example.managermentdepartmentgroupeight.repository.TokenRepository;
import com.example.managermentdepartmentgroupeight.serviceimp.TokenImp;

@Service
public class TokenService implements TokenImp {

	@Autowired
	private TokenRepository tokenRepository;
	
	@Override
	public Token createToken(Token token) {
		// TODO Auto-generated method stub
		return tokenRepository.save(token);
	}

	@Override
	public Token findByToken(String tokenName) {
		// TODO Auto-generated method stub
		return tokenRepository.findByTokenName(tokenName);
	}

}
