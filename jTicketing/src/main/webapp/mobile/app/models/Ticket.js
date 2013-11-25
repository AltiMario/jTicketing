/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regModel('Ticket', {
    fields: [
         {
             name: 'ticketName',
             type: 'string'
         },
         {
             name: 'ticketId',
             type: 'string'
         },
         {
             name: 'ticketImage',
             type: 'string'
         },
         {
             name: 'ticketDesc',
             type: 'string'
         },
         {
             name: 'ticketPrice',
             type: 'string'
         },
         {
             name: 'placeId',
             type: 'string'
         }
     ],
     idProperty: 'ticketId'
});