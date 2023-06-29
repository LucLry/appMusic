package com.luclry.appMusic.api;

import com.luclry.appMusic.model.User;
import com.luclry.appMusic.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserApi {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUsers")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/getUserByUsernameAndPassword")
    public User findUserByUsernameAndPassword(@RequestParam String username, @RequestParam String password) {
        return userService.findUserByUsernameAndPassword(username, password);
    }

    @PostMapping("/saveUser")
    public User saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }
}
