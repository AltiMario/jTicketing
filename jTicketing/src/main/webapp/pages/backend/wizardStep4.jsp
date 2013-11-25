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

<%!public int nullIntconv(String inv) {
		int conv = 0;
		try {
			conv = Integer.parseInt(inv);
		} catch (Exception e) {
		}
		return conv;
	}%>
<%
    Date dataOdierna=DateUtilities.dataOdierna();

	int uid = nullIntconv((String) session.getAttribute("uid"));
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
%>
<html:form action="/processWizardStep3.do" method="post">
<div class="stepContainer">
<h3><img src="./images/01_right.png" align="top"/><bean:message key="message.configurationHelp"/> 4 <bean:message key="label.of"/> 4</h3>
</div>
<div class="left">
<logic:present name="ticketSelezionato" scope="session">
  <h3><bean:message key="label.ticketSelected"/></h3>
  <div style="background-color: #FFE79B;">
   <bean:define id="ticketSelezionato" name="ticketSelezionato" scope="session" type="it.openprj.jTicketing.blogic.model.entity.Ticket"/>
		<table width="100%" border="0" cellspacing="1" cellpadding="1">
		  <tr>
		    <td width="50" rowspan="3"><img src="./ImageProcessor?maxWidth=100&maxHeight=60&sessionFile=ticketImage_<%=ticketSelezionato.getNomeFile()%>" /></td>
		    <td align="left"><b>Titolo: </b><bean:write name="ticketSelezionato" property="titolo" /></td>
		  </tr>
		  <tr>
		    <td align="left"><b>Descrizione:</b> <bean:write name="ticketSelezionato" property="descrizione" /></td>
		  </tr>
		  <tr>
		    <td align="left"><b>Categorie:</b>
		     <logic:present property="prezziCategorie" name="ticketSelezionato">
		       <logic:iterate id="prezzoCategoria" indexId="indiceCategoria" property="prezziCategorie" name="ticketSelezionato" type="it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket">
		         <bean:write name="prezzoCategoria" property="descrizione" />
		       </logic:iterate>
	        </logic:present>
		    </td>
		  </tr>
		</table>
		</div>
</logic:present>
<logic:present name="wizardStep3Form" property="selTicket">
  <h3><bean:message key="message.TicketTime"/></h3>
  <div style="background-color: #FFE79B;">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td align="center">
	<div class="calendar_form" align="left">
	<p><bean:message key="label.year" /> <select name="iYear">
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
	</select> <bean:message key="label.month" /> <select name="iMonth">
		<%
			// print month in combo box to change month in calendar
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
	<html:submit property="isChange" accesskey="V"  titleKey="button.Go" altKey="button.Go"	styleClass="submit" ><bean:message key="button.change" /></html:submit></p>
	</div>
 
    </td>
  </tr>
  <tr>
    <td align="center" valign="top">
     <table width="100%" border="0" align="center" cellpadding="1" cellspacing="1">
     <tr>
     <% 
       String cssClass="dottedBox";
       for(int day=1; day<=days; day++){ %>
      <tr>
        <% cal.set(Calendar.DATE,day);%>
        <td width="5%" nowrap="nowrap" align="left" class="simpleBox"><p>
       <%switch(cal.get(Calendar.DAY_OF_WEEK)){
    	   case 2: cssClass="dottedBox";%>
    		   <bean:message key="label.monday" />
    	<%	   break;
    	   case 3: cssClass="dottedBox";%>
    	       <bean:message key="label.tuesday" />
    	<%	   break;
    	   case 4: cssClass="dottedBox";%>
    	       <bean:message key="label.wednesday" />
    	<%	   break;
    	   case 5: cssClass="dottedBox";%>
    	      <bean:message key="label.thursday" />
    	<%	   break;
    	   case 6: cssClass="dottedBox";%>
    	      <bean:message key="label.friday" />
    	<%	   break;
    	   case 7: cssClass="dottedBox";%>
    	     <bean:message key="label.saturday" />
    	<%	   break;
    	   case 1: cssClass="redDottedBox";%>
  	         <bean:message key="label.sunday" />
  	    <%	   break;
         }
       %> 
       <b><%= day%></b></p></td>
        <td width="95%" class="<%=cssClass %>">
        <logic:present name="listaTurni" scope="session"> 
        <p>
        <bean:define id="listaMesi" name="wizardStep3Form" property="listaMesi" type="java.util.ArrayList"/>
         <%  String checked=""; 
		    CalendarioEventi calendarioMese=(CalendarioEventi)listaMesi.get(iMonth);
			ArrayList<Turno> turni=calendarioMese.getTurni(String.valueOf(day));
            
	      %>
		<logic:iterate id="turnoElemento" indexId="indiceElemento" name="listaTurni" scope="session" type="it.openprj.jTicketing.blogic.model.entity.Turno">
		<bean:define id="lista" name="listaTurni" type="java.util.ArrayList"/>
		<%if(lista!=null && lista.size()>8 && indiceElemento==8){%><br /><%}%>
		<bean:write name="turnoElemento" property="orario"/>
		<%  checked="";
		   if(turni!=null){
				for(int i=0;i<turni.size(); i++){
				  if(turni.get(i)!=null && turni.get(i).equals(turnoElemento)){
				    checked="checked";
				  }
				}
		   }
		    %>
        <input name="selTurno_<%=day%>" type="checkbox" value="<%=indiceElemento %>" <%=checked %>/> 
		</logic:iterate></p>
	</logic:present> 
         </td>
      </tr>
      <%} %>
    </table></td>
  </tr>
  <tr>
  <td align="right">
  <html:submit property="isChange" accesskey="V"  titleKey="button.Go" altKey="button.Go" styleClass="submit" ><bean:message key="button.Save" /></html:submit></td>
  </tr>
