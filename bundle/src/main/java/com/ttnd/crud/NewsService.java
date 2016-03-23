package com.ttnd.crud;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ttnd.model.Feed;
import com.ttnd.model.FeedMessage;
import com.ttnd.model.RSSFeedParser;

/**
 * News Service which is executed every 30 minutes , it fetches the news and check whether
 * it is existing or not and handles it like wise. 
 */
@Component
@Service(value = Runnable.class)
@Property(name = "scheduler.period", longValue = 1800)
public class NewsService implements Runnable{

	@Reference
	private ResourceResolverFactory resolverFactory;
	private Logger LOGGER = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("deprecation")
	public void run() {
		ResourceResolver resourceResolver = null;
		Map<String, Object> properties = new HashMap<String, Object>();
		CrudOperations crudOperations = new CrudOperations();
		String rssLink = null;
		try {
			resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
			Resource newsFeed = resourceResolver.getResource("/content/newsfeed");
			
			if(newsFeed == null){
				properties.put("jcr:primaryType", "nt:unstructured");
				properties.put("sling:resourceType", "newsfeed");
				crudOperations.create(resourceResolver,"/content" ,"newsfeed",properties);
			}
			
			Resource newsData = resourceResolver.getResource("/content/newsdata");
			
			if (newsData != null && newsData.getValueMap().get("rssLink") != null) {
				rssLink = newsData.getValueMap().get("rssLink").toString();
			} else {				
				rssLink = "http://timesofindia.indiatimes.com/rssfeedstopstories.cms";
			}
							
			LOGGER.info("RSS Link fetched is -->>   >> "+rssLink);
			
			RSSFeedParser parser = new RSSFeedParser(rssLink);
			Feed feed = parser.readFeed();
			System.out.println(feed);

			for (FeedMessage message : feed.getMessages()) {
				properties = new HashMap<String, Object>();
				properties.put("jcr:primaryType", "nt:unstructured");
				properties.put("sling:resourceType", "newspage");
				properties.put("jcr:title", message.getTitle());
				properties.put("jcr:description", message.getDescription());
				properties.put("id",message.getGuid());
				properties.put("published", message.getDate());

				String newsTitle = message.getTitle().toLowerCase().trim().replaceAll("[^\\w]", "-");
				
				if(!compareGuid(resourceResolver, message.getGuid())){
					crudOperations.create(resourceResolver,"/content/newsfeed", newsTitle, properties);
				}
			}
		} catch (LoginException e) {
			LOGGER.error("Login Exception {}", e);
		} 
	}
	
	//Function compares the GUID and returns true if the news already exists
	public boolean compareGuid(ResourceResolver resourceResolver,String guid){
		Resource resource = resourceResolver.getResource("/content/newsfeed");
		Iterator<Resource> it = resource.listChildren();
		
		while(it.hasNext()){
			Resource pages = it.next();
			String id = pages.getValueMap().get("id").toString();
			if(guid.compareTo(id) == 0)
				return true;
		}
		return false;
	}
}
