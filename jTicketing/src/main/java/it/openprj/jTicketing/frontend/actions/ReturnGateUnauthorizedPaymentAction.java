/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.frontend.actions;

import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BuyProcessMgr;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ReturnGateUnauthorizedPaymentAction extends Action {
	public ReturnGateUnauthorizedPaymentAction() {
		super();
	}

	@Override
	public ActionForward execute (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ActionMessages messages = new ActionMessages();
		messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.paymentFailure"));
		saveMessages(session, messages);
		//Non e' stato autorizzato devo eliminare tutti i biglietti lockati sul DB
		//PER PRIMA COSA CONTROLLO SE L'UTENTE e' LOGGATO ALTRIMENTI LO FORWARDO ALLA LOGIN/REGISTRAZIONE
		User user=(User)session.getAttribute("user");
		ArrayList<Ticket> listaTickets = (ArrayList<Ticket>) session.getAttribute("listaTickets");
		HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped = (HashMap<String, ArrayList<TicketAcquistato>>) session.getAttribute("purchasedticketGrouped");
		BuyProcessMgr service = ServicesFactory.getInstance().getBuyProcessMgr();
		//service.deleteTicketAcquistatabili(purchasedticketGrouped);
        return mapping.findForward("purchaseUnauthorized");
    }

}