/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.forms;

import it.openprj.jTicketing.blogic.model.entity.CalendarioEventi;
import it.openprj.jTicketing.blogic.model.entity.Ticket;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class CalendarForm extends ActionForm {

	/**
	 * 
	 */
	public static final String SELECTED_MONTH = "1";

	private static final long serialVersionUID = -8098947241940043257L;
	private String selMonth;
	private String selTicket;
	private ArrayList<Ticket> tickets;
	private String selGruppo;
	private String isChange;
	private String btnAvanti;
	private ArrayList<CalendarioEventi> listaMesi = new ArrayList<CalendarioEventi>(12);

	public String getBtnAvanti() {
		return btnAvanti;
	}

	public void setBtnAvanti(String btnAvanti) {
		this.btnAvanti = btnAvanti;
	}

	public CalendarForm() {
		this.initialize();
	}

	public void initialize() {
		listaMesi.clear();
		for (int i = 0; i < 12; i++)
			listaMesi.add(i, new CalendarioEventi());
	}

	public String getIsChange() {
		return isChange;
	}

	public void setIsChange(String isChange) {
		this.isChange = isChange;
	}

	public String getSelMonth() {
		return selMonth;
	}

	public void setSelMonth(String selMonth) {
		this.selMonth = selMonth;
	}

	public String getSelTicket() {
		return selTicket;
	}

	public void setSelTicket(String selTicket) {
		this.selTicket = selTicket;
	}

	public ArrayList<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(ArrayList<Ticket> tickets) {
		this.tickets = tickets;
	}

	public String getSelGruppo() {
		return selGruppo;
	}

	public void setSelGruppo(String selGruppo) {
		this.selGruppo = selGruppo;
	}

	public ArrayList<CalendarioEventi> getListaMesi() {
		return listaMesi;
	}

	public void setListaMesi(ArrayList<CalendarioEventi> listaMesi) {
		this.listaMesi = listaMesi;
	}

}
