/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.dwr;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;

import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;

public class CheckUsernameDWR {

	SecurityMgr securityService = null;
	private String[] useridAppenders = new String[10];

	public String checkUsername(String firstName, String lastName, String username) throws ServletException, IOException , SQLException{

		ArrayList useridsGenerated = null;
		WebContext wctx = WebContextFactory.get();
		securityService = ServicesFactory.getInstance().getSecurityMgr();
		try {
			if (!securityService.isUsernameFree(username)) {
				useridsGenerated = generateUserid(firstName, lastName, username);
				wctx.getHttpServletRequest().setAttribute("useridsGenerated", useridsGenerated);
			}
			wctx.getHttpServletRequest().setAttribute("userid", username);
		} catch (SystemException se) {
			// logging
		}
		return wctx.forwardToString("/pages/register/checkUsername.jsp");
	}

	private ArrayList generateUserid(String firstName, String lastName, String username) throws SystemException , SQLException {
		ArrayList useridsGenerated = new ArrayList(1);
		securityService = ServicesFactory.getInstance().getSecurityMgr();
		String tempUsername = "";
		Calendar c = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG);
		df.setCalendar(c);
		if (firstName == null || firstName.trim().length() == 0)
			firstName = "zuser";
		if (lastName == null || lastName.trim().length() == 0)
			lastName = String.valueOf(c.get(Calendar.YEAR));
		useridAppenders[0] = new String(firstName + "." + lastName);
		useridAppenders[1] = new String("." + firstName);
		useridAppenders[2] = new String("_" + firstName);
		useridAppenders[3] = new String("." + lastName);
		useridAppenders[4] = new String("_" + lastName);
		useridAppenders[5] = new String(".321");
		useridAppenders[6] = new String("_321");
		useridAppenders[7] = new String(".123");
		useridAppenders[8] = new String("_123");
		for (int i = 0; i < 9; i++) {
			if ((firstName != null && firstName.trim().length() > 0) && (lastName != null && lastName.trim().length() != 0) && i == 0)
				tempUsername = firstName + "." + lastName;
			if (securityService.isUsernameFree(tempUsername)) {
				useridsGenerated.add(tempUsername);
			}
			tempUsername = username + useridAppenders[i];
		}

		return useridsGenerated;
	}

}
