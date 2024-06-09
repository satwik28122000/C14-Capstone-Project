package com.bej.proxy;
import com.bej.domain.Employee;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="UserAuthentication",url = "localhost:8089")
public interface UserProxy {
    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody Employee employee);
}
