package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dtos.UserDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	static UserRepository userRepo;
	static UserService userServ;
	static List<User> users = new ArrayList<>();
	static List<UserDTO> usersDto = new ArrayList<>();
	static User user1;
	static User user2;
	
	@BeforeAll
	public static void setup() {
		userRepo = mock(UserRepository.class);
		userServ = new UserService(userRepo);
		user1 = new User(1,"user1","pass1",Role.ADMIN);
		user2 = new User(2,"user2","pass2",Role.CUSTOMER);
		users.add(user1);
		users.add(user2);
		usersDto.add(new UserDTO(user1));
		usersDto.add(new UserDTO(user2));
	}
	
	@Test
	public void getUsersTest() {
		when(userRepo.findAll()).thenReturn(users);
		assertEquals(usersDto, userServ.getUsers());
	}
	
	@Test
	public void getUserByIdTest() throws UserNotFoundException{
		when(userRepo.findById(1)).thenReturn(Optional.of(user1));
		assertEquals(usersDto.get(0), userServ.getUserById(1));
	}
	
	@Test
	public void getUserByUsernameTest() throws UserNotFoundException{
		when(userRepo.findUserByUsername("user1")).thenReturn(user1);
		assertEquals(usersDto.get(0), userServ.getUserByUsername("user1"));
	}
	
	@Test
	public void getUserUsersByRoleTest() {
		List<User> usersByRole = new ArrayList<>();
		usersByRole.add(user1);
		List<UserDTO> usersDtoByRole = new ArrayList<>();
		usersDtoByRole.add(usersDto.get(0));
		when(userRepo.findUsersByRole(Role.ADMIN)).thenReturn(usersByRole);
		assertEquals(usersDtoByRole,userServ.getUsersByRole(Role.ADMIN));
	}
	
	@Test
	public void createUserTest() {
		when(userRepo.save(user1)).thenReturn(user1);
		assertEquals(user1,userServ.createUser(user1));
	}
	
	@Test
	public void updateUserTest() {
		when(userRepo.findById(1)).thenReturn(Optional.of(user1));
		when(userRepo.save(user1)).thenReturn(user1);
		assertEquals(user1,userServ.updateUser(1,user1));
	}
	
	@Test
	public void deleteUserTest() {
		when(userRepo.getById(1)).thenReturn(user1);
		assertEquals(userServ.deleteUser(1),true);
	}
}
