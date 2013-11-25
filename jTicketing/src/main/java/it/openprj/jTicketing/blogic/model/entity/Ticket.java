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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ticket implements Serializable {
	private static final long serialVersionUID = -5516493023142831853L;
	private String uid;
	private String uid_luoghi_interesse;
	private String titolo;
	private String descrizione;
	private String numeroPosto;
    private String sCodicePosto;
    private String sTipologiaEvento;
    private int sIncidenza;
    private String nomeFile;
	private Date data_scadenza;
	private byte[] immagine;
	private ArrayList<PrezzoCategoriaTicket> prezziCategorie= new ArrayList<PrezzoCategoriaTicket>();
	// Ogni elemento HashMap si riferisce ad un anno (key) che contiente un
	// arrayList di 12 mesi
	private HashMap<String, ArrayList<CalendarioEventi>> calendarioCompleto;
    private Long idTypeGenus;
    private String nameTypeGenus;
    private String acronyms;
    private String typeOrganizer;
    private String filmNationality;
    private String executor;
    private String author;
    private int numberOperas;

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getNumeroPosto() {
        return numeroPosto;
    }

    public void setNumeroPosto(String numeroPosto) {
        this.numeroPosto = numeroPosto;
    }

    public String getNameTypeGenus() {
        return nameTypeGenus;
    }

    public void setNameTypeGenus(String nameTypeGenus) {
        this.nameTypeGenus = nameTypeGenus;
    }

    public Long getIdTypeGenus() {
        return idTypeGenus;
    }

    public void setIdTypeGenus(Long idTypeGenus) {
        this.idTypeGenus = idTypeGenus;
    }

    /**
	 * Ritorna il calendario completo in formato hashMap <String anno, CalendarioEventi>
	 * @return
	 */
	public HashMap<String, ArrayList<CalendarioEventi>> getCalendarioCompleto() {
		return calendarioCompleto;
	}

	public void setCalendarioCompleto(HashMap<String, ArrayList<CalendarioEventi>> calendarioCompleto) {
		this.calendarioCompleto = calendarioCompleto;
	}

	public String getNomeFile() {
		return nomeFile;
	}

	public void setNomeFile(String nomeFile) {
		this.nomeFile = nomeFile;
	}

	public byte[] getImmagine() {
		return immagine;
	}

	public void setImmagine(byte[] immagine) {
		this.immagine = immagine;
	}

	public void setPrezziCategorie(ArrayList<PrezzoCategoriaTicket> prezziCategorie) {
		this.prezziCategorie = prezziCategorie;
	}

	public ArrayList<PrezzoCategoriaTicket> getPrezziCategorie() {
		return prezziCategorie;
	}

	public void setPrezziCategorie(List<PrezzoCategoriaTicket> categorie) {
		this.prezziCategorie = (ArrayList<PrezzoCategoriaTicket>) categorie;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid_luoghi_interesse() {
		return uid_luoghi_interesse;
	}

	public void setUid_luoghi_interesse(String uidLuoghiInteresse) {
		uid_luoghi_interesse = uidLuoghiInteresse;
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

	public Date getData_scadenza() {
		return data_scadenza;
	}

	public void setData_scadenza(Date dataScadenza) {
		data_scadenza = dataScadenza;
	}

    public String getSCodicePosto() {
		return sCodicePosto;
	}

	public void setSCodicePosto(String sCodicePosto) {
		this.sCodicePosto = sCodicePosto;
	}

    public String getSTipologiaEvento() {
		return sTipologiaEvento;
	}

	public void setSTipologiaEvento(String sTipologiaEvento) {
		this.sTipologiaEvento = sTipologiaEvento;
	}

    public int getSIncidenza() {
		return sIncidenza;
	}

	public void setSIncidenza(int sIncidenza) {
		this.sIncidenza = sIncidenza;
	}

    public String getTypeOrganizer() {
        return typeOrganizer;
    }

    public void setTypeOrganizer(String typeOrganizer) {
        this.typeOrganizer = typeOrganizer;
    }

    public String getFilmNationality() {
        return filmNationality;
    }

    public void setFilmNationality(String filmNationality) {
        this.filmNationality = filmNationality;
    }

    public int getNumberOperas() {
        return numberOperas;
    }

    public void setNumberOperas(int numberOperas) {
        this.numberOperas = numberOperas;
    }
}
