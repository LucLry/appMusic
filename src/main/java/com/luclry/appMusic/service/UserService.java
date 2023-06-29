package com.luclry.appMusic.service;

import com.luclry.appMusic.dao.UserDao;
import com.luclry.appMusic.model.User;
import org.hibernate.boot.model.internal.CreateKeySecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    public User saveUser(User user){
        return userDao.save(user);
    }

    public User findUserByUsernameAndPassword (String username, String password){
        Optional<User> response = userDao.findByUsernameAndPassword(username, password);

        if (response.isPresent()) {
            return response.get();
        } else {
            return null;
        }
    }
}
