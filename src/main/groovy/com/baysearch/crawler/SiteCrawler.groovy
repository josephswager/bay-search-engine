package com.baysearch.crawler

import javolution.util.FastSet
import org.apache.lucene.index.IndexWriter

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Swager
 * Date: 3/18/13
 * Time: 3:37 PM
 * @author Joseph Swager
 */
class SiteCrawler {
  static IndexWriter writer
  static FastSet indexed
  static def beginDomain

  /**
   *
   */
  SiteCrawler() {
  }

  /**
   *
   */
  def firstCrawl() {

  }

  /**
   * @param url
   * @return
   */
  private static def Domain(def url) {
    def firstDot = url.indexOf(".")
    def lastDot = url.lastIndexOf(".")
    return url.substring(firstDot + 1, lastDot)
  }
}
