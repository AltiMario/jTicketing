<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
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
	<logic:messagesPresent message="true" property="<%=Constants.MESSAGES_INFO%>">
		<div class="main_center">
			<html:messages id="infoMessage" property="<%=Constants.MESSAGES_INFO%>" message="true">
				<h3><img src="./images/success.png" align="top" />&nbsp;<bean:write name="infoMessage"/></h3>
			</html:messages>
		</div>
	</logic:messagesPresent>
</noscript>

<html:form action="/activateAccount.do" method="get">
	<div class="main_center">
		<h3><bean:message key="message.privateInformation" /></h3>
			<p>
				<b><bean:message key="label.ActivationCode" /></b><br />
				<input type="text" name="activationCode" />
	     		<bean:message key="error.requiredFieldActivationCode" />
			</p><br/>
	     	<p align="center">
	     		<html:submit accesskey="A"  titleKey="button.active" altKey="button.active" styleClass="submit" ><bean:message key="button.active" /></html:submit>
	     	</p>
	</div>
	<div class="main_center">
		<h3><bean:message key="message.privacyProtection" /></h3>
		<div class="right_articles">
			<p>
				<bean:message key="message.privacyProtectionStep2" />
			</p>
		</div>
	</div>
</html:form>
<div>&nbsp;</div>