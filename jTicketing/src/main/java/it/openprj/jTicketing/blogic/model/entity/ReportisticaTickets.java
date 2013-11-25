/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;

public class ReportisticaTickets implements Serializable {
	private static final long serialVersionUID = -1392602395532421393L;
	private String anno;
	private String mese;
	private String giorno;
	private String quantita;
	private String quantitaResidua;
	private String venduti;
	private String convalidato;
	private String incasso;
	
	public String getAnno() {
		return anno;
	}
	
	public void setAnno(String anno) {
		this.anno = anno;
	}
	
	public String getMese() {
		return mese;
	}
	
	public void setMese(String mese) {
		this.mese = mese;
	}
	
	public String getGiorno() {
		return giorno;
	}
	
	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}
		
	public String getQuantita() {
		return quantita;
	}
	
	public void setQuantita(String quantita) {
		this.quantita = quantita;
	}
	
	public String getQuantitaResidua() {
		return quantitaResidua;
	}
	
	public void setQuantitaResidua(String quantitaResidua) {
		this.quantitaResidua = quantitaResidua;
	}
	
	public String getVenduti() {
		return venduti;
	}
	
	public void setVenduti(String venduti) {
		this.venduti = venduti;
	}
	
	public String getConvalidato() {
		return convalidato;
	}
	
	public void setConvalidato(String convalidato) {
		this.convalidato = convalidato;
	}
	
	public String getIncasso() {
		return incasso;
	}
	
	public void setIncasso(String incasso) {
		this.incasso = incasso;
	}
}
