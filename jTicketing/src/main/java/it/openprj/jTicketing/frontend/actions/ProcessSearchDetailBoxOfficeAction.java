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
import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ProcessSearchDetailBoxOfficeAction extends Action {

    public ProcessSearchDetailBoxOfficeAction() {
        super();
    }

    @SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
		}
		ArrayList<Ticket> listaTickets=null;
        String uidLuoghiInteresse = (String) request.getParameter("uid");
		session.setAttribute("uid", uidLuoghiInteresse);		
		if(session.getAttribute("listaLuoghiInteresse") == null) {
			ArrayList<LuogoInteresse> listaLuoghiInteresse = null;
			
			try {
				DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
				listaLuoghiInteresse = (ArrayList<LuogoInteresse>) service.searchLuoghiInteresse();
				session.setAttribute("listaLuoghiInteresse", listaLuoghiInteresse);
			} catch (SystemException se) {
				return mapping.findForward("fail");
			} 
		}
		
		ArrayList<LuogoInteresse> listaLuoghiInteresse=(ArrayList<LuogoInteresse>)session.getAttribute("listaLuoghiInteresse");
		LuogoInteresse dettaglioLuogoInteresse=null;
		try{
		  DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
	      listaTickets = (ArrayList<Ticket>) service.searchTickets(uidLuoghiInteresse);
		  for(int i=0; i<listaLuoghiInteresse.size(); i++) {
			  dettaglioLuogoInteresse=listaLuoghiInteresse.get(i);
			  if (dettaglioLuogoInteresse.getUid().equalsIgnoreCase(uidLuoghiInteresse)) {
				  break;
			  }
		  } 
		  
		  CalendarioEventi calendarioEventi = new CalendarioEventi();
		  Calendar ca = new GregorianCalendar();
		  String iDay = String.valueOf(ca.get(Calendar.DATE));
		  String iYear = String.valueOf(ca.get(Calendar.YEAR));
		  String iMonth = String.valueOf(ca.get(Calendar.MONTH));
		  
		  session.setAttribute("iDay", iDay);
		  session.setAttribute("iYear", iYear);
		  session.setAttribute("iMonth", iMonth);
		  
		  calendarioEventi = service.searchCalendarioMeseTotale(iMonth, iYear, uidLuoghiInteresse);
		  
		  ArrayList<AvailableDay> uidTicketForDay = new ArrayList<AvailableDay>();
		  ArrayList<CalendarioEventi> listaCalendarioEventi = new ArrayList<CalendarioEventi>();
		  uidTicketForDay=(ArrayList<AvailableDay>)service.searchTicketForDay(iYear, iMonth, iDay, uidLuoghiInteresse);
		  listaTickets.removeAll(listaTickets);
		  for(int i = 0; i<uidTicketForDay.size(); i++ ){
			 Ticket ticket = new Ticket();
			 CalendarioEventi ce = new CalendarioEventi();
			 ticket = service.searchTickets(uidTicketForDay.get(i).getAttractionId());
			 ce = service.searchCalendarioGiorno(iDay, iMonth, iYear, uidLuoghiInteresse, ticket.getUid());
			 listaCalendarioEventi.add(ce);
			 listaTickets.add(ticket);
		  }
		  session.setAttribute("listaCalendarioEventi", listaCalendarioEventi);
		  Ticket ticketTest = new Ticket();
		  ticketTest = null;
		  session.setAttribute("calendarioEventi"+iMonth+iYear, calendarioEventi);
		  session.setAttribute("dettaglioLuogoInteresse", dettaglioLuogoInteresse);
		  session.setAttribute("listaTickets", listaTickets);
		  session.setAttribute("ticket", ticketTest);
		} catch (SystemException se){
			ActionMessages messages = new ActionMessages(); 
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.systemFailure"));
			saveMessages(request, messages);
		    return mapping.findForward("fail");
		} 

        return mapping.findForward("success");
    }

}
