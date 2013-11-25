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
import it.openprj.jTicketing.blogic.entity.ConfigurationSIAE;
import it.openprj.jTicketing.blogic.entity.FilmNationality;
import it.openprj.jTicketing.blogic.entity.InterestPlace;
import it.openprj.jTicketing.blogic.entity.Purchase;
import it.openprj.jTicketing.blogic.entity.Role;
import it.openprj.jTicketing.blogic.entity.TicketCategory;
import it.openprj.jTicketing.blogic.entity.Turn;
import it.openprj.jTicketing.blogic.entity.TypeGenus;
import it.openprj.jTicketing.blogic.entity.User0;
import it.openprj.jTicketing.blogic.exceptions.DAException;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.dal.dao.ReportisticaDAO;
import it.openprj.jTicketing.blogic.model.dto.WizardDTO;
import it.openprj.jTicketing.blogic.model.entity.Birthplace;
import it.openprj.jTicketing.blogic.model.entity.CalendarioEventi;
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

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaTemplate;


/**
 *
 * @author Andrea Saba
 */
public class JpaBackEndMgrImpl implements BackEndMgr {

	private static final Logger log = Logger.getLogger(JpaBackEndMgrImpl.class);

	private JpaTemplate jpaTemplate;
  public void setJpaTemplate(JpaTemplate jpaTemplate) {
    this.jpaTemplate = jpaTemplate;
  }

  public List<TicketAcquistato> validateTicket(Long placeId, String confirmationCode) {
		List<Purchase> l = (List<Purchase>)  jpaTemplate.findByNamedParams(
				"select p from Purchase p where p.category.placeId=:pid and p.confirmationCode=:c",
				fillMap(null, "c", confirmationCode, "pid", placeId));
		List<TicketAcquistato> res = new ArrayList<TicketAcquistato>();
		for (Purchase p : l) {
			TicketAcquistato t = new TicketAcquistato();			
			t.setUid(p.getId());
			t.setCodiceVerifica(p.getConfirmationCode());
			t.setConvalidato(p.getConfirmed() + "");
			t.setDataConvalida(p.getConfirmationDate());
			t.setDataAcquisto(p.getPurchaseDate());
			User u=new User();
			u.setFirstName(p.getBuyer().getName());
		    u.setLastName(p.getBuyer().getSurname());
			u.setIdUser(p.getBuyer().getId());			 
			t.setAcquirente(u);
			res.add(t);
		}		
		return res;
	}
 
	public TicketAcquistato annullaTicket(Long uidTicket) {
		List<Purchase> l = (List<Purchase>) jpaTemplate.findByNamedParams(
				"select p from Purchase p where p.id=:u",
				fillMap(null, "u", uidTicket));

		TicketAcquistato res = null;
		if (l.size() > 0) {
			Purchase p = l.get(0);
			p.setConfirmed('Y');
			p.setConfirmationDate(new Date(System.currentTimeMillis()));
			res = new TicketAcquistato();
			res.setUid(p.getId());
			res.setCodiceVerifica(p.getConfirmationCode());
			res.setConvalidato(p.getConfirmed() + "");
			res.setDataConvalida(p.getConfirmationDate());
			res.setDataAcquisto(p.getPurchaseDate());
			User u=new User();
			u.setFirstName(p.getBuyer().getName());
		    u.setLastName(p.getBuyer().getSurname());
			u.setIdUser(p.getBuyer().getId());			 
			res.setAcquirente(u);
		}
  	  return res;
	}

	public List<ReportisticaLuoghiInteresse> reportisticaLuoghiInteresse(User user) {
    User0 u= jpaTemplate.find(User0.class,user.getIdUser());
    List<ReportisticaLuoghiInteresse> res= new ArrayList<ReportisticaLuoghiInteresse>();
    for (InterestPlace ip : u.getInterestPlaces()) {
      ReportisticaLuoghiInteresse l = new ReportisticaLuoghiInteresse();
      l.setUid(ip.getId()+"");
      l.setTitolo(ip.getTitle());
      l.setDescrizione(ip.getDescription());
      res.add(l);
    }
    return res;
	}


