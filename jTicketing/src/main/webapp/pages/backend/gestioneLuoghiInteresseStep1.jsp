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

<logic:iterate id="objElemento" name="listaLuoghiInteresse" indexId="indiceElemento" scope="session">
<div class="main_center">
	<h3><a href="./processGestioneLuoghiInteresseStep2.do?dispatch=get&uid=<bean:write name="objElemento" property="uid" />"><bean:write name="objElemento" property="titolo" /></a></h3>
	<div class="left_box2">
	<table width="100%" border="0">
		<tr>
			<logic:notEqual name="objElemento" property="immagine" value="No">
				<td align="left">
					<img src="./ImageProcessor?maxWidth=300&maxHeight=300&type=1&uid=<bean:write name="objElemento" property="uid" />" />
				</td>
			</logic:notEqual>
			<td align="justify" valign="top">
			    <bean:write name="objElemento" property="descrizione" />
			</td>
		</tr>
	</table>
	</div>
</div>
</logic:iterate>
<form name="newLuogoInteresse" method="get" action="./processGestioneLuoghiInteresseNew.do">
<input type="hidden" name="dispatch" value="get"/>
<div align="center">
	<input type="submit" name="new" class="submit" value="<bean:message key="label.new"/>" title="<bean:message key="label.new"/>" />
</div>
</form>
<div>&nbsp;</div>