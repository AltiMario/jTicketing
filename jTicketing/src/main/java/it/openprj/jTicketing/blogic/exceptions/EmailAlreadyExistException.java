/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.exceptions;

public class EmailAlreadyExistException extends Exception {
	private static final long serialVersionUID = 3838485796405979801L;
	
	public EmailAlreadyExistException() {
		super();
	}
	
	public EmailAlreadyExistException(String msg) {
		super(msg);
	}
	
	public EmailAlreadyExistException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
