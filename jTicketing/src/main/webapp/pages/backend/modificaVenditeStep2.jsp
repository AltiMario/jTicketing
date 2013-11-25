<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<logic:iterate id="objElemento" name="listaTickets" indexId="indiceElemento" scope="session">
<div class="main_center">
	<h3><a href="./processModificaVenditeStep3.do?dispatch=edit&uidLuogoInteresse=<bean:write name="objElemento" property="uid_luoghi_interesse" />&uidTicket=<bean:write name="objElemento" property="uid" />"><bean:write name="objElemento" property="titolo" /></a></h3>
	<div class="left_box2">
	<table width="100%" border="0">
		<tr>
		 	<td align="left">
		 		<img src="./ImageProcessor?maxWidth=100&maxHeight=70&type=2&uid=<bean:write name="objElemento" property="uid" />" />
 				<bean:write name="objElemento" property="descrizione" />
			</td>
		</tr>
	</table>
	</div>
</div>
</logic:iterate>
<div>&nbsp;</div>