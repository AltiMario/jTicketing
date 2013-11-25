/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regApplication({
    name: "jticketing",
    defaultTarget: "viewport",
    launch: function () {
        this.viewport = new jticketing.Viewport({
            application: this
        });
    }
});