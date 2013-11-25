/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.RegistrationForm = Ext.extend(Ext.form.FormPanel, {
    initComponent: function () {
        Ext.apply(this, {
            items: [
				{
					xtype: 'fieldset',
					defaults: {
						labelAlign: 'left',
						labelWidth: '20%'
					},
					items: [                                        
						{
							xtype: 'emailfield',
							placeHolder: 'Username',
							name: 'userName',
							label: 'Username',
							id: 'userName',
							required: true
						}, {
							xtype: 'passwordfield',
							placeHolder: 'Password',
							label: 'Password',
							name: 'password',
							id: 'password',
							required: true
						}, {
							xtype: 'emailfield',
							placeHolder: 'Email',
							label: 'Email',
							name: 'email',
							id: 'email',
							required: true
						}
					]
				}
            ]
        });

        Ext.apply(this, {
            dockedItems: [
			{
				xtype: 'toolbar',
				dock: 'bottom',
				ui: 'dark',
				items: [
					{
						xtype: 'spacer'
					},
					{
						id: 'btnReset',
						ui: 'confirm',
						text: 'Reset',
						handler: function () {
							jticketing.views.registrationForm.reset();
						}
					},
					{
						id: 'btnSubmit',
					   ui: 'confirm',
						text: 'Submit',
						handler: function () {
							Ext.dispatch({
								controller: jticketing.controllers.registration,
								action: 'submitRegistrationForm',
								data: jticketing.views.registrationForm
							});
						}
					},                                                
					{
						id: 'btnCancelRegisteration',
						ui: 'action',
						text: 'Cancel',
						handler: function () {
							Ext.dispatch({
								controller: jticketing.controllers.login,
								action: 'hideRegistrationForm'
							});
						}
					}
				]
			}]
        });
        jticketing.views.RegistrationForm.superclass.initComponent.apply(this, arguments);
    }
});
Ext.reg('registrationform', jticketing.views.RegistrationForm);