<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
	<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>	
	<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
	<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
	<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
	<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
	<%@ page import="it.openprj.jTicketing.core.common.DevelopConfig"%>
	<%@ page language="java" import="it.openprj.jTicketing.blogic.model.entity.User"%>
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	<link href="./css/style.css" rel="stylesheet" type="text/css" media="screen" />
    <link href="/images/favicon.ico" rel="shortcut icon" type="image/x-icon" />
	<link rel="stylesheet" href="<html:rewrite page="/css/extjs/ext-all.css" />" type="text/css" />
	<title><%if(!DevelopConfig.GHOST_MODE){%><bean:message key="label.domainName"/><%}%></title>
	<% User user = (User) session.getAttribute("user"); %>
	<%@ page import="java.util.ResourceBundle"%>
	<% ResourceBundle resources = ResourceBundle.getBundle("resources.config"); %>
</head>
<%
	try {
		String test = session.getAttribute("session").toString();
	} catch (Exception e) {
		String redirectURL = "home.do";
        response.sendRedirect(redirectURL);
	}
	String AdminWithLuoghiInteresse = resources.getString("AdminWithLuoghiInteresse");
%>

<!--  <body id="pagebg">-->
<body>
	<div id="first-header-main">
    
    <div id="main-page">
    	<div id="corner-top-left">
    	<div id="corner-top-right">
    	<div id="corner-bottom-left">
    	<div id="corner-bottom-right">
    	<script type="text/javascript" src="<html:rewrite page="/js/extjs/ext-base.js" />"></script>
		<script type="text/javascript" src="<html:rewrite page="/js/extjs/ext-all.js" />"></script>
		<tiles:useAttribute name="currentTab" classname="java.lang.String" />
		<tiles:useAttribute name="showMenu"	classname="java.lang.String" />
		<tiles:useAttribute name="menu"	classname="java.lang.String" />
		<tiles:useAttribute name="search" classname="java.lang.String" />
		<tiles:useAttribute name="pageBody"	classname="java.lang.String" />
		<tiles:useAttribute name="titleBar"	classname="java.lang.String" />
		<tiles:useAttribute name="container" classname="java.lang.String" ignore="true" />
		<tiles:useAttribute name="tiles" classname="java.util.List"	ignore="true" />
		<tiles:useAttribute name="titles" classname="java.util.List" ignore="true" />
		<tiles:insert attribute="header"/>
		
		<div id="content">
			<tiles:insert attribute="page">
				<tiles:put name="currentTab" value="<%= currentTab %>"/>
				<tiles:put name="pageBody"	 value="<%= pageBody %>"/>
				<tiles:put name="titles"	 value="<%= titles %>"/>
				<tiles:put name="showMenu"	 value="<%= showMenu %>"/>
				<tiles:put name="menu"		 value="<%= menu %>"/>
				<tiles:put name="search"	 value="<%= search %>"/>
				<%
					if ((user != null) && user.isAdministrator() && AdminWithLuoghiInteresse.equals("true")) {
				%>
						<tiles:useAttribute name="navLabelsWithLuoghiInteresse" classname="java.util.List"	ignore="true" />
						<tiles:useAttribute name="navLinksWithLuoghiInteresse" classname="java.util.List" ignore="true" />
						<tiles:put name="navLabels"	 value="<%= navLabelsWithLuoghiInteresse %>"/>
						<tiles:put name="navLinks"	 value="<%= navLinksWithLuoghiInteresse %>"/>
				<%
					} else {
				%>
						<tiles:useAttribute name="navLabels" classname="java.util.List"	ignore="true" />
						<tiles:useAttribute name="navLinks" classname="java.util.List" ignore="true" />
						<tiles:put name="navLabels"	 value="<%= navLabels %>"/>
						<tiles:put name="navLinks"	 value="<%= navLinks %>"/>
				<%		
					}
					
				%>
				<tiles:put name="tiles"		 value="<%= tiles %>"/>
				<tiles:put name="titleBar"	 value="<%= titleBar %>"/>
			</tiles:insert>
			</div>
		</div>
		</div>
		</div>
		</div>
		</div>
	</div>
    <div id="content_me">
        <tiles:insert attribute="footer"/>
    </div>
	<div>
            Copyright &copy; 2010-2012 <a href="http://www.openprj.it">OpenPRJ srl</a>
    </div>
</body>
</html>