package org.kemri.wellcome.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.log4j.Logger;

public class InitListener implements ServletContextListener,
		ServletContextAttributeListener{
	
	public static String PREFIX = "context";
	protected final Logger log = Logger.getLogger(InitListener.class);
	
	public void contextInitialized(ServletContextEvent arg0) {
		String pr = arg0.getServletContext().getContextPath();
		ServletContext ctx = arg0.getServletContext();
		ctx.setAttribute(PREFIX, pr);		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent arg0) {

	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent arg0) {

	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent arg0) {

	}
}
