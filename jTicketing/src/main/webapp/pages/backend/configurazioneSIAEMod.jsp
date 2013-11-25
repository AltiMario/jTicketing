<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<%--
  Created by IntelliJ IDEA.
  User: AltiMario
  Date: 27/09/11
  Time: 14.38
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page language="java" import="java.util.*,java.text.*, it.openprj.jTicketing.blogic.model.entity.*"%>

<html:form action="/processConfigurazioneSIAENew.do" method="post" enctype="multipart/form-data">
<bean:define id="objElemento" name="dettaglioConfigurazioneSIAE"
	scope="session"
	type="it.openprj.jTicketing.blogic.model.entity.ConfigurazioneSIAE">
</bean:define>

    <html:hidden property="dispatch" value="edit"/>
    <html:hidden name="objElemento" property="uid" />
    <div class="main_center">

    <h3><bean:message key="label.configSIAE"/></h3>
	<div class="left_box2">
	<table width="100%" border="0">
		<tr>
	    	<td align="left">
	    		<bean:message key="label.SSystemCode"/>
	    	</td>
	    	<td align="left">

                <html:text name="objElemento" property="SCodiceSistema" />
	    	</td>
	  	</tr>
        <tr>
	    	<td align="left">
	    		<bean:message key="label.STCOrganizer"/>
	    	</td>
	    	<td align="left">
	    		<html:text name="objElemento" property="SIDCFTitolare" />
	    	</td>
	  	</tr>
        <tr>
	    	<td align="left">
	    		<bean:message key="label.SEmissionSystem"/>
	    	</td>
	    	<td align="left">
	    		<html:text name="objElemento" property="SSistemaEmissione" />
	    	</td>
	  	</tr>
        <tr>
	    	<td align="left">
	    		<bean:message key="label.SSmartCard" />
	    	</td>
	    	<td align="left">
	    		<html:text name="objElemento" property="SCartaAttivazione" />
	    	</td>
	  	</tr>

	  	<tr>
	  		<td align="center" colspan="2">
	  			<html:submit property="btnRimuovi"  styleClass="submit" ><bean:message key="button.Delete" /></html:submit>
	  			<html:submit property="isChange"  styleClass="submit" ><bean:message key="button.Save" /></html:submit>
	  		</td>
	  	</tr>
	</table>
	</div>
</div>
  <%--</logic:iterate>--%>
</html:form>
<div>&nbsp;</div>