package com.example.demo.service;

import com.example.demo.models.Role;
import com.example.demo.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService {

    User getUser(long id);

    boolean save(User user);

    void update(User user);

    void delete(long id);

    List<User> getListOfUsers();

    Role getRoleById(Long id);

    void save(Role role);

    void save(User user, Role role);

    boolean contains(String username);

    List<Role> getAllRoles();

}
