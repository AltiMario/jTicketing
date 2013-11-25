/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.forms.security;

import java.io.Serializable;

import org.apache.struts.action.ActionForm;

public class ProfileForm extends ActionForm {

	private String firstname;
	private String lastname;
	private String email;
	private String emailreply;
	private String jcaptcha_response;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmailreply() {
		return emailreply;
	}
	public void setEmailreply(String emailreply) {
		this.emailreply = emailreply;
	}
	public String getJcaptcha_response() {
		return jcaptcha_response;
	}
	public void setJcaptcha_response(String jcaptcha_response) {
		this.jcaptcha_response = jcaptcha_response;
	}


}
