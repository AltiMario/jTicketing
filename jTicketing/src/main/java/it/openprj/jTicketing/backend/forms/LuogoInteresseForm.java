/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.forms;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class LuogoInteresseForm extends ActionForm {
	private static final long serialVersionUID = -2070692041919953778L;
	private String uid;
	private String titolo;
	private String descrizione;
	private FormFile theFile;
	private String btnRimuovi;
	private String btnAnnulla;
	
	public String getUid () {
		return uid;
	}

	public void setUid (String uid) {
		this.uid = uid;
	}
	
	public String getTitolo () {
		return titolo;
	}

	public void setTitolo (String titolo) {
		this.titolo = titolo;
	}

	public String getDescrizione () {
		return descrizione;
	}

	public void setDescrizione (String descrizione) {
		this.descrizione = descrizione;
	}

	public FormFile getTheFile () {
		return theFile;
	}

	public void setTheFile (FormFile theFile) {
		this.theFile = theFile;
	}
	
	public String getBtnRimuovi() {
		return btnRimuovi;
	}

	public void setBtnRimuovi(String btnRimuovi) {
		this.btnRimuovi = btnRimuovi;
	}
	
	public String getBtnAnnulla() {
		return btnAnnulla;
	}

	public void setBtnAnnulla(String btnAnnulla) {
		this.btnAnnulla = btnAnnulla;
	}
}
