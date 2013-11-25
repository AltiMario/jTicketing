/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.backend.actions;

import it.openprj.jTicketing.backend.forms.ConfigSIAEForm;
import it.openprj.jTicketing.blogic.model.entity.ConfigurazioneSIAE;
import it.openprj.jTicketing.blogic.model.entity.User;
import it.openprj.jTicketing.blogic.services.factory.ServicesFactory;
import it.openprj.jTicketing.blogic.services.manager.DataTablesMgr;
import it.openprj.jTicketing.core.common.Constants;
import org.apache.struts.action.*;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: AltiMario
 * Date: 29/09/11
 * Time: 10.50
 * To change this template use File | Settings | File Templates.
 */
public class ProcessConfigurazioneSIAEStep2Action  extends DispatchAction {
    public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
            HttpSession session = request.getSession();
            ActionMessages messages = new ActionMessages();
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


            ConfigurazioneSIAE configurazioneSIAE = new ConfigurazioneSIAE ();

            configurazioneSIAE.setSCodiceSistema((String) request.getParameter("SCodiceSistema"));
            configurazioneSIAE.setSIDCFTitolare((String) request.getParameter("SIDCFTitolare"));
            configurazioneSIAE.setSSistemaEmissione((String) request.getParameter("SSistemaEmissione"));
            configurazioneSIAE.setSCartaAttivazione((String) request.getParameter("SCartaAttivazione"));

            ConfigSIAEForm myForm = (ConfigSIAEForm) form;

            if (myForm.getBtnAnnulla() != null && myForm.getBtnAnnulla().equalsIgnoreCase("Cancel")) {
                return mapping.findForward("success");

            }

            if (configurazioneSIAE.getSCodiceSistema().length() == 0) {
                messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sSystemCodeIsRequired"));
                saveMessages(session, messages);

            }

            if (configurazioneSIAE.getSIDCFTitolare().length() == 0) {
                messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sTCOrganizerIsRequired"));
                saveMessages(session, messages);
            }

            if (configurazioneSIAE.getSSistemaEmissione().length() == 0) {
                messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sEmissionSystemIsRequired"));
                saveMessages(session, messages);
            }

            if (configurazioneSIAE.getSCartaAttivazione().length() == 0) {
                messages.add(Constants.MESSAGES_ERROR, new ActionMessage("error.sSmartCardIsRequired"));
                saveMessages(session, messages);
            }

            if (messages.size() > 0) {
                session.setAttribute("dettaglioConfigurazioneSIAE", configurazioneSIAE);
                return mapping.findForward("fail");
            }

            DataTablesMgr dataTablesMgr = ServicesFactory.getInstance().getDataTablesMgr();

            dataTablesMgr.updateConfigurazioneSIAE (configurazioneSIAE);

            ArrayList<ConfigurazioneSIAE> listaConfigurazioneSIAE = null;
            DataTablesMgr service = ServicesFactory.getInstance().getDataTablesMgr();
            listaConfigurazioneSIAE = (ArrayList<ConfigurazioneSIAE>) service.selectConf();
            session.setAttribute("dettaglioConfigurazioneSIAE", listaConfigurazioneSIAE);

            return mapping.findForward("success");
        }

}
