/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.exceptions.UserNotFoundException;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.blogic.utilities.RandomPassword;
import it.openprj.jTicketing.blogic.utilities.encrypt.DataEncrypt;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.core.mailer.Mailer;
import java.util.Locale;
import java.util.ResourceBundle;

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

public final class RecoveryPasswordAction extends Action {

    private static Logger log = Logger.getLogger(RecoveryPasswordAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SecurityMgr service = null;
		User user = null;
		try {
			Locale lingua = Locale.getDefault();
			ResourceBundle resources = ResourceBundle.getBundle("resources.config");
            String server = request.getServerName()+":"+request.getServerPort()+request.getContextPath();
			String username = (String) PropertyUtils.getSimpleProperty(form, "userid");
			service = ServicesFactory.getInstance().getSecurityMgr();
			user = service.getUserByUsername(username);
            
			int charsNumber=8;
            try {
            	charsNumber=Integer.parseInt(resources.getString("password_chars_number"));
            } catch (NumberFormatException ex) {
                log.debug("Wrong parameter format.Should be an Integer");
                charsNumber=8;
            }
            
            String nuovaPassword = RandomPassword.getRandomString(charsNumber);
            user.setUserPass(DataEncrypt.getInstance().encrypt(nuovaPassword));
            service.updateUser(user);
            user.setUserPass(nuovaPassword);
			Mailer.sendPassword(user,server);
		} catch (SystemException se) {
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.userIdNotValid"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		} catch(UserNotFoundException unfe){
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.userIdNotValid"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		}
		ActionMessages messages = new ActionMessages();
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.sendPassword"));
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
