<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<bean:cookie id="uname" name="eTicketingUsername" value=""/>
<%@ page import="it.openprj.jTicketing.core.common.Constants" %>
<noscript>
	<logic:messagesPresent message="true" property="<%=Constants.MESSAGES_ERROR%>">
		<div class="main_center">
			<html:messages id="errorMessage" property="<%=Constants.MESSAGES_ERROR%>" message="true">
				<h3><img src="./images/error.png" align="top" />&nbsp;<bean:write name="errorMessage"/></h3>
			</html:messages>
		</div>
	</logic:messagesPresent>
</noscript>
<div class="main_center">
	<html:form action="/login" method="post">
    	<table width="100%" border="0"> 
      	<tr>
        	<td width="50%">
	        	<fieldset> 
	          	<legend class="legend"><bean:message key="message.logInToYourAccount" /></legend>
	          	<br>
	          	<table  width="100%" border="0">
					<tr> 
	              		<td nowrap="nowrap">
	              			<bean:message key="label.UserID" />
	              		</td>
	              		<td align="center" width="100%">
	              			<p><input type="text" name="username" value="" size="19" maxlength="19"/></p>
	              		</td>
	            	</tr>   
	            	<tr>
	              		<td>&nbsp;</td>
	              		<td>
	                		<html:link page="/forgotUserid.do">
	                			<bean:message key="message.forgottenUserId" />
	                		</html:link>
	               		</td>
	            	</tr>
	            	<tr>
		              	<td nowrap="nowrap">
		              		<bean:message key="label.Password" />
		              	</td>
		              	<td align="center" width="100%">
		              		<p><input type="password" name="password" value="" size="19" maxlength="19"/></p>
		              	</td>
	            	</tr>
	            	<tr>
	              		<td>&nbsp;</td>
	              		<td>
	                 		<html:link page="/forgotPassword.do">
	                 			<bean:message key="message.forgottenPassword" />
	                 		</html:link>
	             		</td>
	            	</tr>
	            	<tr>
	              		<td align="right">&nbsp;</td>
	              		<td align="right">
	              			<input name="Submit" type="submit" value="<bean:message key="button.enter" /> " class="submit">
	              		</td>
	            	</tr>
	          	</table>
	          	</fieldset>
          	</td>
        	<td width="50%" valign="top">
        		<fieldset> 
          		<legend class="legend"><bean:message key="message.registration" /></legend>
           		<table width="100%" border="0">
            		<tr>
              			<td >
              				<bean:message key="message.buyTicketFromPc" /><br/>&nbsp;
               			</td>
            		</tr>
            		<tr>
              			<td align="right" >
              				<input name="Submit" type="button" value="<bean:message key="button.registered" /> " class="submit" onclick="javascript:location.href='<html:rewrite page="/prepareRegister.do"/>'"/>
              			</td>
            		</tr>
          		</table>
          		</fieldset> 
          	</td>
      	</tr>
      	<div id="fb-root"></div>
      <script>
        window.fbAsyncInit = function() {
          FB.init({
            appId      : '305781336147195',
            status     : true, 
            cookie     : true,
            xfbml      : true,
            oauth      : true,
          });
					
		FB.Event.subscribe('auth.login', function (response) {
			window.location = "socialNetwork.do?accessToken="+response.authResponse.accessToken;
		});
        };
        (function(d){
           var js, id = 'facebook-jssdk'; if (d.getElementById(id)) {return;}
           js = d.createElement('script'); js.id = id; js.async = true;
           js.src = "//connect.facebook.net/en_US/all.js";
           d.getElementsByTagName('head')[0].appendChild(js);
         }(document));
		
      </script>
	   <div class="fb-login-button" scope="user_photos,user_checkins,user_events,read_stream, publish_stream, email">Login with Facebook</div>
		</table>
	</html:form>
</div>
<div>&nbsp;</div>
