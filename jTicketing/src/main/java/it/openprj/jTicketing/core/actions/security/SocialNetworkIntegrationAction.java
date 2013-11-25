/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.blogic.exceptions.EmailNotFoundException;
import it.openprj.jTicketing.blogic.model.entity.Role;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.SecurityMgr;
import it.openprj.jTicketing.blogic.utilities.encrypt.DataEncrypt;
import it.openprj.jTicketing.core.common.Constants;
import it.openprj.jTicketing.core.mailer.Mailer;

import java.util.Date;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public final class SocialNetworkIntegrationAction extends Action {
	private Logger log = Logger.getLogger(SocialNetworkIntegrationAction.class);
	private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResourceBundle configProperties = ResourceBundle.getBundle("resources.facebook");
		String logMsg = "SocialNetworkController:getAccessCode";
		String accessToken = request.getParameter("accessToken");
		HttpSession session = request.getSession();
		
		try{
			String apiKey =  configProperties.getString("facebook-api-key");
			String apiSecret = configProperties.getString("facebook-api-secret");
			if (accessToken != null) {
				String callBackURL = getCallBackURL(request);
				StringBuilder sBuilder = new StringBuilder(callBackURL);
				OAuthService service = new ServiceBuilder()
						.provider(FacebookApi.class).apiKey(apiKey)
						.apiSecret(apiSecret).callback(sBuilder.toString())
						.build();
				Token EMPTY_TOKEN = null;
				Verifier verifier = new Verifier(accessToken);
				Token aToken = new Token(accessToken, apiSecret);
				OAuthRequest oAuthRequest = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
				service.signRequest(aToken, oAuthRequest);
			    Response oAuthResponse = oAuthRequest.send();
			    String responseJSON = oAuthResponse.getBody();
			    
			    //FIXME: Insert Facebook return details in database if those are not exst.
			    JSONObject jsonObject = (JSONObject)  new JSONParser().parse(responseJSON);
			    String id = (String)jsonObject.get("id");
			    String name = (String)jsonObject.get("name");
			    String firstName = (String)jsonObject.get("first_name");
			    String lastName = (String)jsonObject.get("last_name");
			    String link = (String)jsonObject.get("link");
			    String userName = (String)jsonObject.get("username");
			    String gender = (String)jsonObject.get("gender");
			    Double timezone = (Double)jsonObject.get("timezone");
			    String locale = (String)jsonObject.get("locale");
			    boolean verified = (Boolean)jsonObject.get("verified");
			    String updatedTime = (String)jsonObject.get("updated_time");
			    String emailId = (String)jsonObject.get("email");
			    
			    
				log.debug("id" + id);
				log.debug("name" + name);
				log.debug("firstName" + firstName);
				log.debug("lastName" + lastName);
				log.debug("link" + link);
				log.debug("userName" + userName);
				log.debug("gender" + gender);
				log.debug("timezone" + timezone);
				log.debug("locale" + locale);
				log.debug("verified" + verified);
				log.debug("updatedTime" + updatedTime);
				log.debug("emailId" + emailId);
			    
				SecurityMgr securityMgr = ServicesFactory.getInstance().getSecurityMgr();
			    User user = null;
			    try {
			    	user = securityMgr.getUserByEmail(emailId);
				} catch (EmailNotFoundException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (user == null) {
					user = securityMgr.newUser();
					user.setUserName(userName);
					user.setUserPass(DataEncrypt.getInstance().encrypt(generatePassword(6))); 
		            //user.setBirthdate(DataConverter.sqlDateConverter(registrationForm.getBirthdate()));
		            //user.setCf(registrationForm.getCf());
		            //user.setIdbirthplace(registrationForm.getIdbirthplace());
					user.setEmail(emailId);
					user.setFirstName(firstName);
					user.setLastName(lastName);
					user.setActivated(true);
					user.getRoles().put("Acquirente", new Role("Acquirente"));
					securityMgr.registerUser(user);
					//send mail
					String server = request.getServerName()+":"+request.getServerPort()+request.getContextPath();
					Mailer.sendPassword(user,server);
			    }
			    session.setAttribute("user", user);
				session.setAttribute("roles", user.getRoles());
			}else{ //Error Case
				String error = request.getParameter("error");
				String errorReason = null;
				String errorDescription = null;
				if (error != null && !error.equals("")) {
					errorReason = request.getParameter("error_reason");
					errorDescription = request.getParameter("error_description");
				}
				/*ActionMessages messages = new ActionMessages(); 
				messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.wrongUserIdOrPassword"));
				saveMessages(request, messages);*/
			    return mapping.findForward("fail");
			}	
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			
		}
		ActionMessages messages = new ActionMessages();
		messages.add(Constants.MESSAGES_INFO, new ActionMessage("message.AccountActivationOk"));
		saveMessages(request, messages);
		return mapping.findForward("success");
	}
	
	//-----------------HELPERS--------------------------------
	private String getCallBackURL(HttpServletRequest request){
		StringBuilder sBuilder = new StringBuilder();
		String requestURL = request.getRequestURL().toString();
		String[] url = requestURL.split("fbcallback");
		sBuilder.append(url[0]);
		return sBuilder.toString();
	}
	private static String generatePassword(int length){ 
		Random random = new Random((new Date()).getTime());
		char[] values = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
				'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7',
				'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
				'W', 'X', 'Y', 'Z' };

		String genPassword = "";

		for (int i = 0; i < length; i++) {
			int index = random.nextInt(values.length);
			genPassword += values[index];
		}
		return genPassword;
	}
}