    public List<Birthplace> selectBirthplaceList() {
        List<Birthplace> res = new ArrayList<Birthplace>();
        for (it.openprj.jTicketing.blogic.entity.Birthplace c : (List<it.openprj.jTicketing.blogic.entity.Birthplace>) jpaTemplate.find("select c from Birthplace c order by name asc")) {
            Birthplace l = new Birthplace();
            l.setIdbirthplace(c.getIdBirthplace());
            l.setName(c.getName());
            res.add(l);
        }
        return res;
    }

    public List<GenereEvento> selectTypegenusList() {
        List<GenereEvento> res = new ArrayList<GenereEvento>();
        for (TypeGenus c : (List<TypeGenus>) jpaTemplate.find("select c from TypeGenus c order by description asc")) {
            GenereEvento l = new GenereEvento();
            l.setIdTypeGenus(c.getIdTypeGenus());
            l.setDescription(c.getDescription());
            res.add(l);
        }
        return res;
    }

    public List<NazionalitaFilm> selectFilmnationalityList() {
        List<NazionalitaFilm> res = new ArrayList<NazionalitaFilm>();
        for (FilmNationality c : (List<FilmNationality>) jpaTemplate.find("select c from FilmNationality c order by nation asc")) {
            NazionalitaFilm l = new NazionalitaFilm();
            l.setNation(c.getNation());
            l.setAcronyms(c.getAcronyms());
            res.add(l);
        }
        return res;
    }


    public List<ModificaVenditeLuoghiInteresse> modificaVenditeLuoghiInteresse(User user) {
    User0 u= jpaTemplate.find(User0.class,user.getIdUser());
    List<ModificaVenditeLuoghiInteresse> res= new ArrayList<ModificaVenditeLuoghiInteresse>();
    for (InterestPlace ip : u.getInterestPlaces()) {
      ModificaVenditeLuoghiInteresse l = new ModificaVenditeLuoghiInteresse();
      l.setUid(ip.getId()+"");
      l.setTitolo(ip.getTitle());
      l.setDescrizione(ip.getDescription());
      res.add(l);
    }
    return res;
  }

	@SuppressWarnings("unchecked")
  public List<ReportisticaLuoghiInteresse> reportisticaLuoghiInteresseAdmin() {
    List<ReportisticaLuoghiInteresse> res= new ArrayList<ReportisticaLuoghiInteresse>();
    for (InterestPlace ip : (List<InterestPlace>) jpaTemplate.find("select ip from InterestPlace ip")) {
      ReportisticaLuoghiInteresse l = new ReportisticaLuoghiInteresse();
      l.setUid(ip.getId()+"");
      l.setTitolo(ip.getTitle());
      l.setDescrizione(ip.getDescription());
      res.add(l);
    }
    return res;
	}

  // TODO: 00 migrate
	public List<ReportisticaTickets> reportisticaTickets(String uid) throws SystemException, SQLException {
		ReportisticaDAO reportisticaDAO = new ReportisticaDAO();
		List<ReportisticaTickets> listaReportisticaTickets = null;
		try {
			listaReportisticaTickets = reportisticaDAO.selectReportisticaTickets(uid);
		} catch (DAException e) {
			log.error(e);
			throw new SystemException();
		}
		return listaReportisticaTickets;
	}

  // TODO: this method is almost the same of the next one, but l.setAssegnatoOperatore, think about it ...
	public List<LuogoInteresse> searchLuoghiOperatore(User user) {
    User0 u= jpaTemplate.find(User0.class,user.getIdUser());
    List<LuogoInteresse> res= new ArrayList<LuogoInteresse>();
    for (InterestPlace ip : u.getInterestPlaces()) {
      LuogoInteresse l = new LuogoInteresse();
      l.setUid(ip.getId()+"");
      l.setTitolo(ip.getTitle());
      l.setDescrizione(ip.getDescription());
      // l.setAssegnatoOperatore(ip.getAssignedOperators().get(0).getId()+"");
      res.add(l);
    }
    return res;
  }

	@SuppressWarnings("unchecked")
	public List<LuogoInteresse> selectListaLuoghiInteresseOperatore(int uid) {
    String q= "select ip, (select u.id from ip.assignedOperators u where u.id="+uid+" ) from InterestPlace ip where ip.state='P'";
	List<LuogoInteresse> res= new ArrayList<LuogoInteresse>();
    for (Object[] ip0 : (List<Object[]>) jpaTemplate.find(q)) {
      LuogoInteresse l = new LuogoInteresse();
      InterestPlace ip= (InterestPlace) ip0[0];
      l.setUid(ip.getId()+"");
      l.setTitolo(ip.getTitle());
      l.setDescrizione(ip.getDescription());
      if (ip0[1]!=null)
        l.setAssegnatoOperatore(ip0[1]+"");
      res.add(l);
    }
    return res;
 }

