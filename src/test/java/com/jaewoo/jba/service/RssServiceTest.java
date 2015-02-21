package com.jaewoo.jba.service;

import java.io.File;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jaewoo.jba.entity.Item;
import com.jaewoo.jba.exception.RssException;

public class RssServiceTest {
	
	private RssService rssService;

	@Before
	public void setUp() throws Exception {
		rssService = new RssService();
	}

	@Test
	public void testGetItemsFile() throws RssException {
		List<Item> items = rssService.getItems(new File("src/test/resource/javavids.xml"));
		System.out.println(items.toString());
		System.out.println(items.size());
	}

}
