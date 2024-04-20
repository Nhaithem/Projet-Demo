package com.example.demo.service;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.AuthenticationResponse;
import com.example.demo.dto.ForgotPassword;
import com.example.demo.dto.ResetPassword;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private EmailService emailService;


    @Transactional
    public void signIn(UserDto userDto) throws Exception {

        if(userRepository.findUserByEmail(userDto.getEmail()).isPresent())
            throw new Exception("User exists !");

        User user = User.builder()
                .fullName(userDto.getFullName())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
                .isEnabled(false)
                .isLocked(false)
                .build();

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        String token = jwtService.generateToken(user);

        String link = "http://localhost:4200/activated?token=" + token;

        String body = emailService.buildEmail(user.getFullName(), link);
        emailService.sendSimpleEmail(
                user.getEmail(),
                "Please confirm your account",
                body
        );


    }


    public AuthenticationResponse logIn(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user= userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User email %s not found",request.getEmail())));

        AuthenticationResponse res = new AuthenticationResponse();
        user.getAuthorities().forEach(grantedAuthority -> res.setRoles(grantedAuthority.getAuthority().toString()));
        String token = jwtService.generateToken(user);
        log.info(token);
        log.info(jwtService.extractId(token));
        res.setToken(token);
        return res;
    }



    public void confirmUser(String token) {

        if(jwtService.isTokenExpired(token))
            throw new IllegalStateException("token expired");

        String username = jwtService.extractUsername(token);
        User user= userRepository.findUserByEmail(username).orElseThrow(()-> new IllegalStateException("Token invalid"));

        if(user.isEnabled())
            throw new IllegalStateException("email already confirmed");

        user.setIsEnabled(true);
        log.info( "user enabled : ",user.getIsEnabled() );
        userRepository.save(user);
    }

    public String forgotpassword(ForgotPassword email){

        log.info(email.getEmail());
        User user= userRepository.findUserByEmail(email.getEmail()).orElseThrow(()-> new UsernameNotFoundException(String.format("User email %s not found",email.getEmail())));

        String token = jwtService.generateToken(user);

        String link = "http://localhost:4200/reset-password?token=" + token;
        log.info(link);
        String body = emailService.buildEmail(user.getFullName(), link);
        emailService.sendSimpleEmail(
                user.getEmail(),
                "Password Reset",
                body
        );

        return "email sent";
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User getUserById(Long id) throws Exception {
        Optional<User> user =  userRepository.findById(id);
        if (user.isEmpty())
            throw new Exception("user not found");

        return user.get();
    }


    public void resetpassword(ResetPassword password, String token){
        if(jwtService.isTokenExpired(token))
            throw new IllegalStateException("Expired token");
        String email = jwtService.extractUsername(token);
        User user= userRepository.findUserByEmail(email).orElseThrow(()-> new IllegalStateException("Token invalid"));
        log.info(user.getPassword());
        log.info(password.getPassword());

        user.setPassword(passwordEncoder.encode(password.getPassword()));
        log.info(user.getPassword());
        userRepository.save(user);
    }

    public User findUserById(Long id) throws Exception {
        log.info("getting user "+id);
        return userRepository.findById(id).orElseThrow(()-> new Exception("User not found"));
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(()-> new UsernameNotFoundException(String.format("User email %s not found",email)));
    }



    public void updateUser(UserDto userDto) throws Exception {

        userRepository.findUserByEmail(userDto.getEmail()).orElseThrow(()-> new Exception("User not found"));

        User user = UserMapper.Instance.userDtoToUser(userDto);

        userRepository.save(user);
    }




}
