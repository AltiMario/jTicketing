/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.sql.Date;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private long idUser = 0;
	private String userName = "";
	private String userPass = "";
    private String firstName = "";
	private String lastName = "";
    private Date birthdate;
    private String cf;
	private String email = "";
	private String activationCode = "";
	private boolean enabled = false;
	private Map<String, Role> roles = new HashMap<String, Role>();
	private boolean activated;
	private String ruolo = "";
    private long idbirthplace;
    private Locale country = Locale.getDefault();
    private ResourceBundle messageResource = ResourceBundle.getBundle("resources.MessageResources", country);
    
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

    public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

    public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setRoles(Map<String, Role> roles) {
		this.roles = roles;
	}
	
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	
	public long getIdUser() {
		return idUser;
	}

	public String getUserName() {
		return userName;
	}

	public String getCf() {
		return cf;
	}

    public String getUserPass() {
		return userPass;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

    public Date getBirthdate() {
		return birthdate;
    }

	public String getEmail() {
		return email;
	}

	public String getActivationCode() {
		return activationCode;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Map<String, Role> getRoles() {
		return roles;
	}

	public boolean hasRole(String role) {
		if (!roles.isEmpty()) {
			if (roles.containsKey(role))
				return true;

		}
		return false;
	}
	
	public String getRuolo() {
		return ruolo;
	}	

	public boolean isAdministrator() {
		return hasRole(messageResource.getString("role.admin"));
	}

	public boolean isOperatore() {
		return hasRole(messageResource.getString("role.operator"));
	}
	
	public boolean isBotteghino() {
		return hasRole(messageResource.getString("role.boxOffice"));
	}

    public long getIdbirthplace() {
        return idbirthplace;
    }

    public void setIdbirthplace(long idbirthplace) {
        this.idbirthplace = idbirthplace;
    }
}
