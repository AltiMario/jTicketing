/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @Cacheable
@Table(name="JT_TP_EVENTS_SCHEDULE")

/**
 *
 * @author Andrea Saba
 */
public class Turn {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_TP_EVENTS_SCHEDULE")
  private long id;

  @Column(name = "HOUR")
  private String hour;

  @Column(name = "OPEN_TIME")
  private String openTime;

  @Column(name = "QTY")
  private long qty;

  @ManyToOne
  @JoinColumn(referencedColumnName = "UID_TICKETS", name = "UID_TICKET")
  private Attraction attraction;

  @OneToMany(mappedBy="turn",cascade=CascadeType.ALL,orphanRemoval=true)
  private List<AvailableDayTurn> availableDayTurns;

  @Column(name="UID_INTEREST_PLACE")
  private long placeId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getHour() {
    return hour;
  }

  public void setHour(String hour) {
    this.hour = hour;
  }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public long getQty() {
    return qty;
  }

  public void setQty(long qty) {
    this.qty = qty;
  }


  public Attraction getAttraction() {
    return attraction;
  }

  public void setAttraction(Attraction attraction) {
    this.attraction = attraction;
  }

  public long getPlaceId() {
    return placeId;
  }

  public void setPlaceId(long placeId) {
    this.placeId = placeId;
  }

  public List<AvailableDayTurn> getAvailableDayTurns() {
    return availableDayTurns;
  }

  public void setAvailableDayTurns(List<AvailableDayTurn> availableDayTurns) {
    this.availableDayTurns = availableDayTurns;
  }

}