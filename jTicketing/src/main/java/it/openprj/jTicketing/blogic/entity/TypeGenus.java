/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import javax.persistence.*;


@Entity @Cacheable
@Table(name="JTS_TYPE_GENUS")

/**
 *
 * @author Roberto Marino
 */
public class TypeGenus {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_TYPE_GENUS")
  private long idTypeGenus;

  @Column(name = "DESCRIPTION")
  private String description;

    public long getIdTypeGenus() {
        return idTypeGenus;
    }

    public void setIdTypeGenus(long idTypeGenus) {
        this.idTypeGenus = idTypeGenus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}