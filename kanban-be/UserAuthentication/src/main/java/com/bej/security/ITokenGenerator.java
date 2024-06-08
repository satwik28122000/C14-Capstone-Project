package com.bej.security;

import com.bej.domain.Employee;

public interface ITokenGenerator {
    String createToken(Employee employee);
}
