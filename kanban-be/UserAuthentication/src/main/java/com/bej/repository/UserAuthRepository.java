package com.bej.repository;

import com.bej.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthRepository extends JpaRepository<User,String> {

    User findByUserIdAndPassword(String userId,String password);
}
