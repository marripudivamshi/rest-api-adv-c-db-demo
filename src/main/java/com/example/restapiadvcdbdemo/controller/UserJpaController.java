package com.example.restapiadvcdbdemo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

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

import com.example.restapiadvcdbdemo.dao.PostJpaRepository;
import com.example.restapiadvcdbdemo.dao.UserDataJpaRepository;
import com.example.restapiadvcdbdemo.exception.UserNotFoundException;
import com.example.restapiadvcdbdemo.user.Post;
import com.example.restapiadvcdbdemo.user.User;

import jakarta.validation.Valid;

@RestController
public class UserJpaController {
	
	@Autowired
	private UserDataJpaRepository jpaRepository;
	
	@Autowired
	private PostJpaRepository postJpaRepository;
	
	@GetMapping(path="/Jpa/Users")
	public List<User> findAllUsers() {
		return jpaRepository.findAll();
	}
	
	//Implementing HATEAOS
	//Entity model
	//webmvclink builder
	@GetMapping(path="/Jpa/Users/{id}")
	public EntityModel<User> findUserById(@PathVariable int id) {
		Optional<User> savedUser = jpaRepository.findById(id);
		
		if (savedUser == null) {
			throw new UserNotFoundException("User not found for given id : " +id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(savedUser.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).findAllUsers());
		entityModel.add(link.withRel("all-users"));
		
		return entityModel;
	}
	
	@PostMapping(path="/Jpa/Users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = jpaRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
							.path("/{id}")
							.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping(path="/Jpa/Users/{id}")
	public void deleteUserById(@PathVariable int id) {
		jpaRepository.deleteById(id);
	}
	
	@GetMapping(path="/Jpa/Users/{id}/Posts")
	public List<Post> findPostsOfUser(@PathVariable int id) {
		Optional<User> savedUser = jpaRepository.findById(id);
		
		if (savedUser == null) {
			throw new UserNotFoundException("User not found for given id : " +id);
		}
		
		return savedUser.get().getPosts();
	}
	
	@PostMapping(path="/Jpa/Users/{id}/Posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<User> savedUser = jpaRepository.findById(id);
		
		if (savedUser == null) {
			throw new UserNotFoundException("User not found for given id : " +id);
		}
		
		post.setUser(savedUser.get());
		Post savedPost = postJpaRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedPost.getId()).toUri();
		
		return ResponseEntity.created(location).build();
	}
	
}
