/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regModel('Month', {
    fields: [
         {
             name: 'monthText',
             type: 'string'
         },
         {
             name: 'monthValue',
             type: 'int'
         }
     ],
    idProperty: 'monthValue'
});