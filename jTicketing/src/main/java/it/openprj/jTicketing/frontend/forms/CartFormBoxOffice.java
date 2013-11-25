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
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class CartFormBoxOffice extends ActionForm {
	private static final long serialVersionUID = -3875683629150226378L;
	private ArrayList<TicketAcquistato> listaTicketAcquistati;
	private String categoria;
	private String quantita;
	private String btn;
	private String email;
	private String emailReplica;
	private BigDecimal totale = null;
//	private ArrayList<PrezzoCategoriaTicket> categoria1;
//	private ArrayList<String> quantita1;

	public BigDecimal getTotale() {
		BigDecimal prezzo = null;
		totale = new BigDecimal(0);
		if (listaTicketAcquistati != null) {
			for (int i = 0; i < listaTicketAcquistati.size(); i++) {
				prezzo = listaTicketAcquistati.get(i).getCategoria().getPrezzo();
				totale = totale.add(prezzo, MathContext.UNLIMITED);
			}
		}
		return totale;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailReplica() {
		return emailReplica;
	}

	public void setEmailReplica(String emailReplica) {
		this.emailReplica = emailReplica;
	}

	public String getBtn() {
		return btn;
	}

	public void setBtn(String btn) {
		this.btn = btn;
	}

	public ArrayList<TicketAcquistato> getListaTicketAcquistati() {
		return listaTicketAcquistati;
	}

	public void setListaTicketAcquistati(ArrayList<TicketAcquistato> listaTicketAcquistati) {
		this.listaTicketAcquistati = listaTicketAcquistati;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getQuantita() {
		return quantita;
	}

	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}

//	public ArrayList<PrezzoCategoriaTicket> getCategoria1() {
//		return categoria1;
//	}
//
//	public void setCategoria1(ArrayList<PrezzoCategoriaTicket> categoria1) {
//		this.categoria1 = categoria1;
//	}
//
//	public ArrayList<String> getQuantita1() {
//		return quantita1;
//	}
//
//	public void setQuantita1(ArrayList<String> quantita1) {
//		this.quantita1 = quantita1;
//	}
	

}
