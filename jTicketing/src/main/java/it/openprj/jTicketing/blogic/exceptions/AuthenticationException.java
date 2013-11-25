/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.exceptions;

public class AuthenticationException extends Exception {
	private static final long serialVersionUID = 3838485796405979821L;
	
	public AuthenticationException() {
		super();
	}

	public AuthenticationException(String msg) {
		super(msg);
	}
	
	public AuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
