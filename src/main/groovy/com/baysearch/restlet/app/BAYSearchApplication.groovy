package com.baysearch.restlet.app

import com.baysearch.restlet.actions.SearchResultsGrabber
import org.restlet.Application
import org.restlet.Restlet
import org.restlet.routing.Router

/**
 * Created with IntelliJ IDEA.
 * User: josephswager
 * Date: 5/18/13
 * Time: 4:04 PM
 * To change this template use File | Settings | File Templates.
 */
class BAYSearchApplication extends Application {
  /**
   *
   * @return
   */
  @Override
  Restlet createInboundRoot() {
    def router = new Router(getContext())

    router.attach("/result", SearchResultsGrabber.class)

    return router    //To change body of overridden methods use File | Settings | File Templates.
  }
}
