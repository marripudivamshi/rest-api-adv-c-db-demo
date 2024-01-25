package com.example.restapiadvcdbdemo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Repository;

import com.example.restapiadvcdbdemo.user.User;

@Repository
public class UserDaoService {

	private static List<User> users = new ArrayList<>();
	
	private static int userCount = 0;
	
	static {
		users.add(new User(++userCount, "shanthan", LocalDate.now().minusYears(31)));
		users.add(new User(++userCount, "rohith", LocalDate.now().minusYears(31)));
		users.add(new User(++userCount, "vishnu", LocalDate.now().minusYears(31)));
		users.add(new User(++userCount, "nitin", LocalDate.now().minusYears(31)));
	}
	
	public List<User> findAll() {
		return users;
	}
	
	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
	
	public User saveUser(User user) {
		user.setId(++userCount);
		users.add(user);
		return user;
	}
	
	public void deleteOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.removeIf(predicate);
	}
}
