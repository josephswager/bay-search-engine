package com.baysearch.crawler

import org.apache.lucene.index.IndexWriter

/**
 * Created with IntelliJ IDEA.
 * User: josephswager
 * Date: 5/18/13
 * Time: 3:37 PM
 * To change this template use File | Settings | File Templates.
 */
class SiteCrawler {
  static IndexWriter writer
  static ArrayList indexed
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
