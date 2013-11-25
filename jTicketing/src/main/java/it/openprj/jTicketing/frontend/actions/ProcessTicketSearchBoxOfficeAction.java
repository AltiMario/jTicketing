/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.frontend.actions;

import it.openprj.jTicketing.blogic.entity.AvailableDay;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.CalendarioEventi;
import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;

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

public class ProcessTicketSearchBoxOfficeAction extends Action {
	public ProcessTicketSearchBoxOfficeAction() {
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
		
        String uidLuoghiInteresse = (String) request.getParameter("uid");
		String uidTicket = request.getParameter("uidTicket");
		String iYear = request.getParameter("iYear");
		String iMonth = request.getParameter("iMonth");
		String iDay = request.getParameter("iDay");	
		
		DataTablesMgr serviceCalendario = ServicesFactory.getInstance().getDataTablesMgr();
		ArrayList<Ticket> listaTickets = (ArrayList<Ticket>) serviceCalendario.searchTickets(uidLuoghiInteresse);
		  ArrayList<AvailableDay> uidTicketForDay = new ArrayList<AvailableDay>();
		  
		  ArrayList<CalendarioEventi> listaCalendarioEventi = new ArrayList<CalendarioEventi>(); 
		  if(iDay == null){
			  Calendar ca = new GregorianCalendar();
			  iDay = String.valueOf(ca.get(Calendar.DATE));
		  }
		  
		  uidTicketForDay=(ArrayList<AvailableDay>)serviceCalendario.searchTicketForDay(iYear, iMonth, iDay, uidLuoghiInteresse);
		  listaTickets.removeAll(listaTickets);
		  for(int i = 0; i<uidTicketForDay.size(); i++ ){
			 Ticket ticket = new Ticket();
			 CalendarioEventi ce = new CalendarioEventi();
			 
			 ticket = serviceCalendario.searchTickets(uidTicketForDay.get(i).getAttractionId());
			 ce = serviceCalendario.searchCalendarioGiorno(iDay, iMonth, iYear, uidLuoghiInteresse, ticket.getUid());
			 
			 listaTickets.add(ticket);
			 listaCalendarioEventi.add(ce);
		  }
		  
		session.setAttribute("listaTickets", listaTickets);
		session.setAttribute("listaCalendarioEventi", listaCalendarioEventi);
		
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
		ArrayList<Ticket> listaTickets1 = (ArrayList<Ticket>) session.getAttribute("listaTickets");
		Ticket ticket = null;
		for (int i = 0; i < listaTickets1.size(); i++) {
			ticket = listaTickets1.get(i);
			if (ticket.getUid().equalsIgnoreCase(uidTicket)) {
				break;
			}
		}
		
		ArrayList<PrezzoCategoriaTicket> prezzoCategoriaTicket = new ArrayList<PrezzoCategoriaTicket>();
		prezzoCategoriaTicket = ticket.getPrezziCategorie(); 
		session.setAttribute("prezzoCategoriaTicketSel", prezzoCategoriaTicket);
		
		session.setAttribute("iYear", iYear);
		session.setAttribute("iMonth", iMonth);
		session.setAttribute("iDay", iDay);
		session.setAttribute("uid", uidLuoghiInteresse);
		session.setAttribute("uidTicket", uidTicket);
		session.setAttribute("ticket", ticket);
		HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped = (HashMap<String, ArrayList<TicketAcquistato>>) session.getAttribute("purchasedticketGrouped");
	 
		try {
			DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
			//calendarioEventi = service.searchCalendarioMese(iMonth, iYear, uidLuoghiInteresse, uidTicket);
			calendarioEventi = service.searchCalendarioMeseTotale(iMonth, iYear, uidLuoghiInteresse);
		
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
