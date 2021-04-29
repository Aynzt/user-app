package com.example.userapp.util.dto;

import lombok.Data;

@Data
public class UserDTO {	
	private String firstname;
	private String lastname;
	private String login;
	private String pwd;
	private String address;
	private boolean resident;
	private int age;
}
