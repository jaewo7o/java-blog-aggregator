package com.jaewoo.jba.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaewoo.jba.entity.Blog;
import com.jaewoo.jba.entity.User;

public interface BlogRepository extends JpaRepository<Blog, Integer>{

	List<Blog> findByUser(User user);
}