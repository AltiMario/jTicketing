/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.forms;

import java.io.Serializable;

public class ErrorMessage implements Serializable{
	private static final long serialVersionUID = -5516493023132831853L;
	private String id;
	private String msg;
	
	public ErrorMessage() {
	}
	
	public ErrorMessage(String id, String msg){
		this.id=id;
		this.msg=msg;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
