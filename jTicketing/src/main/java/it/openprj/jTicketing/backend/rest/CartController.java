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

import it.openprj.jTicketing.backend.jaxb.PurchasedTicketResponceType;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * 
 * @author Naveen
 */
@Path("/cart")
public class CartController {

	private static Logger logger = Logger.getLogger(CartController.class);

	@Path("detail")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PurchasedTicketResponceType> detail(@Context HttpServletRequest request) {

		List<PurchasedTicketResponceType> objArray = new ArrayList<PurchasedTicketResponceType>();

		boolean present = true;

		HttpSession session = request.getSession();

		HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped = (HashMap<String, ArrayList<TicketAcquistato>>) session
				.getAttribute("purchasedticketGrouped");

		if (purchasedticketGrouped == null) {
			present = false;
		}
		
		if (present) {
			for (String keyMap : purchasedticketGrouped.keySet()) {
				TicketAcquistato ticketAcquistato = purchasedticketGrouped.get(keyMap).get(0);
				PurchasedTicketResponceType obj = new PurchasedTicketResponceType();

				obj.setPurchasedTicketId(ticketAcquistato.getUid());
				obj.setPurchasedTicketExpirationDate(ticketAcquistato.getDataScadenza());
				obj.setPurchasedTicketDay(ticketAcquistato.getGiorno());
				obj.setPurchasedTicketMonth(ticketAcquistato.getMese());
				obj.setPurchasedTicketYear(ticketAcquistato.getAnno());
				obj.setPurchasedTicketTurn(ticketAcquistato.getTurno());
				obj.setPurchasedTicketVerificationCode(ticketAcquistato.getCodiceVerifica());
				obj.setPurchasedTicket(TicketsController.mapFromBean(ticketAcquistato.getTicket()));
				obj.setPurchasedTicketTurnId(ticketAcquistato.getUidTurno());
				obj.setPurchasedTicketPrice(TicketsController.mapFromBean(ticketAcquistato.getCategoria()));
				obj.setPurchasedTicketPlaceId(ticketAcquistato.getUidLuogoInteresse());
				obj.setPurchasedTicketValidated(ticketAcquistato.getConvalidato());
				obj.setPurchasedTicketValidationDate(ticketAcquistato.getDataConvalida());
				obj.setPurchasedTicketValidationOperator(UsersController.mapFromBean(ticketAcquistato
						.getOperatoreConvalidante()));
				obj.setPurchasedTicketBuyer(UsersController.mapFromBean(ticketAcquistato.getAcquirente()));

				objArray.add(obj);
			}
		}

		return objArray;
	}
}
