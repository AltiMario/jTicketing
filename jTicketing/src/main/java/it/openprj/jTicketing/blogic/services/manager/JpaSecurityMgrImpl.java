/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.services.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import it.openprj.jTicketing.blogic.entity.User0;
import it.openprj.jTicketing.blogic.exceptions.AccountNotActivatedException;
import it.openprj.jTicketing.blogic.exceptions.AlreadyActivatedException;
import it.openprj.jTicketing.blogic.exceptions.AuthenticationException;
import it.openprj.jTicketing.blogic.exceptions.EmailAlreadyExistException;
import it.openprj.jTicketing.blogic.exceptions.EmailNotFoundException;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.exceptions.UserNotFoundException;
import it.openprj.jTicketing.blogic.model.entity.Role;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.utilities.DataConverter;

import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaTemplate;

/**
 *
 * @author Andrea Saba
 */
public class JpaSecurityMgrImpl implements SecurityMgr {

  @SuppressWarnings("unused")
  private static final Logger log = Logger.getLogger(JpaBackEndMgrImpl.class);

  protected JpaTemplate jpaTemplate;

  public void setJpaTemplate(JpaTemplate jpaTemplate) {
    this.jpaTemplate = jpaTemplate;
  }

  @SuppressWarnings("unchecked")
  public User authenticateAdmin(String userName) throws AuthenticationException, AccountNotActivatedException {
    List<User0> l= (List<User0>) jpaTemplate.findByNamedParams("select u from User0 u where u.userid=:uid", JpaBackEndMgrImpl.fillMap(null,"uid",userName));
    if (l.size()<1)
      throw new AuthenticationException();
    User0 u= l.get(0);
    if (u.getActivated()!='S')
      throw new AccountNotActivatedException();

		return getUser(new User(),u);
	}

  public static List<User> getUsers(List<User0> l) {
    List<User> res = new ArrayList<User>();
    for (User0 u : l) {
      res.add(getUser(new User(), u));
    }
    return res;
  }

	public static User getUser(User res, User0 u) {
    res.setIdUser(u.getId());
    res.setFirstName(u.getName());
    res.setLastName(u.getSurname());
    res.setEmail(u.getEmail());
    res.setActivationCode(u.getActivationCode());
    res.setUserName(u.getUserid());
    res.setUserPass(u.getPassword());
    res.setActivated(DataConverter.stringToBooleanConverter(u.getActivated()+""));
    res.setEnabled(DataConverter.stringToBooleanConverter(u.getEnabled()+""));
    for (it.openprj.jTicketing.blogic.entity.Role r : u.getRoles()) {
      Role resrole = new Role();
      resrole.setIdUser(u.getId());
      resrole.setIdUserRoles(r.getId());
      resrole.setRoleName(r.getName());
      res.getRoles().put(resrole.getRoleName(), resrole);
      res.setRuolo(resrole.getRoleName());  // TODO evil evil!
    }
    return res;
  }

	@SuppressWarnings("unchecked")
  public User authenticate(String userName, String userPass) throws AuthenticationException, AccountNotActivatedException {
    List<User0> l= (List<User0>) jpaTemplate.findByNamedParams(
        "select u from User0 u where u.userid=:uid and u.password=:pwd", JpaBackEndMgrImpl.fillMap(null,"uid",userName,"pwd",userPass));
    if (l.size()<1)
      throw new AuthenticationException();
    User0 u= l.get(0);
    if (u.getActivated()!='S')
      throw new AccountNotActivatedException();

    return getUser(new User(),u);
  }

  @SuppressWarnings("unchecked")
  public User getUserByUsername(String userName) throws UserNotFoundException, AccountNotActivatedException {
    List<User0> l = (List<User0>) jpaTemplate.findByNamedParams("select u from User0 u where u.userid=:uid",JpaBackEndMgrImpl.fillMap(null, "uid", userName));
    if (l.size() < 1)
      throw new UserNotFoundException();
    User0 u = l.get(0);
    if (u.getActivated() != 'S')
      throw new AccountNotActivatedException();
    return getUser(new User(), u);
  }

