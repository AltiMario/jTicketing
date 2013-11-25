/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.TopToolbar = Ext.extend(Ext.Toolbar, {
    dock: "top",
    align: "Right",
    ui: 'dark',
    height: '100',

    items: [
        {
            xtype: "container", html: "<img height='70' width='150' src='lib/resources/images/logo_jticketing.png'/>"
        }
    ],
    initComponent: function () {
        jticketing.views.TopToolbar.superclass.initComponent.apply(this, arguments);
    }
});

Ext.reg('toptoolbar', jticketing.views.TopToolbar);