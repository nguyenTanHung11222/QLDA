package com.example.managermentdepartmentgroupeight.controller;

import com.example.managermentdepartmentgroupeight.dto.Employee;
import com.example.managermentdepartmentgroupeight.entity.Department;
import com.example.managermentdepartmentgroupeight.sercivecall.DepartmentEmployee;
import com.example.managermentdepartmentgroupeight.serviceimp.DepartmenntImp;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppRestController {

    @Autowired
    DepartmenntImp departmenntImp;

    @Retry(name="basic")
    @RateLimiter(name = "basicExample")
    @GetMapping("department")
    public List<Department> outputListDepartment(){
        return departmenntImp.getListDepartment();
    }

    @Retry(name="basic")
    @RateLimiter(name = "basicExample")
    @GetMapping("department/{id}")
    public List<Employee> getDepartmentEmployee(@PathVariable long id){
        DepartmentEmployee departmentEmployee = departmenntImp.getListDepartmentAndEmployee(id);
        return departmentEmployee.getGetListEmployee();
    }

    @Retry(name="basic")
    @RateLimiter(name = "basicExample")
    @GetMapping("employees")
    public List<Employee> outputDSEmployees(){
        return departmenntImp.getListEmployee();
    }

    @Retry(name="basic")
    @RateLimiter(name = "basicExample")
    @GetMapping("employees/{name}")
    public List<Employee> outputDSEmployees(@PathVariable String name){
        return departmenntImp.getListEmployeeByName(name);
    }

    @PostMapping("employees")
    public String outputAddEmployee(@RequestBody Employee employee){
        if(departmenntImp.addEmployee(employee)){
            return "Add Employee Success";
        }
        return "Add Employee Not Success";
    }

}
