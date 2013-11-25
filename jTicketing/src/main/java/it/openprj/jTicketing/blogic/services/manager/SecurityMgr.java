/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.manager;

import it.openprj.jTicketing.blogic.exceptions.AccountNotActivatedException;
import it.openprj.jTicketing.blogic.exceptions.AlreadyActivatedException;
import it.openprj.jTicketing.blogic.exceptions.AuthenticationException;
import it.openprj.jTicketing.blogic.exceptions.EmailAlreadyExistException;
import it.openprj.jTicketing.blogic.exceptions.EmailNotFoundException;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.exceptions.UserNotFoundException;
import it.openprj.jTicketing.blogic.model.entity.User;

import java.sql.SQLException;

public interface SecurityMgr {

  public abstract User authenticateAdmin(String userName) throws AuthenticationException, AccountNotActivatedException,
                                                         SQLException;

  public abstract User authenticate(String userName, String userPass) throws AuthenticationException,
                                                                     AccountNotActivatedException, SQLException;

  public abstract User getUserByUsername(String userName) throws UserNotFoundException, AccountNotActivatedException,
                                                         SQLException;

  public abstract User getUserByEmail(String email) throws EmailNotFoundException, SQLException;

  public abstract void registerUser(User user) throws EmailAlreadyExistException, UserNotFoundException, AccountNotActivatedException;

  public abstract User newUser();

  public abstract User activateUser(String activationCode) throws UserNotFoundException, AlreadyActivatedException,
                                                          SQLException;

  public abstract void deleteUser(User user) throws SystemException, SQLException;

  public abstract void updateUserPassword(String userName, String password);

  public abstract void updateUser(User user) throws SystemException, SQLException;

  public abstract boolean isUsernameFree(String username) throws SQLException;

}