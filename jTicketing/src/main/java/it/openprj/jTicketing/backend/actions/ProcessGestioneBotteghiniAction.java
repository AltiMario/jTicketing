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

public class ProcessGestioneBotteghiniAction extends DispatchAction {
	public ActionForward get(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		
		User user = (User) session.getAttribute("user");
		if (user == null || user.isOperatore() == false) {
			return mapping.findForward("home");
		}
		
		ArrayList<User> listaBotteghini;
		BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
		String nome = request.getParameter ("nome");
		if (nome == null) {
			nome = "";
		}
		String cognome = request.getParameter ("cognome");
		if (cognome == null) {
				cognome = "";
		}
		String username = request.getParameter ("username");
		if (username == null) {
			username = "";
		}
		String email = request.getParameter ("email");
		if (email == null) {
			email = "";
		}
		String ruolo = request.getParameter ("ruolo");
		if (ruolo == null) {
			ruolo = "";
		}
		listaBotteghini = (ArrayList<User>) service.selectBotteghini(nome, cognome, username, email, ruolo);
		session.setAttribute("listaBotteghini", listaBotteghini);
		session.setAttribute("uid", "");
		session.setAttribute("nome", nome);
		session.setAttribute("cognome", cognome);
		session.setAttribute("username", username);
		session.setAttribute("email", email);
		session.setAttribute("ruolo", ruolo);
		String uid = request.getParameter ("uid");
		if (uid != null) {
			session.setAttribute("uid", request.getParameter ("uid"));
			ArrayList<LuogoInteresse> listaLuoghiInteresseBotteghino;
			ArrayList<LuogoInteresse> listaLuoghiInteresseBotteghinoU;
			LuogoInteresse a = new LuogoInteresse(); 
			ArrayList<LuogoInteresse> listaLuoghiInteresseBotteghinoR = null;
			
			listaLuoghiInteresseBotteghino = (ArrayList<LuogoInteresse>) service.selectListaLuoghiInteresseBotteghino(Integer.parseInt(request.getParameter ("uid")));
			listaLuoghiInteresseBotteghinoU = (ArrayList<LuogoInteresse>) service.searchLuoghiBotteghino(user);
			listaLuoghiInteresseBotteghinoR = listaLuoghiInteresseBotteghinoU;
			int c = 0;
			for(int i = 0; listaLuoghiInteresseBotteghino.size()>i; i++){
				for(int y = 0; listaLuoghiInteresseBotteghinoU.size()>y; y++){					
					if(listaLuoghiInteresseBotteghino.get(i)!= null && listaLuoghiInteresseBotteghinoU.get(y)!= null && listaLuoghiInteresseBotteghino.get(i).getUid().equals(listaLuoghiInteresseBotteghinoU.get(y).getUid())){
						a = listaLuoghiInteresseBotteghino.get(i);
					  	listaLuoghiInteresseBotteghinoR.set(c, a);
						
						System.out.print("i:"+listaLuoghiInteresseBotteghino.get(i).getUid());
						System.out.print("y:"+listaLuoghiInteresseBotteghinoU.get(y).getUid());
						System.out.println();
						c++;
					}
				}	
			}
			session.setAttribute("listaLuoghiInteresseBotteghino", listaLuoghiInteresseBotteghinoR);
		}
		
		return mapping.findForward("success");		
	}
}