package com.example.demo.dto;


import java.util.List;

import com.example.demo.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String fullName;

  
    private String email;

    private String password;

 
    private Role role;
}
