<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<tiles:useAttribute name="currentTab" classname="java.lang.String" />
<tiles:useAttribute name="pageBody" classname="java.lang.String" />
<tiles:useAttribute name="showMenu" classname="java.lang.String" />
<tiles:useAttribute name="navLabels" classname="java.util.List" ignore="true"/>
<tiles:useAttribute name="navLinks" classname="java.util.List" ignore="true"/>
<tiles:useAttribute name="titleBar" classname="java.lang.String" ignore="true" />
<tiles:useAttribute name="container" classname="java.lang.String" ignore="true"/>

<% 
	if(showMenu.equalsIgnoreCase("true")) {
%>
		<tiles:insert attribute="menu">
			<tiles:put name="currentTab" value="<%= currentTab %>" />
			<tiles:put name="navLabels"  value="<%= navLabels %>" />
			<tiles:put name="navLinks"   value="<%= navLinks %>" />
		</tiles:insert>
<%
	}
%>

<div>
	<tiles:insert attribute="pageBody">
		<tiles:put name="titleBar"   value="<%= titleBar %>" />
		<tiles:put name="container"  value="<%= container %>" />
		<tiles:put name="navLabels"  value="<%= navLabels %>" />
		<tiles:put name="navLinks"   value="<%= navLinks %>" />
		<tiles:put name="currentTab" value="<%= currentTab %>" />
	</tiles:insert>
</div>