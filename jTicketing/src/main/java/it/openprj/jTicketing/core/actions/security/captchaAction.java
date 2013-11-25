/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.core.actions.security;

import it.openprj.jTicketing.core.common.Constants;
import com.octo.captcha.module.config.CaptchaModuleConfigHelper;
import com.octo.captcha.module.struts.CaptchaServicePlugin;
import com.octo.captcha.module.struts.VerifyCaptchaChallengeAction;
import com.octo.captcha.service.CaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class captchaAction extends Action {

    private Log log = LogFactory.getLog(VerifyCaptchaChallengeAction.class);

    public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm,
                                 HttpServletRequest httpServletRequest,
                                 HttpServletResponse httpServletResponse)
            throws Exception {

    	String captcha = httpServletRequest.getParameter("captcha");
    	
    	if (captcha != null && captcha.equals("renew")) {
    		return actionMapping.findForward("failure") != null ? actionMapping.findForward("failure") :
                actionMapping.getInputForward();
        }
    	
        log.debug("enter captcha challenge verification");

        CaptchaService service = CaptchaServicePlugin.getInstance().getService();

        String responseKey = CaptchaServicePlugin.getInstance().getResponseKey();

        String captchaID;

        captchaID = CaptchaModuleConfigHelper.getId(httpServletRequest);

        // get challenge response from the request
        String challengeResponse =
                httpServletRequest.getParameter(responseKey);

        if (log.isDebugEnabled()) log.debug("response for id " + captchaID
                + " : " + challengeResponse);

        //cleanning the request
        httpServletRequest.removeAttribute(responseKey);

        Boolean isResponseCorrect = Boolean.FALSE;

        if (challengeResponse != null) {

            // Call the Service method
            try {
                isResponseCorrect = service.validateResponseForID(captchaID,
                        challengeResponse);
            } catch (CaptchaServiceException e) {

                log.debug("Error during challenge verification", e);
                // so the user will be redirected to the error page
                httpServletRequest.setAttribute(CaptchaServicePlugin.getInstance().getMessageKey(),
                        CaptchaModuleConfigHelper.getMessage(httpServletRequest));

                log.debug("forward to error with message : "
                        + CaptchaModuleConfigHelper.getMessage(httpServletRequest));

                return actionMapping.findForward("failure") != null ? actionMapping.findForward("failure") :
                    actionMapping.getInputForward();
            }
        }
        // forward user to the success URL or redirect it to the error URL
        if (isResponseCorrect.booleanValue()) {
            // clean the request and call the next action
            // (forward success)
            log.debug("correct : forward to success");
            return actionMapping.findForward("success");
        } else {
            if (log.isDebugEnabled()) {
                log.debug("false  : forward to failure with message : "
                        + CaptchaModuleConfigHelper.getMessage(httpServletRequest));
                log.debug("in request attribute key : "
                        + CaptchaServicePlugin.getInstance().getMessageKey());
            }
            // If the challenge response is not specified, forward failure
            httpServletRequest.setAttribute(CaptchaServicePlugin.getInstance().getMessageKey(),
                    CaptchaModuleConfigHelper.getMessage(httpServletRequest));
            
            ActionMessages messages = new ActionMessages();
			messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.wrongCheckCode"));
			saveMessages(httpServletRequest, messages);
            
            return actionMapping.findForward("failure") != null ? actionMapping.findForward("failure") :
                    actionMapping.getInputForward();
        }
    }
}