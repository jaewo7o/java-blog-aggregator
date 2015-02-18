package com.jaewoo.jba.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.jaewoo.jba.entity.Blog;
import com.jaewoo.jba.entity.Item;
import com.jaewoo.jba.entity.Role;
import com.jaewoo.jba.entity.User;
import com.jaewoo.jba.repository.BlogRepository;
import com.jaewoo.jba.repository.ItemRepository;
import com.jaewoo.jba.repository.RoleRepository;
import com.jaewoo.jba.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findOne(int id) {
		return userRepository.findOne(id);
	}

	@Transactional
	public User findOneWithBlog(int id) {
		User user = userRepository.findOne(id);
		
		List<Blog> blogs = blogRepository.findByUser(user);
		for (Blog blog : blogs) {
			List<Item> items = itemRepository.findByBlog(blog, new PageRequest(0, 10, Direction.DESC, "publishedDate"));
			blog.setItems(items);
		}
		user.setBlogs(blogs);
		
		return user;
	}
	
	public void save(User user) {
		user.setEnabled(true);
		
		List<Role> roles = new ArrayList<Role>();
		Role role = roleRepository.findByName("ROLE_USER");
		roles.add(role);
		user.setRoles(roles);
		
		userRepository.save(user);
	}

	public User findOneWithBlog(String name) {
		User user = userRepository.findByName(name);
		return findOneWithBlog(user.getId());
	}

	public void delete(int id) {
		userRepository.delete(id);
	}

	public User findOne(String username) {
		return userRepository.findByName(username);
	}
}