/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.TopContainer = Ext.extend(Ext.Panel, {
    id: 'jticketingTopContainer',
    
    autoScroll: true,
    scroll: 'vertical',

    listeners: {
        afterrender: function () {
            this.setHeight(0);
        }
    },
    initComponent: function () {
        jticketing.views.TopContainer.superclass.initComponent.apply(this, arguments);
    }
});

Ext.reg('topcontainer', jticketing.views.TopContainer);