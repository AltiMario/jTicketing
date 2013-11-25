<%@page import="it.openprj.jTicketing.frontend.forms.CartFormBoxOffice"%>
<%@page import="org.apache.commons.beanutils.PropertyUtils"%>
<%@page import="org.apache.commons.beanutils.BeanUtils"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@ page language="java" import="java.util.*,java.text.*, it.openprj.jTicketing.blogic.model.entity.*"%>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<%!public int nullIntconv(String inv) {
		int conv = 0;
		try {
			conv = Integer.parseInt(inv);
		} catch (Exception e) {
		}
		return conv;		
		}%>	
<%
	int uid = nullIntconv((String) session.getAttribute("uid")); 
    int uidTicket = nullIntconv((String) session.getAttribute("uidTicket")); 
    int iDay = nullIntconv((String) session.getAttribute("iDay"));
	int iYear = nullIntconv((String) session.getAttribute("iYear"));
	int iMonth = nullIntconv((String) session.getAttribute("iMonth"));
	//CalendarioEventi calendarioEventi= (CalendarioEventi)session.getAttribute("calendarioEventi"+iMonth+iYear);
	CalendarioEventi calendarioEventi = (CalendarioEventi)session.getAttribute("calendarioEventi"+iMonth+iYear);
	ArrayList<CalendarioEventi> listaCalendarioEventi = (ArrayList<CalendarioEventi>)session.getAttribute("listaCalendarioEventi"); 
	ArrayList<Ticket> listaTickets = (ArrayList<Ticket>)session.getAttribute("listaTickets");
	Calendar ca = new GregorianCalendar();
	int iTDay = ca.get(Calendar.DATE);
	int iTYear = ca.get(Calendar.YEAR);
	int iTMonth = ca.get(Calendar.MONTH);

	if (iYear == 0) {
		iYear = iTYear;
		iMonth = iTMonth;
	}

	GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);

	int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);

	cal = new GregorianCalendar(iYear, iMonth, days);
	int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
%>
<div>

	<html:form method="post" action="/processTicketBuyBoxOffice.do">
	<div class="main_center">
	<input type="hidden" name="idTurno" value="<%=session.getAttribute("idTurno") %>">
	<input type="hidden" name="uidTicket" value="<%=session.getAttribute("uidTicket") %>">	
	<input type="hidden" name="turno" value="<%=session.getAttribute("turno") %>">
	<input type="hidden" name="uidTurno" value="<%=session.getAttribute("uidTurno") %>">
	
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
	    		<a href="./processTicketBuyBoxOffice.do?codiceVerifica=<%= purchasedticketGrouped.get(keyMap).get(0).getCodiceVerifica() %>">
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
	<html:submit accesskey="A" titleKey="button.Go" altKey="button.Go" styleClass="submit_2" property="btn" ><bean:message key="button.Buy" /></html:submit>
				<%
		}
 %>

	</div>
	
	</html:form>
<bean:define id="objElemento" name="dettaglioLuogoInteresse"
	scope="session"
	type="it.openprj.jTicketing.blogic.model.entity.LuogoInteresse">
</bean:define>
<logic:iterate id="objTicket" name="listaTickets"
		indexId="indiceElemento" scope="session">
</logic:iterate>		
	
