<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<tiles:useAttribute name="navLabels" classname="java.util.List" />
<tiles:useAttribute name="navLinks" classname="java.util.List" />
<tiles:useAttribute name="currentTab" classname="java.lang.String" />
<div class="bar" id="menu">
    <ul>
<% 
	for(int i = 0; i < navLabels.size(); i++) {
		if (navLabels.get(i).equals(currentTab)) {
%> 
		    <li class="active">
		    	<a href=""><span><%= navLabels.get(i) %></span></a>
		    </li>
<%
		} else {
%>
			<li>
				<a href="<%= navLinks.get(i) %>" accesskey="<%= navLabels.get(i).toString().charAt(0) %>"><span><%= navLabels.get(i) %></span></a>
			</li>
<%
		}
	}
%>		
    </ul>
</div>
