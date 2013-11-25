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
@Table(name="JT_BIRTHPLACE")

/**
 *
 * @author Roberto Marino
 */
public class Birthplace {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID_BIRTHPLACE")
  private long idBirthplace;

  @Column(name = "NAME")
  private String name;

    public long getIdBirthplace() {
        return idBirthplace;
    }

    public void setIdBirthplace(long idBirthplace) {
        this.idBirthplace = idBirthplace;
    }

    public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}