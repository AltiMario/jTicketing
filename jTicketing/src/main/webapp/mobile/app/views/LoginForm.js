/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.LoginForm = Ext.extend(Ext.form.FormPanel, {
    initComponent: function () {
        var config = {
			layout: 'fit',
			fullscreen: true,
			scroll: 'vertical',
			dockedItems: [
				{
					dock: 'bottom',
					xtype: 'toolbar',
					type: 'light',
					items: [
						{
							xtype: 'spacer'
						},
						{
                            id: 'btnReset',
							text: 'Reset',
							ui: 'confirm',
                            handler: this.onResetAction,
                        },
						{
							id: 'btnLogin',
							text: 'Submit',
							ui: 'confirm',
							handler: this.onSubmitAction,
							scope: this
						},
						{
							id: 'btnCancelLogin',
							ui: 'action',
							text: 'Cancel',
							handler: this.onCancelAction,
							scope: this
						}
					]
				}
			],
			items: [
				{
					xtype: 'fieldset',
					//title: 'Login',
					instructions: 'Please enter the information above.',
					defaults: {
						required: true,
						labelAlign: 'left',
						labelWidth: '20%'
					},
					items: [{
                        xtype: 'emailfield',
						placeHolder: 'Username',
                        name : 'userName',
                        label: 'Username',
						id: 'userName',
                        useClearIcon: true,
                        autoCapitalize : false,
						required: true
					}, {
                        xtype: 'passwordfield',
						placeHolder: 'Password',
                        name : 'password',
                        label: 'Password',
						id: 'password', 
                        useClearIcon: true,
						required: true						
                    }]
				}
			]
		};
		Ext.apply(this, config);
        jticketing.views.LoginForm.superclass.initComponent.call(this);
	},
	onSubmitAction: function() {
		Ext.dispatch({
			controller: jticketing.controllers.login,
			action: 'submitLoginForm',
			data: jticketing.views.loginForm
		});
	},
	onResetAction: function() {
		jticketing.views.loginForm.reset();
	},
	onCancelAction:function () {
		Ext.dispatch({
			controller: jticketing.controllers.login,
			action: 'hideRegistrationForm'
		});
	}
		
});	
Ext.reg('loginform', jticketing.views.LoginForm);