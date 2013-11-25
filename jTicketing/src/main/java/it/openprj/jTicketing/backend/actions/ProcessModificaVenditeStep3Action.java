/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.model.entity.Turno;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ProcessModificaVenditeStep3Action extends DispatchAction {
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		String uidLuogoInteresse = request.getParameter("uidLuogoInteresse");
		String uidTicket = request.getParameter("uidTicket");
		String iYear = request.getParameter("iYear");
		String iMonth = request.getParameter("iMonth");
		
		if (iYear == null && iMonth == null) {
			Calendar ca = new GregorianCalendar();
			iYear = String.valueOf(ca.get(Calendar.YEAR));
			iMonth = String.valueOf(ca.get(Calendar.MONTH));
		}
		
		DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
		List<Turno> listaTurni = (List<Turno>) service.searchTurni(uidTicket);
		List<Turno> listaTurniGiorni = service.turniGiorni (uidTicket , iYear , Integer.toString((Integer.parseInt(iMonth) + 1)));
		session.setAttribute("iMonth", iMonth);
		session.setAttribute("iYear", iYear);
		session.setAttribute("uidLuogoInteresse" , uidLuogoInteresse);
		session.setAttribute("uidTicket" , uidTicket);
		session.setAttribute("listaTurni" , listaTurni);
		session.setAttribute("listaTurniGiorni" , listaTurniGiorni);
		return mapping.findForward("success");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		String uidLuogoInteresse = request.getParameter("uidLuogoInteresse");
		String uidTicket = request.getParameter("uidTicket");
		String iYear = request.getParameter("iYear");
		String iMonth = request.getParameter("iMonth");
		String days = request.getParameter("days");
		DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
		List<Turno> listaTurni = (List<Turno>) session.getAttribute("listaTurni");
		List<Turno> listaTurniGiorni = (List<Turno>) session.getAttribute("listaTurniGiorni");
		for (int countTurniGiorni = 1 ; countTurniGiorni <= Integer.parseInt(days) ; countTurniGiorni++) {
			for (int countTurni = 0 ; countTurni < listaTurni.size() ; countTurni ++) {
				String turno = request.getParameter("turno_" + countTurniGiorni + "_" + listaTurni.get(countTurni).getUidTpOrarioEventi());
				String[] selTurno = request.getParameterValues("selTurno_" + countTurniGiorni + "_" + listaTurni.get(countTurni).getUidTpOrarioEventi());
				if (turno.equals("new") && selTurno != null) {
					String uidCalendarioEventi = null;
					for (int count = 0 ; count < listaTurniGiorni.size() ; count++) {
						if (listaTurniGiorni.get(count).getGiorno().equals(Integer.toString(countTurniGiorni))) {
							uidCalendarioEventi = listaTurniGiorni.get(count).getUidCalendarioEventi();
						}
					}
					if (uidCalendarioEventi == null) {
						service.insertEvento (Long.parseLong(uidLuogoInteresse), Long.parseLong(uidTicket), Integer.toString(countTurniGiorni), iMonth, iYear);
						uidCalendarioEventi = service.lastInsertEvento();
					}
					Turno newTurno = new Turno();
					newTurno.setUidTpOrarioEventi(listaTurni.get(countTurni).getUidTpOrarioEventi());
					newTurno.setQuantita(listaTurni.get(countTurni).getQuantita());
					newTurno.setUidCalendarioEventi(uidCalendarioEventi);
					service.insertOrariEventi (newTurno);
					listaTurni = (List<Turno>) service.searchTurni(uidTicket);
					listaTurniGiorni = service.turniGiorni (uidTicket , iYear , Integer.toString((Integer.parseInt(iMonth) + 1)));
				} else if (turno.equals("newchecked") && selTurno == null) {
					String uidCalendarioEventi = null;
					String uidOrariEventi = null;
					int numTurniGiorno = 0;
					for (int count = 0 ; count < listaTurniGiorni.size() ; count++) {
						if (listaTurniGiorni.get(count).getGiorno().equals(Integer.toString(countTurniGiorni))) {
							if (listaTurniGiorni.get(count).getUidTpOrarioEventi().equals(listaTurni.get(countTurni).getUidTpOrarioEventi())) {
								uidCalendarioEventi = listaTurniGiorni.get(count).getUidCalendarioEventi();
								uidOrariEventi = listaTurniGiorni.get(count).getUidTaOrarioEventi();
							}
							numTurniGiorno ++;
						}
					}
					if (numTurniGiorno == 1) {
						service.deleteEvento(uidCalendarioEventi);
					}
					service.deleteOrariEventi (uidOrariEventi);
					listaTurni = (List<Turno>) service.searchTurni(uidTicket);
					listaTurniGiorni = service.turniGiorni (uidTicket , iYear , Integer.toString((Integer.parseInt(iMonth) + 1)));
				}
			}
		}
		listaTurni = (List<Turno>) service.searchTurni(uidTicket);
		listaTurniGiorni = service.turniGiorni (uidTicket , iYear , Integer.toString((Integer.parseInt(iMonth) + 1)));
		session.setAttribute("iMonth", iMonth);
		session.setAttribute("iYear", iYear);
		session.setAttribute("uidLuogoInteresse" , uidLuogoInteresse);
		session.setAttribute("uidTicket" , uidTicket);
		session.setAttribute("listaTurni" , listaTurni);
		session.setAttribute("listaTurniGiorni" , listaTurniGiorni);
		return mapping.findForward("success");
	}
}
