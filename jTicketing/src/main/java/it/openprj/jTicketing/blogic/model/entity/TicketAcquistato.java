/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;
import java.sql.Date;

public class TicketAcquistato implements Serializable {

	private static final long serialVersionUID = 6219606471217633883L;
	private long uid;
	private Date dataScadenza;
	private Date dataAcquisto;
	private String giorno;
	private String mese;
	private String anno;
	private String turno;
	private String codiceVerifica;
	private Ticket ticket;
	private long uidTurno;
	private PrezzoCategoriaTicket categoria;
	private long uidLuogoInteresse;
	private String convalidato;
	private Date dataConvalida;
	private User operatoreConvalidante;
	private User acquirente;
	private String idTurno;
	
	public String getGiorno() {
		return giorno;
	}

	public String getTurno() {
		return turno;
	}

	public void setTurno(String turno) {
		this.turno = turno;
	}

	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}

	public String getMese() {
		return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public PrezzoCategoriaTicket getCategoria() {
		return categoria;
	}

	public void setCategoria(PrezzoCategoriaTicket categoria) {
		this.categoria = categoria;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(Date dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getCodiceVerifica() {
		return codiceVerifica;
	}

	public void setCodiceVerifica(String codiceVerifica) {
		this.codiceVerifica = codiceVerifica;
	}

	public long getUidTurno() {
		return uidTurno;
	}

	public void setUidTurno(long uidTurno) {
		this.uidTurno = uidTurno;
	}

	public long getUidLuogoInteresse() {
		return uidLuogoInteresse;
	}

	public void setUidLuogoInteresse(long uidLuogoInteresse) {
		this.uidLuogoInteresse = uidLuogoInteresse;
	}

	public String getConvalidato() {
		return convalidato;
	}

	public void setConvalidato(String convalidato) {
		this.convalidato = convalidato;
	}

	public Date getDataConvalida() {
		return dataConvalida;
	}

	public void setDataConvalida(Date dataConvalida) {
		this.dataConvalida = dataConvalida;
	}

	public User getOperatoreConvalidante() {
		return operatoreConvalidante;
	}

	public void setOperatoreConvalidante(User operatoreConvalidante) {
		this.operatoreConvalidante = operatoreConvalidante;
	}

	public User getAcquirente() {
		return acquirente;
	}

	public void setAcquirente(User acquirente) {
		this.acquirente = acquirente;
	}
	
	public String getIdTurno() {
		return idTurno;
	}

	public void setIdTurno(String idTurno) {
		this.idTurno = idTurno;
	}
	

	public Date getDataAcquisto() {
		return dataAcquisto;
	}

	public void setDataAcquisto(Date dataAcquisto) {
		this.dataAcquisto = dataAcquisto;
	}

	@Override
	public boolean equals(Object obj) {
		boolean identical = true;
		if (this.getUidTurno() != ((TicketAcquistato) obj).getUidTurno())
			identical = false;
		if (this.getGiorno() != ((TicketAcquistato) obj).getGiorno())
			identical = false;
		if (this.getMese() != ((TicketAcquistato) obj).getMese())
			identical = false;
		if (this.getAnno() != ((TicketAcquistato) obj).getAnno())
			identical = false;
		if (this.getUidLuogoInteresse() != ((TicketAcquistato) obj).getUidLuogoInteresse())
			identical = false;

		return identical;
	}

}
