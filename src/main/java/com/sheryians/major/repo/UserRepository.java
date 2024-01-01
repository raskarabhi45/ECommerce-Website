package com.sheryians.major.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheryians.major.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	//custome method
	//iski implementation JPA khud kr dega u dont need to right body
	Optional<User> findUserByEmail(String email);

}
