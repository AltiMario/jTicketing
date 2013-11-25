/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import java.util.ArrayList;

import it.openprj.jTicketing.backend.forms.ConfigSIAEForm;
import it.openprj.jTicketing.blogic.model.entity.ConfigurazioneSIAE;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.core.common.Constants;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;


public class ProcessConfigurazioneSIAEStep1Action extends DispatchAction {
    public ActionForward get (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}

		User user = (User) session.getAttribute("user");
		if (user == null || user.isAdministrator() == false) {
			return mapping.findForward("home");
		}

		@SuppressWarnings("unchecked")
		ArrayList<ConfigurazioneSIAE> listaConfigurazioneSIAE = (ArrayList<ConfigurazioneSIAE>) session.getAttribute ("dettaglioConfigurazioneSIAE");
		ConfigurazioneSIAE dettaglioConfigurazioneSIAE = null;
		String uidConfigurazioneSIAE = (String) request.getParameter("uid");

		for(int i=0; i<listaConfigurazioneSIAE.size(); i++) {
			dettaglioConfigurazioneSIAE =listaConfigurazioneSIAE.get(i);
			if (dettaglioConfigurazioneSIAE.getUid().equalsIgnoreCase(uidConfigurazioneSIAE)) {
				break;
			}
		}
		session.setAttribute("dettaglioConfigurazioneSIAE", dettaglioConfigurazioneSIAE);
        System.out.println(mapping.findForward("success"));
        return mapping.findForward("success");
    }
}





