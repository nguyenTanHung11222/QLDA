package com.example.managermentdepartmentgroupeight.service;

import com.example.managermentdepartmentgroupeight.dto.Employee;
import com.example.managermentdepartmentgroupeight.entity.Department;
import com.example.managermentdepartmentgroupeight.repository.DepartmentRepository;
import com.example.managermentdepartmentgroupeight.sercivecall.DepartmentEmployee;
import com.example.managermentdepartmentgroupeight.serviceimp.DepartmenntImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService implements DepartmenntImp {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Department> getListDepartment() {
        return departmentRepository.findAll();
    }

    @Override
    public DepartmentEmployee getListDepartmentAndEmployee(long id) {
        List<Employee> employees = new ArrayList<>();
        Department department = departmentRepository.findById(id).get();
        Employee[] employeeList = restTemplate.getForObject("http://MANAGERMENT-EMPLOYEES-GROUP-EIGHT/employees/"+id, Employee[].class);
        for (Employee e : employeeList){
            if(e !=null){
                employees.add(e);
            }
        }
        DepartmentEmployee departmentEmployee = new DepartmentEmployee();
        departmentEmployee.setDepartment(department);
        departmentEmployee.setGetListEmployee(employees);
        return departmentEmployee;
    }

    @Override
    public Department getDepartment(long id) {
        return departmentRepository.findById(id).get();
    }

    @Override
    public List<Employee> getListEmployee() {
        List<Employee> employees = new ArrayList<>();
        Employee[] employeeList = restTemplate.getForObject("http://MANAGERMENT-EMPLOYEES-GROUP-EIGHT/employee", Employee[].class);
        for (Employee e : employeeList){
            if(e !=null){
                employees.add(e);
            }
        }
        return employees;
    }

    @Override
    public List<Employee> getListEmployeeByName(String name) {
        List<Employee> employees = new ArrayList<>();
        Employee[] employeeList = restTemplate.getForObject("http://MANAGERMENT-EMPLOYEES-GROUP-EIGHT/employee/"+name, Employee[].class);
        for (Employee e : employeeList){
            if(e !=null){
                employees.add(e);
            }
        }
        return employees;
    }

    @Override
    public Boolean deleteEmployeeById(long id) {
        try {
            restTemplate.delete("http://MANAGERMENT-EMPLOYEES-GROUP-EIGHT/employee/"+id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Boolean addEmployee(Employee employee) {
        restTemplate.postForEntity("http://MANAGERMENT-EMPLOYEES-GROUP-EIGHT/employee/", employee, String.class);
        return true;
    }

}
