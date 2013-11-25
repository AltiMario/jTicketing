/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;

public class Birthplace implements Serializable {
	private static final long serialVersionUID = -1392604395532421393L;
	private long idbirthplace;
    private String name;

    public long getIdbirthplace() {
        return idbirthplace;
    }

    public void setIdbirthplace(long idbirthplace) {
        this.idbirthplace = idbirthplace;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}
