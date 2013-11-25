/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.dwr;

import java.io.IOException;
import javax.servlet.ServletException;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class CaptchaDWR {
	
	public String getInclude() throws ServletException, IOException {
		
		WebContext wctx = WebContextFactory.get();
		return wctx.forwardToString("/pages/captcha/captchabox.jsp");
	}
	
}
