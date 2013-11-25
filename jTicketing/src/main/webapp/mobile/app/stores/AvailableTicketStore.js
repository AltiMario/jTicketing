/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.stores.availableTicketStore = new Ext.data.Store({
    model: 'AvailableTicket',
    sorters: 'ticketId',

    getGroupString: function (record) {
        return record.get('ticketName')[0];
    },

    data: [
        { ticketId: '1', ticketDate:'8/25/2011'},
        { ticketId: '1', ticketDate:'8/25/2011'},
        { ticketId: '1', ticketDate:'8/25/2011'},
        { ticketId: '2', ticketDate:'8/25/2011'},
        { ticketId: '3', ticketDate:'8/25/2011'},
        { ticketId: '4', ticketDate:'8/25/2011'},
        { ticketId: '5', ticketDate:'8/25/2011'},
        { ticketId: '6', ticketDate:'8/25/2011'}
    ]
});