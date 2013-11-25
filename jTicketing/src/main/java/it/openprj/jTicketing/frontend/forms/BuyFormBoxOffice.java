/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.frontend.forms;

import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class BuyFormBoxOffice extends ActionForm {
	private static final long serialVersionUID = -3875683629150226378L;
	private ArrayList<TicketAcquistato> listaTicketAcquistati;
	private ArrayList<PrezzoCategoriaTicket> categoria;
	private ArrayList<String> quantita;
	

	public ArrayList<TicketAcquistato> getListaTicketAcquistati() {
		return listaTicketAcquistati;
	}

	public void setListaTicketAcquistati(ArrayList<TicketAcquistato> listaTicketAcquistati) {
		this.listaTicketAcquistati = listaTicketAcquistati;
	}

	public ArrayList<PrezzoCategoriaTicket> getCategoria() {
		return categoria;
	}

	public void setCategoria(ArrayList<PrezzoCategoriaTicket> categoria) {
		this.categoria = categoria;
	}

	public ArrayList<String> getQuantita() {
		return quantita;
	}

	public void setQuantita(ArrayList<String> quantita) {
		this.quantita = quantita;
	}

	


}
