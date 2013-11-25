/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity @Cacheable
@Table(name="JT_TA_EVENTS_SCHEDULE")

/**
 *
 * @author Andrea Saba
 */
public class AvailableDayTurn {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_EVENTS_SCHEDULE")
  private long id;

  @Column(name="AVAILABLE_QTY")
  private long availableQty;

  @ManyToOne
  @JoinColumn(referencedColumnName = "UID_EVENTS_CALENDAR", name = "UID_EVENTS_CALENDAR" )
  private AvailableDay availableDay;

  @ManyToOne
  @JoinColumn(referencedColumnName = "UID_TP_EVENTS_SCHEDULE", name = "UID_TP_EVENT_SCHEDULE" )
  private Turn turn;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getAvailableQty() {
    return availableQty;
  }

  public void setAvailableQty(long availableQty) {
    this.availableQty = availableQty;
  }

  public AvailableDay getAvailableDay() {
    return availableDay;
  }

  public void setAvailableDay(AvailableDay availableDay) {
    this.availableDay = availableDay;
  }

  public Turn getTurn() {
    return turn;
  }

  public void setTurn(Turn turn) {
    this.turn = turn;
  }

}