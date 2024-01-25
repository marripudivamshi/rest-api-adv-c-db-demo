package com.example.restapiadvcdbdemo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.restapiadvcdbdemo.dao.UserDaoService;
import com.example.restapiadvcdbdemo.exception.UserNotFoundException;
import com.example.restapiadvcdbdemo.user.User;

import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserDaoService daoService;
	
	@GetMapping(path="/Users")
	public List<User> findAllUsers() {
		return daoService.findAll();
	}
	
	//Implementing HATEAOS
	//Entity model
	//webmvclink builder
	@GetMapping(path="/Users/{id}")
	public EntityModel<User> findUserById(@PathVariable int id) {
		User savedUser = daoService.findOne(id);
		
		if (savedUser == null) {
			throw new UserNotFoundException("User not found for given id : " +id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(savedUser);
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping(path="/Users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = daoService.saveUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path="/Users/{id}")
	public void deleteUserById(@PathVariable int id) {
		daoService.deleteOne(id);
	}
	
}
