/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.utilities;

public class TypeGenusList {
    public static String[] listName;

    //TODO: da rendere dinamico con le variabili in lingua del file di properties
    public TypeGenusList(String country) {
        if (country.equals("IT")) {
            listName = new String[]{
                    "Avvenimento sportivo", "Cinema", "Concerto", "Mostra", "Opera"
            };
        } else {
            listName = new String[]{
                    "Sporting event ","Cinema ","Concert ","Show ","Opera"
            };
        }
    }


}
