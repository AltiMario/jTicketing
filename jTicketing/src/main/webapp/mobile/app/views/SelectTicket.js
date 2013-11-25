/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.SelectTicket = Ext.extend(Ext.Panel, {
    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    defaults: {
        flex: 1,
        pack: 'center',
        align: 'center'
    },
    constructor: function (config) {

        var ticketRecord = jticketing.stores.ticketStore.getById(config);
        var placeRecord = jticketing.stores.placeStore.getById(ticketRecord.get("placeId"));

        Ext.apply(this, {
            items: [
                    {
                        xtype: 'panel',
                        html: '<img style="float:left" width="100" height="100" src="' + ticketRecord.get('ticketImage') + '" /><div style="float:left">' + placeRecord.get('placeName') + '<br />   ' + ticketRecord.get('ticketName') + '<br/><br/>' + ticketRecord.get('ticketPrice') + '</div>'
                    },
                    {
                        xtype: 'formpanel',                        
                        style:{
                            marginTop: '80px'
                        },
                        
                        dockedItems: [{
                            xtype: 'toolbar',
                            ui: 'light',
                            dock: 'top',
                            items: [
                                        {
                                            xtype: 'button',
                                            ui: 'dark',
                                            text: 'Change',
                                            id: 'btnGoBackToTickets',
                                            handler: function () {
                                                jticketing.viewport.setActiveItem(1);
                                                Ext.dispatch({                                                    
                                                    controller: jticketing.controllers.place,
                                                    action: 'loadPlaceTicket',
                                                    data: ticketRecord.get("placeId")
                                                });
                                            }
                                        }
                                ]
                        }
                        ],
                        items: [                                
                                 new Ext.ux.DatePicker({
                                     fullscreen: false,
                                     id: 'selectedTicketDate',
                                     minDate: (new Date()).add(Date.DAY, -40),
                                     maxDate: (new Date()).add(Date.DAY, 55)
                                 }),
                                 {
                                     xtype: 'button',
                                     ui: 'dark',
                                     text: 'Show Availibility',
                                     id: 'btnShowTicketAvailibility',
                                     handler: function () {
                                         Ext.dispatch({
                                             controller: jticketing.controllers.ticket,
                                             action: 'showTicketAvailibility',
                                             data: { ticketId: ticketRecord.get("ticketId"), date: Ext.getCmp('selectedTicketDate').getValue(true) }
                                         });
                                     }
                                 }
                            ]
                    }
            ]
        });



        jticketing.views.SelectTicket.superclass.constructor.apply(this, arguments);
    },

    initComponent: function () {
        jticketing.views.SelectTicket.superclass.initComponent.apply(this, arguments);
    }
});

Ext.reg('selectticket', jticketing.views.SelectTicket);