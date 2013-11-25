/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.forms;

import java.io.Serializable;

public class RecordForm implements Serializable{
	private static final long serialVersionUID = -5516493033142831853L;
	private Object data;
	private boolean success=true;
	
	public RecordForm(){		
	}
	
	public RecordForm(Object data){	
		this.data=data;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
}
