/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.blogic.exceptions.EmailNotFoundException;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.core.mailer.Mailer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

/**
 * Permette di spedire all'utente la username smarrita
 * 
 * 
 */
public final class RecoveryUseridAction extends Action {

        private static Logger log = Logger.getLogger(RecoveryUseridAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecurityMgr service = null;
                User user=null;
		try {
                    String server=request.getServerName()+":"+request.getServerPort()+request.getContextPath();
                    String email = (String) PropertyUtils.getSimpleProperty(form, "email");
                    String emailreply = (String) PropertyUtils.getSimpleProperty(form, "emailreply");

                    if(!email.trim().equalsIgnoreCase(emailreply.trim())){
                            ActionMessages messages = new ActionMessages();
                            messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.emailAreDifferent"));
                            saveMessages(request, messages);
                            return mapping.findForward("fail");
                    }
                    service= ServicesFactory.getInstance().getSecurityMgr();
                    user=service.getUserByEmail(email);
                    Mailer.sendUserid(user,server);
		} catch (SystemException se) {
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.userIdNotValid"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		} catch(EmailNotFoundException unfe){
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.emailNotArchivied"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		}
		ActionMessages messages = new ActionMessages();
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.sendUserID"));
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
