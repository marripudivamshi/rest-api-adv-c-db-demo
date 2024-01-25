package com.example.restapiadvcdbdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapiadvcdbdemo.user.Post;

public interface PostJpaRepository extends JpaRepository<Post, Integer> {

}
