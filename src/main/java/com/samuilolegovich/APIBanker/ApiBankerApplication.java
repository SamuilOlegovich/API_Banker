package com.samuilolegovich.APIBanker;

import com.samuilolegovich.APIBanker.model.Test3;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApiBankerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiBankerApplication.class, args);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Test3();
	}

}
