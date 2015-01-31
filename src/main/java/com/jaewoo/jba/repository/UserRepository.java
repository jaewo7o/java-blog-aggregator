package com.jaewoo.jba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaewoo.jba.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