<%-- <h3><bean:message key="label.availableTicketsType" /></h3> --%>
<h3><bean:message key="label.dayTimeAndRound" /> <%=iDay%></h3>
<%
	for(int i = 0;listaTickets.size()>i && listaCalendarioEventi.size()>i; i++ ){
		 CalendarioEventi calEven = listaCalendarioEventi.get(i);
	 %>
<div class="main_center">	 
<table border="0" cellpadding="1" cellspacing="1" width="98%"
	align="center">	
		<tr>
			<td class="calendarHourMinuteBox"><%=listaTickets.get(i).getTitolo() %></td>
		</tr>
		<tr>
			<td class="turnBox">
			 <div class="ticketImage">
 				 <img src="./ImageProcessor?maxWidth=100&maxHeight=70&type=2&uid=<%=listaTickets.get(i).getUid() %>" />
 			</div>
			<p align="left"> &nbsp; &nbsp; <%=listaTickets.get(i).getDescrizione() %></p><br />
			<p align="right">
				<%-- <a href="./processTicketSearchBoxOffice.do?uid=<%=listaTickets.get(i).getUid_luoghi_interesse() %>&uidTicket=<%=listaTickets.get(i).getUid() %>"><bean:message key="label.select" /></a> --%>
			</p>
			</td>
		</tr>	
</table>

 

<% 
   String giorno= ""+iDay+(iMonth+1)+iYear;
   String cssClass="turnBox";
    
  /*  for(int y = 0; listaCalendarioEventi.size()>y; y++){ */
	  
	   
   /* if(calEven!=null && calEven.getTurni(giorno)!=null) {  */
      Turno turno=null;
      %>



 <table border="0" cellpadding="1" cellspacing="1" width="100%" align="center">
	<tr>
		<td class="calendarHourMinuteBox"><bean:message key="label.time" /></td>
		<td class="calendarHourMinuteBox"><bean:message key="label.availability" /></td>
		<td class="calendarHourMinuteBox"><bean:message key="label.select" /></td>
		<td class="calendarHourMinuteBox"><bean:message key="label.select" /></td>
		<td class="calendarHourMinuteBox"><bean:message key="label.select" /></td>
	</tr>
	<%
		int percentualeRimanenza=0;
	    
		for(int y=0; y<calEven.getTurni(giorno).size(); y++){
									
	
	    	turno=calEven.getTurni(giorno).get(y);
	    	
	    	
	    	%>

	    	<html:form method="post" action="processTicketBuyBoxOffice.do?uidTurno=<%=turno.getUidTaOrarioEventi() %>&turno=<%=turno.getOrario()%>&idTurno=<%=turno.getUidTpOrarioEventi() %>&uidTicket=<%=listaTickets.get(i).getUid() %>">
	    	<%-- <bean:define id="objTicket" name="ticket" scope="session" type="it.openprj.jTicketing.blogic.model.entity.Ticket"/> --%>	
	    		
	    		<%	   	
	    	percentualeRimanenza=turno.getPercentualeQuantita();
	    	if (percentualeRimanenza>=0 && percentualeRimanenza<=34) {
	    		cssClass="turnBoxRed";
	    	}
	    	if (percentualeRimanenza>35 && percentualeRimanenza<=74) {
	    		cssClass="turnBoxYellow";
	    	}
	    	if (percentualeRimanenza>75) {
	    		cssClass="turnBoxGreen";
	    	}
	    	
	    %>
	<tr>
		<td class="calendarHourMinuteBox"><%=turno.getOrario()%></td>
		<td class="<%=cssClass%>"><b><%=turno.getQuantitaResidua()%>&nbsp;<bean:message key="label.of" />&nbsp;<%=turno.getQuantita()%></td>
      	<%
      		if(turno.getQuantitaResidua()>0) { 
      	%>
			<%-- <td class="turnBox"><%=turno.getQuantitaAquistata()%>&nbsp;nel&nbsp; --%><%-- <a href="./processTicketBuyBoxOffice.do?uidTurno=<%=turno.getUidTaOrarioEventi() %>&uidTicket=<%=uidTicket %>&turno=<%=turno.getOrario()%>&idTurno=<%=turno.getUidTpOrarioEventi() %>"> --%><!-- <img src="./images/cart.png" alt="Acquista"></a></td> -->
	  	<%
	  		} else { 
	  	%>	
	     <!-- <td class="turnBox"><img src="./images/nocart.png"></td> --> 
		<%
			} 
		%>
		<td class="turnBox">
		<%
			CartFormBoxOffice cartFormBoxOffice = new CartFormBoxOffice();  
		    cartFormBoxOffice=(CartFormBoxOffice)session.getAttribute("cartFormCategorie");
		%>
		
		
	
	<html:select property="categoria" > 
		<%-- <logic:iterate id="objPrezzoTicket" name="objTicket" property="prezziCategorie" indexId="indiceElemento">
			<option value="<bean:write name="objPrezzoTicket" property="uid" />"><bean:write name="objPrezzoTicket" property="descrizione" /> (<bean:write name="objPrezzoTicket" property="prezzo" /> EUR)</option> --%>
			<%for(int c = 0; listaTickets.get(i).getPrezziCategorie().size()>c; c++ ){
				
			 %>
			<option value="<%=listaTickets.get(i).getPrezziCategorie().get(c).getUid()%>"><%=listaTickets.get(i).getPrezziCategorie().get(c).getDescrizione()%> (<%=listaTickets.get(i).getPrezziCategorie().get(c).getPrezzo()%> EUR)</option>
			<%} %>
      	<%-- </logic:iterate> --%>
	</html:select>
	</td>
	<td class="turnBox">
	<html:text property="quantita" titleKey="label.euroPrice" altKey="label.euroPrice" styleClass="jsrequired {errorLocation:'none'}" />
	</td>
	<td>
	<input type="hidden" name="uidTurno" value="<%=turno.getUidTaOrarioEventi() %>">
	<input type="hidden" name="uidTicket" value="<%=uidTicket %>">
	<%session.setAttribute("uidTicket",turno.getUidTicket()); %>
	<input type="hidden" name="turno" value="<%=turno.getOrario() %>">
	<input type="hidden" name="idTurno" value="<%=turno.getUidTpOrarioEventi() %>">
		<html:submit accesskey="V"  titleKey="button.Go" altKey="button.Go" styleClass="submit" indexed="" property="btn"><bean:message key="button.add" /></html:submit>
	</td>
	</tr>
	</html:form>	
	 
	<%
		}
	%>
	
	</table>
	</div>
	<%
	}
	%>
	<%-- <html:submit accesskey="V" value="Compra" titleKey="button.Go" altKey="button.Go"	styleClass="submit"  property="btn" /></p> --%>
	
