package com.baysearch.config

/**
 * Created with IntelliJ IDEA.
 * User: Joseph Swager
 * Date: 7/5/13
 * Time: 1:22 AM
 * To change this template use File | Settings | File Templates.
 */
class ApplicationProperties {

  def config

  public ApplicationProperties(){

  }
  public ApplicationProperties(String env){
    ApplicationProperties ap = new ApplicationProperties()
    //noinspection GrDeprecatedAPIUsage
    ap.setConfig(new ConfigSlurper("$env").parse(new File('mysite.groovy').toURL()) as ConfigSlurper)


  }

}