	@SuppressWarnings("unchecked")
	public List<LuogoInteresse> selectListaLuoghiInteresseBotteghino(int uid) {
	    String q= "select ip, (select u.id from ip.assignedOperators u where u.id="+uid+" ) from InterestPlace ip where ip.state='P' ";
		List<LuogoInteresse> res= new ArrayList<LuogoInteresse>();
	    for (Object[] ip0 : (List<Object[]>) jpaTemplate.find(q)) {
	    	if(ip0.length > 1){
	    LuogoInteresse l = new LuogoInteresse();
	      InterestPlace ip= (InterestPlace) ip0[0];
	      l.setUid(ip.getId()+"");
	      l.setTitolo(ip.getTitle());
	      l.setDescrizione(ip.getDescription());
	      if (ip0[1]!=null)
	        l.setAssegnatoBotteghino(ip0[1]+"");
	    res.add(l);
	    	}
	    }
	    return res;
	 }
	
	public List<LuogoInteresse> searchLuoghiBotteghino(User user) {
	    User0 u= jpaTemplate.find(User0.class,user.getIdUser());
	    List<LuogoInteresse> res= new ArrayList<LuogoInteresse>();
	    for (InterestPlace ip : u.getInterestPlaces()) {
	      LuogoInteresse l = new LuogoInteresse();
	      l.setUid(ip.getId()+"");
	      l.setTitolo(ip.getTitle());
	      l.setDescrizione(ip.getDescription());
	      l.setAssegnatoBotteghino(ip.getAssignedOperators().get(0).getId()+"");
	      res.add(l);
	    }
	    return res;
	  }

	@SuppressWarnings("unchecked")
  public List<String> selectListaUidLuoghiInteresse() {
    List<String> res= new ArrayList<String>();
    for (InterestPlace ip : (List<InterestPlace>) jpaTemplate.find("select ip from InterestPlace ip where ip.state='P'")) {
      res.add(ip.getId()+"");
    }
    return res;
  }

	public void deleteLuoghiOperatori(String uid)  {
    User0 u= (User0) jpaTemplate.find(User0.class,Long.parseLong(uid));
    u.getInterestPlaces().clear();
	}

	public void deleteLuoghiBotteghini(String uid)  {
	    User0 u= (User0) jpaTemplate.find(User0.class,Long.parseLong(uid));
	    u.getInterestPlaces().clear();
		}

	// TODO: it associate a place to an operator, this means that the transaction is badly managed ...
	public void insertLuoghiOperatori(String userId, String placeId) {
	jpaTemplate.find(User0.class,Long.parseLong(userId)).getInterestPlaces().add(jpaTemplate.find(InterestPlace.class,Long.parseLong(placeId)));
	}

	public void insertLuoghiBotteghini(String userId, String placeId) {
		jpaTemplate.find(User0.class,Long.parseLong(userId)).getInterestPlaces().add(jpaTemplate.find(InterestPlace.class,Long.parseLong(placeId)));
		}

  // TODO: it associate a role to an operator, this means that the transaction is badly managed ...
	// TODO: we have also to think again about a ManyToMany association that is not used at all (User should has a role list)
	public void updateRuolo(String userId , String roleId) {
	  // TODO: parsing = evil
	  User0 u= jpaTemplate.find(User0.class,Long.parseLong(userId));
	  if (u.getRoles().size()>0)
      u.getRoles().set(0,jpaTemplate.find(Role.class,Long.parseLong(roleId)));
	  else
      u.getRoles().add(jpaTemplate.find(Role.class,Long.parseLong(roleId)));
	}

	@SuppressWarnings("unchecked")
  public List<User> selectOperatori(String nome , String cognome , String username , String email , String ruolo) {
    StringBuilder sb= new StringBuilder();
    sb.append("select u from User0 u ");
    sb.append("inner join u.roles as roles where u.activated = 'S' AND roles.id != 1 AND roles.id != 4 ");
    if (notEmpty(nome)) sb.append(" AND u.name like '%"+nome+"%' ");
    if (notEmpty(cognome)) sb.append(" AND u.surname like '%"+cognome+"%' ");
    if (notEmpty(username)) sb.append(" AND u.userid like '%"+username+"%' ");
    if (notEmpty(email)) sb.append(" AND u.email like '%"+email+"%' ");
    if (notEmpty(ruolo)) sb.append(" AND roles.name like '%"+ruolo+"%' ");
    return JpaSecurityMgrImpl.getUsers(jpaTemplate.find(sb.toString()));
	}

