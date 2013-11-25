/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProcessHomeAdminAction extends Action {
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session = request.getSession();
		session.setAttribute("session", "active");
		String username = "";
		String userAdminCASIntegration = "";
		
		try {
			ResourceBundle resources = ResourceBundle.getBundle("resources.config");
			userAdminCASIntegration = resources.getString("UserAdminCASIntegration");
		} catch (MissingResourceException mre) {
			System.err.println("resources.config not found");
		}
		
		try {
			username = (String) session.getAttribute("edu.yale.its.tp.cas.client.filter.user");
		} catch (Exception e) {
			return mapping.findForward("home");
		}
				
		if (username != null && username.equals(userAdminCASIntegration)) {
			User user = null;
			SecurityMgr service = ServicesFactory.getInstance().getSecurityMgr();
			user = service.authenticateAdmin("Admin");
			session.setAttribute("user", user);
			session.setAttribute("roles", user.getRoles());
			return mapping.findForward("success");
		} else {
			System.err.println("i dati di accesso non sono validi");
			return mapping.findForward("fail");
		}
	}
}