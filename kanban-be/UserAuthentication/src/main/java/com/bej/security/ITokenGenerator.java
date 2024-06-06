package com.bej.security;

import com.bej.domain.User;

public interface ITokenGenerator {
    String createToken(User user);
}
