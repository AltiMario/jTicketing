/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.forms;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import it.openprj.jTicketing.blogic.entity.FilmNationality;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.GenereEvento;
import it.openprj.jTicketing.blogic.model.entity.NazionalitaFilm;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.blogic.utilities.FilmNationalityList;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

public class TicketForm extends ActionForm {

	private static final long serialVersionUID = -2070692041919953778L;
	private String uid;
	private FormFile theFile;
	private String titolo;
	private String descrizione;
	private String[] categorie;
    private String numeroPosto;
    private String sCodicePosto;
    private String sTipologiaEvento;
    private int sIncidenza;
    private List<LabelValueBean> listaCategorie=new ArrayList<LabelValueBean>() ;
	private String btnAvanti;
	private String btnAdd;
	private String btnRimuovi;
    private Long idTypeGenus;
    private ArrayList<GenereEvento> typegenusList;
    private String acronyms;
    private String typeOrganizer;
    private int numberOperas;
    private String  executor;
    private String  author;
    BackEndMgr service1 = ServicesFactory.getInstance().getBackEndMgrMgr();
    private ArrayList<NazionalitaFilm> filmNationalityList= (ArrayList<NazionalitaFilm>) service1.selectFilmnationalityList();
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

    public Long getIdTypeGenus() {
        return idTypeGenus;
    }

    public void setIdTypeGenus(Long idTypeGenus) {
        this.idTypeGenus = idTypeGenus;
    }

    public ArrayList<GenereEvento> getTypegenusList() {
        return typegenusList;
    }

    public void setTypegenusList(ArrayList<GenereEvento> typegenusList) {
        this.typegenusList = typegenusList;
    }



    public TicketForm() throws SystemException, SQLException {
        typegenusList = (ArrayList<GenereEvento>) service1.selectTypegenusList();

    }

  	public List<LabelValueBean> getListaCategorie() {
		return listaCategorie;
	}

	public void setListaCategorie(List<LabelValueBean> listaCategorie) {
		this.listaCategorie = listaCategorie;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBtnAvanti() {
		return btnAvanti;
	}

	public void setBtnAvanti(String btnAvanti) {
		this.btnAvanti = btnAvanti;
	}

	public String getBtnAdd() {
		return btnAdd;
	}

	public void setBtnAdd(String btnAdd) {
		this.btnAdd = btnAdd;
	}

	public String getBtnRimuovi() {
		return btnRimuovi;
	}

	public void setBtnRimuovi(String btnRimuovi) {
		this.btnRimuovi = btnRimuovi;
	}

	public String[] getCategorie() {
		return categorie;
	}

	public void setCategorie(String[] categorie) {
		this.categorie = categorie;
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

    public FormFile getTheFile() {
		return theFile;
	}

	public void setTheFile(FormFile theFile) {
		this.theFile = theFile;
	}

    public String getTypeOrganizer() {
        return typeOrganizer;
    }

    public void setTypeOrganizer(String typeOrganizer) {
        this.typeOrganizer = typeOrganizer;
    }

    public int getNumberOperas() {
        return numberOperas;
    }

    public void setNumberOperas(int numberOperas) {
        this.numberOperas = numberOperas;
    }

    public ArrayList<NazionalitaFilm> getFilmNationalityList() {
        return filmNationalityList;
    }

    public void setFilmNationalityList(ArrayList<NazionalitaFilm> filmNationalityList) {
        this.filmNationalityList = filmNationalityList;
    }



    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }

    public void reset(ActionMapping mapping, HttpServletRequest request) {
		super.reset(mapping, request);
		this.categorie = null;
		this.descrizione = null;
        this.sCodicePosto = null;
        this.sTipologiaEvento = null;
        this.sIncidenza = 0;
    	this.titolo = null;
		this.theFile = null;
	}
}
