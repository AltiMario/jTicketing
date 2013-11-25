/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.dto;

import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.Turno;
import it.openprj.jTicketing.blogic.model.entity.User;

import java.io.Serializable;
import java.util.ArrayList;

public class WizardDTO implements Serializable {

	private static final long serialVersionUID = 3838485796405979801L;
	private User user;
	private LuogoInteresse luogoInteresse;
	private ArrayList<Ticket> listaTickets;
	private ArrayList<Turno> listaTurni;
	private ArrayList<PrezzoCategoriaTicket> listaPrezziCategorie;

	public ArrayList<Turno> getListaTurni() {
		return listaTurni;
	}

	public void setListaTurni(ArrayList<Turno> listaTurni) {
		this.listaTurni = listaTurni;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LuogoInteresse getLuogoInteresse() {
		return luogoInteresse;
	}

	public void setLuogoInteresse(LuogoInteresse luogoInteresse) {
		this.luogoInteresse = luogoInteresse;
	}

	public ArrayList<Ticket> getListaTickets() {
		return listaTickets;
	}

	public void setListaTickets(ArrayList<Ticket> listaTickets) {
		this.listaTickets = listaTickets;
	}

	public ArrayList<PrezzoCategoriaTicket> getListaPrezziCategorie() {
		return listaPrezziCategorie;
	}

	public void setListaPrezziCategorie(ArrayList<PrezzoCategoriaTicket> listaPrezziCategorie) {
		this.listaPrezziCategorie = listaPrezziCategorie;
	}

}
