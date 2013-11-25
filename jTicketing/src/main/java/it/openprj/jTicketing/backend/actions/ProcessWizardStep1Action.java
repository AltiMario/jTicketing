/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.blogic.model.entity.Turno;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;
import java.util.regex.Pattern;

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

public class ProcessWizardStep1Action extends DispatchAction {

	public ProcessWizardStep1Action() {
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
		if (session.getAttribute("listaPrezziCategorie") == null || ((ArrayList<PrezzoCategoriaTicket>) session.getAttribute("listaPrezziCategorie")).size() == 0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.insertRequiredData"));
			saveMessages(session, messages);
			return mapping.findForward("failPrev");
		}
		ArrayList<Turno> listaTurni = (ArrayList<Turno>) session.getAttribute("listaTurni");

		if (listaTurni == null)
			listaTurni = new ArrayList<Turno>();

		Turno turno = null;

		String orario = (String) PropertyUtils.getSimpleProperty(form, "orario");
		String orarioApertura = (String) PropertyUtils.getSimpleProperty(form, "orarioApertura");
		String quantita = (String) PropertyUtils.getSimpleProperty(form, "quantita");
		String btnAdd = (String) PropertyUtils.getSimpleProperty(form, "btnAdd");

		try {
			// Submited
			if (btnAdd != null && btnAdd.length() > 0) {
				if (orario != null && orario.length() > 0 && orarioApertura != null && orarioApertura.length() > 0 && quantita != null && quantita.length() > 0) {
					turno = new Turno();
					turno.setOrario(orario);
					turno.setOrarioApertura(orarioApertura);
					turno.setQuantita(Integer.parseInt(quantita));
					listaTurni.add(turno);
					session.setAttribute("listaTurni", listaTurni);
				} else {
					if (orario == null || orario.length() == 0)
						messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.priceTimeRoundIsRequired"));

                    if (orarioApertura == null || orarioApertura.length() == 0)
						messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.timeOpenIsRequired"));

                    if (quantita == null || quantita.length() == 0)
						messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.amountIsRequired"));

                        saveMessages(session, messages);
                        return mapping.findForward("fail");
				}
			}
		} catch (NumberFormatException nfe) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.amountIsRequired"));
			saveMessages(session, messages);
			return mapping.findForward("fail");
		}

		PropertyUtils.setSimpleProperty(form, "orario", null);
		PropertyUtils.setSimpleProperty(form, "orarioApertura", null);
		PropertyUtils.setSimpleProperty(form, "quantita", null);
	
		return mapping.findForward("success");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ArrayList<Turno> listaTurni = (ArrayList<Turno>) session.getAttribute("listaTurni");

		if (listaTurni == null)
			return mapping.findForward("success");

		String id = request.getParameter("id");
		listaTurni.remove(Integer.parseInt(id));
		listaTurni.trimToSize();
		
		return mapping.findForward("success");
	}
}
