/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.frontend.actions;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.CalendarioEventi;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.Turno;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ProcessTicketSearchAction extends Action {
	public ProcessTicketSearchAction() {
		super();
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		String uidLuoghiInteresse = request.getParameter("uid");
		String uidTicket = request.getParameter("uidTicket");
		String iYear = request.getParameter("iYear");
		String iMonth = request.getParameter("iMonth");
		String iDay = request.getParameter("iDay");

		CalendarioEventi calendarioEventi = (CalendarioEventi) session.getAttribute("calendarioEventi" + iMonth + iYear);

		Calendar ca = new GregorianCalendar();
		// E' il primo giro devo calcolare anno e mese
		if (iYear == null && iMonth == null) {
			int iTYear = ca.get(Calendar.YEAR);
			int iTMonth = ca.get(Calendar.MONTH);

			iYear = String.valueOf(iTYear);
			iMonth = String.valueOf(iTMonth);
		}

		// Catturo il tipo di ticket selezionare per mostrarne il dettaglio
		ArrayList<Ticket> listaTickets = (ArrayList<Ticket>) session.getAttribute("listaTickets");
		Ticket ticket = null;
		for (int i = 0; i < listaTickets.size(); i++) {
			ticket = listaTickets.get(i);
			if (ticket.getUid().equalsIgnoreCase(uidTicket)) {
				break;
			}
		}
		
		session.setAttribute("iYear", iYear);
		session.setAttribute("iMonth", iMonth);
		session.setAttribute("iDay", iDay);
		session.setAttribute("uid", uidLuoghiInteresse);
		session.setAttribute("uidTicket", uidTicket);
		session.setAttribute("ticket", ticket);
		HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped = (HashMap<String, ArrayList<TicketAcquistato>>) session.getAttribute("purchasedticketGrouped");
		
		try {
			DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
			calendarioEventi = service.searchCalendarioMese(iMonth, iYear, uidLuoghiInteresse, uidTicket);
			if (iDay != null) {
				for (int cont = 0 ; cont < calendarioEventi.getTurni(iDay + (Integer.parseInt(iMonth)+ 1)  + iYear).size() ; cont++) {
					long uidTurnoLong = Long.parseLong(calendarioEventi.getTurni(iDay + (Integer.parseInt(iMonth)+ 1)  + iYear).get(cont).getUidTaOrarioEventi());
					int quantitaAquistata = 0;
					
					if (purchasedticketGrouped == null) {
						purchasedticketGrouped = new HashMap<String, ArrayList<TicketAcquistato>>();
					}
					
					Iterator<String> iter = purchasedticketGrouped.keySet().iterator();
					while (iter.hasNext()) {
						String keyMap = iter.next();
						if (purchasedticketGrouped.get(keyMap).get(0).getUidTurno() == uidTurnoLong) {
							quantitaAquistata = quantitaAquistata + purchasedticketGrouped.get(keyMap).size();
						}
					}
					
					calendarioEventi.getTurni(iDay + (Integer.parseInt(iMonth)+ 1)  + iYear).get(cont).setQuantitaAquistata(quantitaAquistata);
				}
			}
			session.setAttribute("calendarioEventi" + iMonth + iYear, calendarioEventi);
		} catch (SystemException se) {
			return mapping.findForward("fail");
		}

		return mapping.findForward("success");
	}
}
