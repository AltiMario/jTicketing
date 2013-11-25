/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.blogic.exceptions.AlreadyActivatedException;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.exceptions.UserNotFoundException;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.core.mailer.Mailer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class ActivateAccoutAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecurityMgr service = null;
		String activationCode = null;
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		try {
			//Gestione attivazione da email
			activationCode=request.getParameter("activationCode");			
			service = ServicesFactory.getInstance().getSecurityMgr();
			//Procedo all'attivazione dell'utente
			user=service.activateUser(activationCode);
			Mailer.sendWelcomeMail(user);
		} catch (SystemException se) {
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sendEmailProblem"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		} catch (UserNotFoundException unfe){
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.activationCodeIsInvalid"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		} 
		catch (AlreadyActivatedException aae){
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.activactionCodeAlreadyInUse"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		} 
		ActionMessages messages = new ActionMessages();
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.AccountActivationOk"));
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
