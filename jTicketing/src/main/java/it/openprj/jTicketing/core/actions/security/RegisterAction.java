/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;


import it.openprj.jTicketing.blogic.exceptions.*;
import it.openprj.jTicketing.blogic.model.entity.Birthplace;
import it.openprj.jTicketing.blogic.model.entity.Role;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.blogic.utilities.encrypt.DataEncrypt;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.core.forms.security.RegistrationForm;
import it.openprj.jTicketing.core.mailer.Mailer;
import it.openprj.jTicketing.blogic.utilities.DataConverter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;


import org.apache.commons.mail.EmailException;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import java.util.ArrayList;

public final class RegisterAction extends Action {

    /*static String ControllaCF(String cf) {
	    int i, s, c;
	    String cf2;
	    int setdisp[] = {1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20,
		11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	    if( cf.length() == 0 ) return "";
	    if( cf.length() != 16 )
		    return "La lunghezza del codice fiscale non &egrave;\n"
		    + "corretta: il codice fiscale dovrebbe essere lungo\n"
		    + "esattamente 16 caratteri.";
	    cf2 = cf.toUpperCase();
	    for( i=0; i<16; i++ ){
		    c = cf2.charAt(i);
		    if( ! ( c>='0' && c<='9' || c>='A' && c<='Z' ) )
			    return "Il codice fiscale contiene dei caratteri non validi:\n"
			    + "i soli caratteri validi sono le lettere e le cifre.";
	    }
	    s = 0;
	    for( i=1; i<=13; i+=2 ){
		    c = cf2.charAt(i);
		    if( c>='0' && c<='9' )
			    s = s + c - '0';
		    else
			    s = s + c - 'A';
	    }
	    for( i=0; i<=14; i+=2 ){
		    c = cf2.charAt(i);
		    if( c>='0' && c<='9' )	 c = c - '0' + 'A';
		    s = s + setdisp[c - 'A'];
	    }
	    if( s%26 + 'A' != cf2.charAt(15) )
		    return "Il codice fiscale non &egrave; corretto:\n"
		    + "il codice di controllo non corrisponde.";
	    return "";
}     */
    public static boolean check(String regex, String input){
  if (Pattern.matches(regex, input))
    return true;
  else
    return false;
}
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
        SecurityMgr service = null;
		User user = null;
        RegistrationForm registrationForm = (RegistrationForm) form;
		try {
			service = ServicesFactory.getInstance().getSecurityMgr();
			user = service.newUser();

			user.setUserName(registrationForm.getUserid());
			user.setUserPass(DataEncrypt.getInstance().encrypt(registrationForm.getPassword()));
            user.setBirthdate(DataConverter.sqlDateConverter(registrationForm.getBirthdate()));
            user.setCf(registrationForm.getCf());
            user.setIdbirthplace(registrationForm.getIdbirthplace());
			user.setEmail(registrationForm.getEmail());
			user.setFirstName(registrationForm.getFirstname());
			user.setLastName(registrationForm.getLastname());
			user.getRoles().put("Acquirente", new Role("Acquirente"));

            //Todo: inserire il controllo di validita' formale della mail

			if(user.getFirstName().length() == 0 || user.getLastName().length() == 0 || user.getEmail().length() == 0  || user.getUserName().length() == 0) {
				ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.fillInRequiredFields"));
				saveMessages(request, messages);
				return mapping.findForward("fail");
			}
            String esprRegEmail="[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
            //TODO: verify the correct regex codife fiscale
            String esprRegCf="[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]";

            String esprUser="[à,é,è,ì,ò,ù,À,Ò,È,É,Ì,Ù,a-zA-Z0-9.][^()&<>@]{6,30}";
            String esprNameSurname="[à,é,è,ì,ò,ù,À,Ò,È,É,Ì,Ù,a-zA-Z']{0,50}";

            boolean resultUser = check(esprUser,user.getUserName());
            if(resultUser != true){
                ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.wrongUserId"));
				saveMessages(request, messages);
				return mapping.findForward("fail");

            }
            boolean resultEmail = check(esprRegEmail,user.getEmail());
            if(resultEmail != true){
                ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.wrongEmail"));
				saveMessages(request, messages);
				return mapping.findForward("fail");

            }

            if(registrationForm.getPassword().length() < 6){
                ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.wrongPasswordLenght"));
				saveMessages(request, messages);
				return mapping.findForward("fail");

            }
            if(!user.getUserPass().trim().equalsIgnoreCase(DataEncrypt.getInstance().encrypt(registrationForm.getPasswordreply()).trim())){
                ActionMessages messages = new ActionMessages();
                messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.passwordsAreDifferent"));
                saveMessages(request, messages);
                return mapping.findForward("fail");
            }

             boolean resultName = check(esprNameSurname,user.getFirstName());
            if(resultName != true){
                ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.wrongName"));
				saveMessages(request, messages);
				return mapping.findForward("fail");

            }
             boolean resultSurname = check(esprNameSurname,user.getLastName());
            if(resultSurname != true){
                ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.wrongSurname"));
				saveMessages(request, messages);
				return mapping.findForward("fail");

            }
           /*  boolean resultCf = check(esprRegCf,user.getCf());
            if(resultCf != true){
                ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.wrongCf"));
				saveMessages(request, messages);
				return mapping.findForward("fail");

            }
             */

// TODO: momentaneamente controllo inattivo a causa della questione dell'internazionalizzazione


			if (registrationForm.getCondition_1() == null) {
				ActionMessages messages = new ActionMessages();
				messages.add(Constants.MESSAGES_WARNING, new ActionMessage("error.acceptConditionUse"));
				saveMessages(request, messages);
				return mapping.findForward("fail");
			}
			service.registerUser(user);
			Mailer.sendActivationCode(user);

        } catch (EmailException em) {
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.mailNotSent"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
        } catch (UserNotFoundException em) {
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.userExist"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
        } catch (AccountNotActivatedException em) {
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.userExist"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		} catch (SystemException se) {
			service.deleteUser(user);
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.systemFailure"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		} catch (EmailAlreadyExistException eaee){
			ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.emailArchivied"));
			saveMessages(request, messages);
			return mapping.findForward("fail");
		}

		ActionMessages messages = new ActionMessages();
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.registrationOk"));
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.accountInformation"));
		saveMessages(request, messages);
		request.getSession().removeAttribute("registrationForm");
		return mapping.findForward("success");
	}
}
