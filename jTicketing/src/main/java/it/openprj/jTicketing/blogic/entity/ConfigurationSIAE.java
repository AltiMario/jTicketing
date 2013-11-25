/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.entity;

import javax.persistence.*;
import java.util.List;


@Entity @Cacheable
@Table(name="JTS_ID_SYSTEM")

/**
 *
 * @author Roberto Marino
 */
public class ConfigurationSIAE {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "S_UID_SYSTEM")
  private long id;

  @Column(name = "S_EMISSION_SYSTEM")
  private String sEmissionSystem;

  @Column(name="S_SMART_CARD")
  private String sSmartCard;

  @Column(name="S_SYSTEM_CODE")
  private String sSystemCode;

  @Column(name="S_TC_ORGANIZER")
  private String sTCOrganizer;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSEmissionSystem() {
    return sEmissionSystem;
  }

  public void setSEmissionSystem(String sEmissionSystem) {
    this.sEmissionSystem = sEmissionSystem;
  }

  public String getSSmartCard() {
    return sSmartCard;
  }

  public void setSSmartCard(String sSmartCard) {
    this.sSmartCard = sSmartCard;
  }

  public String getSSystemCode() {
    return sSystemCode;
  }

  public void setSSystemCode(String sSystemCode) {
    this.sSystemCode = sSystemCode;
  }

  public String getSTCOrganizer() {
    return sTCOrganizer;
  }

  public void setSTCOrganizer(String sTCOrganizer) {
    this.sTCOrganizer = sTCOrganizer;
  }

}