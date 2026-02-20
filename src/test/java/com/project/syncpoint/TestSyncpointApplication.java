package com.project.syncpoint;

import org.springframework.boot.SpringApplication;

public class TestSyncpointApplication {

	public static void main(String[] args) {
		SpringApplication.from(SyncpointApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
