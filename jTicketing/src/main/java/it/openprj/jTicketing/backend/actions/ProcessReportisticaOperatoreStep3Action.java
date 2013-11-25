/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.blogic.model.entity.ReportisticaTickets;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class ProcessReportisticaOperatoreStep3Action extends DispatchAction {
	public ActionForward get(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		try {
			session.getAttribute("session").toString();
		} catch (Exception e) {
			session.setAttribute("session", "active");
			return mapping.findForward("home");
		}
		ArrayList<ReportisticaTickets> listaReportisticaTickets;
		String uid = (String) request.getParameter("uid");
		BackEndMgr service = ServicesFactory.getInstance().getBackEndMgrMgr();
		listaReportisticaTickets = (ArrayList<ReportisticaTickets>) service.reportisticaTickets(uid);
		session.setAttribute("listaReportisticaTickets", listaReportisticaTickets);
		
		return mapping.findForward("success");
	}
}
