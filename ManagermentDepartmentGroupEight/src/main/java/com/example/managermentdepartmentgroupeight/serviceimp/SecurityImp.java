package com.example.managermentdepartmentgroupeight.serviceimp;


public interface SecurityImp {
    String findLoggedInUsername();

    void autoLogin(String username, String password);
}