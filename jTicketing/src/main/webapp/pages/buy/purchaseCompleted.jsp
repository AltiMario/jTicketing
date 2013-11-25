<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<bean:define id="user" name="user" scope="session" type="it.openprj.jTicketing.blogic.model.entity.User" />
<div class="main_center" align="center">
	<p>&nbsp;<img src="./images/success.png" align="top"/>&nbsp;<bean:message key="message.paymentOK" /></p><br/>
	<p>&nbsp;<img src="./images/success.png" align="top"/>&nbsp;<bean:message key="message.sendBuyConfirm" /><b><bean:write name="user" property="email"/></b></p><br/>
	<p>&nbsp;<img src="./images/info.png" align="top"/>&nbsp;<bean:message key="message.printBuyConfirm" /></p>
</div>
<div>&nbsp;</div>
<%
	session.removeAttribute("listaTickets");
	session.removeAttribute("purchasedticketGrouped");
	session.removeAttribute("listForRenderTicketPurchased");
%>
