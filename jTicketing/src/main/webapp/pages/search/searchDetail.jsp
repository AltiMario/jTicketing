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

<bean:define id="objElemento" name="dettaglioLuogoInteresse"
	scope="session"
	type="it.openprj.jTicketing.blogic.model.entity.LuogoInteresse">
</bean:define>

<div class="main_center">
<h3><bean:write name="objElemento" property="titolo" /></h3>
	<div class="left_box2">
	<table width="100%" border="0">
		<tr>
			<logic:notEqual name="objElemento" property="immagine" value="No">
				<td align="left">
					<img src="./ImageProcessor?maxWidth=300&maxHeight=300&type=1&uid=<bean:write name="objElemento" property="uid" />" />
				</td>
			</logic:notEqual>
			<td align="justify" valign="top">
				<bean:write name="objElemento" property="descrizione" />
			</td>
		</tr>
	</table>
	</div>
</div>
<div class="main_center">
<h3><bean:message key="label.availableTicketsType" /></h3>
<table border="0" cellpadding="1" cellspacing="1" width="98%"
	align="center">
	<logic:iterate id="objTicket" name="listaTickets"
		indexId="indiceElemento" scope="session">
		<tr>
			<td class="calendarHourMinuteBox"><bean:write name="objTicket" property="titolo" /></td>
		</tr>
		<tr>
			<td class="turnBox">
			 <div class="ticketImage">
 				 <img src="./ImageProcessor?maxWidth=100&maxHeight=70&type=2&uid=<bean:write name="objTicket" property="uid" />" />
 			</div>
			<p align="left"> &nbsp; &nbsp; <bean:write name="objTicket" property="descrizione" /></p><br />
			<p align="right">
                <input type="button" class="submit" value="<bean:message key="button.Select" />" onclick="location.href='./processTicketSearch.do?uid=<bean:write name="objElemento" property="uid" />&uidTicket=<bean:write name="objTicket" property="uid" />'">
			</p>
			</td>
		</tr>
	</logic:iterate>
</table>
</div>
<div>&nbsp;</div>





