<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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

<html:form action="/register.do" method="post" >
<bean:define id="objComune" name="registrationForm" type="it.openprj.jTicketing.core.forms.security.RegistrationForm"/>
	<div class="main_center">

		<br/>
		<h3><bean:message key="message.chooseUserIdAndPassword" /></h3>
		<br/>
		<p align="left">
			&nbsp;<b><bean:message key="label.UserID" /></b><br/>
			&nbsp;<html:text property="userid"></html:text>
			<bean:message key="message.useLettersOrNumbers" />
		</p><br/>
        <p align="left">
			&nbsp;<b><bean:message key="label.EmailAddress" /></b><br/>
			&nbsp;<html:text property="email" ></html:text>
			<bean:message key="error.requiredFieldEmail" />
		</p><br/>
		<p align="left">
			&nbsp;<b><bean:message key="label.Password" /></b><br/>
			&nbsp;<input type="password" name="password" id="password" />
			<bean:message key="error.requiredField" />
		</p><br/>
		<p align="left">
			&nbsp;<b><bean:message key="label.PasswordRepeat" /></b><br/>
			&nbsp;<input type="password" name="passwordreply" id="passwordreply" />
			<bean:message key="message.passwordCharacters" />
		</p><br/>

        <h3><bean:message key="message.privateInformation" /></h3>
        <p align="left">
			&nbsp;<b><bean:message key="label.Name" /></b><br/>
			&nbsp;<html:text property="firstname" ></html:text>
			<bean:message key="error.requiredFieldNumbersLetters" />
		</p><br/>
		<p align="left">
			&nbsp;<b><bean:message key="label.Surname" /></b><br/>
			&nbsp;<html:text property="lastname" ></html:text>
			<bean:message key="error.requiredFieldNumbersLetters" />
		</p><br/>
        <p align="left">
		    &nbsp;<b><bean:message key="label.Birthplace" /></b><br/>
		    <html:select property="idbirthplace">
		    <logic:iterate id="birthplacelistId" name="objComune" property="birthplacelist">
                <option value="<bean:write name="birthplacelistId" property="idbirthplace" />">
                    <bean:write name="birthplacelistId" property="name" />
                </option>
      	    </logic:iterate>
       	    </html:select>
        <bean:message key="error.requiredField1" />
		</p><br/>
        <p align="left">
			&nbsp;<b><bean:message key="label.Birthdate" /></b><br/>
			&nbsp;<html:text property="birthdate"></html:text>
			<bean:message key="error.requiredField1" />
		</p><br/>
        <p align="left">
			&nbsp;<b><bean:message key="label.CF" /></b><br/>
			&nbsp;<html:text property="cf" ></html:text>
			<bean:message key="error.requiredField2" />
		</p><br/>
		<br/>
		<jsp:include page="/pages/captcha/captchabox.jsp" flush="true"></jsp:include>
		<br/>
		<h3><bean:message key="message.privacyRules" /></h3>
		<br/>
        <p align="left">
			&nbsp;<input type="checkbox" value="checked" name="condition_1"/>
			&nbsp;<bean:message key="message.termsOfUse" />
		</p><br />
		<p align="center">
			<html:submit  alt="Invia" styleClass="submit"><bean:message key="button.Send" /></html:submit>
		</p>
	</div>
</html:form>
<div>&nbsp;</div>