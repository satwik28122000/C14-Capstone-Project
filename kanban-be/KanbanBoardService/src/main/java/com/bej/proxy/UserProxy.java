package com.bej.proxy;
import com.bej.domain.Employee;
import com.bej.domain.Manager;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="UserAuthentication",url = "http://localhost:8089/auth/")
public interface UserProxy {
    @PostMapping("/users/save")
    public ResponseEntity<?> saveUser(@RequestBody Employee employee);

    @PostMapping("/managers/saveManager")
    public ResponseEntity<?> createManager(@RequestBody Manager manager);
}
