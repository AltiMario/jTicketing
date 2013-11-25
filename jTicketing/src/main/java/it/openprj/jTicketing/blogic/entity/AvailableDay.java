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
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity @Cacheable
@Table(name="JT_TA_EVENTS_CALENDAR")

/**
 *
 * @author Andrea Saba
 */
public class AvailableDay {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_EVENTS_CALENDAR")
  private long id;

  @Column(name="YEAR")
  private int year;

  @Column(name="MONTH")
  private int month;

  @Column(name="DAY")
  private int day;

  @OneToMany(mappedBy="availableDay",cascade=CascadeType.ALL)
  private List<AvailableDayTurn> availableDayTurns= new ArrayList<AvailableDayTurn>();

  @Column(name="UID_TICKETS")
  private long attractionId;

  @Column(name="UID_INTEREST_PLACES")
  private long placeId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public List<AvailableDayTurn> getAvailableDayTurns() {
    return availableDayTurns;
  }

  public void setAvailableDayTurns(List<AvailableDayTurn> availableDayTurns) {
    this.availableDayTurns = availableDayTurns;
  }

  public long getPlaceId() {
    return placeId;
  }

  public void setPlaceId(long placeId) {
    this.placeId = placeId;
  }

  public long getAttractionId() {
    return attractionId;
  }

  public void setAttractionId(long attractionId) {
    this.attractionId = attractionId;
  }

}