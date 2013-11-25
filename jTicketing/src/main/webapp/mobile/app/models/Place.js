/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regModel('Place', {
    fields: [
         {
             name: 'placeName',
             type: 'string'
         },
         {
             name: 'placeId',
             type: 'string'
         },
         {
             name: 'placeImage',
             type: 'string'
         },
         {
             name: 'placeDesc',
             type: 'string'
         }
     ],
     idProperty: 'placeId'
});