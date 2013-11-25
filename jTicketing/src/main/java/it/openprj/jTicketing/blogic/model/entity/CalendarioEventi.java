/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CalendarioEventi implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6987217604989254833L;
	private String uidLuoghiInteresse;
	private String mese;
	private String anno;
	private HashMap<String, List<Turno>> listaGiorniDelMese = new HashMap<String, List<Turno>>(0);


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

	public HashMap<String, List<Turno>> getListaGiorniDelMese() {
		return listaGiorniDelMese;
	}

	public void setListaGiorniDelMese(HashMap<String, List<Turno>> listaGiorniDelMese) {
		this.listaGiorniDelMese = listaGiorniDelMese;
	}

	public String getUidLuoghiInteresse() {
		return uidLuoghiInteresse;
	}

	public void setUidLuoghiInteresse(String uidLuoghiInteresse) {
		this.uidLuoghiInteresse = uidLuoghiInteresse;
	}

	public HashMap<String, List<Turno>> getCalendarioEventi() {
		return listaGiorniDelMese;
	}

	public void setCalendarioEventi(HashMap<String, List<Turno>> calendarioEventi) {
		this.listaGiorniDelMese = calendarioEventi;
	}

	public void addTurni(String giorno, List<Turno> listaTurni) {
		this.listaGiorniDelMese.put(giorno, listaTurni);
	}

	public ArrayList<Turno> getTurni(String giorno) {
		return (ArrayList<Turno>) this.listaGiorniDelMese.get(giorno);
	}

}
