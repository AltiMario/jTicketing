/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.filters;

import java.io.IOException;

import javax.servlet.Filter; 
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import it.openprj.jTicketing.blogic.model.entity.*;

public class AuthorizationFilter implements Filter
{
  public void init(FilterConfig filterConfig) throws ServletException {
		String roles = filterConfig.getInitParameter("roles");
		if (roles == null || "".equals(roles)) {
		  roleNames = new String[0];
		}
		else {
			roles.trim();
			roleNames = roles.split("\\s*,\\s*");
		}
		onErrorUrl = filterConfig.getInitParameter("onError");
		if (onErrorUrl == null || "".equals(onErrorUrl)) {
			onErrorUrl = "/index.jsp";
		}
  }

	public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) 
                 throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
    
    HttpSession session = req.getSession(); // get the session or create it
    User user = (User) session.getAttribute("user");
    ActionErrors errors = new ActionErrors();
    if (user == null) {
    	errors.add("Global Error", new ActionMessage( "error.authentication.required" ));
    }
    else {
    	boolean hasRole = false;
    	for (int i=0; i<roleNames.length; i++) {
    		if (user.getRoles().containsKey(roleNames[i])) {
    			hasRole = true;
    			break;
    		}
    	}
    	if (!hasRole) {
				errors.add("Global Error", new ActionMessage( "error.authorization.required" ));
    	}
		}

    if (errors.isEmpty()) {
			chain.doFilter(request, response);    	
    }
    else {    	
			req.setAttribute(Globals.ERROR_KEY, errors);
			req.getRequestDispatcher(onErrorUrl).forward(req, res);    	
    }
  }

	public void destroy() {
	}

	private String[] roleNames;
	private String onErrorUrl;
}