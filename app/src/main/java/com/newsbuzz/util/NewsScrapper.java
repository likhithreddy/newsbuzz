package com.newsbuzz.util;

import com.newsbuzz.entity.News;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsScrapper {

	List<News> theNews;
	private String cat;

	private void getNews(String category) {
		theNews = new ArrayList<>();
		try {
			
			Document doc = Jsoup.connect("https://www.inshorts.com/en/read/" + category).userAgent("Mozilla/17.0").get();
			Elements newsCards = doc.select(".news-card");
			for(Element newsCard : newsCards) {
				
				// Extracting the title of the card 
				String title = newsCard.select(".news-card-title").select("a").text();
				title = title.substring(0, title.length()-6);
				
				// Extracting the image url
				String imageUrl = newsCard.select(".news-card-image").attr("style").split("'")[1];
				
				// Extracting the url
				String url = "https://www.inshorts.com" + newsCard.select(".news-card-title").select("a").attr("href");
				
				// Extracting the content
				String content = newsCard.select(".news-card-content").select("div").text().split(". short by")[0] + ".";
				
				// Extracting the author
				String author = newsCard.selectFirst(".news-card-author-time").select("span.author").text();
				
				// Extracting the date
				String date = newsCard.selectFirst(".news-card-content").select("span.date").text();
				
				// Extracting the time
				String time = newsCard.selectFirst(".news-card-content").select("span.time").text();
				
				// Extracting the read more url
				String readMoreUrl = newsCard.select("div.read-more").select("a").attr("href");
				
				News news = new News(title, imageUrl, url, content, author, date, time, readMoreUrl);
				theNews.add(news);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//We will call this method from Region class to set the selected category.

	public List<News> getTopNews(String cat) {
		this.cat="sports";
		getNews(cat);
		return theNews;
	}

	public List<News> getTopNews(List<String> categories) {
		List<News> resultNews = new ArrayList<>();
		if(categories==null || categories.size()==0) {
			return getTopNews("hatke");
		} else {
			for(String cat:categories) {
				resultNews.addAll(getTopNews(cat));
			}
		}
		return resultNews;
	}

	public List<News> getCategoryNews(String category) throws Exception {
		getNews(category);
		if(theNews.isEmpty()) {
			throw new Exception("News category not available - " + category);
		}
		return theNews;
	}
}
