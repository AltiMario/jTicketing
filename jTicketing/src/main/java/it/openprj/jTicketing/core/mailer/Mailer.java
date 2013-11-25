/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.mailer;

import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.TicketAcquistato;
import it.openprj.jTicketing.blogic.model.entity.User;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;

public class Mailer {

	private static String smtpServer = null;
	private static String smtpPort = null;
	private static boolean smtpSSL = true;
	private static String smtpUserid = null;
	private static String smtpPwd = null;
	private static String mailFrom = null;
	private static String mailSpam = null;
	private static String pathImmagini_common = null;
	private static String mail_banner = null;
	private static String mail_submit = null;
	private static String mail_activate = null;
	private static Logger log = Logger.getLogger(Mailer.class);

	static {
		ResourceBundle resources = ResourceBundle.getBundle("resources.mailconfig");
		smtpServer = resources.getString("SMTP_SERVER");
		smtpPort = resources.getString("SMTP_PORT");

		if (resources.getString("SMTP_SSL").equals("true"))
			smtpSSL = true;
		else
			smtpSSL = false;
		smtpUserid = resources.getString("SMTP_USERID");
		smtpPwd = resources.getString("SMTP_PWD");
		mailFrom = resources.getString("MAIL_FROM");
		mailSpam = resources.getString("MAIL_SPAM");
		pathImmagini_common = resources.getString("pathImmagini_common");
		mail_banner = resources.getString("mail_banner");
		mail_submit = resources.getString("mail_submit");
		mail_activate = resources.getString("mail_activate");
	}

	public static void sendActivationCode(User user) throws EmailException {

		try {
            Locale country = Locale.getDefault();
			ResourceBundle mailText = ResourceBundle.getBundle("resources.MessageResources", country);
			
			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpServer);
			email.setAuthentication(smtpUserid, smtpPwd);
			email.setSSL(smtpSSL);
			email.setSmtpPort(Integer.parseInt(smtpPort));
			email.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());
			email.setFrom(mailFrom, "noreply");
			email.setSubject(mailText.getString("mail.ActivationAccount")+ mailText.getString("label.domainName"));
			
			StringBuffer htmlMail = new StringBuffer();
			htmlMail.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			htmlMail.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
			htmlMail.append("<head>");
			htmlMail.append("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
			htmlMail.append("<title>"+mailText.getString("label.productName")+" Email</title>");
			htmlMail.append("<style type='text/css'>");
			htmlMail.append("<!--");
			htmlMail.append(".Stile1 {");
			htmlMail.append("	font-family: Arial, Helvetica, sans-serif;");
			htmlMail.append("	font-size: 10px;");
			htmlMail.append("}");
			htmlMail.append(".Stile2 {font-size: 12}");
			htmlMail.append(".Stile3 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; }");
			htmlMail.append(".Stile4 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; }");
			htmlMail.append(".Stile5 {color: #666666}");
			htmlMail.append(".Stile7 {font-size: 14px}");
			htmlMail.append(".Stile8 {");
			htmlMail.append("	font-size: 18px;");
			htmlMail.append("	color: #666666;");
			htmlMail.append("}");
			htmlMail.append("-->");
			htmlMail.append("</style>");
			htmlMail.append("</head>");
			htmlMail.append("<body>");
			htmlMail.append("<table width='962' border='0' cellspacing='5'>");
			htmlMail.append("  <tr bordercolor='0' class='Stile2'>");
			htmlMail.append("    <td width='100%' bordercolor='0'><p class='Stile4'>" + mailText.getString("mail.cheers") + " " + user.getFirstName() + " " + user.getLastName() + " (" + user.getUserName() + "),</p>");
			htmlMail.append("    <p class='Stile3'>" + mailText.getString("mail.littleStepToRegistrationEnd") + " </p>");
			htmlMail.append("    <p class='Stile3'>" + mailText.getString("mail.setCode") + "</p></BR>");
			htmlMail.append("    <p align='center' class='Stile4'><span class='Stile8'> "+user.getActivationCode()+"</span></p>");
			htmlMail.append("    <p class='Stile4'>" + mailText.getString("mail.theStaff") + " "+mailText.getString("label.domainName")+"</p></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0'><span class='Stile5'><br />");
			htmlMail.append("    "+ mailText.getString("label.domainName")+" "+ mailText.getString("mail.sendToAddress") + " " + user.getEmail() + " "+mailText.getString("mail.accountEmail")+" <a href='http://"+mailText.getString("label.wwwDomainName")+"'>"+mailText.getString("label.wwwDomainName")+"</a>. " + mailText.getString("mail.registrationInfo") + " <a href='mailto:"+mailFrom+"'>"+mailFrom+"</a><br />");
			htmlMail.append("    </span></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' bgcolor='#CCCCCC'>");
			htmlMail.append("    <td width='100%'><div align='center' class='Stile1'><a href='http://"+mailText.getString("label.domainName")+"/'>" + mailText.getString("mail.copyright") + " 2010 "+mailText.getString("label.productName")+"</a></div></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("</table>");
			htmlMail.append("</body>");
			htmlMail.append("</html>");
			
			email.setHtmlMsg(htmlMail.toString());
			email.send();
		} catch (EmailException me) {
			throw new EmailException(me);
		} catch (MissingResourceException mre) {
			System.err.println("resources.mailconfig not found");
			throw new EmailException(mre);
		}

	}

