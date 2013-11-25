/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ProcessCercaLuoghiInteresseBotteghinoAction extends DispatchAction {
	public ActionForward get (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		
		
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		
		User user = (User) session.getAttribute("user");
		if (user == null || user.isBotteghino() == false) {
			return mapping.findForward("home");
		}
		
		ArrayList<LuogoInteresse> listaLuoghiInteresse = null;
		
		try {
			BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
			listaLuoghiInteresse = (ArrayList<LuogoInteresse>) service.searchLuoghiBotteghino(user);
			session.setAttribute("listaLuoghiInteresse", listaLuoghiInteresse);
			
		} catch (SystemException se) {
			return mapping.findForward("fail");
		} 

        return mapping.findForward("success");
    }	
}
