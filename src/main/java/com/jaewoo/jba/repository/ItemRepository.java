package com.jaewoo.jba.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.jaewoo.jba.entity.Blog;
import com.jaewoo.jba.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

	List<Item> findByBlog(Blog blog, Pageable pageable);
	
	List<Item> findByBlogAndLink(Blog blog, String link);
}
