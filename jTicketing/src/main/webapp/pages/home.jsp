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
	<h3 ><bean:write name="objElemento" property="titolo"/></h3>
	<div class="left_box2">
	<table width="100%" border="0">
		<tr>
			<logic:notEqual name="objElemento" property="immagine" value="No">
				<td align="left">
                    <a href="./processSearchDetail.do?uid=<bean:write name="objElemento" property="uid" />">
					    <img src="./ImageProcessor?maxWidth=300&maxHeight=300&type=1&uid=<bean:write name="objElemento" property="uid" />" />
                    </a>
				</td>
			</logic:notEqual>
			<td align="justify"  valign="top">

			   <bean:write name="objElemento" property="descrizione" />

			</td>
            </tr>
        <tr>
            <td></td>
            <td align="right"><input type="button" class="submit" value="<bean:message key="button.Choose" />" onclick="location.href='./processSearchDetail.do?uid=<bean:write name="objElemento" property="uid" />'"></td>
        </tr>
	</table>
	</div>

</div>
<div align="right">


    </div>
</logic:iterate>
<div>&nbsp;</div>