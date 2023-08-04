package com.example.managermentdepartmentgroupeight.serviceimp;

import com.example.managermentdepartmentgroupeight.entity.Token;

public interface TokenImp {
	Token createToken(Token token);
	Token findByToken(String tokenName);
}
