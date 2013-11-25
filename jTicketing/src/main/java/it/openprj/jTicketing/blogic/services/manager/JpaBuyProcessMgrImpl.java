/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.manager;

import it.openprj.jTicketing.blogic.entity.AvailableDayTurn;
import it.openprj.jTicketing.blogic.entity.Purchase;
import it.openprj.jTicketing.blogic.entity.TicketCategory;
import it.openprj.jTicketing.blogic.entity.Turn;
import it.openprj.jTicketing.blogic.entity.User0;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaTemplate;


/**
 *
 * @author Andrea Saba
 */
public class JpaBuyProcessMgrImpl implements BuyProcessMgr {
	@SuppressWarnings("unused")
  private static final Logger log = Logger.getLogger(JpaBuyProcessMgrImpl.class);

  private JpaTemplate jpaTemplate;
  public void setJpaTemplate(JpaTemplate jpaTemplate) {
    this.jpaTemplate = jpaTemplate;
  }

	public int quantitaResidua(long uidTurno) throws SystemException, SQLException {
	  long res= (Long) jpaTemplate.findByNamedParams(
	                                       "select a.availableQty from AvailableDayTurn a where a.id=:aid",
	                                       JpaBackEndMgrImpl.fillMap(null,"aid",uidTurno)).get(0);
	  return (int) res;
	}

	public void insertTicketAcquistatabili(User user, HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped) throws SystemException, SQLException {
		Iterator<String> iter = purchasedticketGrouped.keySet().iterator();
		while (iter.hasNext()) {
			String keyMap = iter.next();
			long uidTurno = purchasedticketGrouped.get(keyMap).get(0).getUidTurno();
			int quantitaAquistata = purchasedticketGrouped.get(keyMap).size();

			AvailableDayTurn adt= jpaTemplate.find(AvailableDayTurn.class,uidTurno);
			adt.setAvailableQty(adt.getAvailableQty()-quantitaAquistata);

      for (int i = 0; i < purchasedticketGrouped.get(keyMap).size(); i++) {
        TicketAcquistato ticketAcquistato = purchasedticketGrouped.get(keyMap).get(i);
        Purchase p = new Purchase();
        p.setAttractionId(Long.parseLong(ticketAcquistato.getTicket().getUid()));
        p.setPurchaseDate(new Date(System.currentTimeMillis()));
        p.setConfirmationCode(ticketAcquistato.getCodiceVerifica());
        p.setConfirmed('N');
        p.setConfirmationDate(null);
        p.setBuyerEMail(user.getEmail());
        p.setDay(Integer.parseInt(ticketAcquistato.getGiorno()));
        p.setMonth(Integer.parseInt(ticketAcquistato.getMese()));
        p.setYear(Integer.parseInt(ticketAcquistato.getAnno()));
        p.setBuyer(jpaTemplate.find(User0.class, user.getIdUser()));
        p.setCategory(jpaTemplate.find(TicketCategory.class, Long.parseLong(ticketAcquistato.getCategoria().getUid())));
        p.setTicketPrice(ticketAcquistato.getCategoria().getPrezzo());
        p.setTurn(jpaTemplate.find(Turn.class, Long.parseLong(ticketAcquistato.getIdTurno())));
        jpaTemplate.persist(p);
        ticketAcquistato.setUid(p.getId());
      }
		}
	}

	public void insertTicketAcquistatabiliBotteghino(User user, HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped) throws SystemException, SQLException {
		Iterator<String> iter = purchasedticketGrouped.keySet().iterator();
		while (iter.hasNext()) {
			String keyMap = iter.next();
			long uidTurno = purchasedticketGrouped.get(keyMap).get(0).getUidTurno();
			int quantitaAquistata = purchasedticketGrouped.get(keyMap).size();

			AvailableDayTurn adt= jpaTemplate.find(AvailableDayTurn.class,uidTurno);
			adt.setAvailableQty(adt.getAvailableQty()-quantitaAquistata);
			GregorianCalendar gc = new GregorianCalendar();

      for (int i = 0; i < purchasedticketGrouped.get(keyMap).size(); i++) {
        TicketAcquistato ticketAcquistato = purchasedticketGrouped.get(keyMap).get(i);
        Purchase p = new Purchase();
        p.setAttractionId(Long.parseLong(ticketAcquistato.getTicket().getUid()));
        p.setPurchaseDate(new Date(System.currentTimeMillis()));
        p.setConfirmationCode(ticketAcquistato.getCodiceVerifica());
        p.setConfirmed('Y');
        p.setConfirmationDate(new Date(System.currentTimeMillis()));
        p.setBuyerEMail(user.getEmail());
        p.setDay(Integer.parseInt(ticketAcquistato.getGiorno()));
        p.setMonth(Integer.parseInt(ticketAcquistato.getMese()));
        p.setYear(Integer.parseInt(ticketAcquistato.getAnno()));
        p.setBuyer(jpaTemplate.find(User0.class, user.getIdUser()));
        p.setCategory(jpaTemplate.find(TicketCategory.class, Long.parseLong(ticketAcquistato.getCategoria().getUid())));
        p.setTicketPrice(ticketAcquistato.getCategoria().getPrezzo());
        p.setTurn(jpaTemplate.find(Turn.class, Long.parseLong(ticketAcquistato.getIdTurno())));
        jpaTemplate.persist(p);
        ticketAcquistato.setUid(p.getId());
      }
		}
	}
}
