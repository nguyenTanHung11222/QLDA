package com.example.managermentdepartmentgroupeight.dto;

import lombok.Data;

@Data
public class Employee {

    private long id;
    private String name;
    private String mssv;
    private int namSinh;
    private long idDepartment;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMssv() {
		return mssv;
	}
	public void setMssv(String mssv) {
		this.mssv = mssv;
	}
	public int getNamSinh() {
		return namSinh;
	}
	public void setNamSinh(int namSinh) {
		this.namSinh = namSinh;
	}
	public long getIdDepartment() {
		return idDepartment;
	}
	public void setIdDepartment(long idDepartment) {
		this.idDepartment = idDepartment;
	}

    
}
