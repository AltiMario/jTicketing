/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.core.common.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;


/**
 * Classe che invalida la sessione ed effettua il logout
 * 
 */
public final class LogoutAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		//Invalido la sessione
		session.invalidate();
		ActionMessages messages = new ActionMessages(); 
		messages.add(Constants.RAPID_INFO, new ActionMessage("message.disconnect"));
		messages.add("connectionStatus", new ActionMessage("message.disconnect"));
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
