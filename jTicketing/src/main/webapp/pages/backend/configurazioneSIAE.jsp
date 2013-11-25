<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page language="java" import="java.util.*,java.text.*, it.openprj.jTicketing.blogic.model.entity.*"%>

<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<logic:iterate id="objElemento" name="dettaglioConfigurazioneSIAE" indexId="indiceElemento" scope="session">
    <html:hidden property="dispatch" value="edit"/>
    <html:hidden name="objElemento" property="uid" />

<div class="main_center">
    <h3><bean:message key="label.configSIAE"/></h3>
	<div class="left_box2">
	<table width="100%" border="0">
		<tr>
	    	<td align="left">
	    		<bean:message key="label.SSystemCode"/>:
	    	</td>
	    	<td align="left">
                <bean:write name="objElemento" property="SCodiceSistema" />

	    	</td>
	  	</tr>
        <tr>
	    	<td align="left">
	    		<bean:message key="label.STCOrganizer"/>:
	    	</td>
	    	<td align="left">
	    		<bean:write name="objElemento" property="SIDCFTitolare" />
	    	</td>
	  	</tr>
        <tr>
	    	<td align="left">
	    		<bean:message key="label.SEmissionSystem"/>:
	    	</td>
	    	<td align="left">
	    		<bean:write name="objElemento" property="SSistemaEmissione" />
	    	</td>
	  	</tr>
        <tr>
	    	<td align="left">
	    		<bean:message key="label.SSmartCard" />:
	    	</td>
	    	<td align="left">
	    		<bean:write name="objElemento" property="SCartaAttivazione" />
	    	</td>
	  	</tr>

	  	<tr>
           <td >
            &nbsp;
	    	</td>
              <td >
            &nbsp;
	    	</td>
	  		<td align="left">

               <form name="newLuogoInteresse"  action="./processConfigurazioneSIAEStep1.do?dispatch=get&uid=<bean:write name="objElemento" property="uid" />">
                    <input type="hidden" name="dispatch" value="get"/>
                    <input type="hidden" name="uid" value=1/>
                    <div align="center">
                        <input type="submit" src="" name="Modify" class="submit" value="<bean:message key="button.change"/>" title="<bean:message key="button.change"/>" />
                    </div>
                    </form>
	  		</td>
	  	</tr>
	</table>
	</div>
</div>
    </logic:iterate>


<div>&nbsp;</div>