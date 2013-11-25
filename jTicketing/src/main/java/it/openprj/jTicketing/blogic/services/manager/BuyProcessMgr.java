/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.manager;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public interface BuyProcessMgr {

  public abstract int quantitaResidua(long uidTurno) throws SystemException, SQLException;

  public abstract void insertTicketAcquistatabili(User user,
                                                  HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped)
                                                                                                                      throws SystemException,
                                                                                                                      SQLException;
  
  public abstract void insertTicketAcquistatabiliBotteghino(User user,
          													HashMap<String, ArrayList<TicketAcquistato>> purchasedticketGrouped)
                                                                              										   throws SystemException,
                                                                              										   SQLException;

}