/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;

public class Turno implements Serializable {

	private static final long serialVersionUID = 1L;
	private String uidTpOrarioEventi;
	private String uidTaOrarioEventi;
	private String orario;
	private String orarioApertura;
	private int quantita;
	private int quantitaResidua;
	private String uidCalendarioEventi;
	private String uidLuogoInteresse;
	private String uidTicket;
	private String giorno;
	private int quantitaAquistata;

    public String getOrarioApertura() {
        return orarioApertura;
    }

    public void setOrarioApertura(String orarioApertura) {
        this.orarioApertura = orarioApertura;
    }

    public int getPercentualeQuantita() {
		return Math.round((100 * quantitaResidua) / quantita);
	}

	public String getUidTpOrarioEventi() {
		return uidTpOrarioEventi;
	}

	public void setUidTpOrarioEventi(String uidTpOrarioEventi) {
		this.uidTpOrarioEventi = uidTpOrarioEventi;
	}

	public String getUidTaOrarioEventi() {
		return uidTaOrarioEventi;
	}

	public void setUidTaOrarioEventi(String uidTaOrarioEventi) {
		this.uidTaOrarioEventi = uidTaOrarioEventi;
	}

	public String getUidLuogoInteresse() {
		return uidLuogoInteresse;
	}

	public void setUidLuogoInteresse(String uidLuogoInteresse) {
		this.uidLuogoInteresse = uidLuogoInteresse;
	}

	public String getUidTicket() {
		return uidTicket;
	}

	public void setUidTicket(String uidTicket) {
		this.uidTicket = uidTicket;
	}


	public String getOrario() {
		return orario;
	}

	public void setOrario(String orario) {
		this.orario = orario;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public int getQuantitaResidua() {
		return quantitaResidua;
	}

	public void setQuantitaResidua(int quantitaResidua) {
		this.quantitaResidua = quantitaResidua;
	}

	public String getUidCalendarioEventi() {
		return uidCalendarioEventi;
	}

	public void setUidCalendarioEventi(String uidCalendarioEventi) {
		this.uidCalendarioEventi = uidCalendarioEventi;
	}

	public String getGiorno() {
		return giorno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public int getQuantitaAquistata() {
		return quantitaAquistata;
	}

	public void setQuantitaAquistata(int quantitaAquistata) {
		this.quantitaAquistata = quantitaAquistata;
	}

}
