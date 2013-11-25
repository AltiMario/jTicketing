/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Entity @Cacheable
@Table(name="JT_USERS")

/**
 *
 * @author Andrea Saba
 */
public class User0 {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "UID_USERS")
  private long id;

  @Column(name="NAME")
  private String name;

  @Column(name="SURNAME")
  private String surname;

  @Column(name="EMAIL")
  private String email;

  @Column(name="USERID", unique=true)
  private String userid;

  @Column(name="USERPASS")
  private String password;

  @Column(name="BIRTHDATE")
  private Date birthdate;

  @Column(name="CF")
  private String cf;

  @Column(name="SUBSCRIPTION_DATE")
  private Date subscriptionDate;

  @Column(name="ENABLED")
  private char enabled;

  @Column(name="ACTIVATION_CODE")
  private String activationCode;

  @Column(name="ACTIVATED")
  private char activated;

  @Column(name="ID_BIRTHPLACE")
  private long idBirthplace;

  @ManyToMany @JoinTable(name="JT_TA_USERS_ROLES",
      joinColumns= @JoinColumn(name="UID_USERS", referencedColumnName="UID_USERS"),
      inverseJoinColumns= @JoinColumn(name="UID_ROLE", referencedColumnName="UID_ROLES")
   )
  private List<Role> roles= new ArrayList<Role>();

  @ManyToMany @JoinTable(name="JT_TA_OPERATORS_PLACES",
     joinColumns= @JoinColumn(name="UID_OPERATOR", referencedColumnName="UID_USERS"),
     inverseJoinColumns= @JoinColumn(name="UID_INTEREST_PLACES", referencedColumnName="UID_INTEREST_PLACES")
  )
  private List<InterestPlace> interestPlaces;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Date getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(Date birthdate) {
    this.birthdate = birthdate;
  }

  public String getCf() {
    return cf;
  }

  public void setCf(String cf) {
    this.cf = cf;
  }

  public Date getSubscriptionDate() {
    return subscriptionDate;
  }

  public void setSubscriptionDate(Date subscriptionDate) {
    this.subscriptionDate = subscriptionDate;
  }

  public char getEnabled() {
    return enabled;
  }

  public void setEnabled(char enabled) {
    this.enabled = enabled;
  }

  public String getActivationCode() {
    return activationCode;
  }

  public void setActivationCode(String activationCode) {
    this.activationCode = activationCode;
  }

  public char getActivated() {
    return activated;
  }

  public void setActivated(char activated) {
    this.activated = activated;
  }

  public List<Role> getRoles() {
    return roles;
  }

  public void setRoles(List<Role> roles) {
    this.roles = roles;
  }

  public List<InterestPlace> getInterestPlaces() {
    return interestPlaces;
  }

  public void setInterestPlaces(List<InterestPlace> interestPlaces) {
    this.interestPlaces = interestPlaces;
  }

    public long getIdBirthplace() {
        return idBirthplace;
    }

    public void setIdBirthplace(long idBirthplace) {
        this.idBirthplace = idBirthplace;
    }
}