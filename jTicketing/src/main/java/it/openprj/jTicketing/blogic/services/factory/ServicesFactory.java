/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.factory;

import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.blogic.services.manager.BuyProcessMgr;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.blogic.utilities.SpringApplicationContextHelper;

public class ServicesFactory {
  private static ServicesFactory singletonInstance;

  private ServicesFactory() {
    super();
  }

  public static ServicesFactory getInstance() {
    if (singletonInstance == null) {
      singletonInstance = new ServicesFactory();
    }
    return singletonInstance;
  }

  public SecurityMgr getSecurityMgr() {
    return (SecurityMgr) SpringApplicationContextHelper.getInstance().getBean("SecurityMgr");
  }

  public DataTablesMgr getDataTablesMgr() {
    return (DataTablesMgr) SpringApplicationContextHelper.getInstance().getBean("DataTablesMgr");
  }

  // TODO: rename it in getBackEndMgr ...
  public BackEndMgr getBackEndMgrMgr() {
    return (BackEndMgr) SpringApplicationContextHelper.getInstance().getBean("BackEndMgr");
  }

  public BuyProcessMgr getBuyProcessMgr() {
    return (BuyProcessMgr) SpringApplicationContextHelper.getInstance().getBean("BuyProcessMgr");
  }

}
