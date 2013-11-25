/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.blogic.exceptions.AuthenticationException;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.blogic.utilities.encrypt.DataEncrypt;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.core.mailer.Mailer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public final class UpdatePasswordAction extends Action {
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecurityMgr service = null;
		User user = null;

		ActionMessages messages = new ActionMessages();
		try {
			service = ServicesFactory.getInstance().getSecurityMgr();
			user = (User) request.getSession().getAttribute("user");
			String oldpassword = (String) PropertyUtils.getSimpleProperty(form, "oldpassword");
			String password = (String) PropertyUtils.getSimpleProperty(form, "password");
			String passwordreply = (String) PropertyUtils.getSimpleProperty(form, "passwordreply");
            try{
                        service.authenticate(user.getUserName(), DataEncrypt.getInstance().encrypt(oldpassword));
			}catch(AuthenticationException ae){
			messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.wrongCurrentPassword"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
			}
			
			if (!password.trim().equalsIgnoreCase(passwordreply.trim())) {
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.passwordsAreDifferent"));
				saveMessages(request, messages);
				return mapping.findForward("fail");
			}
			user.setUserPass(DataEncrypt.getInstance().encrypt(password));                        
			service.updateUser(user);
			Mailer.sendProfileUpdatedNotify(user);

		} catch (SystemException se) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sendEmailProblem"));
			saveMessages(request, messages);
			return mapping.findForward("fail");

		}
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.updatePasswordOK"));
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.sentEmailNotification"));
		saveMessages(request, messages);
		
		request.getSession().removeAttribute("profileForm");
		return mapping.findForward("success");
	}

}
