/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.manager;

import it.openprj.jTicketing.blogic.entity.User0;
import it.openprj.jTicketing.blogic.exceptions.AccountNotActivatedException;
import it.openprj.jTicketing.blogic.exceptions.AuthenticationException;
import it.openprj.jTicketing.blogic.model.entity.User;

import java.util.List;


/**
 * Just a dummy implementation for the security manager, it doesn't check
 * the password (but you will have to know the user id, of course).
 *
 * @author Andrea Saba
 */
public class JpaSecurityMgrDummy extends JpaSecurityMgrImpl {

	@SuppressWarnings("unchecked")
  public User authenticate(String userName, String userPass) throws AuthenticationException, AccountNotActivatedException {
    List<User0> l= (List<User0>) jpaTemplate.findByNamedParams(
        "select u from User0 u where u.userid=:uid", JpaBackEndMgrImpl.fillMap(null,"uid",userName));
    if (l.size()<1)
      throw new AuthenticationException();
    User0 u= l.get(0);
    if (u.getActivated()!='S')
      throw new AccountNotActivatedException();

    return getUser(new User(),u);
  }

}
