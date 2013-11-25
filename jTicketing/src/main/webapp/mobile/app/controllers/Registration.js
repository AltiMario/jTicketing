﻿/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regController("registration", {
    submitRegistrationForm: function () {
        var userModel = Ext.ModelMgr.create(jticketing.views.registrationForm.getValues(), 'User');
        var registrationErrors = userModel.validate();
        if (registrationErrors.isValid()) {
            Ext.Ajax.request({
				url: global.baseURL + 'rest/register/register',
				params: {
					userName: userModel.data.userName,
					password: userModel.data.password,
					email: userModel.data.email
				},
				success: function (result) {
					var jsonData = Ext.util.JSON.decode(result.responseText);					
					var success = jsonData.success;
					if(jsonData.success=="true"){						
						jticketing.viewport.setActiveItem(1, 'slide');
						jticketing.views.registrationForm.reset();
						jticketing.views.bottomToolbar.setActiveItem(0);
						jticketing.views.bottomToolbar.items.items[2].tab.setVisible(false);
						jticketing.views.bottomToolbar.items.items[3].tab.setVisible(false);
						jticketing.views.bottomToolbar.items.items[4].tab.setVisible(true);			
					}else{
						Ext.Msg.alert("Error", jsonData.message);
					}
					
				},
				failure: function(result){
					Ext.Msg.alert("Error", result.responseText);
				}
			});			
        }
        else {
            var registrationErrorsMessages = '';
            Ext.each(registrationErrors.items, function (item, i) {
                registrationErrorsMessages += item.message + "<br />";
            });
            Ext.Msg.alert("Errors", registrationErrorsMessages);
            return false;
        }
    }
});

jticketing.controllers.registration = Ext.ControllerManager.get("registration");