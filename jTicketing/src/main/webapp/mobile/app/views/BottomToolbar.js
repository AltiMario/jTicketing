/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.BottomToolbar = Ext.extend(Ext.TabPanel, {
    tabBar: {
        dock: 'top',
        layout: {
            pack: 'left'
        }
    },
    ui: 'plain',
    dock: 'top',
    scroll: 'horizontal',
    
    listeners: {
        cardswitch: function (container, newCard, oldCard, index, isAnimate) {
            if (newCard.id == 'btnCarrello') {
                jticketing.viewport.setActiveItem(2);
                Ext.dispatch({
                    controller: jticketing.controllers.ticket,
                    action: 'showCart'
                });
            }
            else if (newCard.id == 'btnHome') {
                jticketing.viewport.setActiveItem(1);
                Ext.dispatch({
                    controller: jticketing.controllers.place,
                    action: 'loadPlaces'
                });
            }
            else if (newCard.id == 'btnLogin') {
                jticketing.viewport.setActiveItem(0);
            }
            else if (newCard.id == 'btnRegister') {
                Ext.dispatch({
                    controller: jticketing.controllers.login,
                    action: 'showRegistrationForm'
                });
            }
            else if (newCard.id == 'btnLogout') {
                Ext.dispatch({
                    controller: jticketing.controllers.login,
                    action: 'doLogout'
                });
            }
        },
        afterrender: function (tab) {
            jticketing.views.bottomToolbar.items.items[4].tab.setVisible(false);
        }
    },
    items: [
        {
            id: 'btnHome',
            title: 'Home'
        },
        {
            id: 'btnCarrello',
            title: 'Carrello'
        },
        {
            id: 'btnLogin',
            title: 'Login'
        },
        {
            id: 'btnRegister',
            title: 'Register'
        },
        {
            id: 'btnLogout',
            title: 'Log Out'
        }
    ],
    initComponent: function () {
        jticketing.views.BottomToolbar.superclass.initComponent.apply(this, arguments);
    }
});

Ext.reg('bottomtoolbar', jticketing.views.BottomToolbar);