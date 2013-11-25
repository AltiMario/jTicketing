/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.exceptions;

public class AccountNotActivatedException extends Exception {
	private static final long serialVersionUID = 3838285796405979801L;
	
	public AccountNotActivatedException() {
		super();
	}
	
	public AccountNotActivatedException(String msg) {
		super(msg);
	}
	
	public AccountNotActivatedException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
