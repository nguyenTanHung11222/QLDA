package com.example.managermentdepartmentgroupeight.controller;

import com.example.managermentdepartmentgroupeight.authen.AccountPrincipal;
import com.example.managermentdepartmentgroupeight.dto.Employee;
import com.example.managermentdepartmentgroupeight.entity.Account;
import com.example.managermentdepartmentgroupeight.sercivecall.DepartmentEmployee;
import com.example.managermentdepartmentgroupeight.serviceimp.AccountImp;
import com.example.managermentdepartmentgroupeight.serviceimp.DepartmenntImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

    @Autowired
    DepartmenntImp departmenntImp;
    @Autowired
	private AccountImp accountImp;
    @Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @GetMapping("/")
    public String getIndex(Model model){
        model.addAttribute("listEmployee", departmenntImp.getListEmployee());
        model.addAttribute("listDepartment", departmenntImp.getListDepartment());
        return "admin_dashboard";
    }

    @GetMapping("/login")
    public String getLogin(Model model){
        model.addAttribute("account", new AccountPrincipal());
    	return "signin";
    }
    
    @RequestMapping(value="/register", method=RequestMethod.POST, consumes= {"application/x-www-form-urlencoded"})
	public String register(@RequestParam String username, @RequestParam String password) {
		Account acc = new Account();
		acc.setUsername(username);
		acc.setPassword(bCryptPasswordEncoder.encode(password));
		accountImp.createAccount(acc);
		return "signin";
	}
    /*
    public Account register(Account account) {
		Account acc = new Account();
		acc.setUsername(account.getUsername());
    	acc.setPassword(new BCryptPasswordEncoder().encode(account.getPassword()));
	
		return accountImp.createAccount(acc);
	}
	*/
    @RequestMapping("/employee/{id}")
    public String getEmployeeByIdDepartment(@PathVariable(name="id") long id, Model model){
        DepartmentEmployee departmentEmployee = departmenntImp.getListDepartmentAndEmployee(id);
        model.addAttribute("listEmployee",departmentEmployee.getGetListEmployee());
        model.addAttribute("listDepartment", departmenntImp.getListDepartment());
        return "admin_dashboard";
    }

    @RequestMapping("/employee/")
    public String getEmployeeByName(@Param("keysearch") String keysearch, Model model){

        model.addAttribute("listEmployee",departmenntImp.getListEmployeeByName(keysearch));
        model.addAttribute("listDepartment", departmenntImp.getListDepartment());
        return "admin_dashboard";
    }
    @RequestMapping("/delete/{id}")
    public String deleteEmployeeById(@PathVariable(name="id") long id){
        departmenntImp.deleteEmployeeById(id);
        return "redirect:/";
    }
    @RequestMapping("/saveEmployee/")
    public String addEmployee(@Param("name") String name, @Param("mssv") String mssv, @Param("namSinh") int namSinh,@Param("idDepartment") long idDepartment){
        Employee employee = new Employee();
        employee.setId(0);
        employee.setName(name);
        employee.setMssv(mssv);
        employee.setNamSinh(namSinh);
        employee.setIdDepartment(idDepartment);
        departmenntImp.addEmployee(employee);
        return "redirect:/";
    }

}