</table>
  </div>
</logic:present>
</div>

<div class="right">
<h3>Selezione Biglietto</h3>
<div style="background-color: #f5f5f5;">
 <logic:present property="tickets" name="wizardStep3Form" >
	  	<logic:iterate id="ticketElemento" property="tickets" name="wizardStep3Form" indexId="indiceElemento" 	type="it.openprj.jTicketing.blogic.model.entity.Ticket"  >
		<table width="100%" border="0" cellpadding="3" cellspacing="3">
		  <tr>
		   <td rowspan="3" width="20"><html:radio property="selTicket"  value="<%=String.valueOf(indiceElemento.intValue())%>" /></td>
		    <th width="10%"><b>Biglietto:</b></th>
		    <td><p><bean:write name="ticketElemento"  property="titolo"/>  		
		    <logic:present property="prezziCategorie" name="ticketElemento">
		    (
		       <logic:iterate id="prezzoCategoria" indexId="indiceCategoria" property="prezziCategorie" name="ticketElemento" type="it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket">
		         <bean:write name="prezzoCategoria" property="descrizione" />
		       </logic:iterate>
		       )
	        </logic:present>
	        </p></td>
		  </tr>
		</table>	

		</logic:iterate>
	</logic:present>
	<div align="right">
	   <html:submit property="isChange" accesskey="V"  titleKey="button.Go" altKey="button.Go" styleClass="submit" ><bean:message key="button.Select" /></html:submit>
	</div>
 </div>	
 <br/>
 <h3><bean:message key="message.help"/> <bean:message key="message.configurationHelp"/> 4 <bean:message key="label.of"/> 5</h3>
<div class="right_articles">
<p><img src="./images/success.png" align="top"/> <bean:message key="message.ticketDateSelect"/></p><BR/>
<p><img src="./images/info.png" align="top"/> <bean:message key="message.ticketDateSelectNext"/></p><BR/>
<div align="right">
<html:form method="get" action="/processWizardStep4">
<p>
	<input type="button" accesskey="I" value="<bean:message key="button.Back" />" class="submit" onClick="javascript:history.back();" />
	<html:submit accesskey="V"  titleKey="button.Go"	altKey="button.Go" styleClass="submit" ><bean:message key="button.End" /></html:submit>
	</p>
</html:form>
</div>
</div>
 </div>
</html:form>


