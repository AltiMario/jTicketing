<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page language="java" import="java.util.*,java.text.*, it.openprj.jTicketing.blogic.model.entity.* "%>

<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<html:form method="post" action="/processTicketBuy.do">
	<div class="main_center">
	<h3><bean:message key="label.cart"/></h3>
<%
	boolean present=true;
	Map<String, ArrayList<TicketAcquistato>> purchasedticketGrouped = (HashMap<String, ArrayList<TicketAcquistato>>) session.getAttribute("purchasedticketGrouped");
	if(purchasedticketGrouped == null) {
		present = false;
	}
	//Raggruppo nell'hashmap i ticket dello stesso tipo.
	ArrayList<TicketAcquistato> gruppoBiglietti = null;
	Iterator<String> iter = null;
	int counter = 0;
    Locale country = Locale.getDefault();
	Locale.setDefault(country);
	NumberFormat nf = NumberFormat.getCurrencyInstance(country);
	nf.setParseIntegerOnly(true);
	double totale=0;
	if(present){
		iter = purchasedticketGrouped.keySet().iterator();
%> 
	<table width="100%" border="1" cellspacing="1" cellpadding="1">
		<tr>
			<td class="gestioneOperatoriLabel">N°</td>
	    	<td class="gestioneOperatoriLabel"><bean:message key="label.ticket"/></td>
	    	<td class="gestioneOperatoriLabel"><bean:message key="label.OpenDate"/></td>
	    	<td class="gestioneOperatoriLabel"><bean:message key="label.number"/></td>
	    	<td class="gestioneOperatoriLabel"><bean:message key="label.UnitPrice"/></td>
		</tr>
<%
		while (iter.hasNext()) {
			String keyMap = iter.next();
			String titoloTicket = purchasedticketGrouped.get(keyMap).get(0).getTicket().getTitolo();
			String dataIngresso = purchasedticketGrouped.get(keyMap).get(0).getGiorno() + "/" + purchasedticketGrouped.get(keyMap).get(0).getMese() + "/" + purchasedticketGrouped.get(keyMap).get(0).getAnno();
			double prezzoUnitario = purchasedticketGrouped.get(keyMap).get(0).getCategoria().getPrezzo().doubleValue();
			int quantitaAquistata = purchasedticketGrouped.get(keyMap).size();
			gruppoBiglietti = purchasedticketGrouped.get(keyMap);
			totale+= prezzoUnitario * quantitaAquistata;
			counter++;
%>
		<tr>
	    	<td><%=counter %></td>
	    	<td><%=titoloTicket%></td>
	    	<td><%=dataIngresso%></td>
	    	<td>
	    		<%=quantitaAquistata%>
	    		<a href="./processTicketBuy.do?codiceVerifica=<%= purchasedticketGrouped.get(keyMap).get(0).getCodiceVerifica() %>">
	    			<img src="./images/nocart.png" />
	    		</a>
	    	</td>
	    	<td align="center">EUR <%=nf.format(prezzoUnitario) %></td>
	  	</tr>
<%
		}
%>
		<tr>
	    	<td class="gestioneOperatoriLabel" colspan="4"><bean:message key="label.amount"/></td>
	    	<td class="gestioneOperatoriLabel" align="center">EUR <%=nf.format(totale) %></td>
		</tr>
	</table>
<% 
	}
	if (!present) { 
%>
		<b><bean:message key="message.EmptyCart" /></b>
		<br />
		<br />
		<bean:message key="message.HomeClick" />

<%
	}
%>
	</div>
	<div class="main_center">
		<h3><bean:message key="message.help" /></h3>
		<div class="right_articles">
			<p><img src="./images/info.png" align="top" /><bean:message key="message.addDifferentTicketsType" /></p>
			<br />
			<div align="center"><html:hidden property="step" value="2" />
<%
	if(present) { 
%>
		<table width="100%" border="0">
		<tr align="center">
			<td>
				<html:submit accesskey="A"  titleKey="button.Go" altKey="button.Go" styleClass="submit_2" property="btn" ><bean:message key="button.ChooseTickets" /></html:submit>
			</td>
			<td>
				<html:submit accesskey="A" titleKey="button.Go" altKey="button.Go" styleClass="submit_2" property="btn" ><bean:message key="button.Buy" /></html:submit>
			</td>
		</tr>
		</table>
<%
	}
%>
			</div>
		</div>
	</div>
</html:form>
<div>&nbsp;</div>