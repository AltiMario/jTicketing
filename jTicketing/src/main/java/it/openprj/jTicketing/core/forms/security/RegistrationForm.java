/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.forms.security;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.Birthplace;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import org.apache.struts.action.ActionForm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationForm extends ActionForm {

	private String firstname;
	private String lastname;
    private String birthdate;
    private String cf;
	private String userid;
	private String password;
    private String passwordreply;
    private String email;
	private String jcaptcha_response;
	private String condition_1;
	private String condition_2;
    private long idbirthplace;
    private ArrayList<Birthplace> birthplacelist;

    public long getIdbirthplace() {
        return idbirthplace;
    }

    public void setIdbirthplace(long idbirthplace) {
        this.idbirthplace = idbirthplace;
    }

    BackEndMgr service1 = ServicesFactory.getInstance().getBackEndMgrMgr();

    public RegistrationForm() throws SystemException, SQLException {
        birthplacelist = (ArrayList<Birthplace>) service1.selectBirthplaceList();
    }


    public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

    public String getBirthdate() {
		return birthdate;
    }

    public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordreply() {
		return passwordreply;
	}

	public void setPasswordreply(String passwordreply) {
		this.passwordreply = passwordreply;
	}

    public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJcaptcha_response() {
		return jcaptcha_response;
	}

	public void setJcaptcha_response(String jcaptcha_response) {
		this.jcaptcha_response = jcaptcha_response;
	}

	public String getCondition_1() {
		return condition_1;
	}

	public void setCondition_1(String condition_1) {
		this.condition_1 = condition_1;
	}
	
	public String getCondition_2() {
		return condition_2;
	}

	public void setCondition_2(String condition_2) {
		this.condition_2 = condition_2;
	}

      public ArrayList<Birthplace> getBirthplacelist() {
		return birthplacelist;
	}


    public void setBirthplacelist(List<Birthplace> Comune) {
		this.birthplacelist = (ArrayList<Birthplace>) Comune;
	}




}
