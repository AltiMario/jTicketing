/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

import java.io.Serializable;

public class GenereEvento implements Serializable {
	private static final long serialVersionUID = -1392604395532421393L;
	private Long idTypeGenus;
    private String description;


    public Long getIdTypeGenus() {
        return idTypeGenus;
    }

    public void setIdTypeGenus(Long idTypeGenus) {
        this.idTypeGenus = idTypeGenus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
