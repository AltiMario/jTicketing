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

<html:form method="get" action="/processLuoghiInteresseEdit.do">
<input type="hidden" name="dispatch" value="start"/>
<div class="main_center">
<h3><bean:message key="label.placesAllocated"/></h3>
<logic:present name="listaLuoghiInteresse" scope="session">
	<logic:iterate id="objElemento" name="listaLuoghiInteresse"
		indexId="indiceElemento" scope="session">
		<div class="left_box2">
		<table width="100%" border="0">
			<tr>
			    <td colspan="2"><html:radio property="selLuogoInteresse"  value="<%=String.valueOf(indiceElemento.intValue())%>" /></td>
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
</logic:present>
</div>
<div class="main_center">
<h3><bean:message key="message.help"/></h3>
<div class="right_articles">
<p><img src="./images/messages/success.png" align="top"/> <bean:message key="message.selectPlaceAndPushButton"/></p><BR/>
<p><img src="./images/messages/info.png" align="top"/> <bean:message key="message.informationAboutCategoryTicketsAndPrice"/></p><BR/>
<p><img src="./images/messages/alert.png" align="top"/><b><bean:message key="message.attentionStartConfiguration"/></b></p><BR/>
<div align="right">
	<html:submit accesskey="V"  property="btnAvanti" titleKey="button.Go"
		altKey="button.Go" styleClass="submit" ><bean:message key="button.Forward" /></html:submit>
</div>
</div>
</div>
</html:form>
<div>&nbsp;</div>