package com.nhom29;

import com.nhom29.Repository.HinhAnhRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class StackOverFlowApplication implements CommandLineRunner {
	@Autowired
	private HinhAnhRepository hinhAnhRepository;
	public static void main(String[] args) {
		SpringApplication.run(StackOverFlowApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
