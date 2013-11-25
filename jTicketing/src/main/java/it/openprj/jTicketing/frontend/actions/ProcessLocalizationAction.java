/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.frontend.actions;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.Globals;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProcessLocalizationAction extends Action {

	public ProcessLocalizationAction() {
		super();
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}

		Locale locale = request.getLocale();

		String language = request.getParameter("language");
		String country = request.getParameter("country");

		if ((language != null && language.length() > 0) && (country != null && country.length() > 0)) {
			locale = new java.util.Locale(language, country);
		} else if (language != null && language.length() > 0) {
			locale = new java.util.Locale(language, "");
		}

		session.setAttribute(Globals.LOCALE_KEY, locale);

		return mapping.findForward("success");
	}

}
