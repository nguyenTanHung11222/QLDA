package com.example.managermentdepartmentgroupeight.serviceimp;

import com.example.managermentdepartmentgroupeight.authen.AccountPrincipal;
import com.example.managermentdepartmentgroupeight.entity.Account;

public interface AccountImp {
	AccountPrincipal findById(Long id);
	Account createAccount(Account account);
	AccountPrincipal findByUsername(String username);
	AccountPrincipal sendRegister(AccountPrincipal account);
}
