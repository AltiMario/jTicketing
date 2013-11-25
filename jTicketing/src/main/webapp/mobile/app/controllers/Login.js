﻿/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regController("login", {
    submitLoginForm: function () {
        var loginModel = Ext.ModelMgr.create(jticketing.views.loginForm.getValues(), 'Login');
        var loginErrors = loginModel.validate();
		if (loginErrors.isValid()) {
			Ext.Ajax.request({
				url: global.baseURL + 'rest/login/login',
				params: {
					userName: loginModel.data.userName,
					password: loginModel.data.password
				},
				success: function (result) {
					localStorage.setItem("loginstatus", true);
					var jsonData = Ext.util.JSON.decode(result.responseText);					
					var success = jsonData.success;
					if(jsonData.success=="true"){						
						jticketing.viewport.setActiveItem(jticketing.views.mainCarousel, 'slide');
						jticketing.views.loginForm.reset();
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
            var loginErrorMessages = '';
            Ext.each(loginErrors.items, function (item, i) {
                loginErrorMessages += item.message + "<br />";
            });
            Ext.Msg.alert("Errors", loginErrorMessages);
            return false;
        }
    },
	showLoginForm: function(){
	    jticketing.viewport.setActiveItem(0);
    },
    showRegistrationForm: function () {
        jticketing.viewport.setActiveItem(3, 'slide');
    },

    hideRegistrationForm: function () {
        jticketing.viewport.setActiveItem(1, 'slide');
        jticketing.views.bottomToolbar.setActiveItem(0);
    },

    doLogout: function () {
        jticketing.views.topContainer.setVisible(true);
        jticketing.views.topContainer.removeAll();
         
        jticketing.views.topContainer.removeDocked(jticketing.views.topContainer.dockedItems.items[0]);
		jticketing.viewport.setActiveItem(1);
        jticketing.views.bottomToolbar.setActiveItem(0);
        jticketing.views.bottomToolbar.items.items[2].tab.setVisible(true);
        jticketing.views.bottomToolbar.items.items[3].tab.setVisible(true);
        jticketing.views.bottomToolbar.items.items[4].tab.setVisible(false);
    }
});

jticketing.controllers.login = Ext.ControllerManager.get("login");