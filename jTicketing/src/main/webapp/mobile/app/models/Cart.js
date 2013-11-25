/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regModel('Cart', {
    fields: [
         {
             name: 'cartItem',
             type: 'string'
         },
         {
             name: 'cartItemQuantity',
             type: 'int'
         }
     ],
     idProperty: 'cartItem'
});