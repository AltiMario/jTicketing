/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

/**
 * 
 */
package it.openprj.jTicketing.backend.rest;

import it.openprj.jTicketing.backend.jaxb.GenericResultResponseType;
import it.openprj.jTicketing.blogic.exceptions.AccountNotActivatedException;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.exceptions.UserNotFoundException;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.blogic.utilities.encrypt.DataEncrypt;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * @author c_rsharv
 * 
 */
@Path("/login")
public class LoginController {
	private static Logger logger = Logger.getLogger(LoginController.class);

	@Path("login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public GenericResultResponseType login(@FormParam("userName") String userName,
			@FormParam("password") String password) {
		GenericResultResponseType obj = new GenericResultResponseType();
		try {
			SecurityMgr service = ServicesFactory.getInstance().getSecurityMgr();
			String pwd = DataEncrypt.getInstance().encrypt(password);
			User user = service.getUserByUsername(userName);
			if (user != null && user.getUserPass().equals(pwd)) {
				obj.setSuccess(true);
				obj.setMessage("User is logged in successfully.");
			} else {
				obj.setSuccess(false);
				obj.setMessage("Invalid username/password.!");
			}
		} catch (AccountNotActivatedException ex) {
			logger.error("SystemException occured during detail and calendar Ticket.",ex);
			obj.setSuccess(false);
			obj.setMessage("User not enabled");
		} catch (UserNotFoundException ex) {
			logger.error("SystemException occured during detail and calendar Ticket.",ex);
			obj.setSuccess(false);
			obj.setMessage("Invalid username/password.!");
		} catch (SQLException ex) {
			logger.error("SQLExfeption occured during detail Ticket.",ex);
			obj.setSuccess(false);
			obj.setMessage("Internal Server Error");
		} catch (SystemException e) {
			logger.error("System Exception.",e);
			obj.setSuccess(false);
			obj.setMessage("Internal Server Error.");
		}
		return obj;
	}
}