	public static void sendWelcomeMail(User user) throws SystemException {
     try{
		Locale lingua = Locale.getDefault();
		ResourceBundle mailText = ResourceBundle.getBundle("resources.MessageResources", lingua);
		
		// Create the email message
		HtmlEmail email = new HtmlEmail();
		email.setHostName(smtpServer);
		email.setAuthentication(smtpUserid, smtpPwd);
		email.setSSL(smtpSSL);
		email.setSmtpPort(Integer.parseInt(smtpPort));
		email.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());
		email.setFrom(mailFrom, "noreply");
		email.setSubject(mailText.getString("mail.welcome")+ " (" + user.getUserName() + ")");
		
		StringBuffer htmlMail = new StringBuffer();
		htmlMail.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
		htmlMail.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
		htmlMail.append("<head>");
		htmlMail.append("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
		htmlMail.append("<title>"+mailText.getString("label.productName")+" Email</title>");
		htmlMail.append("<style type='text/css'>");
		htmlMail.append("<!--");
		htmlMail.append(".Stile1 {");
		htmlMail.append("	font-family: Arial, Helvetica, sans-serif;");
		htmlMail.append("	font-size: 10px;");
		htmlMail.append("}");
		htmlMail.append(".Stile2 {font-size: 12}");
		htmlMail.append(".Stile3 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; }");
		htmlMail.append(".Stile4 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; }");
		htmlMail.append(".Stile5 {color: #666666}");
		htmlMail.append(".Stile7 {font-size: 14px}");
		htmlMail.append(".Stile8 {");
		htmlMail.append("	font-size: 18px;");
		htmlMail.append("	color: #666666;");
		htmlMail.append("}");
		htmlMail.append("-->");
		htmlMail.append("</style>");
		htmlMail.append("</head>");
		htmlMail.append("<body>");
		htmlMail.append("<table width='962' border='0' cellspacing='5'>");
		htmlMail.append("  <tr bordercolor='0' class='Stile2'>");
		htmlMail.append("    <td width='100%' bordercolor='0'><p class='Stile4'>" + mailText.getString("mail.welcome") + " " + user.getFirstName() + " " + user.getLastName() + " (" + user.getUserName() + "),</p>");
		htmlMail.append("    <p class='Stile3'>   " + mailText.getString("mail.registrationOK") + " </p>");
		htmlMail.append("    <p class='Stile3'>" + mailText.getString("mail.blank") +"</p><br>");
		htmlMail.append("    <p class='Stile4'>" + mailText.getString("mail.theStaff") + " "+mailText.getString("label.domainName")+"</p></td>");
		htmlMail.append("  </tr>");
		htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
		htmlMail.append("    <td bordercolor='0'><span class='Stile5'><br />");
		htmlMail.append("    "+ mailText.getString("label.domainName")+" " + mailText.getString("mail.sendToAddress") + " " + user.getEmail() + " " + mailText.getString("mail.accountEmail") + " <a href='http://"+mailText.getString("label.wwwDomainName")+"'>"+mailText.getString("label.wwwDomainName")+"</a>. " + mailText.getString("mail.registrationInfo") + " <a href='mailto:"+mailSpam+"'>"+mailSpam+"</a><br />");
		htmlMail.append("    </span></td>");
		htmlMail.append("  </tr>");
		htmlMail.append("  <tr bordercolor='0' bgcolor='#CCCCCC'>");
		htmlMail.append("    <td width='100%'><div align='center' class='Stile1'><a href='http://"+mailText.getString("label.domainName")+"/'>" + mailText.getString("mail.copyright") + " 2010 "+mailText.getString("label.productName")+"</a></div></td>");
		htmlMail.append("  </tr>");
		htmlMail.append("</table>");
		htmlMail.append("</body>");
		htmlMail.append("</html>");
		
		email.setHtmlMsg(htmlMail.toString());
		email.send();
     } catch (EmailException me) {
			throw new SystemException(me);
		} catch (MissingResourceException mre) {
			System.err.println("resources.MessageResources not found");
			throw new SystemException(mre);
		}

	}
	
