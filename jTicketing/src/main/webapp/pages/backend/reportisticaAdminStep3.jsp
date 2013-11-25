<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<div class="main_center">
		<h3>Reportistica</h3>
</div>
<div class="main_center">
	<table width="100%" border="0" cellspacing="5" class="left_box2">
		<tr>
		    <td class="gestioneOperatoriLabel">
				<bean:message key="label_re.year"/>
			</td>
			<td class="gestioneOperatoriLabel">
				<bean:message key="label_re.month"/>
			</td>
			<td class="gestioneOperatoriLabel">
				<bean:message key="label_re.day"/>
			</td>
			<td class="gestioneOperatoriLabel">
				<bean:message key="label_re.amount"/>
			</td>
			<td class="gestioneOperatoriLabel">
				<bean:message key="label_re.amountRemained"/>
			</td>
			<td class="gestioneOperatoriLabel">
				<bean:message key="label_re.sold"/>
			</td>
			<td class="gestioneOperatoriLabel">
				<bean:message key="label_re.validated"/>
			</td>
			<td class="gestioneOperatoriLabel">
				<bean:message key="label_re.built"/>
			</td>
		</tr>
		<logic:iterate id="objElemento" name="listaReportisticaTickets" indexId="indiceElemento" scope="session">
		<tr>
			<td align="left">
		 		<bean:write name="objElemento" property="anno" />
			</td>
			<td align="left">
		 		<bean:write name="objElemento" property="mese" />
			</td>
			<td align="left">
		 		<bean:write name="objElemento" property="giorno" />
			</td>
		 	<td align="left">
		 		<bean:write name="objElemento" property="quantita" />
			</td>
			<td align="left">
		 		<bean:write name="objElemento" property="quantitaResidua" />
			</td>
			<td align="left">
		 		<bean:write name="objElemento" property="venduti" />
			</td>
			<td align="left">
		 		<bean:write name="objElemento" property="convalidato" />
			</td>
			<td align="left">
		 		<bean:write name="objElemento" property="incasso" />
			</td>
		</tr>
		</logic:iterate>
	</table>
</div>
<div>&nbsp;</div>