<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page language="java" import="java.util.*,java.text.*, it.openprj.jTicketing.blogic.model.entity.*, it.openprj.jTicketing.blogic.utilities.*"%>
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
    Date dataOdierna=DateUtilities.dataOdierna();
	int iDay = nullIntconv((String) session.getAttribute("iDay"));
	int iYear = nullIntconv((String) session.getAttribute("iYear"));
	int iMonth = nullIntconv((String) session.getAttribute("iMonth"));
	Calendar ca = new GregorianCalendar();
	int iTDay = ca.get(Calendar.DATE);
	int iTYear = ca.get(Calendar.YEAR);
	int iTMonth = ca.get(Calendar.MONTH);
	GregorianCalendar cal = new GregorianCalendar(iYear, iMonth, 1);
	int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);
	int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);
	String uidTicket = (String) session.getAttribute("uidTicket");
	String uidLuogoInteresse = (String) session.getAttribute("uidLuogoInteresse");
%>
<html:form action="/processModificaVenditeStep3.do" method="post">
<html:hidden property="dispatch" value="edit"/>
<div class="main_center">
	<h3><bean:message key="message.OperationMenu"/></h3>
	<div class="right_articles">
		<p align="left"><img src="./images/group-checked.gif" align="top"/><a href="./processModificaVenditeTickets.do?dispatch=get&uidTicket=<%= uidTicket %>"> <bean:message key="label.EditTickets"/></a></p>
	</div>
</div>
<input type="hidden" value="<%= uidLuogoInteresse %>" name="uidLuogoInteresse" />
<input type="hidden" value="<%= uidTicket %>" name="uidTicket" />
<div class="main_center">
	<h3><bean:message key="message.TicketTime"/></h3>
	<div>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
  		<tr>
    		<br/>
    		<td align="center">
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
				<bean:message key="label.month" /> <select name="iMonth">
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
				<html:submit property="isChange"  styleClass="submit" ><bean:message key="button.change" /></html:submit></p>
				<br/>
			</td>
  		</tr>
  	</table>
  	</div>
</div>
</html:form>

<html:form action="/processModificaVenditeStep3.do" method="post">
<html:hidden property="dispatch" value="save"/>
<input type="hidden" value="<%= uidLuogoInteresse %>" name="uidLuogoInteresse" />
<input type="hidden" value="<%= uidTicket %>" name="uidTicket" />
<input type="hidden" value="<%= iYear %>" name="iYear" />
<input type="hidden" value="<%= iMonth %>" name="iMonth" />
<input type="hidden" value="<%= days %>" name="days" />
<div class="main_center">	
  	<div>
  	<table width="100%" border="0" cellpadding="0" cellspacing="0">
 		<tr>
    		<td align="center" valign="top">
    			<div style="background-color: #FFE79B;">
    			<table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
<% 
	String cssClass="dottedBox";
	for(int day=1; day<=days; day++) { 
%>
    				<tr>
<% 
		cal.set(Calendar.DATE,day);
%>
       					<td width="1%" nowrap="nowrap" align="left" class="simpleBox">
       						<p>
<%
		switch(cal.get(Calendar.DAY_OF_WEEK)) {
			case 2: 
				cssClass="dottedBox";
%>
    	   					<bean:message key="label.monday" />
<%
			break;
		    case 3:
		    	cssClass="dottedBox";
%>
   	       					<bean:message key="label.tuesday" />
<%
			break;
		    case 4:
		    	cssClass="dottedBox";
%>
	       					<bean:message key="label.wednesday" />
<%
			break;
		    case 5:
		    	cssClass="dottedBox";
%>
          					<bean:message key="label.thursday" />
<%
			break;
		    case 6:
		    	cssClass="dottedBox";
%>
          					<bean:message key="label.friday" />
<%
			break;
		    case 7:
		    	cssClass="dottedBox";
%>
         					<bean:message key="label.saturday" />
<%
			break;
		    case 1:
		    	cssClass="redDottedBox";
%>
  	       					<bean:message key="label.sunday" />
<%
			break;
		}
%> 
    						<b><%= day%></b>
    						</p>
    					</td>
<%
		List<Turno> listaTurni = ((List<Turno>) session.getAttribute("listaTurni"));
		List<Turno> listaTurniGiorni = ((List<Turno>) session.getAttribute("listaTurniGiorni"));
		String checked;
		int quantita;
		String disabled;
		for(int countTurni = 0; countTurni < listaTurni.size(); countTurni++){
			if (iYear > iTYear || (iYear == iTYear && iMonth > iTMonth) || (iYear == iTYear && iMonth == iTMonth && day >= iTDay)) {
				checked = "";
				quantita = 0;
				disabled = "";
				for(int countTurniGiorni = 0; countTurniGiorni < listaTurniGiorni.size(); countTurniGiorni++) {
					if (Integer.parseInt(listaTurniGiorni.get(countTurniGiorni).getGiorno()) == day && listaTurniGiorni.get(countTurniGiorni).getUidTpOrarioEventi().equals(listaTurni.get(countTurni).getUidTpOrarioEventi())) {
						checked = "checked";
						quantita = listaTurniGiorni.get(countTurniGiorni).getQuantita();
						if (quantita > 0) {
							disabled ="disabled";
						}
					} 
				}
%>
						<td align="left" width="12%" class="<%=cssClass %>">
							&nbsp;<input name="selTurno_<%= day %>_<%= listaTurni.get(countTurni).getUidTpOrarioEventi() %>" type="checkbox" value="" <%= checked %> <%= disabled %>/>
							<input type="hidden" name="turno_<%= day %>_<%= listaTurni.get(countTurni).getUidTpOrarioEventi() %>" value="new<%= checked %><%= disabled %>" />
							<%= listaTurni.get(countTurni).getOrario() %>
							(<%= quantita %>)
						</td>
<%
			} else {
				quantita = 0;
				checked = "";
				for(int countTurniGiorni = 0; countTurniGiorni < listaTurniGiorni.size(); countTurniGiorni++) {
					if (Integer.parseInt(listaTurniGiorni.get(countTurniGiorni).getGiorno()) == day && listaTurniGiorni.get(countTurniGiorni).getUidTpOrarioEventi().equals(listaTurni.get(countTurni).getUidTpOrarioEventi())) {
						checked = "checked";
						quantita = listaTurniGiorni.get(countTurniGiorni).getQuantita();
					} 
				}	
				
				
%>
						<td align="left" width="12%" class="<%=cssClass %>">
							&nbsp;<input name="selTurno_<%= day %>_<%= listaTurni.get(countTurni).getUidTpOrarioEventi() %>" type="checkbox" value="" <%= checked %> disabled />
							<input type="hidden" name="turno_<%= day %>_<%= listaTurni.get(countTurni).getUidTpOrarioEventi() %>" value="old" />
							<%= listaTurni.get(countTurni).getOrario() %>
							(<%= quantita %>)
						</td>
<%				
			}
		}
%>
		<td class="<%=cssClass %>">
		</td>
<%
	}
%>
					</tr>
				</table>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<br />
				<html:submit property="isChange"  styleClass="submit" ><bean:message key="button.Save" /></html:submit>
			</td>
		</tr>
	</table>
 	</div>
</div>
</html:form>
<div>&nbsp;</div>