package com.example.userapp.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import com.example.userapp.model.User;
import com.example.userapp.repository.UserRepository;
import com.example.userapp.service.UserService;
import com.example.userapp.util.dto.UserDTO;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepo;
	
	@InjectMocks
	private UserService userService;
	
	private User user;
	private UserDTO dto;
	
	@BeforeEach
	void setup() {		
		user = new User();
		user.setAddress("address");
		user.setAge(20);
		user.setFirstname("Firstname");
		user.setId(1L);
		user.setLastname("Lastname");
		user.setLogin("username");
		user.setPwd("password");
		user.setResident(true);	
		
		dto = new UserDTO();
		dto.setAddress("address");
		dto.setAge(20);
		dto.setFirstname("test");
		dto.setLastname("test");
		dto.setLogin("test-login");
		dto.setPwd("test-pwd");
		dto.setResident(false);
	}
	
	@Test
	void givenLogin_whenRegistring_mustThrowNotFound() {
		// mocking
		when(userRepo.findByLogin(ArgumentMatchers.anyString())).thenThrow(ResponseStatusException.class);
		
		// assert
		assertThrows(ResponseStatusException.class, () -> {
			userService.register(dto);
		});		
	}
	
	@Test
	void givenCorrectUser_whenRegistring_mustReturnID() {
		// mocking
		when(userRepo.findByLogin(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(null));
		
		when(userRepo.save(ArgumentMatchers.any())).thenReturn(user);
		
		// assert
		Long result = userService.register(dto);
		
		assertThat(result).isEqualTo(1);
	}
	
	@Test
	void assertGettingInfo_returnUser() {
		// mocking
		when(userRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(user));
		
		dto = userService.getInfo(12L);
		
		assertThat(dto).isNotNull();
	}
	
	@Test
	void assertGettingInfo_throwNotFound() {
		// mocking
		when(userRepo.findById(ArgumentMatchers.anyLong())).thenThrow(ResponseStatusException.class);
		
		assertThrows(ResponseStatusException.class, () -> {
			userService.getInfo(12L);
		});
	}
}
