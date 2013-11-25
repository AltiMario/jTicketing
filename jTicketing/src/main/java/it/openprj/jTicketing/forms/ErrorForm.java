/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.forms;

import java.io.Serializable;

public class ErrorForm implements Serializable{
	private static final long serialVersionUID = -5516493023142831853L;
	private Object[] errors;
	private boolean success=false;
	
	public ErrorForm(){		
	}
	
	public ErrorForm(Object[] errors){	
		this.errors=errors;
	}

	public Object[] getErrors() {
		return errors;
	}

	public void setErrors(Object[] errors) {
		this.errors = errors;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
