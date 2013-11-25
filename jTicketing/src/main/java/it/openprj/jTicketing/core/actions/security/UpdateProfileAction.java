/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.core.forms.security.ProfileForm;
import it.openprj.jTicketing.core.mailer.Mailer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Permette la registrazione di un nuovo utente generico nel database
 * 
 * 
 */
public final class UpdateProfileAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecurityMgr service = null;
		User user = null;
		User newUser = null;
		ActionMessages messages = new ActionMessages();
		try {
			service = ServicesFactory.getInstance().getSecurityMgr();
			newUser = service.newUser();
			// Recuper i dati attuali dell'utente
			user = (User) request.getSession().getAttribute("user");
			ProfileForm profileForm = (ProfileForm) form;
			newUser.setEmail(profileForm.getEmail());
			newUser.setFirstName(profileForm.getFirstname());
			newUser.setLastName(profileForm.getLastname());

			// Controllo emails corrispondenti
			if (newUser.getEmail()!=null &&  !newUser.getEmail().equals("") &&
                                profileForm.getEmailreply()!=null && !profileForm.getEmailreply().equals("") &&
                                !profileForm.getEmail().trim().equalsIgnoreCase(profileForm.getEmailreply().trim())) {
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.emailAreDifferent"));
				saveMessages(request, messages);
				return mapping.findForward("fail");
			}
			
			//Controllo se e' stata inserita una nuova email
			if(!newUser.getEmail().equalsIgnoreCase(user.getEmail())){
				if(service.getUserByEmail(newUser.getEmail())==null){
					user.setEmail(newUser.getEmail());
				}else{
				    messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.emailArchivied"));
				    saveMessages(request, messages);
				    return mapping.findForward("fail");
				}
			}
			
			if (newUser.getFirstName().trim().length() > 0)
				user.setFirstName(newUser.getFirstName());
			if (newUser.getLastName().trim().length() > 0)
				user.setLastName(newUser.getLastName());
			
			
			service.updateUser(user);
			Mailer.sendProfileUpdatedNotify(user);

		} catch (SystemException se) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sendEmailProblem"));
			saveMessages(request, messages);
			return mapping.findForward("fail");

		}
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.updateAccountOK"));
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.sentEmailNotification"));
		saveMessages(request, messages);
		// pulisco il form dalla sessione
		request.getSession().removeAttribute("profileForm");
		return mapping.findForward("success");
	}

}
