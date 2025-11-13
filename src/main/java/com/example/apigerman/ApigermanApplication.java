package com.example.apigerman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({
    "com.example.apigerman.entities",
    "com.example.demo.entities"      // ← entidades del jar
})
public class ApigermanApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApigermanApplication.class, args);
	}

}