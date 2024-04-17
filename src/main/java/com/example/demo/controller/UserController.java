package com.example.demo.controller;

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
	    public User getUserById(@PathVariable Long id) {
	        return userService.getUserById(id);
	    }

	    @PostMapping("/adduser")
	    public User addUser(@RequestBody User user) {
	        return userService.addUser(user);
	    }

	    @PutMapping("/{id}")
	    public User updateUser(@PathVariable Long id, @RequestBody User user) {
	        return userService.updateUser(id, user);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteUser(@PathVariable Long id) {
	    	userService.deleteUser(id);
	    }
	    
	   
	}

