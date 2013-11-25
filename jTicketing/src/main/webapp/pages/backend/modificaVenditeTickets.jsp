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

<html:form action="/processModificaVenditeTickets.do" method="post" enctype="multipart/form-data">
<html:hidden property="dispatch" value="edit"/>
<html:hidden name="dettaglioTicket" property="uid" />
<div class="main_center">
	<h3><bean:message key="label.EditTickets"/></h3>
	<div style="background-color: #f5f5f5;">
		<table width="100%" border="0" cellpadding="3" cellspacing="3">
		<tr>
	    	<td><bean:message key="label.ticketTitle"/></td>
	    	<td align="right"><html:text name="dettaglioTicket" property="titolo" /></td>
	  	</tr>
	  	<tr>
	    	<td><bean:message key="label.ticketDescription"/></td>
	    	<td align="right"><html:textarea name="dettaglioTicket" property="descrizione" cols="48" rows="3" /></td>
	  	</tr>
	  	<tr>
		  	<td><bean:message key="label.picture"/></td>
		  	<td align="right">
		  		<img src="./ImageProcessor?maxWidth=100&maxHeight=70&type=2&uid=<bean:write name="dettaglioTicket" property="uid" />" />
				<br />
				<html:file property="theFile" />
			</td>
	  	</tr>
	  	<tr>
	  		<td align="center" colspan="2">
	  			<html:submit property="btnRimuovi"  styleClass="submit" ><bean:message key="button.Delete" /></html:submit>
	  			<html:submit property="isChange"  styleClass="submit" ><bean:message key="button.Save" /></html:submit>
	  		</td>
	  	</tr>
	  	</table>
	</div>
</div>
</html:form>
<div>&nbsp;</div>