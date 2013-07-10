package com.baysearch.config

class ApplicationPropertiesTest extends GroovyTestCase {
  void testThis(){
    def siteType = 'prod'
    ApplicationProperties ap
    ap = new ApplicationProperties(siteType)
    println ap.properties

  }
}
