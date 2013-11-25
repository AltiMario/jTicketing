/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.*;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;

public class ProcessWizardLuogoInteresseAction extends DispatchAction {

    public ProcessWizardLuogoInteresseAction() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	public ActionForward get(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
        invalidaSceltePrecedenti(session);
        ArrayList<LuogoInteresse> listaLuoghiInteresse = (ArrayList<LuogoInteresse>) session.getAttribute("listaLuoghiInteresse");
		// Leggo i luoghi di interesse legati all'utente dal DB
		User user = (User) session.getAttribute("user");
        BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
		try {
			listaLuoghiInteresse = (ArrayList<LuogoInteresse>) service.searchLuoghiOperatore(user);
            if (listaLuoghiInteresse == null || listaLuoghiInteresse.size() == 0) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("err.029"));
				saveMessages(session, messages);
				return mapping.findForward("failPrev");
			}
			session.setAttribute("listaLuoghiInteresse", listaLuoghiInteresse);
   		} catch (SystemException se) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.priceIsRequired"));
			saveMessages(session, messages);
			return mapping.findForward("failPrev");
		}
		return mapping.findForward("refresh");
	}

	@SuppressWarnings("unchecked")
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ArrayList<LuogoInteresse> listaLuoghiInteresse = (ArrayList<LuogoInteresse>) session.getAttribute("listaLuoghiInteresse");

		String selLuogoInteresse = (String) PropertyUtils.getSimpleProperty(form, "selLuogoInteresse");
		String btnAvanti = (String) PropertyUtils.getSimpleProperty(form, "btnAvanti");
        request.setAttribute("dispatch", "wizard");
		if ((btnAvanti != null && btnAvanti.equalsIgnoreCase("Forward >")) && (selLuogoInteresse == null || selLuogoInteresse.trim().length() == 0)) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.selectPlaceAndPushButton"));
			saveMessages(session, messages);
			return mapping.findForward("fail");
		} else {
			// Leggo i luoghi di interesse legati all'utente dal DB
			User user = (User) session.getAttribute("user");
			BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
			try {
                listaLuoghiInteresse = (ArrayList<LuogoInteresse>) service.searchLuoghiOperatore(user);
				if (listaLuoghiInteresse == null || listaLuoghiInteresse.size() == 0) {
					messages.add(Constants.MESSAGES_ERROR, new ActionMessage("err.029"));
					saveMessages(session, messages);
					return mapping.findForward("failPrev");
				}
				session.setAttribute("listaLuoghiInteresse", listaLuoghiInteresse);
			} catch (SystemException se) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.priceIsRequired"));
				saveMessages(session, messages);
				return mapping.findForward("failPrev");
			}
		}
		if (btnAvanti != null && btnAvanti.equalsIgnoreCase("Forward >")) {
			// A controlli fatti inserisco in sessione il luogo selezionato
			LuogoInteresse luogoInteresse = listaLuoghiInteresse.get(Integer.parseInt(selLuogoInteresse));
            session.setAttribute("luogoInteresse", luogoInteresse);
			return mapping.findForward("success");
		}
		return mapping.findForward("fail");
	}

    private void invalidaSceltePrecedenti(HttpSession session) {
        ArrayList<PrezzoCategoriaTicket> listaPrezziCategorie = (ArrayList<PrezzoCategoriaTicket>) session.getAttribute("listaPrezziCategorie");
        if(listaPrezziCategorie!=null && !listaPrezziCategorie.isEmpty()) {
            session.removeAttribute("listaPrezziCategorie");
        }
        ArrayList<Turno> listaTurni = (ArrayList<Turno>) session.getAttribute("listaTurni");
        if(listaTurni!=null && !listaTurni.isEmpty()) {
            session.removeAttribute("listaTurni");
        }
        ArrayList<Ticket> listaTickets = (ArrayList<Ticket>) session.getAttribute("listaTickets");
        if(listaTickets!=null && !listaTickets.isEmpty()) {
            session.removeAttribute("listaTickets");
        }
    }
}
