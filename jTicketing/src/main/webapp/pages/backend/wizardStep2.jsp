<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.util.*" %>
<%@ page import="java.text.MessageFormat" %><%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<%


        String username, password;
        username= "numeroPosto";
        if(username.equalsIgnoreCase("numeroPosto"))
                username = "bene";
        else
                username = "non bene";
                if(request.getParameter("txtPassword") == null)
                password = "";
        else
                password = request.getParameter("txtPassword");
%>

                           <%
//                            if(strVal.equalsIgnoreCase("cinema")){//String strVal = request.getParameter("nameTypeGenus");
//                               System.out.println("valore"+ strVal);
//                            if(strVal.equalsIgnoreCase("cinema")){
//                               System.out.println("</tr></table>");
//                                 System.out.println("<div id=\"cinema1\" style=\"display: block;\"> ");
//                                    System.out.println("<table width=\"100%\" border=\"0\" cellpadding=\"3\" cellspacing=\"3\">");
//                                    System.out.println("<tr><th width=\"10%\"><b><bean:message key=\"label.typeOrganizer\"/></b></th> ");
//                                     System.out.println("<td ><bean:write name=\"ticketElemento\" property=\"typeOrganizer\"/></td></tr>");%>

<SCRIPT LANGUAGE="JavaScript" SRC="./js/utility.js" type="text/javascript"></SCRIPT>

<div class="stepContainer">
    <h3><img src="./images/01_right.png" align="top"/><bean:message key="message.configurationHelp"/> 3 <bean:message key="label.of"/> 4</h3>
</div>

<div class="main_center">
	<logic:present name="listaTickets" scope="session">
    	<h3><bean:message key="label.ticketsIncluded"/></h3>
		<logic:iterate id="ticketElemento" indexId="indiceElemento"	name="listaTickets" scope="session" type="it.openprj.jTicketing.blogic.model.entity.Ticket">
		<html:form action="/processWizardStep2" method="post" >
	 		<html:hidden property="dispatch" value="remove"/>
			<html:hidden property="id" value="<%=String.valueOf(indiceElemento)%>"/>

			<div style="background-color: #f5f5f5">
				<table width="100%" border="0" cellpadding="3" cellspacing="3">
	  				<tr>
	   					<td rowspan="3" width="100">
	   						<img src="./ImageProcessor?maxWidth=100&maxHeight=60&sessionFile=ticketImage_<%=ticketElemento.getNomeFile()%>" />
	   					</td>
	    				<th width="10%"><b><bean:message key="label.ticketTitle"/></b></th>
	    				<td>
	    					<bean:write name="ticketElemento"  property="titolo"/>
	    				</td>
	  				</tr>
	  				<tr>
	    				<th width="10%"><b><bean:message key="label.ticketDescription"/></b></th>
	    				<td>
	    					<bean:write name="ticketElemento" property="descrizione" />
	    				</td>
	  				</tr>
                     <tr>
	    				<th width="10%"><b><bean:message key="label.PlaceNumber"/></b></th>
	    				<td>
	    					<%--<bean:write name="ticketElemento" property="numeroPosto"/>--%>
                            <%=username %>
	    				</td>
	  				</tr>
                    <tr>
	    				<th width="10%"><b><bean:message key="label.SPlaceOrderCode"/></b></th>
	    				<td>
	    					<bean:write name="ticketElemento" property="SCodicePosto"/>
	    				</td>
	  				</tr>

                    <tr>
                        <th width="10%"><b><bean:message key="label.STypeGenus" /></b></th>

                        <td>
                           <bean:write name="ticketElemento" property="nameTypeGenus" />
                        </td>

                               </tr></table>
                                 <div id="cinema1" style="display: block;">
                                    <table width="100%" border="0" cellpadding="3" cellspacing="3">
                                    <tr><th width="10%"><b><bean:message key="label.typeOrganizer"/></b></th>
                                     <td ><bean:write name="ticketElemento" property="typeOrganizer"/></td></tr>
                                    <tr>
                                         <th width="10%"><b><bean:message key="label.filmNationality" /></b></th>
                                        <td>
                                            <bean:write name="ticketElemento" property="acronyms"/>
                                        </td>
                                    </tr>
                                    <tr>
                                          <th width="10%"><b><bean:message key="label.numberOperas"/></b></th>
                                         <td>
                                             <bean:write name="ticketElemento" property="numberOperas"/>                                         </td>                                    </tr>
                                </table></div>
                     <div id="opera1" style="display: none;">
                        <table width="100%" border="0" cellpadding="3" cellspacing="3">
                    <tr>
                          <th width="10%"><b><bean:message key="label.author"/></b></th>
                         <td>
                             <bean:write name="ticketElemento" property="author"/>
                         </td>
                     </tr>
                     <tr>
                          <th width="10%"><b><bean:message key="label.executor"/></b></th>
                         <td>
                            <bean:write name="ticketElemento" property="executor"/>
                         </td>
                     </tr>
                     <tr>
                              <th width="10%"><b><bean:message key="label.numberOperas"/></b></th>
                             <td>
                                 <bean:write name="ticketElemento" property="numberOperas"/>
                             </td>
                        </tr>
                     </table>
                     </div>
                     <div id="concerto1" style="display: none;">
                        <table width="100%" border="0" cellpadding="3" cellspacing="3">
                          <tr>
                          <th width="10%"><b><bean:message key="label.executor"/></b></th>
                         <td>
                            <bean:write name="ticketElemento" property="executor"/>
                         </td>
                     </tr>
                    </table>
                         </div>
                 <table width="100%" border="0" cellpadding="3" cellspacing="3">
                    <tr>
	    				<th width="10%"><b><bean:message key="label.SEventType"/></b></th>
	    				<td>
	    					<bean:write name="ticketElemento" property="STipologiaEvento"/>&nbsp;<bean:write name="ticketElemento" property="SIncidenza"/>

	    				</td>
	  				</tr>

                    <tr>
	    				 <th width="10%">
	    					<b><bean:message key="label.category"/></b>
	    				</th>
		    			<td>
			    			<logic:present property="prezziCategorie" name="ticketElemento">
			       				<logic:iterate id="prezzoCategoria" indexId="indiceCategoria" property="prezziCategorie" name="ticketElemento" type="it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket">
			         				<bean:write name="prezzoCategoria" property="descrizione" />
			       				</logic:iterate>
			    			</logic:present>
			    		</td>
	  				</tr>
	  				<tr>
	    				<td colspan="3" align="right">
	    					<html:submit accesskey="-"  titleKey="button.Go" altKey="button.Go" styleClass="submit" property="btnRemove"><bean:message key="button.Delete" /></html:submit>
	    				</td>
	  				</tr>
				</table>	
		</div><br/>
	</html:form>
	</logic:iterate>
