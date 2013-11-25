<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="it.openprj.jTicketing.blogic.model.entity.TicketAcquistato"%>
<%@page import="it.openprj.jTicketing.blogic.model.entity.User"%>
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


<logic:present name="listaLuoghiInteresse" scope="session">
<logic:notPresent name="luogoInteresse" scope="session">
<html:form method="get" action="/processValidazioneTicket.do">
<input type="hidden" name="dispatch" value="start"/>

<div class="main_center" style="background-color: #f5f5f5;">
<h3><bean:message key="label.placesAllocated"/></h3>
	<logic:iterate id="objElemento" name="listaLuoghiInteresse"
		indexId="indiceElemento" scope="session">
		<div class="left_box2">
		<table width="100%" border="0">
			<tr>
			    <td colspan="2"><html:radio property="selLuogoInteresse"  value="<%=String.valueOf(indiceElemento.intValue())%>"/></td>
				<td>
				<img src="./ImageProcessor?maxWidth=300&maxHeight=300&type=1&uid=<bean:write name="objElemento" property="uid" />" /></td>
				<td>
				<b><bean:write name="objElemento" property="titolo" /></b><br />
				<p><bean:write name="objElemento" property="descrizione" /></p>
				</td>
			</tr>
		</table>
		</div>
	</logic:iterate>
</div>
<div class="main_center">
<h3><bean:message key="message.help"/></h3>
<div class="right_articles">
<p><img src="./images/success.png" align="top"/> <bean:message key="message.selectPlaceAndPushButtonToValidate"/></p><BR/>
<p><img src="./images/info.png" align="top"/> <bean:message key="message.informationAboutValidation"/></p><BR/>
<div align="right">
	<html:submit accesskey="V"  property="btnAvanti" titleKey="button.Go"
		altKey="button.Go" styleClass="submit" ><bean:message key="button.Forward" /></html:submit>
</div>
</div>
</div>
</html:form>
</logic:notPresent>
</logic:present>


<logic:present name="luogoInteresse" scope="session">
<div class="main_center" style="background-color: #f5f5f5;">
	<h3><bean:message key="message.TicketValidation"/> - <bean:write name="luogoInteresse" scope="session" property="titolo" /></h3>
	<html:form method="get" action="/processValidazioneTicket.do">
  	<html:hidden property="dispatch" value="validateTicket"/>  	
  	<br/>
	<p>
		<bean:message key="label.verificationCode"/> 
	    <html:text property="codiceDiVerifica" value="" titleKey="label.category" altKey="label.category" styleClass="jsrequired jsvalidate_number {errorLocation:'none'}" />
	    <html:submit accesskey="V" titleKey="button.Validate" altKey="button.Validate"	styleClass="submit"><bean:message key="button.Validate" /></html:submit>
	</p>
	</html:form>
	<p>
	
	<br/><br/>
		<logic:present name="ticketAcquistati" scope="session">
	  	<logic:iterate id="objElemento" name="ticketAcquistati"
		indexId="indiceElemento" scope="session">
			  <html:form method="get" action="/processValidazioneTicket.do">
		  		<html:hidden property="dispatch" value="annullaTicket"/>  	
		  		<html:hidden property="selTicket" value="<%=String.valueOf(indiceElemento.intValue())%>"/>  
				  <div style="background-color: #f5f5f5">
				 
				  <h1> <bean:message key="message.ticketHolder" /> </h1>
				<label ><bean:write name="objElemento" property="acquirente.firstName" /></label> 		
				<label ><bean:write name="objElemento" property="acquirente.lastName" /></label>
				<label ><bean:write name="objElemento" property="dataAcquisto" /></label>
				<logic:equal name="objElemento" property="convalidato" value="N" >
				<html:submit accesskey="L" titleKey="button.LoggedIn" altKey="button.LoggedIn"	styleClass="submit"><bean:message key="button.LoggedIn" /></html:submit>
				</logic:equal>				
				</div>
			</html:form>
		</logic:iterate>
		</logic:present>
	</p>
	
	
</div>
<div class="main_center">
	<h3><bean:message key="message.help"/></h3>
	<div class="right_articles">
		<p><img src="./images/info.png" align="top"/> <bean:message key="message.HelpValidation"/></p><BR/>
	</div>
</div>
</logic:present>



<div>&nbsp;</div>