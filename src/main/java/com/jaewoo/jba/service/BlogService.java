package com.jaewoo.jba.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
					Item item1 = new Item();
					item1.setBlog(blog);
					item1.setTitle(item.getTitle());
					item1.setLink(item.getLink());
					item1.setPublishedDate(item.getPublishedDate());
					item1.setDescription(item.getDescription());
					itemRepository.save(item1);
				}
			}
		} catch (RssException e) {
			e.printStackTrace();
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
