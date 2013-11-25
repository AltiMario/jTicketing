/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.exceptions;

public class ServiceException extends Exception {
	private static final long serialVersionUID = 3838485696405979801L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable cause) {
		super(cause);
	}
}
