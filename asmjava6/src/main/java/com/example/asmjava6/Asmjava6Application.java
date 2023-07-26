package com.example.asmjava6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.asmjava6","com.poly"})
public class Asmjava6Application {

	public static void main(String[] args) {
		SpringApplication.run(Asmjava6Application.class, args);
	}

}
