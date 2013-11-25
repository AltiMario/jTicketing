/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.servlets;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;

import javax.servlet.http.HttpServlet;

public class InsertRow extends HttpServlet {

	public InsertRow() throws Exception {
		super();
		try {
			DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
			it.openprj.jTicketing.blogic.model.entity.Role ruolo = service.searchIdAmministratore();
            //al primo avvio crea i ruoli di default (con il linguaggio predefinito)
			if(ruolo.getUserName() == null){
                //crea la lista di tutti i comuni
                service.insertBirthPlace();
				service.insertRuoloAmministratore();
				service.insertUtenteAmministratore();
				service.insertRuoloOperatore();
				service.insertRuoloBotteghino();
				service.insertRuoloUtente();
                //crea la lista "genere eventi"
                service.insertTypegenus();
                //crea la lista delle nazioni
                service.insertFilmNationality();
			}


		} catch (SystemException se) {
			System.out.print(se);
		} 
	}
}
