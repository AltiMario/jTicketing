/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.filters;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import it.openprj.jTicketing.blogic.model.entity.*;

public class AuthenticationFilter implements Filter
{
  public void init(FilterConfig filterConfig) throws ServletException {
  }
   
  public void doFilter(ServletRequest request,
                       ServletResponse response,
                       FilterChain chain) 
                 throws IOException, ServletException {
    HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
    
    HttpSession session = req.getSession(); // get the session or create it
    User user = (User) session.getAttribute("user");
    if (user == null) {
    	// redirect to the login page 
    	res.sendRedirect("/index.jsp");
    }
    else {
			chain.doFilter(request, response);
    }
  }

	public void destroy() {
	}
}