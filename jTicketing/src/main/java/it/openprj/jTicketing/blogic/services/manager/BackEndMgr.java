/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.manager;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.dto.WizardDTO;
import it.openprj.jTicketing.blogic.model.entity.ConfigurazioneSIAE;
import it.openprj.jTicketing.blogic.model.entity.GenereEvento;
import it.openprj.jTicketing.blogic.model.entity.LuogoInteresse;
import it.openprj.jTicketing.blogic.model.entity.ModificaVenditeLuoghiInteresse;
import it.openprj.jTicketing.blogic.model.entity.NazionalitaFilm;
import it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket;
import it.openprj.jTicketing.blogic.model.entity.ReportisticaLuoghiInteresse;
import it.openprj.jTicketing.blogic.model.entity.ReportisticaTickets;
import it.openprj.jTicketing.blogic.model.entity.Ticket;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.Turno;
import it.openprj.jTicketing.blogic.model.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface BackEndMgr {

  public abstract List<TicketAcquistato> validateTicket(Long placeId, String codiceDiVerificaTicket) throws SystemException, SQLException;

  public abstract TicketAcquistato annullaTicket(Long uidTicket) throws SystemException, SQLException;

  public abstract List<it.openprj.jTicketing.blogic.model.entity.Birthplace> selectBirthplaceList() throws SystemException, SQLException;

  public abstract List<GenereEvento> selectTypegenusList() throws SystemException, SQLException;

  public abstract List<NazionalitaFilm> selectFilmnationalityList() throws SystemException, SQLException;

  public abstract List<LuogoInteresse> searchLuoghiOperatore(User user) throws SystemException, SQLException;
  
  public abstract List<LuogoInteresse> searchLuoghiBotteghino(User user) throws SystemException, SQLException;

  public abstract List<ReportisticaLuoghiInteresse> reportisticaLuoghiInteresse(User user) throws SystemException,
                                                                                          SQLException;

  public abstract List<ModificaVenditeLuoghiInteresse> modificaVenditeLuoghiInteresse(User user)
                                                                                                throws SystemException,
                                                                                                SQLException;

  public abstract List<ReportisticaLuoghiInteresse> reportisticaLuoghiInteresseAdmin() throws SystemException,
                                                                                      SQLException;

  public abstract List<ReportisticaTickets> reportisticaTickets(String uid) throws SystemException, SQLException;

  public abstract List<LuogoInteresse> selectListaLuoghiInteresseOperatore(int uid) throws SystemException,
                                                                                   SQLException;
  
  public abstract List<LuogoInteresse> selectListaLuoghiInteresseBotteghino(int uid) throws SystemException,
  SQLException;

  public abstract List<String> selectListaUidLuoghiInteresse() throws SystemException, SQLException;

  public abstract void deleteLuoghiOperatori(String uidOperatore) throws SystemException, SQLException;
  
  public abstract void deleteLuoghiBotteghini(String uidBotteghino) throws SystemException, SQLException;

  public abstract void insertLuoghiOperatori(String uidOperatore, String uidLuogoOperatore) throws SystemException,
                                                                                           SQLException;
  
  public abstract void insertLuoghiBotteghini(String uidBotteghino, String uidLuogoBotteghino) throws SystemException,
  SQLException;

  public abstract void updateRuolo(String uidUtente, String uidRuolo) throws SystemException, SQLException;

  public abstract List<User> selectOperatori(String nome, String cognome, String username, String email, String ruolo)
                                                                                                                      throws SystemException,
                                                                                                                      SQLException;
  
  public abstract List<User> selectBotteghini(String nome, String cognome, String username, String email, String ruolo)
  																														throws SystemException,
  																														SQLException;

  public abstract List<PrezzoCategoriaTicket> searchCategorieTickets(String uidTicket) throws SystemException,
                                                                                      SQLException;

  public abstract List<Ticket> searchTickets(String uidTicket) throws SystemException, SQLException;

  public abstract Ticket dettaglioTicket(String uidTicket) throws SystemException, SQLException;
  
  public abstract Turno dettaglioTurno(String uidTurno) throws SystemException, SQLException;

  public abstract List<Turno> searchTurni(String uidTicket) throws SystemException, SQLException;

  public abstract void saveCalendar(WizardDTO wizardDTO) throws SystemException, SQLException;

//  public abstract List<User> searchBuyerUsers(Long placeId, String codiceDiVerificaTicket) throws SystemException, SQLException;

 // public abstract GenereEvento selectTypeGenusName(Long uid) throws SystemException, SQLException;
  public abstract String selectTypeGenusName(Long uid) throws SystemException, SQLException;

  public abstract List<ConfigurazioneSIAE> selectListaConfigurazioneSIAE() throws SystemException, SQLException;



}