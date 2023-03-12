package com.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class FraudServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudServiceApplication.class, args);
	}


	@PostMapping("fraud-checker")
	public ResponseEntity<ServiceResponse> checkTransaction(@RequestBody ServiceRequest request){
		System.out.println("Received a request " + request);
		// can be a complex logic, it's a demo :)
		if(Math.random() < 0.3)
			return ResponseEntity.ok(new ServiceResponse(request.transactionId(), "invalid"));

		return ResponseEntity.ok(new ServiceResponse(request.transactionId(), "valid"));


	}
}
