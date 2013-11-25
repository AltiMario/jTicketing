/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.openprj.jTicketing.backend.rest;

import it.openprj.jTicketing.backend.jaxb.EventCalendarResponceType;
import it.openprj.jTicketing.backend.jaxb.PriceTicketResponceType;
import it.openprj.jTicketing.backend.jaxb.TicketResponceType;
import it.openprj.jTicketing.backend.jaxb.TurnResponceType;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.CalendarioEventi;
import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.Turno;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * 
 * @author Naveen
 */
@Path("/tickets")
public class TicketsController {

	private static Logger logger = Logger.getLogger(TicketsController.class);

	@Path("detailAndCalendar")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TicketResponceType> detailAndCalendar(@QueryParam(value = "uid") String uidLuoghiInteresse,
			@QueryParam(value = "uidTicket") String uidTicket, @QueryParam(value = "iYear") String iYear,
			@QueryParam(value = "iMonth") String iMonth, @QueryParam(value = "iDay") String iDay,
			@Context HttpServletRequest request) {

		List<TicketResponceType> objArray = new ArrayList<TicketResponceType>();

		try {

			Calendar ca = new GregorianCalendar();
			// E' il primo giro devo calcolare anno e mese
			if (iYear == null && iMonth == null) {
				int iTYear = ca.get(Calendar.YEAR);
				int iTMonth = ca.get(Calendar.MONTH);

				iYear = String.valueOf(iTYear);
				iMonth = String.valueOf(iTMonth);
			}

			DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();

			Ticket ticket = (Ticket) service.searchTickets(Long.parseLong(uidTicket));

			TicketResponceType obj = mapFromBean(ticket);

			HttpSession session = request.getSession();

			HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped = (HashMap<String, ArrayList<TicketAcquistato>>) session
					.getAttribute("purchasedticketGrouped");

			CalendarioEventi calendarioEventi = service.searchCalendarioMese(iMonth, iYear, uidLuoghiInteresse,
					uidTicket);

			if (iDay != null) {
				for (int cont = 0; cont < calendarioEventi.getTurni(iDay + (Integer.parseInt(iMonth) + 1) + iYear)
						.size(); cont++) {
					long uidTurnoLong = Long.parseLong(calendarioEventi
							.getTurni(iDay + (Integer.parseInt(iMonth) + 1) + iYear).get(cont).getUidTaOrarioEventi());
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

					calendarioEventi.getTurni(iDay + (Integer.parseInt(iMonth) + 1) + iYear).get(cont)
							.setQuantitaAquistata(quantitaAquistata);
				}
			}

			if (calendarioEventi != null) {
				ArrayList<EventCalendarResponceType> eventCalendars = new ArrayList<EventCalendarResponceType>();
				EventCalendarResponceType eventCalendar = mapFromBean(calendarioEventi);
				eventCalendars.add(eventCalendar);
				obj.setTicketEventCalendars(eventCalendars);
			}
			objArray.add(obj);

		} catch (SystemException ex) {
			logger.error("SystemException occured during detail and calendar Ticket.");
		} catch (SQLException ex) {
			logger.error("SQLExfeption occured during detail Ticket.");
		}
		return objArray;
	}

	public static TicketResponceType mapFromBean(Ticket ticket) {
		TicketResponceType obj = new TicketResponceType();
		if (ticket != null) {
			obj.setTicketId(ticket.getUid());
			obj.setTicketPlaceId(ticket.getUid_luoghi_interesse());
			obj.setTicketTitle(ticket.getTitolo());
			obj.setTicketDesc(ticket.getDescrizione());
			obj.setTicketSeatNr(ticket.getNumeroPosto());
			obj.setTicketSeatCode(ticket.getSCodicePosto());
			obj.setTicketEventType(ticket.getSTipologiaEvento());
			obj.setTicketIncidence(ticket.getSIncidenza());
			obj.setTicketExpirationDate(ticket.getData_scadenza());
			obj.setTicketImage("ImageProcessor?maxWidth=100&maxHeight=70&type=2&uid=" + ticket.getUid());

			ArrayList<PriceTicketResponceType> ticketPrices = new ArrayList<PriceTicketResponceType>();
			for (PrezzoCategoriaTicket prezzo : ticket.getPrezziCategorie()) {
				PriceTicketResponceType priceTicket = mapFromBean(prezzo);
				ticketPrices.add(priceTicket);
			}
			obj.setTicketPrices(ticketPrices);
		}
		return obj;
	}

	public static PriceTicketResponceType mapFromBean(PrezzoCategoriaTicket prezzo) {
		PriceTicketResponceType obj = new PriceTicketResponceType();
		if (prezzo != null) {
			obj.setPriceUid(prezzo.getUid());
			obj.setPriceDesc(prezzo.getDescrizione());
			obj.setPriceCondition(prezzo.getCondizioni());
			obj.setPriceAmount(prezzo.getPrezzo());
			obj.setPriceStartDate(prezzo.getDataInizio());
			obj.setPriceEndDate(prezzo.getDataFine());
		}
		return obj;
	}

	public static EventCalendarResponceType mapFromBean(CalendarioEventi calendarioEventi) {
		EventCalendarResponceType obj = new EventCalendarResponceType();
		if (calendarioEventi != null) {
			obj.setEventCalendarPlaceId(calendarioEventi.getUidLuoghiInteresse());
			obj.setEventCalendarMonth(calendarioEventi.getMese());
			obj.setEventCalendarYear(calendarioEventi.getAnno());

			ArrayList<TurnResponceType> turnList = new ArrayList<TurnResponceType>();
			for (String key : calendarioEventi.getListaGiorniDelMese().keySet()) {
				for (Turno turno : (calendarioEventi.getListaGiorniDelMese().get(key))) {
					TurnResponceType turn = mapFromBean(turno);
					turn.setTurnDay(key);
					turnList.add(turn);
				}
			}
			obj.setEventCalendarDaysOfMonth(turnList);
		}
		return obj;
	}

	public static TurnResponceType mapFromBean(Turno turno) {
		TurnResponceType obj = new TurnResponceType();
		if (turno != null) {
			obj.setTurnTpEventHourId(turno.getUidTpOrarioEventi());
			obj.setTurnTaEventHourId(turno.getUidTaOrarioEventi());
			obj.setTurnHour(turno.getOrario());
			obj.setTurnStartHour(turno.getOrarioApertura());
			obj.setTurnQuantity(turno.getQuantita());
			obj.setTurnRemainingQuantity(turno.getQuantitaResidua());
			obj.setTurnEventCalendarId(turno.getUidCalendarioEventi());
			obj.setTurnPlaceId(turno.getUidLuogoInteresse());
			obj.setTurnTicketId(turno.getUidTicket());
			obj.setTurnPurchasedQuantity(turno.getQuantitaAquistata());
		}
		return obj;
	}
}
