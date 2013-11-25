/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.manager;

import it.openprj.jTicketing.blogic.entity.Attraction;
import it.openprj.jTicketing.blogic.entity.AvailableDay;
import it.openprj.jTicketing.blogic.entity.AvailableDayTurn;
import it.openprj.jTicketing.blogic.entity.InterestPlace;
import it.openprj.jTicketing.blogic.entity.ConfigurationSIAE;
import it.openprj.jTicketing.blogic.entity.Role;
import it.openprj.jTicketing.blogic.entity.TicketCategory;
import it.openprj.jTicketing.blogic.entity.Turn;
import it.openprj.jTicketing.blogic.entity.User0;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.*;
import java.sql.SQLException;
import java.util.*;
import it.openprj.jTicketing.blogic.entity.TypeGenus;
import it.openprj.jTicketing.blogic.utilities.BirthPlaceList;
import it.openprj.jTicketing.blogic.utilities.FilmAcronymsNationList;
import it.openprj.jTicketing.blogic.utilities.FilmNationalityList;
import org.apache.log4j.Logger;
import org.eclipse.core.internal.content.LazyInputStream;
import org.springframework.orm.jpa.JpaTemplate;
import it.openprj.jTicketing.blogic.utilities.TypeGenusList;

public class JpaDataTablesMgrImpl implements DataTablesMgr {
    String userDefault, passwordDefault, domainName, roleAdmin, roleAdminDesc, roleOperator, roleOperatorDesc, roleBoxOffice, roleBoxOfficeDesc, roleUser, roleUserDesc = null;
    long idBirthplace=1;

    @SuppressWarnings("unused")
    private static final Logger log = Logger.getLogger(JpaDataTablesMgrImpl.class);
    Locale country = Locale.getDefault();

    public JpaDataTablesMgrImpl() {
        try {
            ResourceBundle configResource = ResourceBundle.getBundle("resources.config");
            ResourceBundle messageResource = ResourceBundle.getBundle("resources.MessageResources", country);
            this.userDefault = configResource.getString("UserDefault");
            this.passwordDefault = configResource.getString("PasswordDefault");
            this.domainName = messageResource.getString("label.domainName");
            this.roleAdmin = messageResource.getString("role.admin");
            this.roleAdminDesc = messageResource.getString("role.adminDesc");
            this.roleOperator = messageResource.getString("role.operator");
            this.roleOperatorDesc = messageResource.getString("role.operatorDesc");
            this.roleBoxOffice = messageResource.getString("role.boxOffice");
            this.roleBoxOfficeDesc = messageResource.getString("role.boxOfficeDesc");
            this.roleUser = messageResource.getString("role.user");
            this.roleUserDesc = messageResource.getString("role.userDesc");
        }
        catch (Exception e){
            log.error("ERROR in resources file");
        }
    }

  private JpaTemplate jpaTemplate;

  public void setJpaTemplate(JpaTemplate jpaTemplate) {
    this.jpaTemplate = jpaTemplate;
  }

	@SuppressWarnings("unchecked")
  public List<Turno> turniGiorni(String uidTickets , String year , String month) throws SystemException , SQLException {

    Map<String, Object> params = JpaBackEndMgrImpl.fillMap(null,"aid",Long.parseLong(uidTickets),"month",Integer.parseInt(month),"year",Integer.parseInt(year));
	  StringBuilder q = new StringBuilder();

	  q.append("select ad.id, ad.day, adt.turn.id, adt.id, ");
    q.append("(SELECT count(*) FROM Purchase p WHERE p.attractionId= :aid AND p.turn.id=adt.turn.id");
      q.append(" AND p.year=:year AND p.month=:month AND p.day=ad.day) ");
    q.append("from AvailableDay ad , AvailableDayTurn adt ");
    q.append("where ad.attractionId = :aid and ad.year = :year and ad.month = :month and ad.id = adt.availableDay.id ");
    q.append("order by adt.availableDay.id asc");

    List<Turno> res= new ArrayList<Turno>();
    for (Object[] data : (List<Object[]>) jpaTemplate.findByNamedParams(q.toString(),params)) {
      Turno turno = new Turno();
      turno.setUidCalendarioEventi(data[0]+"");
      turno.setGiorno(data[1]+"");
      turno.setUidTpOrarioEventi(data[2]+"");
      turno.setUidTaOrarioEventi(data[3]+"");
      long x= (Long) data[4];
      turno.setQuantita((int) x);
      res.add(turno);
    }
    return res;
	}

