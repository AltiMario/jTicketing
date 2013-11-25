/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import javax.persistence.*;
import javax.print.DocFlavor;


@Entity @Cacheable
@Table(name="JT_FILM_NATIONALITY")

 /**
 *
 * @author Jlenia Scarpelli
 */


public class FilmNationality {
     @Id
     @Column(name = "ACRONYMS")
      private String acronyms;

      @Column(name = "NATION")
      private String nation;

    public String getAcronyms() {
        return acronyms;
    }

    public void setAcronyms(String acronyms) {
        this.acronyms = acronyms;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
