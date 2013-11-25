<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%> 
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page import="java.util.Calendar, java.text.DateFormat, it.openprj.jTicketing.core.common.DevelopConfig"%>
<%@ page language="java" import="it.openprj.jTicketing.blogic.model.entity.User"%>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<% User user = (User) session.getAttribute("user"); %>
<div id="page">
	<div id="header-main">
	    <div id="header">
			<div id="login-main">
		       	<div id="login">
					<div >
						<ul class="user-login">
							<logic:notPresent name="user" scope="session">
								<bean:cookie id="fname" name="eTicketFirstName" value=""/>
								<bean:cookie id="lname" name="eTicketLastName" value=""/>
								<bean:cookie id="uname" name="eTicketUsername" value=""/>
								<% 
									if(uname.getValue()== "" ){
								%> 
										<li>
											<html:link page="/prepareLogin.do"><bean:message key="link.enter" /></html:link>
											<bean:message key="message.or" /> 
											<html:link page="/prepareRegister.do"><bean:message key="link.registered" /></html:link>
										</li>
								<%
									}
								%>
							</logic:notPresent> 
							<logic:present name="user" scope="session">
								<li>
									<b><bean:message key="message.welcome" />&nbsp;<%= user.getUserName() %>&nbsp;&nbsp;&nbsp;</b>
									<html:link page="/logout.do"><bean:message key="link.exit" /></html:link>
									| <html:link page="/prepareUpdateProfile.do"><bean:message key="link.updateAccount" /></html:link>
									| <html:link page="/prepareUpdatePassword.do"><bean:message key="link.changePassword" /></html:link>
								</li>
							</logic:present> 
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>