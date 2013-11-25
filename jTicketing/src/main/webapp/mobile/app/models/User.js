/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regModel('User', {
    fields: [
                { name: 'userName', type: 'string' },
                { name: 'password', type: 'password' },
                { name: 'email', type: 'email' }
            ],
    validations: [
                { type: 'presence', name: 'userName', message: "Enter Username" },
                { type: 'presence', name: 'password', message: "Enter Password" },                
                { type: 'presence', name: 'email', message: "Enter Email" },
                { type: 'format', name: 'email', matcher: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/, message: "Wrong Email Format" },
            ]
});