/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.backend.forms.ConfigSIAEForm;
import it.openprj.jTicketing.blogic.exceptions.SystemException;
import it.openprj.jTicketing.blogic.model.entity.ConfigurazioneSIAE;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.BackEndMgr;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.core.common.Constants;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class ProcessConfigurazioneSIAEAction extends DispatchAction {
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

                ArrayList<ConfigurazioneSIAE> dettaglioConfigurazioneSIAE = null;
                DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
                    dettaglioConfigurazioneSIAE = (ArrayList<ConfigurazioneSIAE>) service.selectConf();
                    session.setAttribute("dettaglioConfigurazioneSIAE", dettaglioConfigurazioneSIAE);
                    System.out.println("lunghezza dettaglioConfigurazioneSIAE" + dettaglioConfigurazioneSIAE.size());
                    if   (dettaglioConfigurazioneSIAE.size() == 0){
                                    ConfigurazioneSIAE configurazioneSIAE = new ConfigurazioneSIAE ();

                                    configurazioneSIAE.setSCodiceSistema((String) request.getParameter("SCodiceSistema"));
                                    configurazioneSIAE.setSIDCFTitolare((String) request.getParameter("SIDCFTitolare"));
                                    configurazioneSIAE.setSSistemaEmissione((String) request.getParameter("SSistemaEmissione"));
                                    configurazioneSIAE.setSCartaAttivazione((String) request.getParameter("SCartaAttivazione"));

                            session.setAttribute("dettaglioConfigurazioneSIAE", configurazioneSIAE);
                            return mapping.findForward("fail");
            }



//                try {
//                    DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
//                    dettaglioConfigurazioneSIAE = (ArrayList<ConfigurazioneSIAE>) service.selectConf();
//                    session.setAttribute("dettaglioConfigurazioneSIAE", dettaglioConfigurazioneSIAE);
//                    System.out.println("lunghezza dettaglioConfigurazioneSIAE" + dettaglioConfigurazioneSIAE.size());
//                    if   (dettaglioConfigurazioneSIAE.size() == 0){
//
//
//                        String action_redirect= "it.openprj.jTicketing.backend.actions.ProcessConfigurazioneSIAENewAction";
//	                    response.sendRedirect(action_redirect);
//                        System.out.println("ci sono ");
//
//                    }
//
//                } catch (SystemException se) {
//                    return mapping.findForward("fail");
//                }

                return mapping.findForward("success");
            }
    }

