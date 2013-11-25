/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.stores.ticketStore = new Ext.data.Store({
    model: 'Ticket',
    sorters: 'ticketName',

    getGroupString: function (record) {
        return record.get('ticketName')[0];
    },

    data: [
        { ticketId: '1', ticketName: 'ticket1', ticketImage: 'lib/resources/images/place1.jpg', ticketDesc: 'ticket1 Description', placeId: '1', ticketPrice: '100 EURO' },
        { ticketId: '2', ticketName: 'ticket2', ticketImage: 'lib/resources/images/place2.jpg', ticketDesc: 'ticket2 Description', placeId: '1', ticketPrice: '120 EURO' },
        { ticketId: '3', ticketName: 'ticket3', ticketImage: 'lib/resources/images/place3.jpg', ticketDesc: 'ticket3 Description', placeId: '1', ticketPrice: '150 EURO' },
        { ticketId: '4', ticketName: 'ticket4', ticketImage: 'lib/resources/images/place4.jpg', ticketDesc: 'ticket4 Description', placeId: '2', ticketPrice: '200 EURO' },
        { ticketId: '5', ticketName: 'ticket5', ticketImage: 'lib/resources/images/place5.jpg', ticketDesc: 'ticket5 Description', placeId: '2', ticketPrice: '200 EURO' },
        { ticketId: '6', ticketName: 'ticket4', ticketImage: 'lib/resources/images/place4.jpg', ticketDesc: 'ticket6 Description', placeId: '3', ticketPrice: '230 EURO'  },
        { ticketId: '7', ticketName: 'ticket5', ticketImage: 'lib/resources/images/place5.jpg', ticketDesc: 'ticket7 Description', placeId: '4', ticketPrice: '250 EURO' },
        { ticketId: '8', ticketName: 'ticket5', ticketImage: 'lib/resources/images/place1.jpg', ticketDesc: 'ticket8 Description', placeId: '5', ticketPrice: '400 EURO' }
    ]
});