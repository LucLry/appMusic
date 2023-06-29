package com.luclry.appMusic.dao;

import com.luclry.appMusic.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<User, Long> {

    public List<User> findAll();

    public Optional<User> findByUsernameAndPassword(String username, String password);

    public User save(User user);

}
