/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import java.math.BigDecimal;
import java.sql.Date;

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
@Table(name="JT_TA_PURCHASE")

/**
 *
 * @author Andrea Saba
 */
public class Purchase {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_TA_PURCHASE")
  private long id;

  @Column(name="PURCHASE_DATE")
  private Date purchaseDate;

  @Column(name="CONFIRMATION_CODE")
  private String confirmationCode;

  @Column(name="CONFIRMED")
  private char confirmed;

  @Column(name="CONFIRMATION_DATE")
  private Date confirmationDate;

  @Column(name="BUYER_EMAIL")
  private String buyerEMail;

  @Column(name="DAY")
  private int day;

  @Column(name="MONTH")
  private int month;

  @Column(name="YEAR")
  private int year;

  @Column(name="UID_TICKETS")
  private long attractionId;

  @Column(name="TICKET_PRICE")
  private BigDecimal ticketPrice;

  @ManyToOne
  @JoinColumn(referencedColumnName = "UID_USERS", name = "UID_BUYER" )
  private User0 buyer;

  @ManyToOne
  @JoinColumn(referencedColumnName = "UID_TICKETS_CATEGORIES", name = "UID_TICKETS_CATEGORY" )
  private TicketCategory category;

  @ManyToOne
  @JoinColumn(referencedColumnName = "UID_TP_EVENTS_SCHEDULE", name = "UID_TURNO_TICKETS" )
  private Turn turn;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getConfirmationCode() {
    return confirmationCode;
  }

  public void setConfirmationCode(String confirmationCode) {
    this.confirmationCode = confirmationCode;
  }

  public char getConfirmed() {
    return confirmed;
  }

  public void setConfirmed(char confirmed) {
    this.confirmed = confirmed;
  }

  public Date getConfirmationDate() {
    return confirmationDate;
  }

  public void setConfirmationDate(Date confirmationDate) {
    this.confirmationDate = confirmationDate;
  }

  public String getBuyerEMail() {
    return buyerEMail;
  }

  public void setBuyerEMail(String buyerEMail) {
    this.buyerEMail = buyerEMail;
  }

  public int getDay() {
    return day;
  }

  public void setDay(int day) {
    this.day = day;
  }

  public int getMonth() {
    return month;
  }

  public void setMonth(int month) {
    this.month = month;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public User0 getBuyer() {
    return buyer;
  }

  public void setBuyer(User0 buyer) {
    this.buyer = buyer;
  }

  public TicketCategory getCategory() {
    return category;
  }

  public void setCategory(TicketCategory category) {
    this.category = category;
  }

  public Turn getTurn() {
    return turn;
  }

  public void setTurn(Turn turn) {
    this.turn = turn;
  }

  public BigDecimal getTicketPrice() {
    return ticketPrice;
  }

  public void setTicketPrice(BigDecimal ticketPrice) {
    this.ticketPrice = ticketPrice;
  }

  public long getAttractionId() {
    return attractionId;
  }

  public void setAttractionId(long attractionId) {
    this.attractionId = attractionId;
  }

}