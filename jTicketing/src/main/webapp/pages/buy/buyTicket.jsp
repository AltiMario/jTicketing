<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="it.openprj.jTicketing.core.common.Constants" %>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<noscript>
	<logic:messagesPresent message="true" property="<%=Constants.MESSAGES_ERROR%>">
		<div class="main_center">
			<html:messages id="errorMessage" property="<%=Constants.MESSAGES_ERROR%>" message="true">
				<h3><img src="./images/error.png" align="top" />&nbsp;<bean:write name="errorMessage"/></h3>
			</html:messages>
		</div>
	</logic:messagesPresent>
</noscript>
<html:form method="post" action="/processTicketBuy.do">
<bean:define id="objTicket" name="ticket" scope="session" type="it.openprj.jTicketing.blogic.model.entity.Ticket"/>
<div class="ticketContainer">
	<div class="ticketImage">
		<img src="./ImageProcessor?maxWidth=100&maxHeight=70&type=2&uid=<bean:write name="objTicket" property="uid" />" />
 	</div>
	<div class="ticketDescription">
  		<h3><bean:write name="objTicket" property="titolo" /></h3>
  		<bean:write name="objTicket" property="descrizione" />
	</div>
  	<div class="ticketPrice">
  		<h3><bean:message key="label.prices" /></h3>
  		<logic:iterate id="objPrezzoTicket" name="objTicket" property="prezziCategorie" indexId="indiceElemento">
    		<p><bean:write name="objPrezzoTicket" property="descrizione" /> : <b><bean:write name="objPrezzoTicket" property="prezzo" /> EUR</b></p>
  		</logic:iterate>
  	</div>
</div>
<div class="main_center">
    <h3><bean:message key="message.AddCart"/> <bean:write name="objTicket" property="titolo"/></h3><br/>
	<p><bean:message key="label.category"/>
	<html:select property="categoria"> 
		<logic:iterate id="objPrezzoTicket" name="objTicket" property="prezziCategorie" indexId="indiceElemento">
			<option value="<bean:write name="objPrezzoTicket" property="uid" />"><bean:write name="objPrezzoTicket" property="descrizione" /> (<bean:write name="objPrezzoTicket" property="prezzo" /> EUR)</option>
      	</logic:iterate>
	</html:select>
	<bean:message key="label.number"/>
	<html:text property="quantita" titleKey="label.euroPrice" altKey="label.euroPrice" styleClass="jsrequired {errorLocation:'none'}" />
	<html:submit accesskey="V"  titleKey="button.Go" altKey="button.Go"	styleClass="submit"  property="btn" ><bean:message key="button.add" /></html:submit></p>
</div>
<div class="main_center">
	<h3><bean:message key="message.help"/></h3>
	<div class="right_articles">
		<p><img src="./images/info.png" align="top"/> <bean:message key="message.addDifferentTicketsType"/></p>
		<p><img src="./images/success.png" align="top"/> <bean:message key="message.insertEmailAndPushBuy"/></p><br/>
		<div align="center">
			<html:hidden property="step" value="2" />
			<table width="100%" border="0">
	  			<tr align="center">
	    			<td colspan="2"><html:submit accesskey="C"  titleKey="button.Go"	altKey="button.Go" styleClass="submit_2"  property="btn" ><bean:message key="button.ChooseTickets" /></html:submit></td>
	  				<td colspan="2"><html:submit accesskey="A"  titleKey="button.Go"	altKey="button.Go" styleClass="submit_2"  property="btn" ><bean:message key="button.Cart" /></html:submit></td>
	  			</tr>
			</table>
		</div>
	</div>
</div>
</html:form>
<div>&nbsp;</div>
	     
         


