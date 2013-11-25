/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.filters;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

public class LogonFilter extends Filter {

	@Override
	public String description() {
		return null;
	}

	@Override
	public void doFilter(HttpExchange arg0, Chain arg1) throws IOException {
		 filterConfig.getServletContext()
         .log(":: PostLoginFilter - doFilter");
		 arg1.doFilter(arg0);
	}

	protected FilterConfig filterConfig = null;
	
    public void destroy() {
       filterConfig.getServletContext()
          .log(":: PostLoginFilter - destroy");
       filterConfig = null;
    }
 
    public void init(FilterConfig config) throws ServletException {
       filterConfig = config;
       filterConfig.getServletContext().log(":: PostLoginFilter - init");
    }

}
