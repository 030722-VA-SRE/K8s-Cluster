package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

	static UserRepository userRepo;
	static UserService userServ;
	static AuthService authServ;
	static User user1;
	static User user2;
	static String token;
	
	@BeforeAll
	public static void setup() {
		userRepo = mock(UserRepository.class);
		userServ = new UserService(userRepo);
		authServ = new AuthService(userRepo);
		user1 = new User(1,"user1","pass1",Role.ADMIN);
		user2 = new User(2,"user2","pass2",Role.CUSTOMER);
		
		token = authServ.login(user1.getUsername(),user1.getPassword());
		
	}
	
	@Test
	public void loginTest() {
		assertDoesNotThrow(()->{
			assertEquals(token,authServ.login("user1","pass1"));
		});
	}
	
//	@Test
//	public static void verifyTest() {
////		Claims jwt = Jwts.parser()
////				.setSigningKey("secretKey")
////				.parseClaimsJws(token)
////				.getBody();
////		assertEquals(jwt,authServ.login(user1.))
//	}
}
