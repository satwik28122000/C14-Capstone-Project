package com.bej.repository;

import com.bej.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<Employee,String> {

    Employee findByUserIdAndPassword(String userId, String password);
}
