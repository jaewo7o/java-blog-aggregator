package com.jaewoo.jba.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;

import org.springframework.stereotype.Service;

import com.jaewoo.jba.entity.Item;
import com.jaewoo.jba.exception.RssException;
import com.jaewoo.jba.rss.ObjectFactory;
import com.jaewoo.jba.rss.TRss;
import com.jaewoo.jba.rss.TRssChannel;
import com.jaewoo.jba.rss.TRssItem;

@Service
public class RssService {
	public List<Item> getItems(Source source) throws RssException {
		List<Item> items = new ArrayList<Item>();
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			JAXBElement<TRss> jaxbElement = unmarshaller.unmarshal(source, TRss.class);
			TRss rss = jaxbElement.getValue();
			
			List<TRssChannel> channels = rss.getChannel();
			for (TRssChannel channel : channels) {
				List<TRssItem> rssItems = channel.getItem();
				for (TRssItem tRssItem : rssItems) {
					Item item = new Item();
					item.setTitle(tRssItem.getTitle());
					item.setDescription(tRssItem.getDescription());
					item.setLink(tRssItem.getLink());
					
					Date pubDate = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH).parse(tRssItem.getPubDate());
					item.setPublishedDate(pubDate);
					items.add(item);
				}
			}
		} catch (JAXBException e) {
			throw new RssException(e);
		} catch (ParseException e) {
			throw new RssException(e);
		}
		
		return items;
	}
}
