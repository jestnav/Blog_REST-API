package com.springboot.blog.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JWTAuthResponse;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;

//@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private JwtTokenProvider tokenProvider;

	//@CrossOrigin(origins = "http://localhost:9001")
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					loginDto.getUsernameOrEmail(), 
					loginDto.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = tokenProvider.generateToken(authentication);
		
		// get token from tokenProvider
		return ResponseEntity.ok(new JWTAuthResponse(token));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
		
		if(userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
		}
		
		if(userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
		}
		
		User user  = new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		
		Role roles = roleRepository.findByName("ROLE_ADMIN").get();
		user.setRoles(Collections.singleton(roles));
		
		userRepository.save(user);
		
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	}
}
