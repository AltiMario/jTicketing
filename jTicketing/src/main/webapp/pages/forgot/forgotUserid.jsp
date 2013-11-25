<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/jcaptcha.tld" prefix="jcaptcha"%>
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
<logic:messagesPresent message="true" property="<%=Constants.MESSAGES_WARNING%>">
	<div class="main_center">
		<html:messages id="warningMessage" property="<%=Constants.MESSAGES_WARNING%>" message="true">
			<h3><img src="./images/error.png" align="top" />&nbsp;<bean:write name="warningMessage"/></h3>
		</html:messages>
	</div>
</logic:messagesPresent>
</noscript>

<html:form action="/recoveryUserid.do" method="post">
	<div class="main_center">
		<h3><bean:message key="label.IdRecovery" /></h3>
		<br/>
		<p align="left">
			&nbsp;<b><bean:message key="label.EmailAddress" /></b><br/>
			&nbsp;<input type="text" name="email" />
	     	<bean:message key="error.requiredFieldEmail" />
	    </p><br/>
<%--		<p align="left">
			&nbsp;<b><bean:message key="label.EmailAddressRepeat" /></b><br/>
			&nbsp;<input type="text" name="emailreply" />
	    	<bean:message key="error.requiredFieldEmail" />
		</p><br/>--%>
		<br/>
	    <jsp:include page="/pages/captcha/captchabox.jsp" flush="true"></jsp:include>
	    <br/>
	    <p align="center"><html:submit  alt="Invia" styleClass="submit" ><bean:message key="button.Send" /></html:submit></p>
	</div>
	<div class="main_center">
		<h3><bean:message key="message.helpRetriveInformation" /></h3>
		<div class="right_articles">
			<p>
				<bean:message key="message.insertEmailAddress" />
			</p>
		</div>
	</div>
</html:form>
<div>&nbsp;</div>