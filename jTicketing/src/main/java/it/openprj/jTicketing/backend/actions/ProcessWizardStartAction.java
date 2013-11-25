/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.core.common.Constants;

import java.math.BigDecimal;
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

public class ProcessWizardStartAction extends DispatchAction {
	public ProcessWizardStartAction() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ArrayList<PrezzoCategoriaTicket> listaPrezziCategorie = (ArrayList<PrezzoCategoriaTicket>) session.getAttribute("listaPrezziCategorie");

		if (listaPrezziCategorie == null)
			listaPrezziCategorie = new ArrayList<PrezzoCategoriaTicket>();

		PrezzoCategoriaTicket prezzoCategoraTicket = null;

		String categoria = (String) PropertyUtils.getSimpleProperty(form, "categoria");
		if (categoria == null || categoria.length() == 0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.categoryIsRequired"));
			saveMessages(session, messages);
		}

		String prezzo = (String) PropertyUtils.getSimpleProperty(form, "prezzo");
		String condizioni = (String) PropertyUtils.getSimpleProperty(form, "condizioni");
		for (int i = 0; i < listaPrezziCategorie.size(); i++) {
			if (categoria.equalsIgnoreCase(listaPrezziCategorie.get(i).getDescrizione())) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.categoryName"));
				saveMessages(session, messages);
			}
		}
		if (!messages.isEmpty()) {
			return mapping.findForward("fail");
		}
		try {
			if (prezzo != null & categoria != null) {
				prezzoCategoraTicket = new PrezzoCategoriaTicket();
				prezzoCategoraTicket.setDescrizione(categoria);
				prezzoCategoraTicket.setPrezzo(new BigDecimal(prezzo));
				prezzoCategoraTicket.setCondizioni(condizioni);
				listaPrezziCategorie.add(prezzoCategoraTicket);
				session.setAttribute("listaPrezziCategorie", listaPrezziCategorie);
			} else {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.systemFailure"));
				saveMessages(session, messages);
				return mapping.findForward("fail");
			}
		} catch (NumberFormatException nfe) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.priceIsRequired"));
			saveMessages(session, messages);
			return mapping.findForward("fail");
		}
		PropertyUtils.setSimpleProperty(form, "categoria", null);
		PropertyUtils.setSimpleProperty(form, "prezzo", null);
		PropertyUtils.setSimpleProperty(form, "condizioni", null);
		// Forward to result page
		return mapping.findForward("success");
	}
	
	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		ArrayList<PrezzoCategoriaTicket> listaPrezziCategorie = (ArrayList<PrezzoCategoriaTicket>) session.getAttribute("listaPrezziCategorie");

		if (listaPrezziCategorie == null)
			return mapping.findForward("success");
		
		//Eliminazione
		String id=(String)request.getParameter("id");
		listaPrezziCategorie.remove(Integer.parseInt(id));
		listaPrezziCategorie.trimToSize();
		
		return mapping.findForward("success");
	}
}
