package com.jaewoo.jba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jaewoo.jba.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Integer>{

}