	@SuppressWarnings("unchecked")
	public List<LuogoInteresse> searchLuoghiInteresse() throws SystemException , SQLException {
		List<LuogoInteresse> res= new ArrayList<LuogoInteresse>();
		for (InterestPlace ip : (List<InterestPlace>) jpaTemplate.find("select ip from InterestPlace ip")) {
			LuogoInteresse l = new LuogoInteresse();
			l.setUid(ip.getId()+"");
			l.setTitolo(ip.getTitle());
			l.setDescrizione(ip.getDescription());
			if (ip.getImage()==null) {
				l.setImmagine("No");
			}
			res.add(l);
		}
		return res;
	}
    public List<ConfigurazioneSIAE> selectConf() throws SystemException , SQLException {
		 List<ConfigurazioneSIAE> res= new ArrayList<ConfigurazioneSIAE>();
		for (ConfigurationSIAE ip : (List<ConfigurationSIAE>) jpaTemplate.find("select ip from ConfigurationSIAE ip where ip.id = '1'")) {
			ConfigurazioneSIAE l = new ConfigurazioneSIAE();
			l.setUid(ip.getId()+"");
			l.setSCartaAttivazione(ip.getSSmartCard());
			l.setSCodiceSistema(ip.getSSystemCode());
			l.setSIDCFTitolare(ip.getSTCOrganizer());
			l.setSSistemaEmissione(ip.getSEmissionSystem());

		    res.add(l);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	public it.openprj.jTicketing.blogic.model.entity.Role searchIdAmministratore() throws SystemException , SQLException {
		it.openprj.jTicketing.blogic.model.entity.Role ruolo = new it.openprj.jTicketing.blogic.model.entity.Role();
		for (Role ip : (List<Role>) jpaTemplate.find("select ip from Role ip where ip.name = '"+this.roleAdmin+"'")) {
			ruolo.setIdUser(ip.getId());
			ruolo.setIdUserRoles(ip.getId());
			ruolo.setUserName(ip.getName());
			ruolo.setRoleName(ip.getName());
		}
		return ruolo;
	}

	public BinaryData readLuogoInteresseImage(String uid) throws SystemException , SQLException {
	  InterestPlace ip = jpaTemplate.find(InterestPlace.class,Long.parseLong(uid));
	  BinaryData res= new BinaryData(ip.getImage());
	  return res;
	}

	public BinaryData readTicketImage(String uid) throws SystemException , SQLException {
	  Attraction x = jpaTemplate.find(Attraction.class,Long.parseLong(uid));
	  if (x==null)
	    return new BinaryData(new byte[] {});
    return new BinaryData(x.getImage());
	}

	public CalendarioEventi searchCalendarioMese(String mese, String anno, String uidLuoghiInteresse, String uidTicket) throws SystemException , SQLException {
		CalendarioEventi calendarioEventi = null;
		if (anno != null && mese != null && uidLuoghiInteresse != null && uidTicket != null) {
	    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
			calendarioEventi = selectCalendario(Integer.parseInt(anno), Integer.parseInt(mese) + 1 , uidLuoghiInteresse, Integer.parseInt(uidTicket));
		}
		return calendarioEventi;
	}

	public CalendarioEventi searchCalendarioGiorno(String giorno, String mese, String anno, String uidLuoghiInteresse, String uidTicket) throws SystemException , SQLException {
		CalendarioEventi calendarioEventi = null;
		if (anno != null && mese != null && giorno != null && uidLuoghiInteresse != null && uidTicket != null) {
	    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
			calendarioEventi = selectCalendarioGiorno(Integer.parseInt(anno), Integer.parseInt(mese) + 1 , Integer.parseInt(giorno), uidLuoghiInteresse, Integer.parseInt(uidTicket));
		}
		return calendarioEventi;
	}

	@SuppressWarnings("unchecked")
  public int searchQuantitaResiduaGiorno(String mese, String anno, String giorno , String uidLuoghiInteresse, String uidTicket, String uidTurno) throws SystemException , SQLException {
	  int dd= Integer.parseInt(giorno);
    int mm= Integer.parseInt(mese);
    int yy= Integer.parseInt(anno);
    List<Long> l= jpaTemplate.findByNamedParams("select adt.availableQty from AvailableDayTurn adt " +
                                                   "where adt.turn.id=:turnId " +
                                                   "and adt.availableDay.day=:day " +
                                                   "and adt.availableDay.month=:month " +
                                                   "and adt.availableDay.year=:year ",
                                                   JpaBackEndMgrImpl.fillMap(null,"turnId",Long.parseLong(uidTurno),"day",dd,"month",mm,"year",yy));

    if (l.size()<=0) return 0;
    long x= (Long) l.get(0);
    return (int) x;
	}

	@SuppressWarnings("unchecked")
  public ArrayList<CalendarioEventi> readCalendario(String uidLuoghiInteresse, String uidTicket) throws SystemException , SQLException {
		ArrayList<CalendarioEventi> res = new ArrayList<CalendarioEventi>();
	  List<Object[]> lp = jpaTemplate.findByNamedParams("select distinct ad.year, ad.month from AvailableDay ad " +
	    		"where ad.attractionId=:aId",JpaBackEndMgrImpl.fillMap(null,"aId",Long.parseLong(uidTicket)));
    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
	  int attrId= Integer.parseInt(uidTicket);
	  for (Object[] p : lp) {
			res.add(selectCalendario((Integer) p[0], (Integer) p[1], uidLuoghiInteresse,attrId));
		}
		return res;
	}

	@SuppressWarnings("unchecked")
  private CalendarioEventi selectCalendario(int year, int month, String placeId, int attrId) {
    List<AvailableDay> l = jpaTemplate.findByNamedParams("select ad from AvailableDay ad " +
      		"where ad.attractionId=:aId and ad.year=:year and ad.month=:month",
          JpaBackEndMgrImpl.fillMap(null,"aId",new Long(attrId),"year",year,"month",month));

    CalendarioEventi res = new CalendarioEventi();
    for (AvailableDay ad : l) {
      res.addTurni((ad.getDay()+"") + month + year, selectTurniGiorno(ad.getId()+""));
    }
    return res;
  }

	@SuppressWarnings("unchecked")
	  private CalendarioEventi selectCalendarioGiorno(int year, int month, int day, String placeId, int attrId) {
	    List<AvailableDay> l = jpaTemplate.findByNamedParams("select ad from AvailableDay ad " +
	      		"where ad.attractionId=:aId and ad.year=:year and ad.month=:month and ad.day=:day",
	          JpaBackEndMgrImpl.fillMap(null,"aId",new Long(attrId),"year",year,"day",day,"month",month));

	    CalendarioEventi res = new CalendarioEventi();
	    for (AvailableDay ad : l) {
	      res.addTurni((ad.getDay()+"") + month + year, selectTurniGiorno(ad.getId()+""));
	    }
	    return res;
	  }

  public List<Turno> selectTurniGiorno(String uidCalendarioEventi) {
    List<Turno> res = new ArrayList<Turno>();
    AvailableDay ad = jpaTemplate.find(AvailableDay.class,Long.parseLong(uidCalendarioEventi));
    for (AvailableDayTurn adt : ad.getAvailableDayTurns()) {
      Turno turno = new Turno();
      turno.setOrario(adt.getTurn().getHour());
      turno.setQuantita((int) adt.getTurn().getQty());
      turno.setQuantitaResidua((int) adt.getAvailableQty());
      turno.setUidTaOrarioEventi(adt.getId()+"");
      turno.setUidCalendarioEventi(uidCalendarioEventi);
      turno.setUidTpOrarioEventi(adt.getTurn().getId()+"");
      res.add(turno);
    }
    return res;
  }

  @SuppressWarnings({ "unchecked", "static-access" })
  public List<Ticket> searchTickets(String placeId) {
    // TODO: call JpaBackEndMgrImpl. List<Ticket> searchTickets(String placeId)"); (OR VICE VERSA)
    GregorianCalendar gc= new GregorianCalendar();
    gc.setTimeInMillis(System.currentTimeMillis());
    int today= gc.get(gc.YEAR)*10000+gc.get(gc.MONTH)*100+gc.get(gc.DATE);

    String q= "select att from Attraction att where att.id in (select distinct avlDay.attractionId as attId from AvailableDay avlDay " +
      "where avlDay.placeId=:pid and avlDay.year*10000 + avlDay.month*100 + avlDay.day > :today)";

    List<Ticket> res= new ArrayList<Ticket>();
    for (Attraction att : (List<Attraction>) jpaTemplate.findByNamedParams(q,JpaBackEndMgrImpl.fillMap(null,"pid",Long.parseLong(placeId),"today",today))) {
      res.add(JpaBackEndMgrImpl.getTicket(new Ticket(), att));
    }
    return res;
  }

	@SuppressWarnings("unchecked")
  public List<Turno> searchTurni(String uidTickets) {
	  List<Turn> l= jpaTemplate.findByNamedParams("select t from Turn t where t.attraction.id=:aid", JpaBackEndMgrImpl.fillMap(null,"aid",Long.parseLong(uidTickets)));
    List<Turno> res = new ArrayList<Turno>();
    for (Turn turn : l) {
      Turno turno = new Turno();
      turno.setOrario(turn.getHour());
      turno.setUidTpOrarioEventi(turn.getId()+"");
      turno.setQuantita((int) turn.getQty());
      res.add(turno);
    }
		return res;
	}

	@SuppressWarnings("unchecked")
	  public List<AvailableDay> searchTicketForDay(String year, String month, String day, String placeId) {

		List<AvailableDay> l= jpaTemplate.findByNamedParams("select distinct a from AvailableDay a where a.year=:year AND a.month=:month AND a.day=:day AND a.placeId=:placeId", JpaBackEndMgrImpl.fillMap(null,"year",Integer.parseInt(year),"month",Integer.parseInt(month)+1,"day",Integer.parseInt(day),"placeId",Long.parseLong(placeId)));

	    List<AvailableDay> res = new ArrayList<AvailableDay>();
	    for (AvailableDay availableDaymT : l) {
	      AvailableDay availableDay = new AvailableDay();
	      availableDay.setAvailableDayTurns(availableDaymT.getAvailableDayTurns());
	      availableDay.setAttractionId(availableDaymT.getAttractionId());
	      availableDay.setPlaceId(availableDaymT.getPlaceId());
	      availableDay.setYear(availableDaymT.getYear());
	      availableDay.setMonth(availableDaymT.getMonth());
	      availableDay.setDay(availableDaymT.getDay());
	      availableDay.setAttractionId(availableDaymT.getAttractionId());
	      res.add(availableDay);
	    }
			return res;
		}

	  @SuppressWarnings({ "unchecked", "static-access" })
	  public Ticket searchTickets(long uidTicket) {

	    String q= "select att from Attraction att where att.id=:id )";

	    Ticket res= new Ticket();
	    for (Attraction att : (List<Attraction>) jpaTemplate.findByNamedParams(q,JpaBackEndMgrImpl.fillMap(null,"id",(uidTicket)))) {
	      res = (JpaBackEndMgrImpl.getTicket(new Ticket(), att));
	    }
	    return res;
	  }

    public void updateCategoria(PrezzoCategoriaTicket pct) {
	  TicketCategory cat= jpaTemplate.find(TicketCategory.class, Long.parseLong(pct.getUid()));
	  cat.setDescription(pct.getDescrizione());
	  cat.setInfo(pct.getCondizioni());
      cat.setPrice(pct.getPrezzo());
	}

	public void insertCategoria(Long uidLuoghiInteresse, PrezzoCategoriaTicket pct, String uidTicket) {
	  TicketCategory cat= new TicketCategory();
    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
	  cat.setAttraction(jpaTemplate.find(Attraction.class,Long.parseLong(uidTicket)));
    cat.setDescription(pct.getDescrizione());
    cat.setInfo(pct.getCondizioni());
    cat.setPrice(pct.getPrezzo());
    cat.setPlaceId(uidLuoghiInteresse);
    jpaTemplate.persist(cat);
    pct.setUid(cat.getId()+"");
	}

	public void updateTurno(Turno turno) {
    Turn t= jpaTemplate.find(Turn.class, Long.parseLong(turno.getUidTpOrarioEventi()));
    t.setHour(turno.getOrario());
    t.setOpenTime(turno.getOrarioApertura());
    t.setQty(turno.getQuantita());
    for (AvailableDayTurn adt : t.getAvailableDayTurns()) {
      adt.setAvailableQty(turno.getQuantita());
    }
	}

	public void insertTurno(Turno turno, String uidTicket) {
    Turn t= new Turn();
    t.setHour(turno.getOrario());
    t.setOpenTime(turno.getOrarioApertura());
    t.setQty(turno.getQuantita());
    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
    t.setPlaceId(Long.parseLong(turno.getUidLuogoInteresse()));
    t.setAttraction(jpaTemplate.find(Attraction.class,Long.parseLong(uidTicket)));
    jpaTemplate.persist(t);
    turno.setUidTpOrarioEventi(t.getId()+"");
	}

	public void insertTicket(Ticket ticket) {
	    InterestPlace a= new InterestPlace();
        a.setTitle(ticket.getTitolo());
        a.setDescription(ticket.getDescrizione());
        a.setImage(ticket.getImmagine());
        a.setId(ticket.getIdTypeGenus());

        jpaTemplate.persist(a);
        ticket.setUid(a.getId()+"");
	}

	public void insertRuoloAmministratore() {
		Role a= new Role();
		a.setId(1);
	    a.setName(roleAdmin);
	    a.setDescription(roleAdminDesc);
	    jpaTemplate.persist(a);
	}

	public void insertRuoloOperatore() {
		Role a= new Role();
		a.setId(3);
	    a.setName(roleOperator);
	    a.setDescription(roleOperatorDesc);
	    jpaTemplate.persist(a);

	}

	public void insertRuoloBotteghino() {
		Role a= new Role();
		a.setId(4);
	    a.setName(roleBoxOffice);
	    a.setDescription(roleBoxOfficeDesc);
	    jpaTemplate.persist(a);
	}

	public void insertRuoloUtente() {
		Role a= new Role();
		a.setId(2);
	    a.setName(roleUser);
	    a.setDescription(roleUserDesc);
	    jpaTemplate.persist(a);
	}

    public void insertUtenteAmministratore() {
        User0 a = new User0();
        ArrayList<Role> listaRuoli = new ArrayList<Role>();
        Role role = new Role();
        role.setId(1);
        role.setName(roleAdmin);
        role.setDescription(roleAdminDesc);
        listaRuoli.add(role);
        a.setName(userDefault);
        a.setSurname(userDefault);
        a.setEmail(userDefault+"@"+domainName);
        a.setUserid(userDefault);
        a.setPassword(passwordDefault);
        a.setBirthdate(new Date());
        a.setCf("1234567890");
        a.setSubscriptionDate(new Date());
        a.setEnabled('S');
        a.setActivationCode("");
        a.setActivated('S');
        a.setRoles(listaRuoli);
        a.setIdBirthplace(this.idBirthplace);
        jpaTemplate.persist(a);
    }

	public void deleteCategoria(String uid) {
	  jpaTemplate.remove(jpaTemplate.find(TicketCategory.class, Long.parseLong(uid)));
	}

	public void updateTicket(Ticket ticket) {
    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
    Attraction a= jpaTemplate.find(Attraction.class,Long.parseLong(ticket.getUid()));
    a.setTitle(ticket.getTitolo());
    a.setDescription(ticket.getDescrizione());
    if (ticket.getNomeFile()!=null)
      a.setImage(ticket.getImmagine());
	}

	public int  checkDeleteTicket(String uid) {
	  long res= (Long) jpaTemplate.findByNamedParams("select count(*) from Purchase pt " +
	                                "where pt.turn.attraction.id=:aId",JpaBackEndMgrImpl.fillMap(null,"aId",Long.parseLong(uid)) ).get(0);
	  return (int) res;
	}

	public void deleteTicket(String uid) {
	    jpaTemplate.remove(jpaTemplate.find(Attraction.class, Long.parseLong(uid)));
	}


	public void updateLuogoInteresse(LuogoInteresse iplace) {
    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
    InterestPlace a= jpaTemplate.find(InterestPlace.class,Long.parseLong(iplace.getUid()));
    a.setTitle(iplace.getTitolo());
    //a.setSDenomination(iplace.getSDenominazione());
    //a.setSDenominationCode(iplace.getSCodiceDen());
    a.setDescription(iplace.getDescrizione());
    if (!iplace.getNomeFile().equals(""))
      a.setImage(iplace.getImmagineByte());
	}

    public void updateConfigurazioneSIAE(ConfigurazioneSIAE ip) {
    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
            ConfigurationSIAE l= jpaTemplate.find(ConfigurationSIAE.class,Long.parseLong("1"));
            l.setSSmartCard(ip.getSCartaAttivazione());
			l.setSSystemCode(ip.getSCodiceSistema());
			l.setSTCOrganizer(ip.getSIDCFTitolare());
			l.setSEmissionSystem(ip.getSSistemaEmissione());
	}


	public void deleteLuogoInteresse (String uid) {
    jpaTemplate.remove(jpaTemplate.find(InterestPlace.class, Long.parseLong(uid)));
	}

	public void insertLuogoInteresse (LuogoInteresse iplace) {
    InterestPlace a= new InterestPlace();
    a.setTitle(iplace.getTitolo());
    //a.setSDenomination(iplace.getSDenominazione());
    //a.setSDenominationCode(iplace.getSCodiceDen());
    a.setDescription(iplace.getDescrizione());
    a.setState('P');
    if (!iplace.getNomeFile().equals(""))
      a.setImage(iplace.getImmagineByte());
    jpaTemplate.persist(a);
    iplace.setUid(a.getId()+"");
    }

    //Inserimento dati SIAE
    public void insertConfigurazioneSIAE(ConfigurazioneSIAE configurazioneSIAE) throws SystemException, SQLException {
    ConfigurationSIAE a= new ConfigurationSIAE();
    a.setSEmissionSystem(configurazioneSIAE.getSCodiceSistema());
    a.setSSmartCard(configurazioneSIAE.getSCartaAttivazione());
    a.setSSystemCode(configurazioneSIAE.getSSistemaEmissione());
    a.setSTCOrganizer(configurazioneSIAE.getSIDCFTitolare());

    jpaTemplate.persist(a);
    //configurazioneSIAE.setUid(a.getId()+"");
    }




	public void deleteTurno(Turno turno) {
    jpaTemplate.remove(jpaTemplate.find(Turn.class, Long.parseLong(turno.getUidTpOrarioEventi())));
	}

	public void insertOrariEventi(Turno turno) {
    AvailableDayTurn e= new AvailableDayTurn();
    e.setAvailableQty(turno.getQuantita());
    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
    e.setAvailableDay(jpaTemplate.find(AvailableDay.class,Long.parseLong(turno.getUidCalendarioEventi())));
    Turn turn= jpaTemplate.find(Turn.class, Long.parseLong(turno.getUidTpOrarioEventi()));
    turn.getAvailableDayTurns().add(e);
    e.setTurn(turn);
	}

	public void deleteOrariEventi(String uidCalendarioEventi) {
	  if(uidCalendarioEventi==null)
	    return;
	  AvailableDayTurn x = jpaTemplate.find(AvailableDayTurn.class, Long.parseLong(uidCalendarioEventi));
    if (x!=null)
      jpaTemplate.remove(x);
	}

	public void deleteEvento(String uidCalendarioEventi) {
    if(uidCalendarioEventi==null)
      return;
	  AvailableDay x = jpaTemplate.find(AvailableDay.class, Long.parseLong(uidCalendarioEventi));
	  if (x!=null)
      jpaTemplate.remove(x);
	}

	public void insertEvento(long uid_luoghi_interesse, long uid_tickets, String giorno, String mese, String anno) {
	  AvailableDay a= new AvailableDay();
	  a.setPlaceId(uid_luoghi_interesse);
    a.setAttractionId(uid_tickets);
    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
    a.setYear(Integer.parseInt(anno));
    a.setDay(Integer.parseInt(giorno));
    a.setMonth(Integer.parseInt(mese) + 1);
    jpaTemplate.persist(a);
	}

	public String lastInsertEvento() {
    return ""+jpaTemplate.find("select max(ad.id) from AvailableDay ad").get(0);
	}

	public CalendarioEventi searchCalendarioMeseTotale(String mese, String anno, String uidLuoghiInteresse) throws SystemException , SQLException {
		CalendarioEventi calendarioEventi = null;
		if (anno != null && mese != null && uidLuoghiInteresse != null) {
	    // TODO: parsing front-end properties in the service layer move semantic validation in the wrong place
			calendarioEventi = selectCalendarioTurniTotali(Integer.parseInt(anno), Integer.parseInt(mese) + 1,uidLuoghiInteresse);
		}
		return calendarioEventi;
	}
	
	@SuppressWarnings("unchecked")
	  private CalendarioEventi selectCalendarioTurniTotali(int year, int month, String placeId) {
	    List<AvailableDay> l = jpaTemplate.findByNamedParams("select ad from AvailableDay ad " +
	      		"where ad.year=:year and ad.month=:month and ad.placeId=:placeId",
	          JpaBackEndMgrImpl.fillMap(null,"year",year,"month",month,"placeId",Long.parseLong(placeId)));

	    CalendarioEventi res = new CalendarioEventi();
	    for (AvailableDay ad : l) {
	      res.addTurni((ad.getDay()+"") + month + year, selectTurniGiorno(ad.getId()+""));
	    }
	    return res;
	  }

    public void insertBirthPlace() {
        it.openprj.jTicketing.blogic.entity.Birthplace a = new it.openprj.jTicketing.blogic.entity.Birthplace();
        BirthPlaceList bpl = new BirthPlaceList(country.getCountry());
        for (int i=0; i < bpl.listName.length; i++){
            a.setIdBirthplace(i + 1);
            a.setName(bpl.listName[i]);
            jpaTemplate.merge(a);
        }
    }

    @Override
    public void insertTypegenus() throws SystemException, SQLException {

        TypeGenus a = new TypeGenus();
        TypeGenusList bpl = new TypeGenusList(country.getCountry());
        for (int i=0; i < bpl.listName.length; i++){
            a.setIdTypeGenus(i + 1);
            a.setDescription(bpl.listName[i]);
            jpaTemplate.merge(a);
        }

    }

     public void insertFilmNationality() {
        it.openprj.jTicketing.blogic.entity.FilmNationality a = new it.openprj.jTicketing.blogic.entity.FilmNationality();
        FilmNationalityList nation = new FilmNationalityList(country.getCountry());
        FilmAcronymsNationList acronym = new FilmAcronymsNationList(country.getCountry());
        for (int i=0; i < nation.listName.length; i++){
            a.setAcronyms(acronym.listName[i]);
            a.setNation(nation.listName[i]);
            jpaTemplate.merge(a);
        }
    }


}