	public static void sendPassword(User user,String server) throws SystemException {

		try {
			Locale lingua = Locale.getDefault();
			ResourceBundle mailText = ResourceBundle.getBundle("resources.MessageResources", lingua);
			// embed the image and get the content id
			URL image_header = new URL("http://" + server + pathImmagini_common + mail_banner);
			URL image_submit = new URL("http://"+ server + pathImmagini_common + mail_submit);
			URL image_activate = new URL("http://" + server + pathImmagini_common + mail_activate);

			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpServer);
			email.setAuthentication(smtpUserid, smtpPwd);
			email.setSSL(smtpSSL);
			email.setSmtpPort(Integer.parseInt(smtpPort));
			email.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());
			email.setFrom(mailFrom, "noreply");
			email.setSubject(mailText.getString("mail.sendPassword"));

			String cid_header = email.embed(image_header, "logo");
			String cid_submit = email.embed(image_submit, "Accedi ora al tuo account");
			String cid_activate = email.embed(image_activate, "Attenzione");

			StringBuffer htmlMail = new StringBuffer();
			htmlMail.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			htmlMail.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
			htmlMail.append("<head>");
			htmlMail.append("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
			htmlMail.append("<title>"+mailText.getString("label.productName")+" Email</title>");
			htmlMail.append("<style type='text/css'>");
			htmlMail.append("<!--");
			htmlMail.append(".Stile1 {");
			htmlMail.append("	font-family: Arial, Helvetica, sans-serif;");
			htmlMail.append("	font-size: 10px;");
			htmlMail.append("}");
			htmlMail.append(".Stile2 {font-size: 12}");
			htmlMail.append(".Stile3 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; }");
			htmlMail.append(".Stile4 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; }");
			htmlMail.append(".Stile5 {color: #666666}");
			htmlMail.append(".Stile7 {font-size: 14px}");
			htmlMail.append(".Stile8 {");
			htmlMail.append("	font-size: 18px;");
			htmlMail.append("	color: #666666;");
			htmlMail.append("}");
			htmlMail.append("-->");
			htmlMail.append("</style>");
			htmlMail.append("</head>");
			htmlMail.append("<body>");
			htmlMail.append("<table width='962' border='0' cellspacing='5'>");
			htmlMail.append("  <tr>");
			htmlMail.append("    <td width='100%' bordercolor='0'><img src='cid:" + cid_header + "' /></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile2'>");
			htmlMail.append("    <td width='100%' bordercolor='0'><p class='Stile4'>" + mailText.getString("mail.cheers") + " " + user.getFirstName() + " " + user.getLastName() + " (" + user.getUserName() + "),</p>");
			htmlMail.append("    <p class='Stile3'>   " + mailText.getString("mail.newPasswordIs") + " </p><span class='Style8'><B>" + user.getUserPass() + "</B></span>");
			htmlMail.append("    <p class='Stile3'>" + mailText.getString("mail.passwordInformation") + "</p>");
            htmlMail.append("    <p align='center' class='Stile4'><a href='http://" + server + "/prepareLogin.do'><span class='Stile7'><img src='cid:" + cid_submit + "' border='0' align='absmiddle' /></span></a></p>");
			htmlMail.append("    <p class='Stile4'>" + mailText.getString("mail.theStaff") + " "+mailText.getString("label.domainName")+"</p></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0' bgcolor='#FF9900'><p><strong><img src='cid:" + cid_activate + "' width='16' height='16' align='absmiddle' /> " + mailText.getString("mail.attentionAboutYourAccount") + "</strong></p>");
			htmlMail.append("    </td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0'><span class='Stile5'><br />");
			htmlMail.append("    " + mailText.getString("label.domainName")+" "+  mailText.getString("mail.sendToAddress") + " " + user.getEmail() + " " + mailText.getString("mail.accountEmail") + " <a href='http://"+mailText.getString("label.wwwDomainName")+"'>"+mailText.getString("label.wwwDomainName")+"</a>. " + mailText.getString("mail.registrationInfo") + " <a href='mailto:"+mailFrom+"'>"+mailFrom+"</a><br />");
			htmlMail.append("    </span></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' bgcolor='#CCCCCC'>");
			htmlMail.append("    <td width='100%'><div align='center' class='Stile1'><a href='http://"+mailText.getString("label.domainName")+"/'>" + mailText.getString("mail.copyright") + " 2010 "+mailText.getString("label.productName")+"</a></div></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("</table>");
			htmlMail.append("</body>");
			htmlMail.append("</html>");
			email.setHtmlMsg(htmlMail.toString());

			// set the alternative message
			email.setTextMsg("La tua password nuova password e': " + user.getUserPass() + " ti ricordiamo di cambiarla appena possibile.");
			email.send();
		} catch (EmailException me) {
			throw new SystemException(me);
		} catch (MalformedURLException mue) {
			System.err.println("Malformed SMTP server");
			throw new SystemException(mue);
		} catch (MissingResourceException mre) {
			System.err.println("resources.mailconfig not found");
			throw new SystemException(mre);
		}
	}

