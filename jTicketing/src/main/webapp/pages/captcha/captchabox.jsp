<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/jcaptcha.tld" prefix="jcaptcha"%>
<%--
  jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

  Copyright (C) 2010-2012 OpenPRJ s.r.l.
  All rights reserved

  Site: http://www.openprj.it
  Contact:  info@openprj.it
  --%>

<%
	response.setHeader("CacheControl", "no-cache");
    response.setHeader("pragma", "no-cache");
    response.setHeader("Expires", "-1");
%>

<script type="text/javascript" src="<html:rewrite page="/dwr/interface/CaptchaDWR.js"/>"></script>
<script type="text/javascript" src="<html:rewrite page="/dwr/interface/CheckUsernameDWR.js"/>"></script>
<script type="text/javascript" src="<html:rewrite page="/dwr/engine.js"/>"></script>
<script type="text/javascript" src="<html:rewrite page="/dwr/util.js"/>"></script>

<div id="jcaptchaBox">
	<script type='text/javascript'>
	    var reply0 = function(data)
		{
		      dwr.util.setValue('jcaptchaBox', data, { escapeHtml:false });
		}
	</script>
	<fieldset> 
		<legend class="legend"><bean:message key="message.verificationCode" /></legend>
			<table border="0" width="100%">
				<tr>
			    	<td>
			    		<img src="jcaptcha.do"/>
			    	</td>
				    <td>
				    	<p>
				    		<bean:message key="message.insertLetters" /><br/>
				    		<input type="text" name="jcaptcha_response" /><br/>
				    		<bean:message key="error.requiredField" />
				    	</p>
				    </td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<bean:message key="message.ImageCaptcha"/> <a href="./register.do?captcha=renew"><bean:message key="message.ImageCaptcha1"/></a> <bean:message key="message.ImageCaptcha2"/>
					</td>
				</tr>
			  </table>
	</fieldset>
</div>
