package com.baysearch.restlet.server

import com.baysearch.restlet.app.BAYSearchApplication
import org.restlet.Component

/**
 * Created with IntelliJ IDEA.
 * User: josephswager
 * Date: 5/18/13
 * Time: 3:59 PM
 * To change this template use File | Settings | File Templates.
 */
class BAYSearchServer extends Component {
  BAYSearchServer() throws Exception {
    setName("Restful Application that returns query results of crawled Site's Index")
    getDefaultHost().attach(new BAYSearchApplication())
  }
}
