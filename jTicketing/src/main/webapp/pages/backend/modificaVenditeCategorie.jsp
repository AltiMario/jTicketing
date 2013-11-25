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

<logic:iterate id="categoriaElemento" indexId="indiceElemento"	name="listaModificaVenditeCategorie" scope="session" type="it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket">
<div class="main_center">
	<h3><a href="./processModificaVenditeCategorie.do?dispatch=get" /><bean:write name="categoriaElemento" property="descrizione" /></a></h3>
	<div class="left_box2">
		<table width="100%" border="0">
			<tr>
				<td align="left" width="15%">
					<bean:message key="label.categoryPrice"/>
				</td>
				<td align="left">
					<b><bean:write name="categoriaElemento" property="prezzo"/></b>
				</td>
			</tr>
			<tr>
				<td align="left" width="15%">
					<bean:message key="label.categoryConditions"/>
				</td>
				<td align="left">
					<b><bean:write name="categoriaElemento" property="condizioni"/></b>
				</td>
			</tr>
		</table>
	</div>
</div>
</logic:iterate>
<div>&nbsp;</div>