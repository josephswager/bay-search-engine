package com.baysearch.crawler.document

import org.jsoup.select.Elements
import org.jsoup.Jsoup
import org.apache.lucene.document.*
import org.jsoup.nodes.Document as JDoc

/**
 * @author jswager
 */
class HTMLDocument {
  /**
   * Adds the current HTML document to the index for searching
   * includes (URL path, Title of Page, meta description of page and the body content of the page.)
   * @param url
   * @return The Current URL's HTML Document
   * @throws IOException
   * @throws InterruptedException
   */
  public static Document processDocument(String url) throws IOException, InterruptedException {

    // make a new, empty document
    Document doc = new Document()
    def title = ''
    def body = ''
    def content = ''
    JDoc dom = null

    //build the document to parse based on the dom selection
    try {
      dom = Jsoup.parse(new URL(url), 15000) // it times out in 15,000 milliseconds = 15 seconds
    } catch (IOException e) {
      println "failed to build or timed out Jsoup parser on URL: " + url
      e.printStackTrace()
    }

    Elements canonicalLink = dom.select("link[rel=canonical]")
    def canonical = canonicalLink.attr("href")

    if ( canonical.size() < 10 ) {
      // Add the url as a field named "path". Use a field that is indexed (i.e. searchable), but don't tokenize the field into words.
      doc.add(new Field("path", url, Field.Store.YES, Field.Index.ANALYZED_NO_NORMS))
    } else {
      doc.add(new Field("path", canonical, Field.Store.YES, Field.Index.ANALYZED_NO_NORMS))
    }

    // Grabs the title of the document and save to string for indexing
    try {
      title = dom.title()
    } catch (Exception e) {
      println "no title tag found in document"
      e.printStackTrace()
    }

    // Grabs the wrapper or content of the page. If these div ids do not exist the grabs the body of document
    // to be stored for indexing
    try {
      body = dom.select("div[id=products]").toString()
      if ( body.size() < 2 ) {
        body = dom.select("div[id=main]").toString()
      }
      if ( body.size() < 2 ) {
        body = dom.select("body").toString()
      }
    } catch (Exception e) {
      println "No body tag found in document"
      e.printStackTrace()
    }

    //Grabs and indexes the pages description from the meta data
    try {
      Elements metaLinks = dom.select("meta[name=description]")
      content = metaLinks.attr("content")
    } catch (Exception e) {
      println "No Meta Data SEO standards description found"
      e.printStackTrace()
    }

    // Add the pages meta description as a field that it can be searched and that is stored.
    //Field pageDescription = new Field("description", content, Field.Store.YES, Field.Index.ANALYZED)
    indexWithWeight(doc, "description", content, 1.6F)

    // Add the title as a field that it can be searched and that is stored.
    indexWithWeight(doc, "title", title, 1.8f)

    // Add the body of the html page as a field that it can be searched and that is stored.
    indexWithWeight(doc, "body", body, 0.3f)

    return doc
  }

  /**
   *
   * @param doc
   * @param name
   * @param toBeIndex
   * @param weight
   * @return
   */
  def static indexWithWeight(Document doc, String name, String toBeIndex, float weight) {
    try {
      def field = new Field(name, toBeIndex, Field.Store.YES, Field.Index.ANALYZED)
      field.setBoost(weight)// score multiplier for word matches on body
      doc.add(field)
    } catch (Exception e) {
      e.printStackTrace()
    }
  }

  /**
   *
   */
  HTMLDocument() {
  }
}
