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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.MapKey;
import javax.persistence.Table;


@Entity @Cacheable
@Table(name="JT_INTEREST_PLACES")

/**
 *
 * @author Andrea Saba
 */
public class InterestPlace {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_INTEREST_PLACES")
  private long id;

  @Column(name="TITLE")
  private String title;

  @Column(name="DESCRIPTION")
  private String description;

  @Lob @Column(name="IMAGE")
  private byte[] image;

  @Column(name="STATE")
  private char state;

  @ManyToMany @JoinTable(name="JT_TA_OPERATORS_PLACES",
      joinColumns= @JoinColumn(name="UID_INTEREST_PLACES", referencedColumnName="UID_INTEREST_PLACES"),
      inverseJoinColumns= @JoinColumn(name="UID_OPERATOR", referencedColumnName="UID_USERS")
   )


  @MapKey
  private List<User0> assignedOperators;

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

  public char getState() {
    return state;
  }

  public void setState(char state) {
    this.state = state;
  }

  public List<User0> getAssignedOperators() {
    return assignedOperators;
  }

  public void setAssignedOperators(List<User0> assignedOperators) {
    this.assignedOperators = assignedOperators;
  }

}