package com.bej.proxy;

import com.bej.domain.Manager;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="UserAuthentication",url = "localhost:8089")
public interface ManagerProxy {
    @PostMapping("/saveManager")
    public ResponseEntity<?> createManager(@RequestBody Manager manager);
}
