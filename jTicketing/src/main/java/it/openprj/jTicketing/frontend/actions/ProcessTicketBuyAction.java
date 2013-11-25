/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.frontend.actions;

import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BuyProcessMgr;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.blogic.utilities.RandomPassword;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.frontend.forms.CartForm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

public class ProcessTicketBuyAction extends Action {

	public ProcessTicketBuyAction() {
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
		
		// è stato premuto il tasto elimina dal carrello
		if (request.getParameter("codiceVerifica") != null) {
			HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped = (HashMap<String, ArrayList<TicketAcquistato>>) session.getAttribute("purchasedticketGrouped");
			Iterator<String> iter = purchasedticketGrouped.keySet().iterator();
			while (iter.hasNext()) {
				String keyMap = iter.next();
				if (purchasedticketGrouped.get(keyMap).get(0).getCodiceVerifica().equals(request.getParameter("codiceVerifica"))) {
					purchasedticketGrouped.get(keyMap).remove(0);
					if (purchasedticketGrouped.get(keyMap).size() == 0) {
						purchasedticketGrouped.remove(keyMap);
						iter = purchasedticketGrouped.keySet().iterator();
					}
				}
			}
			CartForm cartForm = (CartForm) form;
			ArrayList<TicketAcquistato> listaAcquisti = null;
			listaAcquisti = cartForm.getListaTicketAcquistati();
			for (int cont=0 ; cont < listaAcquisti.size() ; cont++) {
				if (listaAcquisti.get(cont).getCodiceVerifica().equals(request.getParameter("codiceVerifica"))) {
					listaAcquisti.remove(cont);
				}
			}
			if (purchasedticketGrouped.size() == 0) {
				session.removeAttribute("listaTickets");
				session.removeAttribute("purchasedticketGrouped");
				session.removeAttribute("listForRenderTicketPurchased");
				cartForm.setListaTicketAcquistati(null);
			} else {
				session.setAttribute("purchasedticketGrouped" , purchasedticketGrouped);
				session.setAttribute("listForRenderTicketPurchased", listaAcquisti);
				cartForm.setListaTicketAcquistati(listaAcquisti);
			}
			return mapping.findForward("forwardToCart");
		}
		
		String uidTurno = request.getParameter("uidTurno");
		String idTurno = request.getParameter("idTurno");
		String turno = request.getParameter("turno");
		String uidTicket = request.getParameter("uidTicket");
		String anno = session.getAttribute("iYear").toString();
		String mese = Integer.toString(Integer.parseInt(session.getAttribute("iMonth").toString()) + 1);
		String giorno;
	    String uidLuogoInteresse = session.getAttribute("uid").toString();
		ArrayList<TicketAcquistato> listaAcquisti = null;
		ArrayList<TicketAcquistato> tempListaAcquisti = null;
		ArrayList<Ticket> listaTickets = (ArrayList<Ticket>) session.getAttribute("listaTickets");
		HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped = (HashMap<String, ArrayList<TicketAcquistato>>) session.getAttribute("purchasedticketGrouped");
		String mapKey = "";
		if (uidTurno != null && uidTurno.trim().length() > 0 && uidTicket != null && uidTicket.trim().length() > 0) {
			session.setAttribute("uidTurno", uidTurno);
			session.setAttribute("idTurno", idTurno);
			session.setAttribute("uidTicket", uidTicket);
			session.setAttribute("turno", turno);
		} else {
			uidTurno = session.getAttribute("uidTurno").toString();
			idTurno = session.getAttribute("idTurno").toString();
			uidTicket = session.getAttribute("uidTicket").toString();
			turno = session.getAttribute("turno").toString();
		}
		CartForm cartForm = (CartForm) form;
		
		// è stato premuto il tasto Scegli altri biglietti
		if (cartForm != null && cartForm.getBtn() != null && cartForm.getBtn().equalsIgnoreCase("Choose more tickets")) {
			cartForm.setBtn("");
			return mapping.findForward("change");
		}

		// e' stato premuto il tasto Aggiungi
		if (cartForm != null && cartForm.getBtn() != null && cartForm.getBtn().equalsIgnoreCase("Add")) {
			String uidCategoria = cartForm.getCategoria();
			String qnt = cartForm.getQuantita();
			try {
				int qnt_int = Integer.parseInt(qnt);
			} catch (Exception e) {
				qnt = "0";
				cartForm.setBtn("");
				cartForm.setQuantita("");
				
				ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.ticketNumberIsInvalid"));
				saveMessages(session, messages);
				
				return mapping.findForward("success");
			}
			
			if (Integer.parseInt(qnt) <= 0 ) {
				qnt = "0";
				cartForm.setBtn("");
				cartForm.setQuantita("");
				
				ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.ticketNumberIsInvalid"));
				saveMessages(session, messages);
				
				return mapping.findForward("success");
			}
			
			DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
			
			int ticketResidui = 0;
			giorno = session.getAttribute("iDay").toString();
			ticketResidui = service.searchQuantitaResiduaGiorno(mese, anno, giorno , uidLuogoInteresse , uidTicket , idTurno);
			
			long uidTurnoLong = Long.parseLong(uidTurno);
			int quantitaAquistata = 0;
			
			if (purchasedticketGrouped == null) {
				purchasedticketGrouped = new HashMap<String, ArrayList<TicketAcquistato>>();
			}
			
			Iterator<String> iter = purchasedticketGrouped.keySet().iterator();
			while (iter.hasNext()) {
				String keyMap = iter.next();
				if (purchasedticketGrouped.get(keyMap).get(0).getUidTurno() == uidTurnoLong) {
					quantitaAquistata = quantitaAquistata + purchasedticketGrouped.get(keyMap).size();
				}
			}
			
			if (Integer.parseInt(qnt) > (ticketResidui - quantitaAquistata)) {
				qnt = "0";
				cartForm.setBtn("");
				cartForm.setQuantita("");
				
				ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.ticketNumberNotAvailable"));
				saveMessages(session, messages);
				
				return mapping.findForward("success");
			}
			
			listaAcquisti = cartForm.getListaTicketAcquistati();
			if (listaAcquisti == null)
				listaAcquisti = new ArrayList<TicketAcquistato>();
			TicketAcquistato ticketAcquistato = null;
			
			for (int i = 0; i < Integer.parseInt(qnt); i++) {
				
				// Questi biglietti sono tutti sicuramente dello stesso tipo
				ticketAcquistato = new TicketAcquistato();
				// Catturo il ticket
				Ticket ticket = null;
				for (int x = 0; x < listaTickets.size(); x++) {
					ticket = listaTickets.get(x);
					if (ticket.getUid().equalsIgnoreCase(session.getAttribute("uidTicket").toString()))
						break;
				}
				ticketAcquistato.setTicket(ticket);
				// Seleziono la categoria scelta
				ArrayList<PrezzoCategoriaTicket> listaCategorie = ticket.getPrezziCategorie();
				PrezzoCategoriaTicket categoriaticket = null;
				for (int y = 0; y < listaCategorie.size(); y++) {
					categoriaticket = listaCategorie.get(y);
					if (categoriaticket.getUid().equalsIgnoreCase(uidCategoria))
						break;
				}
				ticketAcquistato.setUid(Long.parseLong(session.getAttribute("uidTicket").toString())); 
				ticketAcquistato.setCategoria(categoriaticket);
				ticketAcquistato.setUidTurno(Long.parseLong(session.getAttribute("uidTurno").toString()));
				ticketAcquistato.setGiorno(giorno);
				ticketAcquistato.setMese(mese);
				ticketAcquistato.setAnno(anno);
				ticketAcquistato.setTurno(session.getAttribute("turno").toString());
				ticketAcquistato.setIdTurno(idTurno);
				User buyer = (User) session.getAttribute("user");
				if (buyer != null)
					ticketAcquistato.setAcquirente(buyer);
				// Generazione del codiceunivoco del biglietto
				
				
				//String uniqueIdentifier = ticketAcquistato.getUidLuogoInteresse() + giorno + mese + anno + UUID.randomUUID().toString();
				String uniqueIdentifier = RandomPassword.getRandomString(5);
				
				// Controllo che non sia già presente il codice di verifica su
				// un altro biglietto
				ticketAcquistato.setCodiceVerifica(uniqueIdentifier);
				listaAcquisti.add(ticketAcquistato);
			}// End for
			tempListaAcquisti = new ArrayList<TicketAcquistato>();
			TicketAcquistato ticketAcquistatoTemp = null;
			if (purchasedticketGrouped == null) {
				purchasedticketGrouped = new HashMap<String, ArrayList<TicketAcquistato>>();
			}
			// Raggruppo nell'hashmap i ticket dello stesso tipo.
			for (int z = 0; z < listaAcquisti.size(); z++) {
				ticketAcquistatoTemp = listaAcquisti.get(z);
				mapKey = ticketAcquistatoTemp.getUidLuogoInteresse() + ticketAcquistato.getUidTurno() + giorno + mese + anno + ticketAcquistatoTemp.getCategoria();
				
				if (ticketAcquistatoTemp.equals(ticketAcquistato)) {
					if (!purchasedticketGrouped.containsKey(mapKey)) {
						purchasedticketGrouped.put(mapKey, new ArrayList<TicketAcquistato>());
					}
			    	// Controllo che la mappa non contenga già questo biglietto
					boolean isPresent = false;
					for (int y = 0; y < purchasedticketGrouped.get(mapKey).size(); y++) {
						if (purchasedticketGrouped.get(mapKey).get(y).getCodiceVerifica().equals(ticketAcquistatoTemp.getCodiceVerifica())) {
							isPresent = true;
							break;
						}
					}
					if (!isPresent) {
						// Non contiene il biglietto quindi lo aggiungo
						purchasedticketGrouped.get(mapKey).add(ticketAcquistatoTemp);
					}
				}
			}
			session.setAttribute("purchasedticketGrouped", purchasedticketGrouped);
			session.setAttribute("listForRenderTicketPurchased", listaAcquisti);
			cartForm.setListaTicketAcquistati(listaAcquisti);
			cartForm.setQuantita("");
			// Forward al carrello
			cartForm.setBtn("");
			return mapping.findForward("forwardToCart");
		}

		// è stato premuto il tasto Carrello
		if (cartForm != null && cartForm.getBtn() != null && cartForm.getBtn().equalsIgnoreCase("Cart")) {
			cartForm.setBtn("");
			return mapping.findForward("forwardToCart");
		}

		// è stato premuto il tasto ontinua acquisti
		if (cartForm != null && cartForm.getBtn() != null && cartForm.getBtn().equalsIgnoreCase("Continua acquisti")) {
			cartForm.setBtn("");
			return mapping.findForward("forwardToSearch");
		}
		
		try {
			// è stato premuto il tasto acquista
			if (cartForm != null && cartForm.getBtn() != null && cartForm.getBtn().equalsIgnoreCase("Buy")) {
				// PER PRIMA COSA CONTROLLO SE L'UTENTE E' LOGGATO ALTRIMENTI LO
				// FORWARDO ALLA LOGIN/REGISTRAZIONE
				User user = (User) session.getAttribute("user");
				if (user != null) {
					if (purchasedticketGrouped == null || purchasedticketGrouped.size() == 0) {
						return mapping.findForward("success");
					}
					BuyProcessMgr service = ServicesFactory.getInstance().getBuyProcessMgr();
					Iterator<String> iter = purchasedticketGrouped.keySet().iterator();
					while (iter.hasNext()) {
						String keyMap = iter.next();
						long uidTurnoLong = purchasedticketGrouped.get(keyMap).get(0).getUidTurno();
						int quantitaAquistata = 0;
						
						Iterator<String> iter2 = purchasedticketGrouped.keySet().iterator();
						while (iter2.hasNext()) {
							String keyMap2 = iter2.next();
							if (purchasedticketGrouped.get(keyMap2).get(0).getUidTurno() == uidTurnoLong) {
								quantitaAquistata = quantitaAquistata + purchasedticketGrouped.get(keyMap2).size();
							}
						}
						int quantitaResidua = service.quantitaResidua(uidTurnoLong);
						if (quantitaResidua < quantitaAquistata) {
							session.removeAttribute("listaTickets");
							session.removeAttribute("purchasedticketGrouped");
							session.removeAttribute("listForRenderTicketPurchased");
							cartForm.setListaTicketAcquistati(null);
							ActionMessages messages = new ActionMessages();
							messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.ticketNumberNotAvailable"));
							saveMessages(session, messages);
							return mapping.findForward("fail");
						} 
					}
					service.insertTicketAcquistatabili(user, purchasedticketGrouped);
					cartForm.setListaTicketAcquistati(null);
					return mapping.findForward("forwardToPayment");
				} else {
					ActionMessages messages = new ActionMessages();
					messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.beforeBuyLogIn"));
					saveMessages(session, messages);
					return mapping.findForward("forwardToLogin");
				}
			}

		} catch (Exception sql) {
			session.removeAttribute("listaTickets");
			session.removeAttribute("purchasedticketGrouped");
			session.removeAttribute("listForRenderTicketPurchased");
			cartForm.setListaTicketAcquistati(null);
			
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("message.ticketNumberNotAvailable"));
			saveMessages(session, messages);
			return mapping.findForward("fail");
		}
		cartForm.setBtn("");
		return mapping.findForward("success");
	}
}