<br/>
</logic:present> 

<html:form action="/processWizardStep2" method="post" enctype="multipart/form-data">

    <bean:define id="objTypeGenus" name="wizardStep2Form" type="it.openprj.jTicketing.backend.forms.TicketForm"/>
   <html:hidden property="dispatch" value="add"/>
   <h3><bean:message key="label.entryForm"/></h3>
	<div style="background-color: #f5f5f5;">
  <table width="100%" border="0" cellpadding="3" cellspacing="3"> 
  <tr>
    <td><bean:message key="label.ticketTitle"/></td>
    <td align="right"><html:text property="titolo" titleKey="label.ticketTitle" altKey="label.ticketTitle" styleClass="jsrequired {errorLocation:'none'}" value="" /></td>
  </tr>
  <tr>
    <td><bean:message key="label.ticketDescription"/></td>
    <td align="right"><html:textarea property="descrizione" titleKey="label.ticketDescription" altKey="label.ticketDescription" styleClass="jsrequired {errorLocation:'none'}" cols="48" rows="3" value="" /></td>
  </tr>
  <tr>
    <td><bean:message key="label.picture"/></td>
    <td align="right"><html:file property="theFile" titleKey="label.picture" altKey="label.picture" styleClass="jsrequired {errorLocation:'none'}"/></td>
  </tr>
  <tr>
    <td><bean:message key="label.category"/></td>
    <td align="right">
    <logic:present name="listaPrezziCategorie" scope="session">
	    <logic:iterate id="categoriaElemento" indexId="indiceElemento"	name="listaPrezziCategorie" scope="session" type="it.openprj.jTicketing.blogic.model.entity.PrezzoCategoriaTicket">
		 <bean:write name="categoriaElemento" property="descrizione"/>: 
		 <html:checkbox  property="categorie" value="<%=String.valueOf(indiceElemento.intValue())%>" titleKey="label.category" altKey="label.category" styleClass="jsrequired {errorLocation:'none'}"/>
		</logic:iterate> 
	</logic:present>
	</td>
  </tr>
  <tr>
    <td><bean:message key="label.PlaceNumber"/></td>
    <td align="right"><html:text property="numeroPosto" name="numeroPosto" titleKey="label.PlaceNumber" altKey="label.PlaceNumber" styleClass="jsrequired {errorLocation:'none'}" value="" /></td>
  </tr>
      <tr>
        <td><bean:message key="label.SPlaceOrderCode"/></td>
        <td align="right"><html:text property="SCodicePosto" titleKey="label.SPlaceOrderCode" altKey="label.SPlaceOrderCode" styleClass="jsrequired {errorLocation:'none'}" value="" /></td>
      </tr>

      <tr>
        <td><bean:message key="label.STypeGenus"/></td>
        <td align="right">

              <html:select property="idTypeGenus" onchange="changeBack(this.form)" name="objTypeGenus">
                 <logic:iterate id="typegenuslistId" name="objTypeGenus" property="typegenusList">
                 <option value="<bean:write name="typegenuslistId" property="idTypeGenus" />">
                        <bean:write name="typegenuslistId" property="description" />
                 </option>
                 </logic:iterate>
              </html:select>
        </td>
      </tr>
