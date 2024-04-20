package com.example.demo.controller;

import com.example.demo.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/api")
public class UserController {

	    @Autowired
	    private UserService userService;

	    @GetMapping ("/user")
	    public List<User> getAllUser() {
	        return userService.findAll();
	    }

	    @GetMapping("/{id}")
	    public User getUserById(@PathVariable Long id) throws Exception {
	        return userService.getUserById(id);
	    }

	}

