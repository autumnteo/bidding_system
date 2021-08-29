package com.services.gateway.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.services.gateway.api.model.LoginRequest;
import com.services.gateway.api.model.UserSigninResponse;
import com.services.gateway.api.services.UserLoginService;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserAuthController {
	@Autowired
	private UserLoginService userLoginService;
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<UserSigninResponse> userLogin(@RequestBody LoginRequest loginReq){
		String tokenString = userLoginService.login(loginReq.getUsername(), loginReq.getPassword());
		HttpHeaders headers = new HttpHeaders();
        List<String> headerlist = new ArrayList<>();
        List<String> exposeList = new ArrayList<>();
        headerlist.add("Content-Type");
        headerlist.add(" Accept");
        headerlist.add("X-Requested-With");
        headerlist.add("Authorization");
        headers.setAccessControlAllowHeaders(headerlist);
        exposeList.add("Authorization");
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", tokenString);
        return new ResponseEntity<UserSigninResponse>(new UserSigninResponse(tokenString), headers, HttpStatus.ACCEPTED);
	}
	
	@PostMapping("/logout")
	@ResponseBody
	public ResponseEntity<UserSigninResponse> userLogout(@RequestHeader(value="Authorization") String token){
		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<UserSigninResponse>(new UserSigninResponse("Logged Out"), headers, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/checktoken")
	@ResponseBody
	public boolean tokenIsValid(@RequestHeader(value="Authorization") String token) {
		return userLoginService.isValidToken(token);
	}
	
}
