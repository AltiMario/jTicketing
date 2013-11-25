/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.openprj.jTicketing.backend.rest;

import it.openprj.jTicketing.backend.jaxb.RoleResponceType;
import it.openprj.jTicketing.backend.jaxb.UserResponceType;
import it.openprj.jTicketing.blogic.exceptions.AccountNotActivatedException;
import it.openprj.jTicketing.blogic.exceptions.UserNotFoundException;
import it.openprj.jTicketing.blogic.model.entity.Role;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;

import java.sql.SQLException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/users")
public class UsersController {

	private static Logger logger = Logger.getLogger(UsersController.class);

	@Path("detail")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserResponceType detail(@QueryParam(value = "userName") String userName) {

		UserResponceType obj = new UserResponceType();

		try {

			SecurityMgr service = ServicesFactory.getInstance().getSecurityMgr();
			User user = service.getUserByUsername(userName);
			if (user != null) {
				obj = mapFromBean(user);
			}

		} catch (AccountNotActivatedException ex) {
			logger.error("SystemException occured during detail and calendar Ticket.");

		} catch (UserNotFoundException ex) {
			logger.error("SystemException occured during detail and calendar Ticket.");
		} catch (SQLException ex) {
			logger.error("SQLExfeption occured during detail Ticket.");
		}
		return obj;
	}

	public static UserResponceType mapFromBean(User user) {
		UserResponceType obj = new UserResponceType();
		if (user != null) {
			obj.setUserId(user.getIdUser());
			obj.setUserName(user.getUserName());
			obj.setUserPass(user.getUserPass());
			obj.setUserFirstName(user.getFirstName());
			obj.setUserLastName(user.getLastName());
			obj.setUserBirthdate(user.getBirthdate());
			obj.setUserCF(user.getCf());
			obj.setUserEmail(user.getEmail());
			obj.setUserActivationCode(user.getActivationCode());
			obj.setUserEnabled(user.isEnabled());
			obj.setUserActivated(user.isActivated());
			obj.setUserRole(user.getRuolo());
			obj.setUserBirthPlaceId(user.getIdbirthplace());
			for (String key : user.getRoles().keySet()) {
				Role role = user.getRoles().get(key);
				RoleResponceType objRole = new RoleResponceType();
				objRole.setRoleId(role.getIdUserRoles());
				objRole.setRoleName(role.getRoleName());
				objRole.setRoleUserId(role.getIdUser());
				objRole.setRoleUsername(role.getUserName());
				obj.getUserRoles().add(objRole);
			}
		}
		return obj;
	}
}
