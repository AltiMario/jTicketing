/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */


package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.backend.forms.TicketForm;
import it.openprj.jTicketing.blogic.model.entity.*;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.core.common.Constants;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Service;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.hibernate.mapping.Array;

public class ProcessWizardStep2Action extends DispatchAction {

	public ProcessWizardStep2Action() {
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

        ArrayList<Ticket> listaTickets = (ArrayList<Ticket>) session.getAttribute("listaTickets");
		ArrayList<PrezzoCategoriaTicket> listaPrezziCategorie = (ArrayList<PrezzoCategoriaTicket>) session.getAttribute("listaPrezziCategorie");


        if ((ArrayList<Turno>) session.getAttribute("listaTurni") == null || ((ArrayList<Turno>) session.getAttribute("listaTurni")).size() == 0) {
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.insertRequiredData"));
			saveMessages(session, messages);
			return mapping.findForward("failPrev");
		}

        TicketForm myForm = (TicketForm) form;


		// Controlli
		if (myForm.getBtnAdd() != null && myForm.getBtnAdd().length() > 0) {
			if (myForm.getTitolo() == null || myForm.getTitolo().trim().length() == 0) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.titleIsRequired"));
			}
			if (myForm.getDescrizione() == null || myForm.getDescrizione().trim().length() == 0) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.descriptionIsRequired"));
			}
			if (myForm.getTheFile() == null || myForm.getTheFile().getFileName() == null || myForm.getTheFile().getFileName().trim().length() == 0) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.pictureIsRequired"));
			}
			if (myForm.getTheFile() == null || myForm.getTheFile().getFileName() == null || (!myForm.getTheFile().getFileName().endsWith("jpg") && !myForm.getTheFile().getFileName().endsWith("png") && !myForm.getTheFile().getFileName().endsWith("gif") && !myForm.getTheFile().getFileName().endsWith("JPG") && !myForm.getTheFile().getFileName().endsWith("PNG") && !myForm.getTheFile().getFileName().endsWith("GIF"))) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.filePictureFormat"));
			}
			if (myForm.getCategorie() == null || myForm.getCategorie().length == 0) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.selectCategory"));
			}

            if (myForm.getSCodicePosto() == null || myForm.getSCodicePosto().trim().length() == 0) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sCodeIsRequired"));
			}

            if (myForm.getIdTypeGenus() == null ) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sTypeGenusIsRequired"));
			}

            if (myForm.getSTipologiaEvento() == null ) {
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sEventTypeIsRequired"));
			}

            if (!messages.isEmpty()) {
				saveMessages(session, messages);
				return mapping.findForward("fail");
			}
		}

		if (listaTickets == null)
			listaTickets = new ArrayList<Ticket>();

        FormFile myFile = null;
		// Process the FormFile uploaded
        if (myForm != null && myForm.getTitolo() != null && myForm.getDescrizione() != null && myForm.getSCodicePosto() != null  && myForm.getSTipologiaEvento() != null) {
			myFile = myForm.getTheFile();
		}


		Ticket ticket = null;
        String descriptionT = null;
        String STipologiaEvento = null;
        int SIncidenza;
		if (myForm.getTitolo() != null && myForm.getDescrizione() != null && myForm.getSCodicePosto() != null && myForm.getIdTypeGenus() != null && myForm.getSTipologiaEvento() != null ) {
			ticket = new Ticket();
            ticket.setTitolo(myForm.getTitolo());
            ticket.setDescrizione(myForm.getDescrizione());
            ticket.setNomeFile(myForm.getTheFile().getFileName());

            //ticket.setNumeroPosto(myForm.getNumeroPosto());
            ticket.setSCodicePosto(myForm.getSCodicePosto());
            ticket.setSIncidenza(myForm.getSIncidenza());
            if (myForm.getCategorie() != null) {
				for (int i = 0; i < myForm.getCategorie().length; i++) {
					if (ticket.getPrezziCategorie() == null)
						ticket.setPrezziCategorie(new ArrayList<PrezzoCategoriaTicket>());
					ticket.getPrezziCategorie().add(listaPrezziCategorie.get(Integer.parseInt(myForm.getCategorie()[i])));
				}
			}
            BackEndMgr service1 = ServicesFactory.getInstance().getBackEndMgrMgr();
            descriptionT= service1.selectTypeGenusName(myForm.getIdTypeGenus());
            ticket.setIdTypeGenus(myForm.getIdTypeGenus());
            ticket.setNameTypeGenus(descriptionT);
            System.out.println("type genus." + descriptionT);
            ticket.setAcronyms(myForm.getAcronyms());
            ticket.setTypeOrganizer(myForm.getTypeOrganizer());
            System.out.println("tipo org." + myForm.getTypeOrganizer());

            ticket.setAcronyms(myForm.getAcronyms());
            ticket.setNumberOperas(myForm.getNumberOperas());


            if (myForm.getSTipologiaEvento().equals("1")) {
                STipologiaEvento = "Spettacolo";
            }  else if (myForm.getSTipologiaEvento().equals("2")) {
                STipologiaEvento = "Intrattenimento";
            }  else  if (myForm.getSTipologiaEvento().equals("3")) {
                STipologiaEvento = "Spettacolo/Intrattenimento (%)";
            }
            ticket.setSTipologiaEvento(STipologiaEvento);
            ticket.setExecutor(myForm.getExecutor());
            ticket.setAuthor(myForm.getAuthor());


			// Metto in sessione prima dell'immagine
            listaTickets.add(ticket);
            session.setAttribute("listaTickets", listaTickets);
			ticket.setImmagine(myFile.getFileData());
			session.setAttribute("ticketImage_" + ticket.getNomeFile(), myFile.getFileData());
		}

        return mapping.findForward("success");


	}



	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionMessages messages = new ActionMessages();
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ArrayList<Ticket> listaTickets = (ArrayList<Ticket>) session.getAttribute("listaTickets");
		if (listaTickets == null)
			return mapping.findForward("success");
		String id = request.getParameter("id");
		listaTickets.remove(Integer.parseInt(id));
		return mapping.findForward("success");
	}
}