	public static void sendUserid(User user,String server) throws SystemException {
                log.debug("Entering sendUserid");
		try {
			Locale lingua = Locale.getDefault();
			ResourceBundle mailText = ResourceBundle.getBundle("resources.MessageResources", lingua);
			// embed the image and get the content id
            URL image_header = new URL("http://"+server+pathImmagini_common+mail_banner);
			URL image_submit = new URL("http://"+server+pathImmagini_common+mail_submit);
			URL image_activate = new URL("http://"+server+pathImmagini_common+mail_activate);
                        
			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpServer);
			email.setAuthentication(smtpUserid, smtpPwd);
			email.setSSL(smtpSSL);
			email.setSmtpPort(Integer.parseInt(smtpPort));
			email.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());
                        log.debug("3");
			email.setFrom(mailFrom, "noreply");
			email.setSubject(mailText.getString("mail.sendUserID"));
			String cid_header = email.embed(image_header, "logo");
			String cid_submit = email.embed(image_submit, "Accedi ora al tuo account");
			String cid_activate = email.embed(image_activate, "Attenzione");
			StringBuffer htmlMail = new StringBuffer();
			htmlMail.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			htmlMail.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
			htmlMail.append("<head>");
			htmlMail.append("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
			htmlMail.append("<title>"+mailText.getString("label.productName")+" Email</title>");
			htmlMail.append("<style type='text/css'>");
			htmlMail.append("<!--");
			htmlMail.append(".Stile1 {");
			htmlMail.append("	font-family: Arial, Helvetica, sans-serif;");
			htmlMail.append("	font-size: 10px;");
			htmlMail.append("}");
			htmlMail.append(".Stile2 {font-size: 12}");
			htmlMail.append(".Stile3 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; }");
			htmlMail.append(".Stile4 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; }");
			htmlMail.append(".Stile5 {color: #666666}");
			htmlMail.append(".Stile7 {font-size: 14px}");
			htmlMail.append(".Stile8 {");
			htmlMail.append("	font-size: 18px;");
			htmlMail.append("	color: #666666;");
			htmlMail.append("}");
			htmlMail.append("-->");
			htmlMail.append("</style>");
			htmlMail.append("</head>");
			htmlMail.append("<body>");
			htmlMail.append("<table width='962' border='0' cellspacing='5'>");
			htmlMail.append("  <tr>");
			htmlMail.append("    <td width='100%' bordercolor='0'><img src='cid:" + cid_header + "'/></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile2'>");
			htmlMail.append("    <td width='100%' bordercolor='0'><p class='Stile4'>" + mailText.getString("mail.cheers") + " " + user.getFirstName() + " " + user.getLastName() + ",</p>");
			htmlMail.append("    <p class='Stile3'>   " + mailText.getString("mail.yourUserID") + " </p><span class='Style8'><B>" + user.getUserName() + "</B></span>");
			htmlMail.append("    <p align='center' class='Stile4'><a href='http://"+mailText.getString("label.wwwDomainName")+"/jTicketingWeb/prepareLogin.do'><span class='Stile7'><img src='cid:" + cid_submit + "' border='0' align='absmiddle' /></span></a></p>");
			htmlMail.append("    <p class='Stile4'>" + mailText.getString("mail.theStaff") + " "+mailText.getString("label.domainName")+"</p></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0' bgcolor='#FF9900'><p><strong><img src='cid:" + cid_activate + "' width='16' height='16' align='absmiddle' /> " + mailText.getString("mail.attentionAboutYourAccount") + "</strong></p>");
			htmlMail.append("    </td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0'><span class='Stile5'><br />");
			htmlMail.append("    " + mailText.getString("label.domainName")+" "+  mailText.getString("mail.sendToAddress") + " " + user.getEmail() + " " + mailText.getString("mail.accountEmail") + " <a href='http://"+mailText.getString("label.wwwDomainName")+"'>"+mailText.getString("label.wwwDomainName")+"</a>. " + mailText.getString("mail.registrationInfo") + " <a href='mailto:"+mailFrom+"'>"+mailFrom+"</a><br />");
			htmlMail.append("    </span></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' bgcolor='#CCCCCC'>");
			htmlMail.append("    <td width='100%'><div align='center' class='Stile1'><a href='http://"+mailText.getString("label.domainName")+"/'>" + mailText.getString("mail.copyright") + " 2010 "+mailText.getString("label.productName")+"</a>,</div></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("</table>");
			htmlMail.append("</body>");
			htmlMail.append("</html>");
			email.setHtmlMsg(htmlMail.toString());
                        log.debug("Creata mail: \n"+htmlMail.toString());
			// set the alternative message
			email.setTextMsg( user.getUserName());

			// send the email
			email.send();
		} catch (EmailException me) {
			throw new SystemException(me);
		} catch (MalformedURLException mue) {
			System.err.println("Malformed SMTP server");
			throw new SystemException(mue);
		} catch (MissingResourceException mre) {
			System.err.println("resources.mailconfig not found");
			throw new SystemException(mre);
		}

	}