	@SuppressWarnings("unchecked")
  public User getUserByEmail(String email) throws EmailNotFoundException {
    List<User0> l = (List<User0>) jpaTemplate.findByNamedParams("select u from User0 u where u.email=:email",JpaBackEndMgrImpl.fillMap(null, "email", email));
    if (l.size() < 1)
      throw new EmailNotFoundException();
    return getUser(new User(), l.get(0));
  }


	public void registerUser(User user) throws EmailAlreadyExistException, UserNotFoundException, AccountNotActivatedException {
        boolean emailok = false;
        boolean userok = false;
		try {
			getUserByEmail(user.getEmail());
			throw new EmailAlreadyExistException();
		} catch (EmailNotFoundException e) {
	        emailok = true;
		}
        try {
            getUserByUsername(user.getUserName());
        } catch (UserNotFoundException e){
            userok = true;
        } catch (AccountNotActivatedException e) {
            userok = false;
        }
        if (emailok && userok) {
			User0 u= new User0();
	        u.setName(user.getFirstName());
	        u.setSurname(user.getLastName());
	        u.setBirthdate(user.getBirthdate());
	        u.setIdBirthplace(user.getIdbirthplace());
	        u.setCf(user.getCf());
	        u.setEmail(user.getEmail());
	        u.setUserid(user.getUserName());
	        u.setPassword(user.getUserPass());
	        u.setSubscriptionDate(new Date());
	        u.setActivationCode(user.getActivationCode());
            u.setEnabled('N');
            u.setActivated('N');
            u.getRoles().add(jpaTemplate.find(it.openprj.jTicketing.blogic.entity.Role.class,2L));
		    jpaTemplate.persist(u);
		    user.setIdUser(u.getId());
        } else if (!userok) {
            throw new UserNotFoundException();
        }
    }

	public User newUser() {
		User user = new User();
		user.setActivationCode(String.valueOf((long) (Math.random() * 1000000000)));
		return user;
	}

	@SuppressWarnings("unchecked")
  public User activateUser(String activationCode)	throws UserNotFoundException, AlreadyActivatedException {
    List<User0> l = (List<User0>) jpaTemplate.findByNamedParams("select u from User0 u where u.activationCode=:acode",JpaBackEndMgrImpl.fillMap(null, "acode", activationCode));
    if (l.size() < 1)
      throw new UserNotFoundException();
    User0 u = l.get(0);
    if (u.getActivated() == 'S')
      throw new AlreadyActivatedException();
    u.setActivated('S');
    u.setEnabled('S');
    return getUser(new User(), u);
  }

	@SuppressWarnings("unchecked")
  public void deleteUser(User user) throws SystemException {
	  List<User0> l = (List<User0>) jpaTemplate.findByNamedParams("select u from User0 u where u.userid=:uid",JpaBackEndMgrImpl.fillMap(null, "uid", user.getUserName()));
	  if (l.size()<1)
	    return;
	  jpaTemplate.remove(l.get(0));
	}

	public void updateUserPassword(String userName, String password) {
		// TODO Auto-generated method stub (was already unimplemented)
	}

	@SuppressWarnings("unchecked")
  public void updateUser(User user) throws SystemException , SQLException {
    List<User0> l = (List<User0>) jpaTemplate.findByNamedParams("select u from User0 u where u.id=:uid",JpaBackEndMgrImpl.fillMap(null, "uid", user.getIdUser()));
    if (l.size()<1)
      return;
    User0 u= l.get(0);


    u.setName(user.getFirstName());
    u.setSurname(user.getLastName());
    u.setEmail(u.getEmail());
    u.setActivationCode(u.getActivationCode());
    u.setUserid(user.getUserName());
    u.setPassword(user.getUserPass());
    u.setActivated(DataConverter.booleanToStringConverter(user.isActivated()).charAt(0));
    u.setEnabled(DataConverter.booleanToStringConverter(user.isEnabled()).charAt(0));
    return;
	}

	public boolean isUsernameFree(String username) throws SQLException {
		try {
			getUserByUsername(username);
			return false;
		} catch (UserNotFoundException enfe) {
			return true;
		} catch (AccountNotActivatedException e) {
			return false;
		}
	}

}
