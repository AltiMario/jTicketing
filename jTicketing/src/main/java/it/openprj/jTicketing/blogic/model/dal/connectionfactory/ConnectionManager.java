/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.dal.connectionfactory;

import it.openprj.jTicketing.blogic.utilities.SpringApplicationContextHelper;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public class ConnectionManager {

  static Logger log = Logger.getLogger(ConnectionManager.class);

  public static Connection getPooledConnection() {
    SpringApplicationContextHelper.getInstance().getBean("dataSource");
    try {
      DataSource ds = (DataSource) SpringApplicationContextHelper.getInstance().getBean("dataSource");
      return ds.getConnection();
    } catch (SQLException e) {
      // TODO: please decide and use a correct strategy about exception handling
      log.error("unable to get connection",e);
      throw new RuntimeException(e);
    }
  }
}
