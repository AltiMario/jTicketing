/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.backend.forms.TicketForm;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.core.common.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;

public class ProcessModificaVenditeTicketsAction extends DispatchAction {
	public ActionForward get(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		Ticket dettaglioTicket;
		String uidTicket = (String) request.getParameter("uidTicket");
		BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
		dettaglioTicket = (Ticket) service.dettaglioTicket(uidTicket);
		session.setAttribute("dettaglioTicket", dettaglioTicket);
		TicketForm myForm = (TicketForm) form;
		myForm.setBtnRimuovi("");
		
		return mapping.findForward("success");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		Ticket ticket = new Ticket ();
		String uidTicket = (String) request.getParameter("uid");
		
		ticket.setUid(uidTicket);
		ticket.setTitolo((String) request.getParameter("titolo"));
		ticket.setDescrizione((String) request.getParameter("descrizione"));
		
		TicketForm myForm = (TicketForm) form;
		ticket.setNomeFile(myForm.getTheFile().getFileName());
		ticket.setImmagine(myForm.getTheFile().getFileData());
		
		DataTablesMgr dataTablesMgr = ServicesFactory.getInstance().getDataTablesMgr();
				
		if (myForm.getBtnRimuovi() != null && myForm.getBtnRimuovi().equalsIgnoreCase("Delete")) {
			myForm.setBtnRimuovi("");
			int ticketVenduti = 0;
			ticketVenduti = dataTablesMgr.checkDeleteTicket(uidTicket);	
			
			if (ticketVenduti > 0) {
				ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.operationIsNotAvailable"));
				saveMessages(session, messages);
			} else {
				dataTablesMgr.deleteTicket(uidTicket);
				return mapping.findForward("successDel");
				//return mapping.findForward("home");
			}
		}
		
		dataTablesMgr.updateTicket(ticket);	
		Ticket dettaglioTicket;
		
		BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
		dettaglioTicket = (Ticket) service.dettaglioTicket(uidTicket);
		session.setAttribute("dettaglioTicket", dettaglioTicket);
		
		return mapping.findForward("success");
	}
}