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
  Set<URL> syncLinkSet
  Document doc;
  def domainURL

  /**
   * Default Constructor
   * @param Url
   * @param DomainURL
   */
  public LinkParser(Url, DomainURL) {
    url = Url
    domainURL = DomainURL
  }

  /**
   * Extracts links from the current html page
   * @return HashSet<URL> - all unique links of current page
   */
  public Set<URL> ExtractLinks() throws Exception {

    // build the document to parse based on the current URLs dom
    try {
      doc = Jsoup.parse(new URL(url), 15000); // it times out in 15,000 milliseconds = 15 seconds
    } catch (IOException e) {
      System.out.println("Jsoup parser failed to build or timed out on URL: " + url)
    }

    Elements robots = findSEOMetaRobotDataFollowRule(doc)
    if(robots.attr("content").contains("NOFOLLOW")){
      return returnEmptyURLSet()
    } else {
      Elements links = getAllLinkOfCurrentDocuemnt()
      syncLinkSet = buildURLLinkSetToReturn(links)

      return syncLinkSet
    }
  }

  /**
   *
   * @param doc
   * @return
   * @throws Exception
   */
  private Elements findSEOMetaRobotDataFollowRule(Document doc) throws Exception{
    return doc.select("meta[name=robots]")
  }

  /**
   *
   * @return
   * @throws NullPointerException
   */
  private Set<URL> returnEmptyURLSet() throws NullPointerException{
    return (new HashSet<URL>().empty)
  }

  /**
   *
   * @return
   * @throws Exception
   */
  private Elements getAllLinkOfCurrentDocuemnt() throws Exception{
    Elements links = doc.select("a[href]")
    return links
  }

  /**
   *
   * @param links
   * @return
   * @throws Exception
   */
  private Set<URL> buildURLLinkSetToReturn(Elements links) throws Exception {
    Set<URL> urlSetOfCurrentPagesLinks =  new HashSet<URL>()
    for (Element link : links) {
      String currentLink =link.attr("href")
      String theLink
      theLink = buildFullLink(currentLink)
      try {
        //test size to keep href such as # from being processed
        if( isLinkValid(theLink) ){
          urlSetOfCurrentPagesLinks.add(buildURLwithCorrectSpaceCoding(theLink))}
      } catch (MalformedURLException e) {
        println "ERROR >>>>>>>>> MalformedURL: " + link.toString()
      }
    }
    return urlSetOfCurrentPagesLinks
  }

  /**
   *
   * @param currentLink
   * @return
   */
  private def buildFullLink(String currentLink) {
    if ( currentLink.startsWith("http://") ) {
      return currentLink
    } else {
      if ( currentLink.startsWith("/") ) {
        return domainURL + currentLink
      } else {
        return domainURL + "/" + currentLink
      }
    }
  }

  /**
   *
   * @param theLink
   * @return
   */
  private def buildURLwithCorrectSpaceCoding(String theLink) {
    return new URL((theLink).replace(" ", "%20"))
  }

  /**
   *
   * @param theLink
   * @return
   */
  private boolean isLinkValid(String theLink) {
    return theLink.size() > 15 || !theLink.contains("javascript:history.go")
  }
}
