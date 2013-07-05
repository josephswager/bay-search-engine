package com.baysearch.crawler.parser

import javolution.util.FastSet
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import groovy.util.logging.*

/**
 * @author jswager
 */
@Log4j
public class LinkParser {
  def url
  FastSet<URL> syncLinkSet
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
   * @return HashSet < URL >  - all unique links of current page
   */
  public FastSet<URL> ExtractLinks() throws Exception {

    // build the document to parse based on the current URLs dom
    try {
      doc = buildDOM()
    } catch (IOException e) {
      logErrorStackTrace(e)
      log.info "Jsoup parser failed to build or timed out on URL: " + url
    }

    Elements robots = findSEOMetaRobotDataFollowRule(doc)
    if ( hasRobotNoFollow(robots) ) {
      return returnEmptyURLSet()
    } else {
      Elements links = getAllLinkOfCurrentDocument()
      syncLinkSet = buildURLLinkSetToReturn(links)

      return syncLinkSet
    }
  }

  /**
   *
   * @param robots
   * @return
   */
  private static boolean hasRobotNoFollow(robots) {
    final String NOFOLLOW = "NOFOLLOW"
    return robots.attr("content").contains(NOFOLLOW)
  }

  /**
   *
   * @return
   * @throws IOException
   */
  private Document buildDOM() throws IOException {
    return Jsoup.parse(new URL(url), 15000); // it times out in 15,000 milliseconds = 15 seconds
  }

  /**
   *
   * @param doc
   * @return
   * @throws Exception
   */
  private static Elements findSEOMetaRobotDataFollowRule(doc) throws Exception {
    return doc.select("meta[name=robots]")
  }

  /**
   *
   * @return
   * @throws NullPointerException
   */
  private static def returnEmptyURLSet() throws NullPointerException {
    return (new FastSet<URL>())
  }

  /**
   *
   * @return
   * @throws Exception
   */
  private Elements getAllLinkOfCurrentDocument() throws Exception {
    return doc.select("a[href]")
  }

  /**
   *
   * @param links
   * @return
   * @throws Exception
   */
  private FastSet<URL> buildURLLinkSetToReturn(Elements links) throws Exception {
    FastSet<URL> urlSetOfCurrentPagesLinks
    urlSetOfCurrentPagesLinks = new FastSet<URL>()
    for ( Element link : links ) {
      String currentLink = link.attr("href")
      String theLink
      theLink = buildFullLink(currentLink)
      try {
        //test size to keep href such as # from being processed
        if ( isLinkValid(theLink) ) {
          urlSetOfCurrentPagesLinks.add(buildURLwithCorrectSpaceCoding(theLink))
        }
      } catch (MalformedURLException e) {
        logErrorStackTrace(e)
        log.info "ERROR >>>>>>>>> MalformedURL: " + link.toString()
      }
    }
    return urlSetOfCurrentPagesLinks
  }

  /**
   *
   * @param currentLink
   * @return
   */
  private def buildFullLink(currentLink) {
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
  private static def buildURLwithCorrectSpaceCoding(theLink) {
    return new URL(URLEncoder.encode((theLink), "UTF-8"))
  }

  /**
   *
   * @param theLink
   * @return
   */
  private static boolean isLinkValid(theLink) {
    return theLink.size() > 15 || !theLink.contains("javascript:history")
  }

  /**
   *
   * @param currentLink
   * @return
   */
  private def isAnExcelFile(currentLink) {

    return (currentLink.attr("href").toString().toLowerCase().endsWith(".xls") ||
            currentLink.attr("href").toString().toLowerCase().endsWith(".xlsx"))
  }

  /**
   *
   * @param e
   * @return
   */
  private static logErrorStackTrace(final Exception e) {
    def error = e.printStackTrace().toString()
    log.error error
  }
}
