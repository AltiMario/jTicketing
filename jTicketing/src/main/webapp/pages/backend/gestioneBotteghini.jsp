<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ page language="java" import="it.openprj.jTicketing.blogic.model.entity.LuogoInteresse"%>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<%!
	public int nullIntconv(String inv) {
		int conv = 0;
		try {
			conv = Integer.parseInt(inv);
		} catch (Exception e) {
		}
		return conv;
	}
%>

<%
	int iUid = nullIntconv((String) session.getAttribute ("uid"));
	String nome = (String) session.getAttribute ("nome");
	String cognome = (String) session.getAttribute ("cognome");
	String username = (String) session.getAttribute ("username");
	String email = (String) session.getAttribute ("email");
	String ruolo = (String) session.getAttribute ("ruolo");
	if (iUid == 0) {
%>
<div class="main_center">
	<h3><bean:message key="label_go.selectUser"/></h3>
	<table width="100%" border="0" cellspacing="5" class="left_box2">
	<html:form action="/processGestioneBotteghini.do?dispatch=get">
		<tr>
		    <td align="left">
		    	<input type="text" name="nome" value="<%=nome %>"></input>
			</td>
			<td align="left">
				<input type="text" name="cognome" value="<%= cognome %>"></input>
			</td>
			<td align="left">
				<input type="text" name="username" value="<%= username %>"></input>
			</td>
			<td align="left">
				<input type="text" name="email" value="<%= email %>"></input>
			</td>
			<td align="left">
				<select name="ruolo">
					<option value=""></option>
					<%
						if (ruolo.equals("Utente registrato")) {
					%>
						<option value="Utente registrato" selected="selected"><bean:message key="role.userDesc"/></option>
						<option value="Botteghino"><bean:message key="role.boxOffice"/></option>
					<% 
						} else if (ruolo.equals("Botteghino"))  {
					%>
						<option value="Utente registrato"><bean:message key="role.userDesc"/></option>
						<option value="Botteghino" selected="selected"><bean:message key="role.boxOffice"/></option>
					<% 
						} else {
					%>
						<option value="Utente registrato"><bean:message key="role.userDesc"/></option>
						<option value="Botteghino"><bean:message key="role.boxOffice"/></option>
					<%
						}
					%>
				</select> 
			</td>
			<td width="5%">
		    	<html:form action="/processGestioneBotteghini.do?dispatch=get">
		    		<input type="submit" name="submit" value="<bean:message key="label_go.filter"/>" class="submitGestioneOperatori" />
		    	</html:form>
			</td>
		</tr>
	</html:form>
	<tr>
	    <td class="gestioneOperatoriLabel">
			<bean:message key="label_go.name"/>
		</td>
		<td class="gestioneOperatoriLabel">
			<bean:message key="label_go.surname"/>
		</td>
		<td class="gestioneOperatoriLabel">
			<bean:message key="label_go.userName"/>
		</td>
		<td class="gestioneOperatoriLabel">
			<bean:message key="label_go.email"/>
		</td>
		<td class="gestioneOperatoriLabel">
			<bean:message key="label_go.role"/>
		</td>
		<td></td>
	</tr>
	<logic:present name="listaBotteghini" scope="session">
		<logic:iterate id="objElemento" name="listaBotteghini" indexId="indiceElemento" scope="session">
				<tr>
				    <td align="left">
						<bean:write name="objElemento" property="firstName" />
					</td>
					<td align="left">
						<bean:write name="objElemento" property="lastName" />
					</td>
					<td align="left">
						<bean:write name="objElemento" property="userName" />
					</td>
					<td align="left">
						<bean:write name="objElemento" property="email" />
					</td>
					<td align="left">
						<bean:write name="objElemento" property="ruolo" />
					</td>
					<td>
				    	<html:form action="/processGestioneBotteghini.do?dispatch=get">
				    		<input type="hidden" name="uid" value="<bean:write name="objElemento" property="idUser" />"></input>
				    		<input type="submit" name="submit" value="<bean:message key="label_go.select"/>" class="submitGestioneOperatori" />
				    	</html:form>
				    </td>
				</tr>
		</logic:iterate>
	</logic:present>
	</table>
</div>

<%
	} else {
%>
		<html:form action="/processGestioneBotteghiniUpdate.do?dispatch=get">
		<div class="main_center">
			<h3><bean:message key="label_go.selectInterestPlace"/></h3>
			<logic:present name="listaLuoghiInteresseBotteghino" scope="session">
				<logic:iterate id="objElemento" name="listaLuoghiInteresseBotteghino"
					indexId="indiceElemento" scope="session">
					<div class="left_box2">
					<table width="100%" border="0">
						<tr>
						    <td>
						    	<input name="checkbox<bean:write name="objElemento" property="uid" />" type="checkbox" <bean:write name="objElemento" property="assegnatoBotteghino" /> />
						    </td>
						    <td align="left">
								<img src="./ImageProcessor?maxWidth=150&maxHeight150&type=1&uid=<bean:write name="objElemento" property="uid" />" />
							</td>
						    <td>
						    	<b><bean:write name="objElemento" property="titolo" /></b><br />
								<p><bean:write name="objElemento" property="descrizione" /></p>
							</td>
						</tr>
					</table>
					</div>
				</logic:iterate>
			</logic:present>
			<br />
			<input type="hidden" name="uid" value="<%= iUid %>" />
			<input type="button" value="<bean:message key="label_go.cancel"/>" class="submit" onclick="javascript:location.href='./processGestioneBotteghini.do?dispatch=get'"/>
			<html:submit value="Aggiorna" styleClass="submitGestioneOperatori" />
    		<br /><br />
		</div>
		</html:form>
<%
	}
%>
<div>&nbsp;</div>