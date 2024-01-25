package com.example.restapiadvcdbdemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapiadvcdbdemo.user.User;

public interface UserDataJpaRepository extends JpaRepository<User, Integer> {

}
