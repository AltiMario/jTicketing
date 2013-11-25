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

<div class="stepContainer">
	<h3><img src="./images/01_right.png" align="top"/><bean:message key="message.configurationHelp"/> 1 <bean:message key="label.of"/> 4</h3>
</div>
<div class="main_center">
	<logic:present name="listaPrezziCategorie" scope="session">
	    <h3><bean:message key="label.categoriesIncluded"/></h3>
		<logic:iterate id="categoriaElemento" indexId="indiceElemento"	name="listaPrezziCategorie" scope="session" type="it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket">
		<html:form method="post" action="/processWizardStart.do">
		<html:hidden property="dispatch" value="remove"/>
		<html:hidden property="id" value="<%=String.valueOf(indiceElemento)%>"/>
		<table width="100%" border="0" cellspacing="1" cellpadding="1" style="background: #f5f5f5;">
		  <tr>
		    <td><bean:message key="label.categoryDescription"/> <b><bean:write name="categoriaElemento" property="descrizione"/></b></td>
		  </tr>
		  <tr>
		    <td><bean:message key="label.categoryPrice"/> <b><bean:write name="categoriaElemento" property="prezzo"/></b></td>
		  </tr>
          <tr>
		    <td><bean:message key="label.categoryConditions"/> <b><bean:write name="categoriaElemento" property="condizioni"/></b> </td>
		  </tr>
		</table>
		</html:form>
		<br />
		</logic:iterate>
	</logic:present> 
	<html:form method="post" action="/processWizardStart.do">
	    <html:hidden property="dispatch" value="add"/>
	    <table width="100%" border="0" cellspacing="3" cellpadding="3">
		  <tr>
		  	<td><bean:message key="label.categoryDescription"/></td>
		    <td align="left"><html:text property="categoria" titleKey="label.category" altKey="label.category" styleClass="jsrequired jsvalidate_number {errorLocation:'none'}" /> </td>
		  </tr>
		  <tr>
		    <td><bean:message key="label.categoryPrice"/></td>
		    <td align="left"><html:text property="prezzo" titleKey="label.euroPrice" altKey="label.euroPrice"	styleClass="jsrequired {errorLocation:'none'}" /></td>
		  </tr>
          <tr>
		    <td><bean:message key="label.categoryConditions"/></td>
		    <td align="left" colspan="3"><html:textarea cols="48" rows="3" property="condizioni" titleKey="label.euroPrice" altKey="label.euroPrice"	/></td>
		  </tr>
		  <tr>
		    <td colspan="4" align="right"><html:submit accesskey="V" titleKey="button.Go" altKey="button.Go" styleClass="submit"  property="btnAdd"><bean:message key="button.add" /></html:submit></td>
		  </tr>
		</table>
	</html:form>
</div>

<div class="main_center">
	<h3><bean:message key="message.help"/> <bean:message key="message.configurationHelp"/> 1 <bean:message key="label.of"/> 4</h3>
	<div class="right_articles">
		<p>
			<img src="./images/success.png" align="top"/> <bean:message key="message.categoryListExample"/>
		</p><br/>
		<p>
			<img src="./images/info.png" align="top"/> <bean:message key="message.categoryListExampleNext"/></p>
		<br/>
		<div align="right">
			<html:form method="get" action="/processWizardStep1.do"> 
  					<html:hidden property="dispatch" value="add"/>
				<html:submit accesskey="V"  titleKey="button.Go"	altKey="button.Go" styleClass="submit"  property="btnAvanti" ><bean:message key="button.Forward" /></html:submit>
			</html:form>
		</div>
	</div>
</div>
<div>&nbsp;</div>