package com.baysearch.config

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Swager
 * Date: 7/5/13
 * Time: 1:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationProperties {

  def theConfig

  public ApplicationProperties(){

  }
  public ApplicationProperties(String env){
    ApplicationProperties ap = new ApplicationProperties()
    InputStream ip = GroovyClassLoader.getSystemResourceAsStream('src/main/groovy/com/baysearch/config/Siteconfig.groovy')
    ap.setTheConfig(new ConfigSlurper('prod').parse(ip.getText()))


  }

}
