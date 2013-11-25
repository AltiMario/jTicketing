/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.frontend.actions;

import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.core.mailer.Mailer;
import it.openprj.jTicketing.frontend.forms.CartForm;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ReturnGateAuthorizedPaymentAction extends Action {
	public ReturnGateAuthorizedPaymentAction() {
		super();
	}

	@SuppressWarnings("unchecked")
	@Override
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		User user=(User)session.getAttribute("user");
		
		if(user.isBotteghino()){
			return mapping.findForward("calendar");
		}
		
		Mailer.sendSimplePurchasedTickets(user, (ArrayList<TicketAcquistato>) session.getAttribute("listForRenderTicketPurchased"));
		return mapping.findForward("purchaseCompleted");
    }
}