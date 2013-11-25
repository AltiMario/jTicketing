/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;

public class ConfigurazioneSIAE implements Serializable {
	private static final long serialVersionUID = -1392604395532421393L;
	private String uid;
	private String sCodiceSistema;
    private String sIDCFTitolare;
    private String sSistemaEmissione;
    private String sCartaAttivazione;
    private String btnAnnulla;
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSCodiceSistema() {
		return sCodiceSistema;
	}

	public void setSCodiceSistema(String sCodiceSistema) {
		this.sCodiceSistema = sCodiceSistema;
	}

    public String getSIDCFTitolare() {
		return sIDCFTitolare;
	}

	public void setSIDCFTitolare(String sIDCFTitolare) {
		this.sIDCFTitolare = sIDCFTitolare;
	}

    public String getSSistemaEmissione() {
		return sSistemaEmissione;
	}

	public void setSSistemaEmissione(String sSistemaEmissione) {
		this.sSistemaEmissione = sSistemaEmissione;
	}

    public String getSCartaAttivazione() {
		return sCartaAttivazione;
	}

	public void setSCartaAttivazione(String sCartaAttivazione) {
		this.sCartaAttivazione = sCartaAttivazione;
	}

    public String getBtnAnnulla() {
		return btnAnnulla;
	}

	public void setBtnAnnulla(String btnAnnulla) {
		this.btnAnnulla = btnAnnulla;
	}
}