</table>
    <div id="cinema" style="display: none;">
          <table width="100%" border="0" cellpadding="3" cellspacing="3">
            <tr>
          <tr>
             <td><bean:message key="label.typeOrganizer"/></td>
              <td align="right">
                  <html:select property="typeOrganizer" >
                    <html:option value="Essay">Essay</html:option>
                    <html:option value="Parrocchiale">Parrocchiale</html:option>
                    <html:option value="Generico">Generico</html:option>
                  </html:select>
             </td>
          </tr>
         <tr>
             <td><bean:message key="label.filmNationality"/></td>
             <td align="right">
                <html:select property="acronyms" >
                     <logic:iterate id="filmNationalityId" name="objTypeGenus" property="filmNationalityList">
                     <option value="<bean:write name="filmNationalityId" property="acronyms" />">
                            <bean:write name="filmNationalityId" property="nation" />
                            (<bean:write name="filmNationalityId" property="acronyms" />)
                     </option>
                     </logic:iterate>
                  </html:select>
             </td>

         </tr>
         <tr>
             <td ><bean:message key="label.numberOperas"/></td>
             <td align="right"><html:text property="numberOperas" titleKey="label.numberOperas" altKey="label.numberOperas" styleClass="jsrequired {errorLocation:'none'}" value="" /></td>


         </tr>
              </table>
     </div>
         <div id="concerto" style="display: none;">
          <table width="100%" border="0" cellpadding="3" cellspacing="3">

         <tr>
             <td ><bean:message key="label.executor"/></td>
             <td align="right"><html:text property="executor" titleKey="label.executor" altKey="label.executor" styleClass="jsrequired {errorLocation:'none'}" value="" /></td>


         </tr>
              </table>
     </div>

        <div id="opera" style="display: none;">
          <table width="100%" border="0" cellpadding="3" cellspacing="3">
          <tr>
             <td ><bean:message key="label.author"/></td>
             <td align="right"><html:text property="author" titleKey="label.author" altKey="label.author" styleClass="jsrequired {errorLocation:'none'}" value="" /></td>
         </tr>
         <tr>
             <td ><bean:message key="label.executor"/></td>
             <td align="right"><html:text property="executor" titleKey="label.executor" altKey="label.executor" styleClass="jsrequired {errorLocation:'none'}" value="" /></td>
         </tr>
         <tr>
             <td ><bean:message key="label.numberOperas"/></td>
             <td align="right"><html:text property="numberOperas" titleKey="label.numberOperas" altKey="label.numberOperas" styleClass="jsrequired {errorLocation:'none'}" value="" /></td>
         </tr>

              </table>
     </div>
        <table width="100%" border="0" cellpadding="3" cellspacing="3">
            <tr>
        <td><bean:message key="label.SEventType"/></td>
     </tr>
     <tr>
        <td></td><td align="right"><bean:message key="label.SShow"/> <input type="radio" name="STipologiaEvento" value="1" checked>
     </td>
     </tr>
     <tr>
        <td></td>
         <td align="right"><bean:message key="label.SEntertainment"/> <html:radio property="STipologiaEvento" value="2" /></td>
     </tr>
     <tr>
        <td></td>
         <td align="right"><bean:message key="label.SIncidence"/> <html:radio property="STipologiaEvento" value="3" />
        <html:text property="SIncidenza" titleKey="label.SIncidence" altKey="label.SIncidence" styleClass="jsrequired {errorLocation:'none'}" value="" />
     </td>

     </tr>

      <tr>
    <td colspan="2" align="right"><html:submit accesskey="I"  titleKey="button.Go" altKey="button.Go" styleClass="submit"  property="btnAdd"><bean:message key="button.add" /></html:submit></td>
  </tr></table>

</div>

</html:form>


</div>
<div class="main_center">
<h3><bean:message key="label.categoryDescription"/> <bean:message key="message.configurationHelp"/> 3 <bean:message key="label.of"/> 4</h3>
<div class="right_articles">
<p><img src="./images/success.png" align="top"/> <bean:message key="message.ticketSalableExample"/></p><BR/>
<p><img src="./images/info.png" align="top"/> <bean:message key="message.ticketSalableDetailsExample"/></p><BR/>
<p><img src="./images/info.png" align="top"/> <bean:message key="message.ticketSalableDetailsNextExample"/></p><BR/>
<div align="right">
<html:form method="get" action="/processWizardStep3.do">
	<p>
	<html:hidden property="dispatch" value="add"/>
	<input type="button" accesskey="I" value="<bean:message key="button.Back" />" class="submit" onClick="javascript:history.back();"/>
	<html:submit accesskey="V"  titleKey="button.Go"	altKey="button.Go" styleClass="submit"  property="btnAvanti"><bean:message key="button.Forward" /></html:submit>
	</p>
</html:form></div>
</div>
</div>
<div>&nbsp;</div>