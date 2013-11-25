/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.User;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ProcessGestioneLuoghiInteresseStep2Action extends DispatchAction {
	public ActionForward get (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		
		User user = (User) session.getAttribute("user");
		if (user == null || user.isAdministrator() == false) {
			return mapping.findForward("home");
		}
		
		@SuppressWarnings("unchecked")
		ArrayList<LuogoInteresse> listaLuoghiInteresse = (ArrayList<LuogoInteresse>) session.getAttribute ("listaLuoghiInteresse");
		LuogoInteresse dettaglioLuogoInteresse = null;
		String uidLuoghiInteresse = (String) request.getParameter("uid");
		
		for(int i=0; i<listaLuoghiInteresse.size(); i++) {
			dettaglioLuogoInteresse =listaLuoghiInteresse.get(i);
			if (dettaglioLuogoInteresse.getUid().equalsIgnoreCase(uidLuoghiInteresse)) {
				break;
			}
		}
		session.setAttribute("dettaglioLuogoInteresse", dettaglioLuogoInteresse);
		
        return mapping.findForward("success");
    }	
}
