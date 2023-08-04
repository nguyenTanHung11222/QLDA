package com.example.managermentdepartmentgroupeight.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.managermentdepartmentgroupeight.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByTokenName(String token);
}
