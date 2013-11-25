/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.frontend.actions;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ProcessSearchDetailAction extends Action {

    public ProcessSearchDetailAction() {
        super();
    }

    @SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
		}
		ArrayList<Ticket> listaTickets=null;
        String uidLuoghiInteresse = (String) request.getParameter("uid");
		String uidOcms = (String) request.getParameter("uidOcms");
		
		if(session.getAttribute("listaLuoghiInteresse") == null) {
			ArrayList<LuogoInteresse> listaLuoghiInteresse = null;
			
			try {
				DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
				listaLuoghiInteresse = (ArrayList<LuogoInteresse>) service.searchLuoghiInteresse();
				session.setAttribute("listaLuoghiInteresse", listaLuoghiInteresse);
			} catch (SystemException se) {
				return mapping.findForward("fail");
			} 
		}
		
		ArrayList<LuogoInteresse> listaLuoghiInteresse=(ArrayList<LuogoInteresse>)session.getAttribute("listaLuoghiInteresse");
		LuogoInteresse dettaglioLuogoInteresse=null;
		try{
		  DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
		  listaTickets = (ArrayList<Ticket>) service.searchTickets(uidLuoghiInteresse);
		  for(int i=0; i<listaLuoghiInteresse.size(); i++) {
			  dettaglioLuogoInteresse=listaLuoghiInteresse.get(i);
			  if (dettaglioLuogoInteresse.getUid().equalsIgnoreCase(uidLuoghiInteresse)) {
				  break;
			  }
		  }
		  
		  session.setAttribute("dettaglioLuogoInteresse", dettaglioLuogoInteresse);
		  session.setAttribute("listaTickets", listaTickets);
		} catch (SystemException se){
			ActionMessages messages = new ActionMessages(); 
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.systemFailure"));
			saveMessages(request, messages);
		    return mapping.findForward("fail");
		} 

        return mapping.findForward("success");
    }

}
