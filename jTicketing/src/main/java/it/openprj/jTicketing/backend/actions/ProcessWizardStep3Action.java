/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.backend.forms.CalendarForm;
import it.openprj.jTicketing.blogic.model.entity.CalendarioEventi;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.Turno;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ProcessWizardStep3Action extends Action {

	public ProcessWizardStep3Action() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ActionMessages messages = new ActionMessages();
        if (session.getAttribute("listaTickets") == null || ((ArrayList<Ticket>) session.getAttribute("listaTickets")).size() == 0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.insertRequiredData"));
			saveMessages(session, messages);
			return mapping.findForward("failPrev");
		}
		CalendarForm calendarForm = (CalendarForm) form;
		// Controlli

		Ticket ticketSelezionato = null;
		ArrayList<Ticket> listaTickets = (ArrayList<Ticket>) session.getAttribute("listaTickets");
		ArrayList<Turno> listaTurni = (ArrayList<Turno>) session.getAttribute("listaTurni");
		HashMap<String, ArrayList<CalendarioEventi>> calendarioCompleto = null;
		String iYear = null;
		String iMonth = null;
		String uidLuoghiInteresse = request.getParameter("uid");
		// Cambio ticket
		if (calendarForm.getSelTicket() != null)
			ticketSelezionato = listaTickets.get(Integer.parseInt(calendarForm.getSelTicket()));

		iYear = request.getParameter("iYear");
		iMonth = request.getParameter("iMonth");
		if ((calendarForm.getIsChange() != null && calendarForm.getIsChange().equalsIgnoreCase("Change")) || (calendarForm.getIsChange() != null && (calendarForm.getIsChange().equalsIgnoreCase("Select")))) {
			// stato cambiato il ticket quindi memorizzo il tutto nel ticket e
			// resetto questo passaggio del wizard

			// � stato cambiato anno quindi memorizzo il tutto nel ticket e
			// resetto questo passaggio del wizard
			if ((iYear != null && session.getAttribute("iYear") != null && !iYear.equalsIgnoreCase(session.getAttribute("iYear").toString())) || (calendarForm.getSelTicket() != null && session.getAttribute("selectedTicket") != null && !calendarForm.getSelTicket().equalsIgnoreCase(session.getAttribute("selectedTicket").toString()))) {
				ArrayList<CalendarioEventi> listaMesi = calendarForm.getListaMesi();
				calendarioCompleto = listaTickets.get(Integer.parseInt(session.getAttribute("selectedTicket").toString())).getCalendarioCompleto();
				if (calendarioCompleto == null)
					calendarioCompleto = new HashMap<String, ArrayList<CalendarioEventi>>();
				calendarioCompleto.put(session.getAttribute("iYear").toString(), (ArrayList<CalendarioEventi>) listaMesi.clone());
				listaTickets.get(Integer.parseInt(session.getAttribute("selectedTicket").toString())).setCalendarioCompleto(calendarioCompleto);
				// Se presente gi� in sessione lo valorizzo con i dati presenti
				if (calendarioCompleto.containsKey(iYear.toString())) {
					// Rimuovo dal form la lista dei mesi dell'anno corrente
					calendarForm.getListaMesi().removeAll(listaMesi);
					// Inserisco nel form la lista dell'anno selezionato per il
					// ticket selezionato
					if (calendarForm.getSelTicket() != null && !calendarForm.getSelTicket().equalsIgnoreCase(session.getAttribute("selectedTicket").toString())) {
						// Il ticket � stato cambiato quindi carico il
						// calendario del ticket nuovo
						calendarioCompleto = listaTickets.get(Integer.parseInt(calendarForm.getSelTicket())).getCalendarioCompleto();
						if (calendarioCompleto == null)
							calendarioCompleto = new HashMap<String, ArrayList<CalendarioEventi>>();
					}
					// Insersco nel form il calendario adeguato
					if (calendarioCompleto != null && calendarioCompleto.containsKey(iYear.toString())) {
						calendarForm.getListaMesi().addAll(calendarioCompleto.get(iYear));
					} else {
						calendarForm.initialize();
					}
				} else {
					// Resetto il calendario per l'anno cambiato
					calendarForm.initialize();
				}
			}
		}

		Calendar ca = new GregorianCalendar();
		// E' il primo giro devo calcolare anno e mese
		if (iYear == null && iMonth == null) {
			int iTYear = ca.get(Calendar.YEAR);
			int iTMonth = ca.get(Calendar.MONTH);
			calendarForm.setSelMonth(String.valueOf(iTMonth + 1));
			iYear = String.valueOf(iTYear);
			iMonth = String.valueOf(iTMonth);
		}
		int days = ca.getActualMaximum(Calendar.DAY_OF_MONTH) + 1;
		session.setAttribute("iMonth", iMonth);
		session.setAttribute("iYear", iYear);

		session.setAttribute("selectedTicket", calendarForm.getSelTicket());
		session.setAttribute("ticketSelezionato", ticketSelezionato);

		calendarForm.setTickets(listaTickets);

		if (calendarForm != null && calendarForm.getIsChange() != null && calendarForm.getIsChange().equalsIgnoreCase("Save")) {
			// Valorizzo il bean dei tickets

			CalendarioEventi calendarioMese = null;
			// giorno, turni
			calendarioMese = calendarForm.getListaMesi().get(Integer.parseInt(iMonth));
			List<Turno> turniGiorno = null;
			for (int day = 1; day <= days; day++) {
				String[] turni = request.getParameterValues("selTurno_" + day);
				// Prendo con iMonth il mese giusto e aggiungo il giorno (day)
				// con i
				// relativi turni (Array di Array)

				if (turni != null) {
					turniGiorno = new ArrayList<Turno>(turni.length);
					for (int x = 0; x < turni.length; x++) {
						turniGiorno.add(listaTurni.get(Integer.parseInt(turni[x])));
					}
				} else {
					turniGiorno = new ArrayList<Turno>(0);
				}
				calendarioMese.getCalendarioEventi().put(String.valueOf(day), turniGiorno);
				calendarioMese.setMese(iMonth);
				calendarioMese.setAnno(iYear);
				calendarForm.getListaMesi().set(Integer.parseInt(iMonth), calendarioMese);
			}
		}
		
		if(calendarForm.getIsChange()!=null && calendarForm.getIsChange().equalsIgnoreCase("End")){
			//Setto il calendario dell'ultimo ticket inserito prima di proseguire
			ArrayList<CalendarioEventi> listaMesi = calendarForm.getListaMesi();
			calendarioCompleto = listaTickets.get(Integer.parseInt(session.getAttribute("selectedTicket").toString())).getCalendarioCompleto();
			if (calendarioCompleto == null)
				calendarioCompleto = new HashMap<String, ArrayList<CalendarioEventi>>();
			calendarioCompleto.put(session.getAttribute("iYear").toString(), (ArrayList<CalendarioEventi>) listaMesi.clone());
			listaTickets.get(Integer.parseInt(session.getAttribute("selectedTicket").toString())).setCalendarioCompleto(calendarioCompleto);
			return mapping.findForward("nextStep");
		}

        return mapping.findForward("success");
	}
}