	@SuppressWarnings("unchecked")
	  public List<User> selectBotteghini(String nome , String cognome , String username , String email , String ruolo) {
	    StringBuilder sb= new StringBuilder();
	    sb.append("select u from User0 u ");
	    sb.append("inner join u.roles as roles where u.activated = 'S' AND roles.id != 1 AND roles.id != 3  ");
	    if (notEmpty(nome)) sb.append(" AND u.name like '%"+nome+"%' ");
	    if (notEmpty(cognome)) sb.append(" AND u.surname like '%"+cognome+"%' ");
	    if (notEmpty(username)) sb.append(" AND u.userid like '%"+username+"%' ");
	    if (notEmpty(email)) sb.append(" AND u.email like '%"+email+"%' ");
	    if (notEmpty(ruolo)) sb.append(" AND roles.name like '%"+ruolo+"%' ");
	    return JpaSecurityMgrImpl.getUsers(jpaTemplate.find(sb.toString()));
		}

	@SuppressWarnings("unchecked")
  public List<PrezzoCategoriaTicket> searchCategorieTickets(String ticketId) {
    List<PrezzoCategoriaTicket> res= new ArrayList<PrezzoCategoriaTicket>();
    for (TicketCategory tc : (List<TicketCategory>)
        jpaTemplate.findByNamedParams("select tc from TicketCategory tc where tc.attraction.id=:aid",fillMap(null,"aid",Long.parseLong(ticketId)))) {
      PrezzoCategoriaTicket pct = new PrezzoCategoriaTicket();
      pct.setUid(tc.getId()+"");
      pct.setDescrizione(tc.getDescription());
      pct.setPrezzo(tc.getPrice());
      pct.setCondizioni(tc.getInfo());
      res.add(pct);
    }
    return res;
  }

