<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="it.openprj.jTicketing.core.common.Constants" %>

<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<script>
    function ShowWaitWindow(text1, text2){
        Ext.MessageBox.show({
           msg: text1,
           progressText: text2,
           width:300,
           wait:true,
           waitConfig: {interval:1000},
           icon:'ext-mb-download'
       });
       
    };
</script>
 
<logic:messagesPresent message="true" property="<%=Constants.MESSAGES_ERROR%>">
  <script>
     var errormessage='';
     <html:messages id="errorMessage" property="<%=Constants.MESSAGES_ERROR%>" message="true">
	    errormessage+= '<bean:write name="errorMessage"/><br />';
	 </html:messages>
	     Ext.onReady(function(){
		     Ext.MessageBox.show({
	           title: '> Errore',
	           msg: errormessage,
	           buttons: Ext.MessageBox.OK,
	           animEl: 'errorMessage',
	           icon: Ext.MessageBox.ERROR
	       });
         });
   </script>
</logic:messagesPresent>

<logic:messagesPresent message="true" property="<%=Constants.MESSAGES_INFO%>">
  <script>
  	 var infomessage='';
     <html:messages id="infoMessage" property="<%=Constants.MESSAGES_INFO%>" message="true">
	    infomessage+= '<bean:write name="infoMessage"/><br />';
	 </html:messages>
	     Ext.onReady(function(){
		     Ext.MessageBox.show({
	           title: '> Informazione',
	           msg: infomessage,
	           buttons: Ext.MessageBox.OK,
	           animEl: 'infoMessage',
	           icon: Ext.MessageBox.INFO
	       });
         });
   </script>
</logic:messagesPresent>

<logic:messagesPresent message="true" property="<%=Constants.MESSAGES_WARNING%>">
  <script>
     var warningmessage='';
     <html:messages id="warningMessage" property="<%=Constants.MESSAGES_WARNING%>" message="true">
	    warningmessage+= '<bean:write name="warningMessage"/><br />';
	 </html:messages>
	     Ext.onReady(function(){
		     Ext.MessageBox.show({
	           title: '> Avviso',
	           msg: warningmessage,
	           buttons: Ext.MessageBox.OK,
	           animEl: 'warningMessage',
	           icon: Ext.MessageBox.WARNING
	       });
         });
   </script>
</logic:messagesPresent>

<logic:messagesPresent message="true" property="<%=Constants.RAPID_INFO%>">
	<script>
	 var rapidmessage='';
	  <html:messages id="rapidMessage" property="<%=Constants.RAPID_INFO%>" message="true">
	    rapidmessage= '<bean:write name="rapidMessage"/><br />';
	  </html:messages>
	   Ext.onReady(function(){
	      ShowStatus(rapidmessage);
	   });
	</script>
</logic:messagesPresent>