	public static void sendProfileUpdatedNotify(User user) throws SystemException {

		try {
			Locale lingua = Locale.getDefault();
			ResourceBundle mailText = ResourceBundle.getBundle("resources.MessageResources", lingua);

			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpServer);
			email.setAuthentication(smtpUserid, smtpPwd);
			email.setSSL(smtpSSL);
			email.setSmtpPort(Integer.parseInt(smtpPort));
			email.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());
			email.setFrom(mailFrom, "noreply");
			email.setSubject(mailText.getString("mail.notice"));
			
			StringBuffer htmlMail = new StringBuffer();
			htmlMail.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			htmlMail.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
			htmlMail.append("<head>");
			htmlMail.append("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
			htmlMail.append("<title>"+mailText.getString("label.productName")+" Email</title>");
			htmlMail.append("<style type='text/css'>");
			htmlMail.append("<!--");
			htmlMail.append(".Stile1 {");
			htmlMail.append("	font-family: Arial, Helvetica, sans-serif;");
			htmlMail.append("	font-size: 10px;");
			htmlMail.append("}");
			htmlMail.append(".Stile2 {font-size: 12}");
			htmlMail.append(".Stile3 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; }");
			htmlMail.append(".Stile4 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; }");
			htmlMail.append(".Stile5 {color: #666666}");
			htmlMail.append(".Stile7 {font-size: 14px}");
			htmlMail.append(".Stile8 {");
			htmlMail.append("	font-size: 18px;");
			htmlMail.append("	color: #666666;");
			htmlMail.append("}");
			htmlMail.append("-->");
			htmlMail.append("</style>");
			htmlMail.append("</head>");
			htmlMail.append("<body>");
			htmlMail.append("<table width='962' border='0' cellspacing='5'>");
			htmlMail.append("  <tr bordercolor='0' class='Stile2'>");
			htmlMail.append("    <td width='100%' bordercolor='0'><p class='Stile4'>" + mailText.getString("mail.cheers") + " " + user.getFirstName() + " " + user.getLastName() + ",</p>");
			htmlMail.append("    <br><p class='Stile3'>   " + mailText.getString("mail.accountUpdated") + " </p><br>");
			htmlMail.append("    <p class='Stile4'>" + mailText.getString("mail.theStaff") + " "+mailText.getString("label.domainName")+"</p></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0'><span class='Stile5'><br />");
			htmlMail.append("    " + mailText.getString("label.domainName")+" "+  mailText.getString("mail.sendToAddress") + " " + user.getEmail() + " " + mailText.getString("mail.accountEmail") + " <a href='http://"+mailText.getString("label.wwwDomainName")+"'>"+mailText.getString("label.wwwDomainName")+"</a>. " + mailText.getString("mail.registrationInfo") + " <a href='mailto:"+mailFrom+"'>"+mailFrom+"</a><br />");
			htmlMail.append("    </span></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' bgcolor='#CCCCCC'>");
			htmlMail.append("    <td width='100%'><div align='center' class='Stile1'><a href='http://"+mailText.getString("label.domainName")+"/'>" + mailText.getString("mail.copyright") + " 2010 "+mailText.getString("label.productName")+"</a>,</div></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("</table>");
			htmlMail.append("</body>");
			htmlMail.append("</html>");
			email.setHtmlMsg(htmlMail.toString());

			//email.setHtmlMsg(user.getUserName());
			email.send();
		} catch (EmailException me) {
			throw new SystemException(me);
		} catch (MissingResourceException mre) {
			System.err.println("resources.mailconfig not found");
			throw new SystemException(mre);
		}
	}

