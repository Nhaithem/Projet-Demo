package com.example.demo.service;

import java.util.List;

import jakarta.transaction.Transactional;
import com.example.demo.dto.*;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface UserService {
	
	
	List<User> findAll();

    User getUserById(Long id);


	void resetpassword(ResetPassword password, String token);

	String forgotpassword(ForgotPassword email);

	void confirmUser(String token);

	AuthenticationResponse logIn(AuthenticationRequest request);

	void signIn(UserDto userDto) throws Exception;

	
}
