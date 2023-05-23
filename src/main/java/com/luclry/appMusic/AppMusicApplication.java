package com.luclry.appMusic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class AppMusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppMusicApplication.class, args);
		System.out.println("app launched");
		System.out.println("current " + new Date());
		System.out.println("1 more hour " + new Date((new Date ()).getTime() + 3600 *1000));

	}

}
