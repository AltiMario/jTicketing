/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.MainContainer = Ext.extend(Ext.Panel, {
    id: 'jticketingMainContainer',
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
            
        }
    },
    initComponent: function () {
        Ext.apply(jticketing.views, {
            topContainer: new jticketing.views.TopContainer(),
            mainCarousel: new jticketing.views.MainCarousel()
        });


        Ext.apply(this, {
            items: [
                jticketing.views.topContainer,
                jticketing.views.mainCarousel
            ]
        });


        jticketing.views.MainContainer.superclass.initComponent.apply(this, arguments);
    }
});

Ext.reg('maincontainer', jticketing.views.MainContainer);