<%
	//}
%>


<%-- <%
	}
%> --%>

<%-- <%
	}
%> --%>
	<html:form method="get" action="/processTicketSearchBoxOffice.do">
	<div class="calendar_form" align="center">
		<input type="hidden" name="uid" value="<%=uid %>">
		<input type="hidden" name="uidTicket" value="<%=uidTicket %>">
		<p><bean:message key="label.year" />
		<select name="iYear">
<%
	for (int iy = iTYear; iy <= iTYear + 5; iy++) {
		if (iy == iYear) {
%>
			<option value="<%=iy%>" selected="selected"><%=iy%></option>
<%
		} else {
%>
			<option value="<%=iy%>"><%=iy%></option>
<%
			}
		}
%>
		</select> 
		<bean:message key="label.month" /> 
		<select name="iMonth">
<%
	for (int im = 0; im <= 11; im++) {
		if (im == iMonth) {
%>
			<option value="<%=im%>" selected="selected"><%=new SimpleDateFormat("MMMM").format(new Date(2008, im, 01))%></option>
<%
		} else {
%>
			<option value="<%=im%>"><%=new SimpleDateFormat("MMMM").format(new Date(2008, im, 01))%></option>
<%
		}
	}
%>
		</select> 
		<html:submit accesskey="V" titleKey="button.Go" altKey="button.Go" styleClass="submit" ><bean:message key="button.change" /></html:submit></p>
	</div>
</html:form>

<div class="main_center">
<h3 align="center"><%=new SimpleDateFormat("MMMM").format(new Date(2008, iMonth, 01)).toUpperCase()%>
<%=iYear%></h3>
<table align="center" border="0" cellpadding="1" cellspacing="1"
	width="100%">
	<tbody>
		<tr>
			<td align="center" class="calendarWeekBox"><bean:message
				key="label.sunday" /></td>
			<td align="center" class="calendarWeekBox"><bean:message
				key="label.monday" /></td>
			<td align="center" class="calendarWeekBox"><bean:message
				key="label.tuesday" /></td>
			<td align="center" class="calendarWeekBox"><bean:message
				key="label.wednesday" /></td>
			<td align="center" class="calendarWeekBox"><bean:message
				key="label.thursday" /></td>
			<td align="center" class="calendarWeekBox"><bean:message
				key="label.friday" /></td>
			<td align="center" class="calendarWeekBox"><bean:message
				key="label.saturday" /></td>
		</tr>
		<%
			int cnt = 1;
	     	String giornoElaborato = null;
			for (int i = 1; i <= 6; i++) {
		%>
		<tr>
			<%
				for (int j = 1; j <= 7; j++) {
					if (cnt < weekStartDay || (cnt - weekStartDay + 1) > days) {
			%>
			<td align="center" height="35">&nbsp;</td>
			<%
					} else {
			  			giornoElaborato= ""+(cnt - weekStartDay + 1)+(iMonth+1)+iYear;
			%>
			
			<%
						if(calendarioEventi!=null && calendarioEventi.getTurni(giornoElaborato)!=null){ 
			%>
			<%
							if (iYear > iTYear || (iYear == iTYear && iMonth > iTMonth) || (iYear == iTYear && iMonth == iTMonth && (cnt - weekStartDay + 1) >= iTDay)) { 
			%>
								<td bgcolor="#CDDF8B" align="center" id="day_<%=(cnt - weekStartDay + 1)%>" class="calendarDayBox">
								   <a href="./processTicketSearchBoxOffice.do?iDay=<%=(cnt - weekStartDay + 1)%>&iYear=<%=iYear%>&iMonth=<%=iMonth%>&uid=<%=uid%>"><span><%=(cnt - weekStartDay + 1)%></span></a>
								</td>
			<%
							} else {
			%>
								<td bgcolor="lightgray" align="center" id="day_<%=(cnt - weekStartDay + 1) %>" class="calendarDayBox">
								   <span><%=(cnt - weekStartDay + 1)%></span>
								</td>	
			<%
							}
						} else {
			%>
			<td align="center" id="day_<%=(cnt - weekStartDay + 1)%>" class="calendarDayBox">
			   <span><%=(cnt - weekStartDay + 1)%></span>
			</td>
			<%
						}
					}
					cnt++;
				}
			%>
		</tr>
		<%
			}
		%>
	</tbody>
</table>
</div>



</div>
<div>&nbsp;</div>
