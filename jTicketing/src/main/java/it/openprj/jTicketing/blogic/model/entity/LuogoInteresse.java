/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;

public class LuogoInteresse implements Serializable {
	private static final long serialVersionUID = -1392604395532421393L;
	private String uid;
	private String titolo;
	private String descrizione;
	private byte[] immagineByte;
	private String immagine;
	private String assegnatoOperatore;
	private String assegnatoBotteghino;
	private String nomeFile;
	
	public String getUid() {
		return uid;
	}
	
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getTitolo() {
		return titolo;
	}
	
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public byte[] getImmagineByte() {
		return immagineByte;
	}

	public void setImmagineByte(byte[] immagineByte) {
		this.immagineByte = immagineByte;
	}
	
	
	public String getImmagine() {
		return immagine;
	}
	
	public void setImmagine(String immagine) {
		this.immagine = immagine;
	}
	
	public String getAssegnatoOperatore() {
		if (assegnatoOperatore != null) {
			return "checked=\"checked\"";
		} else {
			return null;
		}
	}
		
	public void setAssegnatoOperatore(String assegnatoOperatore) {
		this.assegnatoOperatore = assegnatoOperatore;
	}
	
	public String getAssegnatoBotteghino() {
		if (assegnatoBotteghino != null) {
			return "checked=\"checked\"";
		} else {
			return null;
		}
	}
		
	public void setAssegnatoBotteghino(String assegnatoBotteghino) {
		this.assegnatoBotteghino = assegnatoBotteghino;
	}
}
