/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ProcessValidazioneTicketAction extends DispatchAction {

	public ProcessValidazioneTicketAction() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ActionForward get(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		invalidaSceltePrecedenti(session);
		ArrayList<LuogoInteresse> listaLuoghiInteresse = (ArrayList<LuogoInteresse>) session
				.getAttribute("listaLuoghiInteresse");
		// Leggo i luoghi di interesse legati all'utente dal DB
		User user = (User) session.getAttribute("user");
		BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
		try {
			listaLuoghiInteresse = (ArrayList<LuogoInteresse>) service.searchLuoghiOperatore(user);
			if (listaLuoghiInteresse == null || listaLuoghiInteresse.size() == 0) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("err.029"));
				saveMessages(session, messages);
				return mapping.findForward("failPrev");
			}
			session.setAttribute("listaLuoghiInteresse", listaLuoghiInteresse);
		} catch (SystemException se) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.priceIsRequired"));
			saveMessages(session, messages);
			return mapping.findForward("failPrev");
		}
		return mapping.findForward("refresh");
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ArrayList<LuogoInteresse> listaLuoghiInteresse = (ArrayList<LuogoInteresse>) session.getAttribute("listaLuoghiInteresse");

		String selLuogoInteresse = (String) PropertyUtils.getSimpleProperty(form, "selLuogoInteresse");
		String btnAvanti = (String) PropertyUtils.getSimpleProperty(form, "btnAvanti");
        request.setAttribute("dispatch", "wizard");
		if ((btnAvanti != null && btnAvanti.equalsIgnoreCase("Forward >")) && (selLuogoInteresse == null || selLuogoInteresse.trim().length() == 0)) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.selectPlaceAndPushButtonToValidate"));
			saveMessages(session, messages);
			return mapping.findForward("fail");
		} else {
			// Leggo i luoghi di interesse legati all'utente dal DB
			User user = (User) session.getAttribute("user");
			BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
			try {
                listaLuoghiInteresse = (ArrayList<LuogoInteresse>) service.searchLuoghiOperatore(user);
				if (listaLuoghiInteresse == null || listaLuoghiInteresse.size() == 0) {
					messages.add(Constants.MESSAGES_ERROR, new ActionMessage("err.029"));
					saveMessages(session, messages);
					return mapping.findForward("failPrev");
				}
				session.setAttribute("listaLuoghiInteresse", listaLuoghiInteresse);
			} catch (SystemException se) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.priceIsRequired"));
				saveMessages(session, messages);
				return mapping.findForward("failPrev");
			}
		}
		if (btnAvanti != null && btnAvanti.equalsIgnoreCase("Forward >")) {
			// A controlli fatti inserisco in sessione il luogo selezionato
			LuogoInteresse luogoInteresse = listaLuoghiInteresse.get(Integer.parseInt(selLuogoInteresse));
            session.setAttribute("luogoInteresse", luogoInteresse);
			return mapping.findForward("success");
		}
		return mapping.findForward("fail");
	}

	public ActionForward validateTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ActionMessages messages = new ActionMessages();
		BackEndMgr backEndMgr = ServicesFactory.getInstance().getBackEndMgrMgr();
		LuogoInteresse luogoInteresse = (LuogoInteresse)session.getAttribute("luogoInteresse");
		String codiceDiVerifica = request.getParameter("codiceDiVerifica");
		List<TicketAcquistato> ticketAcquistati = backEndMgr.validateTicket(Long.valueOf(luogoInteresse.getUid()),codiceDiVerifica);

		if (ticketAcquistati == null || ticketAcquistati.size()==0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.noTicketBuy"));
			saveMessages(session, messages);
		} else if (ticketAcquistati.size()==1 && ticketAcquistati.get(0).getConvalidato().equals("Y")) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.ticketCodeIsInvalid"));
			saveMessages(session, messages);
		} 
//		else {
//			messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.ticketCodeIsOk"));
//			saveMessages(session, messages);
//		}

		session.setAttribute("ticketAcquistati", ticketAcquistati);
		
		session.setAttribute("codiceDiVerifica", codiceDiVerifica);

		return mapping.findForward("success");
	}
	
	
	public ActionForward annullaTicket(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ActionMessages messages = new ActionMessages();
		BackEndMgr backEndMgr = ServicesFactory.getInstance().getBackEndMgrMgr();
		LuogoInteresse luogoInteresse = (LuogoInteresse)session.getAttribute("luogoInteresse");
		String codiceDiVerifica = (String)session.getAttribute("codiceDiVerifica");
		List<TicketAcquistato> ticketAcquistati =(List<TicketAcquistato>) session.getAttribute("ticketAcquistati");
		String selTicket = (String) PropertyUtils.getSimpleProperty(form, "selTicket");
		TicketAcquistato ticketAcquistato =ticketAcquistati.get(Integer.parseInt(selTicket));

		if (ticketAcquistato==null || ticketAcquistati == null || ticketAcquistati.size()==0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.noTicketBuy"));
			saveMessages(session, messages);
		} else if (ticketAcquistato.getConvalidato().equals("Y")) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.ticketCodeIsInvalid"));
			saveMessages(session, messages);		
		} else {
			ticketAcquistato=backEndMgr.annullaTicket(ticketAcquistato.getUid());
			ticketAcquistati.remove(Integer.parseInt(selTicket));
			ticketAcquistati.add(Integer.parseInt(selTicket),ticketAcquistato);

			messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.ticketValidationIsOk"));
			saveMessages(session, messages);
		}		
		
		session.setAttribute("ticketAcquistati", ticketAcquistati);
		
		session.removeAttribute("codiceDiVerifica");

		return mapping.findForward("success");
	}

	private void invalidaSceltePrecedenti(HttpSession session) {		
		LuogoInteresse luogoInteresse = (LuogoInteresse) session.getAttribute("luogoInteresse");
		if (luogoInteresse != null) {
			session.removeAttribute("luogoInteresse");
		}	
		String codiceDiVerifica = (String) session.getAttribute("codiceDiVerifica");
		if (codiceDiVerifica != null) {
			session.removeAttribute("codiceDiVerifica");
		}	
		List<TicketAcquistato> ticketAcquistati = (List<TicketAcquistato>) session.getAttribute("ticketAcquistati");
		if (ticketAcquistati != null) {
			session.removeAttribute("ticketAcquistati");
		}			
	}

}