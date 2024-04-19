package com.nhom29;

import com.nhom29.Configuration.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StackOverFlowApplication implements CommandLineRunner {
	@Autowired
	private Security security;
	public static void main(String[] args) {
		SpringApplication.run(StackOverFlowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
