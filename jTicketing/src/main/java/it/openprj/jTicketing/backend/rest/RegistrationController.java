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
import it.openprj.jTicketing.blogic.exceptions.EmailAlreadyExistException;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.exceptions.UserNotFoundException;
import it.openprj.jTicketing.blogic.model.entity.Role;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.blogic.utilities.encrypt.DataEncrypt;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * @author c_rsharv
 * @author praveen
 * 
 */
@Path("/register")
public class RegistrationController {
	private static Logger logger = Logger
			.getLogger(RegistrationController.class);

	@Path("register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes("application/x-www-form-urlencoded")
	public GenericResultResponseType register(
			@FormParam("userName") String userName,
			@FormParam("password") String password,
			@FormParam("email") String email) {

		GenericResultResponseType obj = new GenericResultResponseType();
		try {
			SecurityMgr service = ServicesFactory.getInstance().getSecurityMgr();
			User user = null;
			user = service.newUser();
			user.setUserName(userName);
			user.setUserPass(DataEncrypt.getInstance().encrypt(password));
			user.setEmail(email);
			user.getRoles().put("Acquirente", new Role("Acquirente"));
			service.registerUser(user);
			//Mailer.sendActivationCode(user);
			obj.setSuccess(true);
			obj.setMessage("User is registered successfully.");
		} catch (AccountNotActivatedException ex) {
			obj.setSuccess(false);
			obj.setMessage("User is already registered but not activated.");
		} catch (SystemException e) {
			logger.error("Password encryption failed"+e.getMessage(),e);
			obj.setSuccess(false);
			obj.setMessage("Internal Server Error");
		} catch (EmailAlreadyExistException e) {
			obj.setSuccess(false);
			obj.setMessage("Email already registered with given email.");
		} catch (UserNotFoundException e) {
			obj.setSuccess(false);
			obj.setMessage("User is already registered with given user name.");
		}/* catch (EmailException e) {
			obj.setSuccess(false);
			obj.setMessage("User is registered successfully. But Email Dispatch Failed.");
		}*/
		return obj;
	}
}
