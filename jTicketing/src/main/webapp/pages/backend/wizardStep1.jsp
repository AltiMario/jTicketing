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
<h3><img src="./images/01_right.png" align="top"/><bean:message key="message.configurationHelp"/> 2 <bean:message key="label.of"/> 4</h3>
</div>
<div class="main_center">

<logic:present name="listaTurni" scope="session">
    <h3><bean:message key="label.roundsIncluded"/></h3>
	<logic:iterate id="turnoElemento" indexId="indiceElemento"	name="listaTurni" scope="session" type="it.openprj.jTicketing.blogic.model.entity.Turno">
	<html:form method="get" action="/processWizardStep1.do">
	<html:hidden property="dispatch" value="remove"/>
	<html:hidden property="id" value="<%=String.valueOf(indiceElemento)%>"/>
        <div style="background-color: #f5f5f5"> <p>
            <bean:message key="label.timeOrRound"/>
            <html:text name="turnoElemento" property="orario"	titleKey="label.timeOrRound" altKey="label.timeOrRound" styleClass="jsrequired {errorLocation:'none'}" disabled="true"/>
            <bean:message key="label.openTime"/>
            <html:text name="turnoElemento" property="orarioApertura"	titleKey="label.openTime" altKey="label.openTime" styleClass="jsrequired {errorLocation:'none'}" disabled="true"/>
            <bean:message key="label.number"/>
            <html:text name="turnoElemento" property="quantita" titleKey="label.number" altKey="label.number"	styleClass="jsrequired {errorLocation:'none'}" disabled="true"/>
        </div>
	</html:form>
	</logic:iterate>
	 <br/>
</logic:present> 

<div style="background-color: #f5f5f5">
<html:form method="post" action="/processWizardStep1.do">
<html:hidden property="dispatch" value="add"/>
	<h3><bean:message key="label.entryForm"/></h3><br/>
	<p>
		<bean:message key="label.timeOrRound"/>
		<html:text property="orario" titleKey="label.timeOrRound" altKey="label.timeOrRound" styleClass="jsrequired jsvalidate_number {errorLocation:'none'}" />
 		<bean:message key="label.openTime"/>
		<html:text property="orarioApertura" titleKey="label.openTime" altKey="label.openTime" styleClass="jsrequired jsvalidate_number {errorLocation:'none'}" />
        <bean:message key="label.number"/>
    	<html:text property="quantita" titleKey="label.number" altKey="label.number"	styleClass="jsrequired {errorLocation:'none'}" />
    	<html:submit accesskey="V" titleKey="button.Go" altKey="button.Go"	styleClass="submit" property="btnAdd"><bean:message key="button.add" /></html:submit>
    </p>
</html:form>
</div>

</div>
<div class="main_center">
<h3><bean:message key="message.help"/> <bean:message key="message.configurationHelp"/> 2 <bean:message key="label.of"/> 4</h3>
<div class="right_articles">
<p><img src="./images/success.png" align="top"/> <bean:message key="message.timesOrRoundsListExample"/></p><BR/>
<p><img src="./images/info.png" align="top"/> <bean:message key="message.timesOrRoundsListExampleNextStep"/></p><BR/>
<p><img src="./images/success.png" align="top"/> <bean:message key="message.timesOrRoundsListExampleDetails"/></p><BR/>
<p><img src="./images/info.png" align="top"/> <bean:message key="message.timesOrRoundsListExampleDetailsNext"/></p><BR/>
<div align="right">
<html:form method="get" action="/processWizardStep2.do">
	<p>
	<html:hidden property="dispatch" value="add"/>
	<input type="button" accesskey="I" value="<bean:message key="button.Back" />" class="submit" onClick="javascript:history.back();" />
	<html:submit accesskey="V"  property="btnAvanti" titleKey="button.Go"	altKey="button.Go" styleClass="submit" ><bean:message key="button.Forward" /></html:submit>
	</p>
</html:form></div>
</div>
</div>
<div>&nbsp;</div>