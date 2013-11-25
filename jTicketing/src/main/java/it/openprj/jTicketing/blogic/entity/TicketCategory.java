/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import java.math.BigDecimal;

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
@Table(name="JT_TICKETS_CATEGORIES")

/**
 *
 * @author Andrea Saba
 */
public class TicketCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_TICKETS_CATEGORIES")
  private long id;

  @Column(name="DESCRIPTION")
  private String description;

  @Column(name="INFO")
  private String info;

  @Column(name="PRICE")
  private BigDecimal price;

  @ManyToOne
  @JoinColumn(referencedColumnName = "UID_TICKETS", name = "UID_TICKET" )
  private Attraction attraction;

  @Column(name="UID_INTEREST_PLACES")
  private long placeId;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public long getPlaceId() {
    return placeId;
  }

  public void setPlaceId(long placeId) {
    this.placeId = placeId;
  }

  public Attraction getAttraction() {
    return attraction;
  }

  public void setAttraction(Attraction attraction) {
    this.attraction = attraction;
  }

}