package com.jaewoo.jba.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.jaewoo.jba.entity.Blog;
import com.jaewoo.jba.entity.Item;
import com.jaewoo.jba.entity.User;
import com.jaewoo.jba.exception.RssException;
import com.jaewoo.jba.repository.BlogRepository;
import com.jaewoo.jba.repository.ItemRepository;
import com.jaewoo.jba.repository.UserRepository;

@Service
public class BlogService {
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private RssService rssService;
	
	public void saveItems(Blog blog) {
		List<Item> items;
		try {
			items = rssService.getItems(blog.getUrl());
			for (Item item : items) {
				List<Item> findByBlogAndLink = itemRepository.findByBlogAndLink(blog, item.getLink());
				
				if (findByBlogAndLink.isEmpty()) {
					item.setBlog(blog);
					itemRepository.save(item);
				}
			}
		} catch (RssException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedDelay=3600000)
	public void reloadItems() {
		List<Blog> findAll = blogRepository.findAll();
		for (Blog blog : findAll) {
			saveItems(blog);
		}
	}
	
	public void save(Blog blog, String name) {
		User user = userRepository.findByName(name);
		blog.setUser(user);
		blogRepository.save(blog);
		saveItems(blog);
	}
	
	@PreAuthorize("#blog.user.name == authentication.name or hasRole('ROLE_ADMIN')")
	public void delete(@P("blog") Blog blog) {
		blogRepository.delete(blog);
	}

	public Blog findOne(int id) {
		return blogRepository.findOne(id);
	}
}
