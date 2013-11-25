/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.dto.WizardDTO;
import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.Turno;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
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

public class ProcessWizardStep4Action extends Action {

	public ProcessWizardStep4Action() {
		super();
	}

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ActionMessages messages = new ActionMessages();

		// Controllo se esiste la lista dei tickets
		if (session.getAttribute("listaTickets") == null || ((ArrayList<Ticket>) session.getAttribute("listaTickets")).size() == 0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.insertRequiredData"));
			saveMessages(session, messages);
			return mapping.findForward("failStep3");
		}
		// Controllo che sia presente la configurazione per tutti i ticket
		ArrayList<Ticket> listaTicket = (ArrayList<Ticket>) session.getAttribute("listaTickets");
		Ticket ticket = null;
		for (int i = 0; i < listaTicket.size(); i++) {
			ticket = listaTicket.get(i);
			if (ticket.getCalendarioCompleto() == null) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.ticketConfiguration"));
				saveMessages(session, messages);
				return mapping.findForward("failStep3");
			}
		}

		// Mando alla business logic tramite un unico DTO
		WizardDTO wizardDTO = new WizardDTO();
		wizardDTO.setUser((User) session.getAttribute("user"));
		wizardDTO.setListaTickets(listaTicket);
		wizardDTO.setListaTurni((ArrayList<Turno>) session.getAttribute("listaTurni"));
		wizardDTO.setLuogoInteresse((LuogoInteresse) session.getAttribute("luogoInteresse"));
		wizardDTO.setListaPrezziCategorie((ArrayList<PrezzoCategoriaTicket>) session.getAttribute("listaPrezziCategorie"));
		try {
			BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
			service.saveCalendar(wizardDTO);
		} catch (SystemException se) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.systemFailure"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		}
		// Forward to result page
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.configurationEndOk"));
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
}
