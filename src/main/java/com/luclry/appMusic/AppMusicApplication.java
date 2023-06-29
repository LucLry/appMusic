package com.luclry.appMusic;

import com.luclry.appMusic.dao.UserDao;
import com.luclry.appMusic.model.User;
import com.luclry.appMusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class AppMusicApplication implements CommandLineRunner {

	@Autowired
	private UserDao userDao;
	public static void main(String[] args) {

		SpringApplication.run(AppMusicApplication.class, args);
		System.out.println("app launched");

	}

	@Override
	public void run(String... args) throws Exception{
		System.out.println("launching app");

		User u1 = new User(null, "JeanClode", "123");
		userDao.save(u1);
	}

}

/**
 * Artists :
 * 		The Beatles : 3WrFJ7ztbogyGnTHbHJFl2
 * 		Daft Punk : 4tZwfgrHOc3mvqYlEYSvVi
 *		The Alan Parsons Project : 2m62cc253Xvd9qYQ8d2X3d
 *		Queen : 1dfeR4HaWDbWqFHLkxsg1d
 *		Nino Ferrer : 3THqHCN7gq2Z9hLleof9uv
 */