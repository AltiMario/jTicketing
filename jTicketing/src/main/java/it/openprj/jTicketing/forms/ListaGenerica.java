/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.forms;

import java.io.Serializable;

public class ListaGenerica implements Serializable{
	private static final long serialVersionUID = -5512493023142831853L;
	private long totalCount;
	private Object[] results;
	
	public ListaGenerica(){		
	}
	
	public ListaGenerica(long totalCount, Object[] results){	
		this.totalCount=totalCount;
		this.results=results;
	}
	
	public long getTotalCount() {
		return totalCount;
	}
	public Object[] getResults() {
		return results;
	}
	
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	public void setResults(Object[] results) {
		this.results = results;
	}
}
