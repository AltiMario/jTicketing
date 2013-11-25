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

<html:form action="/processGestioneLuoghiInteresseNew.do" method="post" enctype="multipart/form-data">
<bean:define id="objElemento" name="dettaglioLuogoInteresse"
	scope="session"
	type="it.openprj.jTicketing.blogic.model.entity.LuogoInteresse">
</bean:define>
<html:hidden property="dispatch" value="edit"/>
<html:hidden name="objElemento" property="uid" /> 
<div class="main_center">
	<div class="left_box2">
	<table width="100%" border="0">
		<tr>
	    	<td align="left">
	    		<bean:message key="label.title"/>
	    	</td>
	    	<td align="left">	
	    		<html:text name="objElemento" property="titolo" />
	    	</td>
	  	</tr>
        <tr>
	    	<td align="left">
				<bean:message key="label.description"/>
			</td>
	    	<td align="left">
				<html:textarea name="objElemento" property="descrizione" cols="70" rows="14" />
			</td>
		</tr>
		<tr>
			<td align="left">
				<bean:message key="label.image"/>
			</td>
	    	<td align="left">
				<html:file property="theFile" />
			</td>
		</tr>
	  	<tr>
	  		<td align="center" colspan="2">
	  			<html:submit property="btnAnnulla"  styleClass="submit" ><bean:message key="button.Cancel" /></html:submit>
	  			<html:submit property="btnSalva"  styleClass="submit" ><bean:message key="button.Save" /></html:submit>
	  		</td>
	  	</tr>
	</table>
	</div>
</div>
</html:form>
<div>&nbsp;</div>