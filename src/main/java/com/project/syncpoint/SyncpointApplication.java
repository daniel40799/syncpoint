package com.project.syncpoint;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SyncpointApplication {

	public static void main(String[] args) {
		SpringApplication.run(SyncpointApplication.class, args);
	}

}
