package com.codingdojo.springloginreg.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.codingdojo.springloginreg.models.User;
import com.codingdojo.springloginreg.repositories.RoleRepository;
import com.codingdojo.springloginreg.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
  public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder)     {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    
	    
    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }
         
    public void saveUserWithAdminRole(User user) {    	
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }    
        
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public int getCountOfAdminRoleUser() {
    	return userRepository.getCountOfAdminRoleUser();
    }
    
    public List<User> getAllUsers(){
    	return (List<User>) userRepository.findAll();
    }
    
    public void deleteUser(User user) {
    	userRepository.delete(user);
    }
    
    public User findUserById(Long id) {
    	return userRepository.findOne(id);
    }
    
    public void giveAdminPrivilege(Long user_id) {
    	User user = userRepository.findOne(user_id);
    	user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }
}