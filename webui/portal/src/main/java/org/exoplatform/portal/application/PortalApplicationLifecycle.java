package org.exoplatform.portal.application;

import javax.servlet.ServletContext;

import org.exoplatform.container.PortalContainer ;
import org.exoplatform.container.RootContainer ;
import org.exoplatform.container.SessionContainer;
import org.exoplatform.web.application.ApplicationLifecycle;
import org.exoplatform.webui.application.WebuiApplication;
import org.exoplatform.webui.application.WebuiRequestContext;

public class PortalApplicationLifecycle  implements  ApplicationLifecycle {
  
  public void init(WebuiApplication app) {
    PortalApplication papp = (PortalApplication) app ;
    ServletContext context  = papp.getServletConfig().getServletContext();
    RootContainer rootContainer = RootContainer.getInstance();
    PortalContainer pcontainer = rootContainer.getPortalContainer(context.getServletContextName());
    if(pcontainer == null) pcontainer = rootContainer.createPortalContainer(context);
    PortalContainer.setInstance(pcontainer) ;
  }
 
  public void beginExecution(WebuiApplication app, WebuiRequestContext rcontext) throws Exception {
    PortalApplication papp = (PortalApplication) app ;
    ServletContext context  = papp.getServletConfig().getServletContext();
    RootContainer rootContainer = RootContainer.getInstance();
    PortalContainer pcontainer = rootContainer.getPortalContainer(context.getServletContextName());
    PortalContainer.setInstance(pcontainer) ;
    SessionContainer.setInstance(pcontainer.getSessionManager().getSessionContainer(rcontext.getSessionId())) ;
  }

  @SuppressWarnings("unused")
  public void endExecution(WebuiApplication app, WebuiRequestContext rcontext) throws Exception {
    SessionContainer.setInstance(null) ;
    PortalContainer.setInstance(null) ;
  }
  
  public void destroy(WebuiApplication app) {
    PortalApplication papp = (PortalApplication) app ;
    ServletContext context  = papp.getServletConfig().getServletContext();
    RootContainer rootContainer = RootContainer.getInstance();
    PortalContainer pcontainer = rootContainer.getPortalContainer(context.getServletContextName());
    if(pcontainer.isStarted())  pcontainer.stop();
    rootContainer.removePortalContainer(context);
  }

}