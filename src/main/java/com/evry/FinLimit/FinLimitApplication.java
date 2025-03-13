package com.evry.FinLimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories(basePackages = "com.evry.FinLimit.cassandra")
public class FinLimitApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinLimitApplication.class, args);
	}

}
