/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.forms;

import org.apache.struts.action.ActionForm;

/**
 * Created by IntelliJ IDEA.
 * User: AltiMario
 * Date: 23/09/11
 * Time: 14.37
 * To change this template use File | Settings | File Templates.
 */
public class ConfigSIAEForm extends ActionForm {

     public String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String  SCodiceSistema;
     public String SIDCFTitolare ;
     public String SSistemaEmissione ;
     public String SCartaAttivazione;
     private String btnAnnulla;

    public String getSCodiceSistema() {
        return SCodiceSistema;
    }

    public void setSCodiceSistema(String SCodiceSistema) {
        this.SCodiceSistema = SCodiceSistema;
    }

    public String getSIDCFTitolare() {
        return SIDCFTitolare;
    }

    public void setSIDCFTitolare(String SIDCFTitolare) {
        this.SIDCFTitolare = SIDCFTitolare;
    }

    public String getSSistemaEmissione() {
        return SSistemaEmissione;
    }

    public void setSSistemaEmissione(String SSistemaEmissione) {
        this.SSistemaEmissione = SSistemaEmissione;
    }

    public String getSCartaAttivazione() {
        return SCartaAttivazione;
    }

    public void setSCartaAttivazione(String SCartaAttivazione) {
        this.SCartaAttivazione = SCartaAttivazione;
    }

    public String getBtnAnnulla() {
		return btnAnnulla;
	}

	public void setBtnAnnulla(String btnAnnulla) {
		this.btnAnnulla = btnAnnulla;
	}
}
