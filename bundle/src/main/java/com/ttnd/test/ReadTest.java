package com.ttnd.test;

import com.ttnd.model.Feed;
import com.ttnd.model.FeedMessage;
import com.ttnd.model.RSSFeedParser;

public class ReadTest {
	
  public static void main(String[] args) {
    RSSFeedParser parser = new RSSFeedParser("http://timesofindia.indiatimes.com/rssfeedstopstories.cms");
    Feed feed = parser.readFeed();
    System.out.println(feed);
    for (FeedMessage message : feed.getMessages()) {
      System.out.println(message);
    }
  }
} 
