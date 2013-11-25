/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.manager;

import it.openprj.jTicketing.blogic.entity.AvailableDay;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DataTablesMgr {

  public abstract List<Turno> turniGiorni(String uidTickets, String year, String month) throws SystemException,
                                                                                       SQLException;

  public abstract List<LuogoInteresse> searchLuoghiInteresse() throws SystemException, SQLException;

  public abstract List<ConfigurazioneSIAE> selectConf() throws SystemException, SQLException;

  public abstract BinaryData readLuogoInteresseImage(String uid) throws SystemException, SQLException;

  public abstract BinaryData readTicketImage(String uid) throws SystemException, SQLException;

  public abstract CalendarioEventi searchCalendarioMese(String mese, String anno, String uidLuoghiInteresse,
                                                        String uidTicket) throws SystemException, SQLException;

  public abstract int searchQuantitaResiduaGiorno(String mese, String anno, String giorno, String uidLuoghiInteresse,
                                                  String uidTicket, String uidTurno) throws SystemException,
                                                                                    SQLException;

  public abstract ArrayList<CalendarioEventi> readCalendario(String uidLuoghiInteresse, String uidTicket)
                                                                                                         throws SystemException,
                                                                                                         SQLException;

  public abstract List<Ticket> searchTickets(String uidLuoghiInteresse) throws SystemException, SQLException;

  public abstract List<Turno> searchTurni(String uidTickets) throws SystemException, SQLException;

    public abstract void updateCategoria(PrezzoCategoriaTicket prezzoCategoriaTicket) throws SystemException,
                                                                                   SQLException;

  public abstract void insertCategoria(Long uidLuoghiInteresse, PrezzoCategoriaTicket prezzoCategoriaTicket,
                                       String uidTicket) throws SystemException, SQLException;

  public abstract void updateTurno(Turno turno) throws SystemException, SQLException;

  public abstract void insertTurno(Turno turno, String uidTicket) throws SystemException, SQLException;

  public abstract void insertTicket(Ticket ticket) throws SystemException, SQLException;
  
  public abstract void insertRuoloAmministratore() throws SystemException, SQLException;
  
  public abstract void insertRuoloOperatore() throws SystemException, SQLException;
  
  public abstract void insertRuoloBotteghino() throws SystemException, SQLException;
  
  public abstract void insertRuoloUtente() throws SystemException, SQLException;
  
  public abstract void insertUtenteAmministratore() throws SystemException, SQLException;

  public abstract void insertBirthPlace() throws SystemException, SQLException;

  public abstract void insertFilmNationality() throws SystemException, SQLException;

  public abstract void insertTypegenus() throws SystemException, SQLException;

   public abstract void deleteCategoria(String uid) throws SystemException, SQLException;

  public abstract void updateTicket(Ticket ticket) throws SystemException, SQLException;

  public abstract int checkDeleteTicket(String uid) throws SystemException, SQLException;

  public abstract void deleteTicket(String uid) throws SystemException, SQLException;

  public abstract void updateLuogoInteresse(LuogoInteresse luogoInteresse) throws SystemException, SQLException;

  public abstract void updateConfigurazioneSIAE(ConfigurazioneSIAE configurazioneSIAE) throws SystemException, SQLException;

  public abstract void deleteLuogoInteresse(String uid) throws SystemException, SQLException;

  public abstract void insertLuogoInteresse(LuogoInteresse luogoInteresse) throws SystemException, SQLException;

  public abstract void insertConfigurazioneSIAE(ConfigurazioneSIAE configurazioneSIAE) throws SystemException, SQLException;

  //public abstract void updateConfigurazioneSIAE(ConfigurazioneSIAE configurazioneSIAE) throws SystemException, SQLException;

  public abstract void deleteTurno(Turno turno) throws SystemException, SQLException;

  public abstract void insertOrariEventi(Turno turno) throws SystemException, SQLException;

  public abstract void deleteOrariEventi(String uidCalendarioEventi) throws SystemException, SQLException;

  public abstract void deleteEvento(String uidCalendarioEventi) throws SystemException, SQLException;

  public abstract void insertEvento(long uid_luoghi_interesse, long uid_tickets, String giorno, String mese, String anno) throws SystemException, SQLException;

  public abstract List<AvailableDay> searchTicketForDay(String year, String month, String day, String placeId) throws SystemException, SQLException;

  public abstract String lastInsertEvento() throws SystemException, SQLException;
  
  public abstract it.openprj.jTicketing.blogic.model.entity.Role searchIdAmministratore() throws SystemException , SQLException;
  
  public abstract CalendarioEventi searchCalendarioMeseTotale(String mese, String anno, String uidLuoghiInteresse) throws SystemException , SQLException;
  
  public abstract Ticket searchTickets(long uidTicket)throws SystemException, SQLException;
  
  public abstract CalendarioEventi searchCalendarioGiorno(String giorno, String mese, String anno, String uidLuoghiInteresse, String uidTicket) throws SystemException , SQLException;
}