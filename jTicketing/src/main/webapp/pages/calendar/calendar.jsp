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
	CalendarioEventi calendarioEventi= (CalendarioEventi)session.getAttribute("calendarioEventi"+iMonth+iYear);
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
<bean:define id="objTicket" name="ticket" scope="session" type="it.openprj.jTicketing.blogic.model.entity.Ticket"/>
<div>
	<div class="ticketContainer">
 		<div class="ticketImage">
  			<img src="./ImageProcessor?maxWidth=100&maxHeight=70&type=2&uid=<bean:write name="objTicket" property="uid" />" />
 		</div>
 		<div class="ticketDescription">
  			<h3><bean:write name="objTicket" property="titolo" /></h3>
  			<bean:write name="objTicket" property="descrizione" />
  		</div>
  		<div class="ticketPrice">
  			<h3><bean:message key="label.prices" /></h3>
  			<logic:iterate id="objPrezzoTicket" name="objTicket" property="prezziCategorie" indexId="indiceElemento">
    			<p><bean:write name="objPrezzoTicket" property="descrizione" /> : <bean:write name="objPrezzoTicket" property="prezzo" /> EUR</p>
  			</logic:iterate>
  		</div>
	</div>
	<html:form method="get" action="/processTicketSearch.do">
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
        <html:submit accesskey="V" titleKey="button.Go" altKey="button.Go" styleClass="submit"><bean:message key="button.change" /></html:submit>
        </p>

	</div>
</html:form>
<% 
   String giorno= ""+iDay+(iMonth+1)+iYear;
   String cssClass="turnBox";
   if(calendarioEventi!=null && calendarioEventi.getTurni(giorno)!=null) { 
      Turno turno=null;
%>
<div class="main_center">
<h3><bean:message key="label.dayTimeAndRound" /> <%=iDay%></h3>
<table border="0" cellpadding="1" cellspacing="1" width="100%" align="center">
	<tr>
		<td class="calendarHourMinuteBox"><bean:message key="label.time" /></td>
		<td class="calendarHourMinuteBox"><bean:message key="label.availability" /></td>
		<td class="calendarHourMinuteBox"><bean:message key="label.select" /></td>
	</tr>
	<%
		int percentualeRimanenza=0;
	    
		for(int i=0; i<calendarioEventi.getTurni(giorno).size(); i++){
	    	turno=calendarioEventi.getTurni(giorno).get(i);
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
		<td class="<%=cssClass%>"><b><%=turno.getQuantitaResidua()%>&nbsp;<bean:message key="label.of" />&nbsp; <%=turno.getQuantita()%> </td>
      	<%
      		if(turno.getQuantitaResidua()>0) { 
      	%>
			<td class="turnBox"><%=turno.getQuantitaAquistata()%>&nbsp;<bean:message key="message.In" />&nbsp;<a href="./processTicketBuy.do?uidTurno=<%=turno.getUidTaOrarioEventi() %>&uidTicket=<%=uidTicket %>&turno=<%=turno.getOrario()%>&idTurno=<%=turno.getUidTpOrarioEventi() %>"><img src="./images/cart.png" alt="Acquista"></a></td>
	  	<%
	  		} else { 
	  	%>	
	    <td class="turnBox"><img src="./images/nocart.png"></td>
		<%
			} 
		%>
	</tr>
	<%
		}
	%>
	</table>
<%
	}
%>
</div>
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
								   <a href="./processTicketSearch.do?iDay=<%=(cnt - weekStartDay + 1)%>&iYear=<%=iYear%>&iMonth=<%=iMonth%>&uid=<%=uid%>&uidTicket=<%=uidTicket%>"><span><%=(cnt - weekStartDay + 1)%></span></a>
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
