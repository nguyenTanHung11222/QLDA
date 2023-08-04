package com.example.managermentdepartmentgroupeight.repository;

import com.example.managermentdepartmentgroupeight.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
