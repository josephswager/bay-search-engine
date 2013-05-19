package com.baysearch.crawler.parser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * @author jswager
 */
public class LinkParser {
	def url
	URL[] linkArray
	def vector
	Document doc;

	/**
	 * Default Constructor
	 * @param Url
	 */
	public LinkParser(String Url) {
		url = Url
	}

	/**
	 * Extracts links from the current html page
	 * @return
	 */
	public URL[] ExtractLinks(){
		vector = new Vector()
		// build the document to parse based on the dom selection
		try {
			doc = Jsoup.parse(new URL(url), 15000); // it times out in 15,000 milliseconds = 15 seconds
		} catch (IOException e) {
			System.out.println("Jsoup parser failed to build or timed out on URL: " + url)
		}

		Elements robots = doc.select("meta[name=robots]")
		if(robots.attr("content").contains("NOFOLLOW")){
			linkArray = new URL[0]
			return (linkArray)
		} else {
			Elements links = doc.select("a[href]")

			for (Element link : links) {
				def currentLink =link.attr("href")
				try {
					if(currentLink.size() < 15 || currentLink.contains("javascript:history.go")){//test size to keep href such as # from being processed
					} else { vector.add( new URL((currentLink).replace(" ", "%20")) )} // href not processed continue through list
				} catch (MalformedURLException e) {
					println "ERROR >>>>>>>>> MalformedURL: " + link.toString()
				}
			}

			linkArray = new URL[vector.size ()]
			vector.copyInto (linkArray)

			return (linkArray)
		}
	}
}
