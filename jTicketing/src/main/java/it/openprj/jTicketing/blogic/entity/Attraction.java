/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @Cacheable
@Table(name="JT_TICKETS")

/**
 *
 * @author Andrea Saba
 */
public class Attraction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_TICKETS")
  private long id;

  @Column(name="TITLE")
  private String title;

  @Column(name="DESCRIPTION")
  private String description;

  @Column(name="NUMBER_PLACE")
  private String numberPlace;

  @Column(name = "PLACE_ORDER_CODE")
  private String placeOrderCode;

  @Column(name = "TYPE_GENUS")
  private long typeGenus;

  @Column(name = "TYPE_ORGANIZER")
  private String typeOrganizer;

  @Column(name = "FILM_NATIONALITY")
  private String filmNationality;

  @Column(name = "AUTHOR")
  private String author;

  @Column(name = "EXECUTOR")
  private String executor;

  @Column(name = "NUMBER_OPERAS")
  private int numberOperas;

  @Column(name = "EVENT_TYPE")
  private String eventType;

  @Column(name = "INCIDENCE")
  private int incidence;

  @Column(name="IMAGE") @Lob
  private byte[] image;

  @ManyToOne
  @JoinColumn(referencedColumnName = "UID_INTEREST_PLACES", name = "UID_INTEREST_PLACES" )
  private InterestPlace place;

  @OneToMany(mappedBy="attraction",cascade=CascadeType.ALL)
  private List<Turn> turns= new ArrayList<Turn>();

  @OneToMany(mappedBy="attraction",cascade=CascadeType.ALL)
  private List<TicketCategory> categories= new ArrayList<TicketCategory>();

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getNumberPlace() {
        return numberPlace;
    }

    public void setNumberPlace(String numberPlace) {
        this.numberPlace = numberPlace;
    }

    public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public byte[] getImage() {
    return image;
  }

  public void setImage(byte[] image) {
    this.image = image;
  }

  public InterestPlace getPlace() {
    return place;
  }

  public void setPlace(InterestPlace place) {
    this.place = place;
  }

  public List<Turn> getTurns() {
    return turns;
  }

  public void setTurns(List<Turn> turns) {
    this.turns = turns;
  }

  public List<TicketCategory> getCategories() {
    return categories;
  }

  public void setCategories(List<TicketCategory> categories) {
    this.categories = categories;
  }

  public String getPlaceOrderCode() {
        return placeOrderCode;
    }

    public void setPlaceOrderCode(String placeOrderCode) {
        this.placeOrderCode = placeOrderCode;
    }

    public long getTypeGenus() {
        return typeGenus;
    }

    public void setTypeGenus(long typeGenus) {
        this.typeGenus = typeGenus;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getIncidence() {
        return incidence;
    }

    public void setIncidence(int incidence) {
        this.incidence = incidence;
    }

    public String getTypeOrganizer() {
        return typeOrganizer;
    }

    public void setTypeOrganizer(String typeOrganizer) {
        this.typeOrganizer = typeOrganizer;
    }

    public String getFilmNationality() {
        return filmNationality;
    }

    public void setFilmNationality(String filmNationality) {
        this.filmNationality = filmNationality;
    }

    public int getNumberOperas() {
        return numberOperas;
    }

    public void setNumberOperas(int numberOperas) {
        this.numberOperas = numberOperas;
    }
}