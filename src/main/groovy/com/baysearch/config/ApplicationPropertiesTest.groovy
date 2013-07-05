package com.baysearch.config

import com.baysearch.config.ApplicationProperties

class ApplicationPropertiesTest extends GroovyTestCase {

  void testSomething() {
    assert 1 == 1
    assert 2 + 2 == 4 : "We're in trouble, arithmetic is broken"
  }

  void test(){
    ApplicationProperties ap = new ApplicationProperties('prod')
    assert 'prod' == ap.config.site.hostname
  }
}
