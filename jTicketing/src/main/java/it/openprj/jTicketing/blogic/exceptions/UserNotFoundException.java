/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.exceptions;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 3838485796105979801L;
	
	public UserNotFoundException() {
		super();
	}
	public UserNotFoundException(String msg) {
		super(msg);
	}
	public UserNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
