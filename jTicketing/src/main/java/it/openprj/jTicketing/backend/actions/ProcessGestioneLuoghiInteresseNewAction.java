/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import java.util.ArrayList;

import it.openprj.jTicketing.backend.forms.LuogoInteresseForm;
import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.core.common.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.taglibs.standard.lang.jpath.expression.EncodeHtmlFunction;
import java.nio.charset.Charset;
public class ProcessGestioneLuoghiInteresseNewAction extends DispatchAction {
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
		
		LuogoInteresse dettaglioLuogoInteresse = new LuogoInteresse ();
		
		session.setAttribute("dettaglioLuogoInteresse", dettaglioLuogoInteresse);
        return mapping.findForward("new");
    }
	
	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		ActionMessages messages = new ActionMessages();
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
		
		LuogoInteresse luogoInteresse = new LuogoInteresse ();
		
		luogoInteresse.setTitolo(request.getParameter("titolo"));
		luogoInteresse.setDescrizione(request.getParameter("descrizione"));


		
		LuogoInteresseForm myForm = (LuogoInteresseForm) form;
		luogoInteresse.setNomeFile(myForm.getTheFile().getFileName());
		luogoInteresse.setImmagineByte(myForm.getTheFile().getFileData());
		
		if (myForm.getBtnAnnulla() != null && myForm.getBtnAnnulla().equalsIgnoreCase("Annulla")) {
				return mapping.findForward("success");
		}
		
		if (luogoInteresse.getTitolo().length() == 0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.titleIsRequired"));
			saveMessages(session, messages);
            return mapping.findForward("fail");
		}

        if (luogoInteresse.getTitolo().length() > 255) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.titleIsLong"));
			saveMessages(session, messages);
             return mapping.findForward("fail");
		}

		if (luogoInteresse.getDescrizione().length() == 0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.descriptionIsRequired"));
			saveMessages(session, messages);
             return mapping.findForward("fail");
		}

        if (luogoInteresse.getDescrizione().length() > 500) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.descriptionIsLong"));
			saveMessages(session, messages);
             return mapping.findForward("fail");
		}

		if (luogoInteresse.getNomeFile().length() == 0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.pictureIsRequired"));
			saveMessages(session, messages);
             return mapping.findForward("fail");
		}
		
		if (messages.size() > 0) {
			session.setAttribute("dettaglioLuogoInteresse", luogoInteresse);
			return mapping.findForward("fail");
		}
		
		DataTablesMgr dataTablesMgr = ServicesFactory.getInstance().getDataTablesMgr();
		dataTablesMgr.insertLuogoInteresse (luogoInteresse);
		
		ArrayList<LuogoInteresse> listaLuoghiInteresse = null;
		DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
		listaLuoghiInteresse = (ArrayList<LuogoInteresse>) service.searchLuoghiInteresse();
		session.setAttribute("listaLuoghiInteresse", listaLuoghiInteresse);
		
		return mapping.findForward("success");
	}
}
