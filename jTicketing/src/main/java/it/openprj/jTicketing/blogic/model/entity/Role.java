/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;

public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5930843961655270836L;
	private long idUserRoles;
	private String roleName;

	private long idUser;
	private String userName;

	public Role() {
		super();
	}

	public Role(String roleName) {
		super();
		this.roleName = roleName;
	}

	public void setIdUserRoles(long idUserRoles) {
		this.idUserRoles = idUserRoles;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public long getIdUserRoles() {
		return idUserRoles;
	}

	public String getRoleName() {
		return roleName;
	}

	public long getIdUser() {
		return idUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
