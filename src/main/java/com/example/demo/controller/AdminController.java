package com.example.demo.controller;

import com.example.demo.dto.RoleDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users")
    public ModelAndView getPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users.html");
        return modelAndView;
    }

    @GetMapping()
    public Map<String, Object> printAllUsers() {
        Map<String, Object> resMap = new HashMap<>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDTO admin = convertToDTO((User) authentication.getPrincipal());
        List<Role> roles = userService.getAllRoles();

        List<UserDTO> users = userService.getListOfUsers().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
        resMap.put("admin", admin);
        resMap.put("users", users);
        resMap.put("roles", roles);

        return resMap;
    }



    @PostMapping()
    public Map<String, Object> creat(@RequestBody User userDTO) {
        userService.save(userDTO);
        return printAllUsers();
    }


    @PatchMapping("/{id}")
    public Map<String, Object> update(@RequestBody User user) {
        userService.update(user);
        return printAllUsers();
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") long id) {
        userService.delete(id);
        return printAllUsers();
    }


    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private RoleDTO convertToDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

}