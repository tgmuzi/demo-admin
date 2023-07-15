package com.example.demo.modules.health;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @Author muzi
* @Description Admin工程服务健康检查
* @Param
* @return
**/
@RestController
public class HealthCheckController {

	@GetMapping("/demo-health-check")
	public ResponseEntity<String> customCheck() {
	    String message = "demo服务健康检查";
	    return new ResponseEntity<>(message, HttpStatus.OK);
	}

}
