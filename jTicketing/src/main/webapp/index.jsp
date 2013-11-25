<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page language="java" import="it.openprj.jTicketing.blogic.model.entity.User"%>

<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<%
	User user = (User) session.getAttribute("user");
	
	String redirect = "home.do";
	if (user != null && user.isOperatore()) {
		redirect = "homeBackendOperatore.do";
	} else if (user != null && user.isAdministrator()) {
		redirect = "homeBackendAdmin.do";
	} else if (user != null && user.isBotteghino()) {
		redirect = "homeBackendBotteghino.do";
	}	
    response.sendRedirect(redirect);
%>
