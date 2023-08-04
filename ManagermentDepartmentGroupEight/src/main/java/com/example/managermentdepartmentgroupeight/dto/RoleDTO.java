package com.example.managermentdepartmentgroupeight.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.managermentdepartmentgroupeight.authen.AccountPrincipal;

public class RoleDTO {
	private long id;
	private String roleName;
	private List<AccountPrincipal> accounts = new ArrayList<>();
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public List<AccountPrincipal> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<AccountPrincipal> accounts) {
		this.accounts = accounts;
	}
	@Override
	public String toString() {
		return "RoleDTO [roleName=" + roleName + ", accounts=" + accounts + "]";
	}
	
}
