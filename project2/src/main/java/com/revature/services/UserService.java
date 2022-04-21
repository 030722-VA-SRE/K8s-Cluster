package com.revature.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dtos.UserDTO;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

	private UserRepository userRepo;
	private static Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	public UserService(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}
	
	public List<UserDTO> getUsers(){
		List<User> users = userRepo.findAll();
		
		/*-
		 *  converts the list into a stream in which a map function is applied
		 *  The map function applies some logic to each object within the List and returns that object
		 *  the newly UserDto objects are then returned
		 */
		List<UserDTO> usersDto = users.stream()
				.map((user) -> new UserDTO(user))
				.collect(Collectors.toList());
		
		return usersDto;
	}
	
	public UserDTO getUserById(int id) throws UserNotFoundException {
		User user = userRepo.findById(id).orElseThrow(UserNotFoundException::new);
		
		log.info(MDC.get("userToken"));
		return new UserDTO(user);
	}
	
	public UserDTO getUserByUsername(String username) throws UserNotFoundException {
		User user = userRepo.findUserByUsername(username);
		
		log.info(MDC.get("userToken"));
		return new UserDTO(user);
	}
	
	
	public List<UserDTO> getUsersByRole(Role role){
		List<User> users = userRepo.findUsersByRole(role);
		
		List<UserDTO> usersDto = users.stream()
				.map((user) -> new UserDTO(user))
				.collect(Collectors.toList());
		return usersDto;
	}
	
	@Transactional
	public User createUser(User user) {
		return userRepo.save(user);
	}
	
	@Transactional
	public User updateUser(int id,User user) {
		User u = userRepo.findById(id).orElseThrow(UserNotFoundException::new);
		user.setId(u.getId());
		return userRepo.save(user);
	}
	
	@Transactional
	public boolean deleteUser(int id) throws UserNotFoundException {
		// this tries to retrieve a user by id, if it doesn't exist, throws an exception
		getUserById(id);

		userRepo.deleteById(id);
		return true;
	}
}
