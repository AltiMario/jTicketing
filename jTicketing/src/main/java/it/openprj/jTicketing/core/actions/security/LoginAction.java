/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.blogic.exceptions.AccountNotActivatedException;
import it.openprj.jTicketing.blogic.exceptions.AuthenticationException;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.blogic.utilities.encrypt.DataEncrypt;
import it.openprj.jTicketing.core.common.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class LoginAction extends Action {
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = null;
		HttpSession session = request.getSession();

		String username = (String) PropertyUtils.getSimpleProperty(form, "username");
		String remindme = (String) PropertyUtils.getSimpleProperty(form, "remindme");
	
		try{
		  SecurityMgr service = ServicesFactory.getInstance().getSecurityMgr();
		  user = service.authenticate(username, DataEncrypt.getInstance().encrypt(request.getParameter("password")));
		  session.setAttribute("user", user);
		  session.setAttribute("roles", user.getRoles());
		} catch (AuthenticationException auex){
			ActionMessages messages = new ActionMessages(); 
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.wrongUserIdOrPassword"));
			saveMessages(request, messages);
		    return mapping.findForward("fail");
		} catch (AccountNotActivatedException anae){
			ActionMessages messages = new ActionMessages(); 
			messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.accountNotActivated"));
			saveMessages(request, messages);
		    return mapping.findForward("activateAccount");
		} 
		
		// Salvo i cookie su richiesta
		if (remindme != null && remindme.trim().equalsIgnoreCase("X")) {
			Cookie usernameCookie = new Cookie("jTicketingUsername", username);
			usernameCookie.setMaxAge(60 * 60 * 24 * 30); // 30 day expiration
			response.addCookie(usernameCookie);
			Cookie firstNameCookie = new Cookie("jTicketingFirstName", user.getFirstName());
			firstNameCookie.setMaxAge(60 * 60 * 24 * 30); // 30 day expiration
			response.addCookie(firstNameCookie);
			Cookie lastNameCookie = new Cookie("jTicketingLastName", user.getLastName());
			lastNameCookie.setMaxAge(60 * 60 * 24 * 30); // 30 day expiration
			response.addCookie(lastNameCookie);
		}
		
		return mapping.findForward("success");
	}
}
