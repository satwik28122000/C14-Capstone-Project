package com.bej.security;

import com.bej.domain.Employee;
import com.bej.domain.Manager;

public interface ITokenGenerator {
    String createToken(Employee employee);
    String createTokenForManager(Manager manager);
}
