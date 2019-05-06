package com.example.demo.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ClusterLockV2Job {

	//@Scheduled(cron = "0/5 * * * * *")
	public void lock() {
		System.out.println("enter job" + System.currentTimeMillis());
	}

}
