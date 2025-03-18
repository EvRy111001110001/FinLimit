package com.evry.FinLimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = "com.evry.FinLimit")
public class FinLimitApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinLimitApplication.class, args);
	}

//	@Bean
//	public CqlSessionBuilderCustomizer sessionBuilderCustomizerDatacenter() {
//		return builder -> builder.withLocalDatacenter("datacenter1"); // Используй свой DC
//	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer() {
		return builder -> builder
				.withLocalDatacenter("datacenter1") // Обязательно указываем DC
				.withKeyspace("ervy_keyspace"); // Указываем keyspace
	}
}
