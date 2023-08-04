package com.example.managermentdepartmentgroupeight.sercivecall;

import com.example.managermentdepartmentgroupeight.dto.Employee;
import com.example.managermentdepartmentgroupeight.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentEmployee {
    private Department department;
    private List<Employee> getListEmployee;
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public List<Employee> getGetListEmployee() {
		return getListEmployee;
	}
	public void setGetListEmployee(List<Employee> getListEmployee) {
		this.getListEmployee = getListEmployee;
	}
    
    
}
