/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.Viewport = Ext.extend(Ext.Panel, {
    id: 'jticketingViewPort',
    fullscreen: true,
    layout: {
        type: 'card',
        align: 'stretch'
    },
    defaults: {
        flex: 1
    },
    listeners: {
        afterrender: function () {
            this.setActiveItem(1);
        }
    },
    initComponent: function () {

        Ext.apply(jticketing.views, {
            topToolBar: new jticketing.views.TopToolbar(),
            mainContainer: new jticketing.views.MainContainer(),
            bottomToolbar: new jticketing.views.BottomToolbar(),
            topContainer: new jticketing.views.TopContainer(),
            mainCarousel: new jticketing.views.MainCarousel(),
            loginForm: new jticketing.views.LoginForm(),
            registrationForm: new jticketing.views.RegistrationForm()
        });

        Ext.apply(this, {
            items: [
                jticketing.views.loginForm,
                jticketing.views.mainCarousel,
                jticketing.views.topContainer,
                jticketing.views.registrationForm
            ]
        });

        Ext.apply(this, {
            dockedItems: [jticketing.views.topToolBar, jticketing.views.bottomToolbar]
        });

        jticketing.Viewport.superclass.initComponent.apply(this, arguments);
    }
});