  /**
   * Estrae i ticket (Attractions) relativi a un particolare luogo d'interesse
   * che hanno eventi programmati oggi o successivi
   */
	@SuppressWarnings({ "static-access", "unchecked" })
  public List<Ticket> searchTickets(String placeId) {
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

	static Ticket getTicket(Ticket res, Attraction att) {
    res.setUid(att.getId()+"");
    res.setUid_luoghi_interesse(att.getPlace().getId()+"");
    res.setTitolo(att.getTitle());
    res.setDescrizione(att.getDescription());
    res.setNumeroPosto(att.getNumberPlace());
    res.setSCodicePosto(att.getPlaceOrderCode());
    res.setIdTypeGenus(att.getTypeGenus());
    res.setSTipologiaEvento(att.getEventType());
    res.setSIncidenza(att.getIncidence());

	  List<TicketCategory> cats= att.getCategories();
	  for (TicketCategory cat : cats) {
      PrezzoCategoriaTicket categoriaTicket = new PrezzoCategoriaTicket();
      categoriaTicket.setUid(cat.getId()+"");
      categoriaTicket.setDescrizione(cat.getDescription());
      categoriaTicket.setPrezzo(cat.getPrice());
      categoriaTicket.setCondizioni(cat.getInfo());
      res.getPrezziCategorie().add(categoriaTicket);
    }
    return res;
  }

	public Ticket dettaglioTicket(String uidTicket) {
		return getTicket(new Ticket(), jpaTemplate.find(Attraction.class,Long.parseLong(uidTicket)));
    }
	
	static Turno getTurno(Turno res, Turn tur) {
	    res.setUidCalendarioEventi(tur.getId()+"");
	    res.setUidLuogoInteresse(tur.getPlaceId()+"");
	    res.setUidTicket(tur.getAttraction().getId()+"");
	    	//MANCANO DA CARICARE GLI ALTRI DATI
	    
	    return res;
	  }
	
	public Turno dettaglioTurno(String uidTurno) {
	    return getTurno(new Turno(), jpaTemplate.find(Turn.class,Long.parseLong(uidTurno)));
	}

	public List<Turno> searchTurni(String uidTicket) {
	  List<Turno> res= new ArrayList<Turno>();
    for (Turn t : jpaTemplate.find(Attraction.class,Long.parseLong(uidTicket)).getTurns()) {
      Turno turno = new Turno();
      turno.setOrario(t.getHour());
      turno.setQuantita((int) t.getQty());
      turno.setUidTpOrarioEventi(t.getId()+"");
      res.add(turno);
    }
		return res;
	}

	public void saveCalendar(WizardDTO wizardDTO) {
		LuogoInteresse luogoInteresse = wizardDTO.getLuogoInteresse();
		ArrayList<Ticket> listaTickets = wizardDTO.getListaTickets();
		ArrayList<PrezzoCategoriaTicket> listaCategorie = wizardDTO.getListaPrezziCategorie();
		ArrayList<Turno> listaTurni = wizardDTO.getListaTurni();

	  long placeId= Long.parseLong(luogoInteresse.getUid());

		// per ogni ticket creo una transazione
		for (int ticketCount = 0; ticketCount < listaTickets.size(); ticketCount++) {

      Ticket ticket = listaTickets.get(ticketCount);
      Attraction att= new Attraction();
      att.setTitle(ticket.getTitolo());
      att.setDescription(ticket.getDescrizione());
      att.setNumberPlace(ticket.getNumeroPosto());
      att.setPlaceOrderCode(ticket.getSCodicePosto());
      att.setTypeGenus(ticket.getIdTypeGenus());
      att.setEventType(ticket.getSTipologiaEvento());
      att.setIncidence(ticket.getSIncidenza());
      att.setImage(ticket.getImmagine());
      att.setPlace(jpaTemplate.find(InterestPlace.class,placeId));
      ticket.setUid(att.getId()+"");

			// Inserimento tipologiche categorie
			for (int i = 0; i < listaCategorie.size(); i++) {
			  PrezzoCategoriaTicket dto = listaCategorie.get(i);
			  TicketCategory e= new TicketCategory();
			  e.setAttraction(att);
        e.setPlaceId(placeId);
        e.setDescription(dto.getDescrizione());
        e.setInfo(dto.getCondizioni());
        e.setPrice(dto.getPrezzo());
			  att.getCategories().add(e);
			}

			// Inserimento tipologiche orari/turni
			Map<String, Turn> tmap= new HashMap<String, Turn>();
			for (int i = 0; i < listaTurni.size(); i++) {
			  Turno dto = listaTurni.get(i);
				Turn e= new Turn();
			  e.setHour(dto.getOrario());
			  e.setOpenTime(dto.getOrarioApertura());
                System.out.println("dto " + dto.getOrarioApertura());
			  e.setQty(dto.getQuantita());
			  e.setAttraction(att);
			  e.setPlaceId(placeId);
				att.getTurns().add(e);
				tmap.put(e.getHour(),e);
				dto.setUidTpOrarioEventi(e.getId()+"");
			}
			jpaTemplate.persist(att);

			// Inserimento calendario eventi
			HashMap<String, ArrayList<CalendarioEventi>> calendarioCompleto = ticket.getCalendarioCompleto();
			Iterator<String> keys = calendarioCompleto.keySet().iterator();

			while (keys.hasNext()) {
			  String key= keys.next();

				// Chiedo la lista di tutti i mesi di un anno (key=2009)
				ArrayList<CalendarioEventi> anno = calendarioCompleto.get(key);

				// Ciclo tutti i mesi dell'anno
				for (int calendarioMesiCount = 0; calendarioMesiCount < anno.size(); calendarioMesiCount++) {
				  CalendarioEventi mese = anno.get(calendarioMesiCount);
				  HashMap<String, List<Turno>> listaGiorni = mese.getCalendarioEventi();

				  // Ciclo per ogni giorno del mese
					if (!listaGiorni.isEmpty()) {
						for (int giorniCount = 1; giorniCount <= 31; giorniCount++) {

						  // Inserimento giorno, mese e anno se sono presenti i turni
						  ArrayList<Turno> listaTurniGiorno = (ArrayList<Turno>) listaGiorni.get(String.valueOf(giorniCount));
							if (listaTurniGiorno != null && listaTurniGiorno.size() > 0) {
							  AvailableDay ad= new AvailableDay();
							  ad.setAttractionId(att.getId());
							  ad.setYear(Integer.parseInt(mese.getAnno()));
							  ad.setMonth(Integer.parseInt(mese.getMese())+1);
							  ad.setDay(giorniCount);
							  ad.setPlaceId(placeId);

								for (int turniGiornoCount = 0; turniGiornoCount < listaTurniGiorno.size(); turniGiornoCount++) {
								  Turno turno = listaTurniGiorno.get(turniGiornoCount);
									Turn t= tmap.get(turno.getOrario());
									AvailableDayTurn adt= new AvailableDayTurn();
									adt.setTurn(t);
								  adt.setAvailableDay(ad);
								  adt.setAvailableQty(t.getQty());
								  ad.getAvailableDayTurns().add(adt);
								}
						    jpaTemplate.persist(ad);

							}

						}
					}
				}
			}
		}
		log.info("calendar saved");
	}

  public static Map<String, Object> fillMap(Map<String, Object> res, Object ... values ) {
    if (res==null)
      res= new HashMap<String, Object>();

    for (int i = 0; i < values.length; i+=2) {
      res.put((String) values[i], values[i+1]);
    }
    return res;
  }

  private boolean notEmpty(String s) {
    return s!=null && !s.equals("");
  }
	@SuppressWarnings("unchecked")
	  public List<User> selectUidTicket(String nome , String cognome , String username , String email , String ruolo) {
	    StringBuilder sb= new StringBuilder();
	    sb.append("select u from User0 u ");
	    sb.append("inner join u.roles as roles where u.activated = 'S' AND roles.id != 1 ");
	    if (notEmpty(nome)) sb.append(" AND u.name like '%"+nome+"%' ");
	    if (notEmpty(cognome)) sb.append(" AND u.surname like '%"+cognome+"%' ");
	    if (notEmpty(username)) sb.append(" AND u.userid like '%"+username+"%' ");
	    if (notEmpty(email)) sb.append(" AND u.email like '%"+email+"%' ");
	    if (notEmpty(ruolo)) sb.append(" AND roles.name like '%"+ruolo+"%' ");
	    return JpaSecurityMgrImpl.getUsers(jpaTemplate.find(sb.toString()));
		}
	
//	 public User searchBuyerUser(String confirmationCode) {
//		 
//		    List<Purchase> l= jpaTemplate.findByNamedParams("select p from Purchase p where p.confirmationCode=:c", fillMap(null,"c",confirmationCode));
//		    User res = null;
//		    if (l.size()>0) {
//		      res = new User();
//		      Purchase p= l.get(0);
//		    res.setFirstName(p.getBuyer().getName());
//		    res.setLastName(p.getBuyer().getSurname());
//		    res.setIdUser(p.getBuyer().getId());
//		     
//		    }
//			  return res;
//			}

//     public GenereEvento selectTypeGenusName(Long idTypeGenus) throws SystemException, SQLException {
//            List<GenereEvento> TypeGenusName =jpaTemplate.find("select description from TypeGenus where idTypeGenus= '"+idTypeGenus+"' ");
//            TypeGenus l = new TypeGenus();
//            GenereEvento p= null;
//            if (TypeGenusName.size()>0) {
//		      p= TypeGenusName.get(0);
//		      p.setDescription(l.getDescription());
//            }
//            return    p;
//     }
    public String selectTypeGenusName(Long idTypeGenus) throws SystemException, SQLException {
           return ""+jpaTemplate.find("select description from TypeGenus where idTypeGenus= '"+idTypeGenus+"' ").get(0);

     }

    //Seleziona i dati inseriti relativi alla SIAE -> utile per l'edit
    public List<ConfigurazioneSIAE> selectListaConfigurazioneSIAE() {
    List<ConfigurazioneSIAE> res= new ArrayList<ConfigurazioneSIAE>();
    for (ConfigurationSIAE cs : (List<ConfigurationSIAE>) jpaTemplate.find("select cs from ConfigurationSIAE")) {
      ConfigurazioneSIAE l = new ConfigurazioneSIAE();
      l.setSCodiceSistema(cs.getSSystemCode());
      l.setSIDCFTitolare(cs.getSTCOrganizer());
      l.setSSistemaEmissione(cs.getSEmissionSystem());
      l.setSCartaAttivazione(cs.getSSmartCard());
      res.add(l);
    }
    return res;
  }




}
