/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regModel('Login', {
    fields: [
                { name: 'userName', type: 'string' },
                { name: 'password', type: 'password' }
            ],
    validations: [
                { type: 'presence', name: 'userName', message: "Enter Username" },
                { type: 'presence', name: 'password', message: "Enter Password" }
            ]
});