package com.example.managermentdepartmentgroupeight.serviceimp;

import com.example.managermentdepartmentgroupeight.dto.Employee;
import com.example.managermentdepartmentgroupeight.entity.Department;
import com.example.managermentdepartmentgroupeight.sercivecall.DepartmentEmployee;

import java.util.List;

public interface DepartmenntImp {

    public List<Department> getListDepartment();
    public DepartmentEmployee getListDepartmentAndEmployee(long id);
    public Department getDepartment(long id);
    public List<Employee> getListEmployee();
    public List<Employee> getListEmployeeByName(String name);
    public Boolean deleteEmployeeById(long id);
    public Boolean addEmployee(Employee employee);
}
