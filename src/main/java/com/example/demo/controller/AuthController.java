package com.example.demo.controller;


import com.example.demo.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.ForgotPassword;
import com.example.demo.dto.ResetPassword;
import com.example.demo.dto.UserDto;
import com.example.demo.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {

   @Autowired
    private UserServiceImpl userService;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public void register(@RequestBody UserDto userDto) throws Exception {
        userService.signIn(userDto);
    }

    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request){
        return userService.logIn(request);
    }

    @PostMapping("/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirmUser(@RequestParam(value = "token") String token){
       userService.confirmUser(token);

    }

    @PostMapping ("/forgot-password")
    @ResponseStatus(HttpStatus.OK)
    public String forgotUserPassword(@RequestBody ForgotPassword email){
        return  userService.forgotpassword(email);
    }


    @PutMapping("/reset-password")
    @ResponseStatus(HttpStatus.OK)
    public void resetUserPassword(@RequestBody(required = true) ResetPassword password, @RequestParam String token){
        userService.resetpassword(password,token);
    }

}
