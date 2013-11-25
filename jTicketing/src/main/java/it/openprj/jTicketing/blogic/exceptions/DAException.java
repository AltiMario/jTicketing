/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.exceptions;

public class DAException extends Exception {
	private static final long serialVersionUID = 1L;
	private int errorCode;
	
	public DAException(String msg) {
		super(msg);
		this.errorCode = 0;
	}
	
	public DAException(int errorCode, String msg) {
		super(msg);
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
}
