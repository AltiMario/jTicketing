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

import it.openprj.jTicketing.backend.jaxb.PlaceResponceType;
import it.openprj.jTicketing.backend.jaxb.TicketResponceType;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.ReportisticaLuoghiInteresse;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * 
 * @author Naveen
 */
@Path("/places")
public class PlacesController {

	private static Logger logger = Logger.getLogger(PlacesController.class);

	@Path("search")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<PlaceResponceType> search() {
		List<PlaceResponceType> objArray = new ArrayList<PlaceResponceType>();

		try {
			BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
			List<ReportisticaLuoghiInteresse> listLuoghiInteresse = service.reportisticaLuoghiInteresseAdmin();

			for (ReportisticaLuoghiInteresse objLuoghiInteresse : listLuoghiInteresse) {
				PlaceResponceType obj = new PlaceResponceType();

				obj.setPlaceId(objLuoghiInteresse.getUid());
				obj.setPlaceName(objLuoghiInteresse.getTitolo());
				obj.setPlaceImage("ImageProcessor?maxWidth=300&maxHeight=300&type=1&uid=" + objLuoghiInteresse.getUid());
				obj.setPlaceDesc(objLuoghiInteresse.getDescrizione());

				objArray.add(obj);
			}

		} catch (SystemException ex) {
			logger.error("SystemException occured during search Interesse Luoghi.");
		} catch (SQLException ex) {
			logger.error("SQLExfeption occured during search Interesse Luoghi.");
		}
		return objArray;
	}

	@Path("detail")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TicketResponceType> detail(@QueryParam(value = "uid") String uidLuoghiInteresse) {
		List<TicketResponceType> objArray = new ArrayList<TicketResponceType>();

		try {
			DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();

			List<Ticket> listaTickets = (List<Ticket>) service.searchTickets(uidLuoghiInteresse);

			for (Ticket ticket : listaTickets) {
				TicketResponceType obj = TicketsController.mapFromBean(ticket);
				objArray.add(obj);
			}

		} catch (SystemException ex) {
			logger.error("SystemException occured during detail Interesse Luogo.");
		} catch (SQLException ex) {
			logger.error("SQLExfeption occured during detail Interesse Luogo.");
		}
		return objArray;
	}

	public static PlaceResponceType mapFromBean(ReportisticaLuoghiInteresse luoghiInteresse) {
		PlaceResponceType obj = new PlaceResponceType();
		if (luoghiInteresse != null) {
			obj.setPlaceId(luoghiInteresse.getUid());
			obj.setPlaceName(luoghiInteresse.getTitolo());
			obj.setPlaceImage("ImageProcessor?maxWidth=300&maxHeight=300&type=1&uid=" + luoghiInteresse.getUid());
			obj.setPlaceDesc(luoghiInteresse.getDescrizione());
		}
		return obj;
	}

}