	public static void sendPasswordUpdatedNotify(User user, String server) throws SystemException {

		try {
			Locale lingua = Locale.getDefault();
			ResourceBundle mailText = ResourceBundle.getBundle("resources.MessageResources", lingua);
			// embed the image and get the content id
            URL image_header = new URL("http://"+server+pathImmagini_common+mail_banner);
			URL image_submit = new URL("http://"+server+pathImmagini_common+mail_submit);
			URL image_activate = new URL("http://"+server+pathImmagini_common+mail_activate);
			
			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpServer);
			email.setAuthentication(smtpUserid, smtpPwd);
			email.setSSL(smtpSSL);
			email.setSmtpPort(Integer.parseInt(smtpPort));
			email.addTo(user.getEmail(), user.getFirstName() + " " + user.getLastName());
			email.setFrom(mailFrom, "noreply");
			email.setSubject(mailText.getString("mail.notice"));

			String cid_header = email.embed(image_header, "logo");
			String cid_submit = email.embed(image_submit, "Accedi ora al tuo account");
			String cid_activate = email.embed(image_activate, "Attenzione");

			StringBuffer htmlMail = new StringBuffer();
			htmlMail.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			htmlMail.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
			htmlMail.append("<head>");
			htmlMail.append("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
			htmlMail.append("<title>"+mailText.getString("label.productName")+" Email</title>");
			htmlMail.append("<style type='text/css'>");
			htmlMail.append("<!--");
			htmlMail.append(".Stile1 {");
			htmlMail.append("	font-family: Arial, Helvetica, sans-serif;");
			htmlMail.append("	font-size: 10px;");
			htmlMail.append("}");
			htmlMail.append(".Stile2 {font-size: 12}");
			htmlMail.append(".Stile3 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; }");
			htmlMail.append(".Stile4 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; }");
			htmlMail.append(".Stile5 {color: #666666}");
			htmlMail.append(".Stile7 {font-size: 14px}");
			htmlMail.append(".Stile8 {");
			htmlMail.append("	font-size: 18px;");
			htmlMail.append("	color: #666666;");
			htmlMail.append("}");
			htmlMail.append("-->");
			htmlMail.append("</style>");
			htmlMail.append("</head>");
			htmlMail.append("<body>");
			htmlMail.append("<table width='962' border='0' cellspacing='5'>");
			htmlMail.append("  <tr>");
			htmlMail.append("    <td width='100%' bordercolor='0'><img src='cid:" + cid_header + "' /></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile2'>");
			htmlMail.append("    <td width='100%' bordercolor='0'><p class='Stile4'>" + mailText.getString("mail.cheers") + " " + user.getFirstName() + " " + user.getLastName() + ",</p>");
			htmlMail.append("    <br><p class='Stile3'>   " + mailText.getString("mail.passwordUpdated") + " </p><br>");
			htmlMail.append("    <p align='center' class='Stile4'><a href='http://"+mailText.getString("label.wwwDomainName")+"/jTicketingWeb/prepareLogin.do'><span class='Stile7'><img src='cid:" + cid_submit + "' border='0' align='absmiddle' /></span></a></p>");
			htmlMail.append("    <p class='Stile4'>" + mailText.getString("mail.theStaff") + " "+mailText.getString("label.domainName")+"</p></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0' bgcolor='#FF9900'><p><strong><img src='cid:" + cid_activate + "' width='16' height='16' align='absmiddle' /> " + mailText.getString("mail.attentionAboutYourAccount") + "</strong></p>");
			htmlMail.append("    </td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0'><span class='Stile5'><br />");
			htmlMail.append("    " + mailText.getString("label.domainName")+" "+  mailText.getString("mail.sendToAddress") + " " +  user.getEmail() + " " + mailText.getString("mail.accountEmail") + " <a href='http://"+mailText.getString("label.wwwDomainName")+"'>"+mailText.getString("label.wwwDomainName")+"</a>. " + mailText.getString("mail.registrationInfo") + " <a href='mailto:"+mailFrom+"'>"+mailFrom+"</a><br />");
			htmlMail.append("    </span></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' bgcolor='#CCCCCC'>");
			htmlMail.append("    <td width='100%'><div align='center' class='Stile1'><a href='http://"+mailText.getString("label.domainName")+"/'>" + mailText.getString("mail.copyright") + " 2010 "+mailText.getString("label.productName")+"</a>,</div></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("</table>");
			htmlMail.append("</body>");
			htmlMail.append("</html>");
			email.setHtmlMsg(htmlMail.toString());

			email.setTextMsg( user.getUserName());
			email.send();
		} catch (EmailException me) {
			throw new SystemException(me);
		} catch (MalformedURLException mue) {
			System.err.println("Malformed SMTP server");
			throw new SystemException(mue);
		} catch (MissingResourceException mre) {
			System.err.println("resources.mailconfig not found");
			throw new SystemException(mre);
		}
	}
	
	public static void sendPurchasedTickets(User user, ArrayList<TicketAcquistato> listaTickets) throws SystemException {

		try {
			Locale lingua = Locale.getDefault();
			ResourceBundle mailText = ResourceBundle.getBundle("resources.MessageResources", lingua);
			
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpServer);
			email.setAuthentication(smtpUserid, smtpPwd);
			email.setSSL(smtpSSL);
			email.setSmtpPort(Integer.parseInt(smtpPort));
			email.addTo(user.getEmail(), "Gentile utente");
			email.setFrom(mailFrom, "noreply");
			email.setSubject(mailText.getString("mail.notice"));

			StringBuffer htmlMail = new StringBuffer();
			htmlMail.append("<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>");
			htmlMail.append("<html xmlns='http://www.w3.org/1999/xhtml'>");
			htmlMail.append("<head>");
			htmlMail.append("<meta http-equiv='Content-Type' content='text/html; charset=iso-8859-1' />");
			htmlMail.append("<title>"+mailText.getString("label.productName")+" Email</title>");
			htmlMail.append("<style type='text/css'>");
			htmlMail.append("<!--");
			htmlMail.append(".Stile1 {");
			htmlMail.append("	font-family: Arial, Helvetica, sans-serif;");
			htmlMail.append("	font-size: 10px;");
			htmlMail.append("}");
			htmlMail.append(".Stile2 {font-size: 12}");
			htmlMail.append(".Stile3 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; }");
			htmlMail.append(".Stile4 {font-size: 11px; font-family: Arial, Helvetica, sans-serif; font-weight: bold; }");
			htmlMail.append(".Stile5 {color: #666666}");
			htmlMail.append(".Stile7 {font-size: 14px}");
			htmlMail.append(".Stile8 {");
			htmlMail.append("	font-size: 18px;");
			htmlMail.append("	color: #666666;");
			htmlMail.append("}");
			htmlMail.append("-->");
			htmlMail.append("</style>");
			htmlMail.append("</head>");
			htmlMail.append("<body>");
			htmlMail.append("<table width='962' border='0' cellspacing='5'>");
			htmlMail.append("  <tr bordercolor='0' class='Stile2'>");
			htmlMail.append("    <td width='100%' bordercolor='0'><p class='Stile4'>" + mailText.getString("mail.cheers") +" Gentile utente,</p>");
			htmlMail.append("    <br><p class='Stile3'> " + mailText.getString("mail.buyTicketOK") + "<br>"+ mailText.getString("mail.printEmail") +"</p><BR/>");
			htmlMail.append("    <p align='left' class='Stile4'>");
			htmlMail.append("<HR><br/>");
			TicketAcquistato ticket=null;
			for(int i=0; i<listaTickets.size(); i++){
				ticket=listaTickets.get(i);
				htmlMail.append("["+(i+1)+"] DATA INGRESSO ["+ticket.getGiorno()+"/"+ticket.getMese()+"/"+ticket.getAnno() +"] TURNO ["+ticket.getTurno()+"]<br/>");
				htmlMail.append(".........DESCRIZIONE ["+ticket.getTicket().getDescrizione()+"] <br/>");
				htmlMail.append(".........IDENTIFICATIVO UNIVOCO ["+ticket.getCodiceVerifica()+"] <br/>");
				htmlMail.append("<HR><br/>");
			}
			htmlMail.append("    		</p>");
			htmlMail.append("    <p class='Stile4'>" + mailText.getString("mail.theStaff") + " "+mailText.getString("label.domainName")+"</p></td>");
			htmlMail.append("  <tr bordercolor='0' class='Stile3'>");
			htmlMail.append("    <td bordercolor='0'><span class='Stile5'><br />");
			htmlMail.append("    " + mailText.getString("label.domainName")+" "+  mailText.getString("mail.sendToAddress") + " " + user.getEmail() + " " + mailText.getString("mail.accountEmail") + " <a href='http://"+mailText.getString("label.wwwDomainName")+"'>"+mailText.getString("label.wwwDomainName")+"</a>. " + mailText.getString("mail.registrationInfo") + " <a href='mailto:"+mailFrom+"'>"+mailFrom+"</a><br />");
			htmlMail.append("    </span></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("  <tr bordercolor='0' bgcolor='#CCCCCC'>");
			htmlMail.append("    <td width='100%'><div align='center' class='Stile1'><a href='http://"+mailText.getString("label.domainName")+"/'>" + mailText.getString("mail.copyright") + " 2010 "+mailText.getString("label.productName")+"</a>,</div></td>");
			htmlMail.append("  </tr>");
			htmlMail.append("</table>");
			htmlMail.append("</body>");
			htmlMail.append("</html>");
			email.setHtmlMsg(htmlMail.toString());

			// set the alternative message
			email.setTextMsg( "Salve gentile utente,\n di seguito pu� trovare i biglietti acquistati per accedere nei nostri parchi.\nLe ricordiamo di stampare e mostrare la presente email al botteghino di ingresso.");
			email.send();
		} catch (EmailException me) {
			throw new SystemException(me);
		} catch (MissingResourceException mre) {
			System.err.println("resources.mailconfig not found");
			throw new SystemException(mre);
		}

	}
	
	public static void sendSimplePurchasedTickets(User user, ArrayList<TicketAcquistato> listaTickets) throws SystemException {

		try {
			Locale lingua = Locale.getDefault();
			ResourceBundle mailText = ResourceBundle.getBundle("resources.MessageResources", lingua);
			// embed the image and get the content id
			// Create the email message
			HtmlEmail email = new HtmlEmail();
			email.setHostName(smtpServer);
			email.setAuthentication(smtpUserid, smtpPwd);
			email.setSSL(smtpSSL);
			email.setSmtpPort(Integer.parseInt(smtpPort));
			email.addTo(user.getEmail(), "Gentile utente");
			email.setFrom(mailFrom, "noreply");
			email.setSubject(mailText.getString("label.wwwDomainName")+" Biglietto digitale");

			StringBuffer htmlMail = new StringBuffer();
			htmlMail.append(mailText.getString("mail.cheers") +" Gentile utente,\n\n");
			htmlMail.append(mailText.getString("mail.buyTicketOK") + "\n\n"+ mailText.getString("mail.printEmail") +"\n\n\n");
			TicketAcquistato ticket=null;
			for(int i=0; i<listaTickets.size(); i++){
				ticket=listaTickets.get(i);
				htmlMail.append("["+(i+1)+"] DATA INGRESSO ["+ticket.getGiorno()+"/"+ticket.getMese()+"/"+ticket.getAnno() +"] TURNO ["+ticket.getTurno()+"]\n");
				htmlMail.append(".........DESCRIZIONE ["+ticket.getTicket().getDescrizione()+"] \n");
				htmlMail.append(".........IDENTIFICATIVO UNIVOCO ["+ticket.getCodiceVerifica()+"] \n");
				htmlMail.append("----------------------------------------------------------------------------------------------\n");
			}
			htmlMail.append(mailText.getString("mail.theStaff") + " "+mailText.getString("label.domainName")+"\n\n");
			htmlMail.append(mailText.getString("label.domainName")+" "+ mailText.getString("mail.sendToAddress") + " " + user.getEmail() + " " + mailText.getString("mail.accountEmail") + " "+mailText.getString("label.wwwDomainName")+" " + mailText.getString("mail.registrationInfo") + " "+mailFrom+" \n\n");
			htmlMail.append(mailText.getString("mail.copyright") + " "+mailText.getString("label.productName")+" \n");
			// set the html message
			email.setTextMsg(htmlMail.toString());

			email.send();
		} catch (EmailException me) {
			throw new SystemException(me);
		} catch (MissingResourceException mre) {
			System.err.println("resources.mailconfig not found");
			throw new SystemException(mre);
		}
	}
}