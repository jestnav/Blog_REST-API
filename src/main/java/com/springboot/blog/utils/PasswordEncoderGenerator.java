package com.springboot.blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderGenerator {
	
	public static void main(String[] args) {
		
		PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();
		
		// Test -> Insert the manual encrypted password into the DB (copy and paste from the console)
		System.out.println("jordi: ");
		System.out.println(passwordEncoder.encode("jordi"));
		
		System.out.println("\nadmin: ");
		System.out.println(passwordEncoder.encode("admin"));
		
	}
}
