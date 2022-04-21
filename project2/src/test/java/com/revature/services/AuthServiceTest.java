package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

import io.jsonwebtoken.Claims;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	static UserRepository userRepo;
	static UserService userServ;
	static AuthService authServ;
	static User user1;
	static User user2;
	static String tokenAdmin;
	static String tokenCustomer;
	
	@BeforeAll
	public static void setup() {
		userRepo = mock(UserRepository.class);
		userServ = new UserService(userRepo);
		authServ = new AuthService(userRepo);
		user1 = new User(1,"user1","pass1",Role.CUSTOMER);
		user2 = new User(2,"user2","pass2",Role.CUSTOMER);
		
	
		tokenCustomer = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsInJvbGUiOiJDVVNUT01FUiIsImlkIjoyLCJleHAiOjE2NDkxMzI1MDAsImlhdCI6MTY0OTEzMjMyMCwidXNlcm5hbWUiOiJ1c2VyMiJ9.B2ulvFs_HqOidVZGPjRVAhkz8CO7f0DYr9hFh_k1SHo";
		//tokenCustomer = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsInJvbGUiOiJDVVNUT01FUiIsImlkIjoyLCJ1c2VybmFtZSI6InVzZXIyIn0.K0XCIoPbmyxSKwsaRx6PubNLBxsz79yUg29tC8Tb_p4";
	}
	
	@Test
	public void loginTest() {
		when(userRepo.findUserByUsername(user1.getUsername())).thenReturn(user1);
		String newToken = authServ.login(user1.getUsername(), user1.getPassword());
		Claims c = authServ.verify(newToken);
		
		assertEquals(c.get("username"),user1.getUsername());
		assertEquals(c.get("role"),user1.getRole().toString());
	}
	
	@Test
	public void registerTest() {
		String newToken = authServ.register(user2);
		Claims c = authServ.verify(newToken);
		
		assertEquals(c.get("username"),user2.getUsername());
	}
	
	

}
