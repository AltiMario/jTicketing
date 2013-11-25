/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

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

public class ProcessGestioneOperatoriUpdateAction extends DispatchAction {
	public ActionForward get(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		
		ArrayList<String> listaUidLuoghiInteresse;
		BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
		listaUidLuoghiInteresse = (ArrayList<String>) service.selectListaUidLuoghiInteresse();
		String uidOperatore = request.getParameter ("uid");
		service.deleteLuoghiOperatori(uidOperatore);
		service.updateRuolo(uidOperatore , "2");
		
		for(String uidLuogoInteresse:listaUidLuoghiInteresse) {
			if (request.getParameter ("checkbox" + uidLuogoInteresse) != null) {
				service.insertLuoghiOperatori(uidOperatore , uidLuogoInteresse);
				service.updateRuolo(uidOperatore , "3");
			} 
		}
		
		session.setAttribute("uid", null);
		session.setAttribute("listaLuoghiInteresseOperatore", null);
				
		return mapping.findForward("success");		
	}
}