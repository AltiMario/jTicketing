/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.AvailableTicket = Ext.extend(Ext.Panel, {
    layout: {
        type: 'vbox',
        align: 'left'
    },

    defaults: {
        flex: 0,
        pack: 'center',
        align: 'center'
    },    

    constructor: function (config) {

        var ticketRecord = jticketing.stores.ticketStore.getById(config.ticketId);
        var selectedDate = config.date;
        var placeRecord = jticketing.stores.placeStore.getById(ticketRecord.get("placeId"));
        var selectedTicketId = config.ticketId;

        var ticketAvailibilityLabelPanel = new Ext.Panel({
            layout: {
                type: 'hbox',
                align: 'stretch'
            },

            defaults: {
                flex: 1,
                pack: 'center',
                align: 'center'
            },
            items: [{
                xtype: 'panel',
                html: 'Orario'
            },
                {
                    xtype: 'panel',
                    html: 'Disponibilita'
                }, {
                    xtype: 'panel',
                    html: 'Seleziona'
                }
            ]
        });


        var count = jticketing.stores.availableTicketStore.getCount();
        var availableTicketCount = 0;
        for (var i = 0; i < count; i++) {
            var ticketId = jticketing.stores.availableTicketStore.getAt(i).get('ticketId');
            var ticketDate = jticketing.stores.availableTicketStore.getAt(i).get('ticketDate');
            if (ticketId == selectedTicketId && ticketDate == selectedDate) {
                availableTicketCount++;
            }
        }

        var ticketAvailibilityPanel = new Ext.Panel({
            layout: {
                type: 'hbox',
                align: 'stretch'
            },

            defaults: {
                flex: 1,
                pack: 'center',
                align: 'center'
            },
            items: [{
                xtype: 'panel',
                html: '<center>' + selectedTicketId + '</center>'
            },
                    {
                        xtype: 'panel',
                        html: (availableTicketCount == 0) ? '<center><span style="background-color:red;">1 of ' + availableTicketCount + '</span></center>' : '<center><span style="background-color:green;">1 of ' + availableTicketCount + '</span></center>'
                    }, {
                        xtype: 'button',
                        text: 'Buy',
                        id: 'buyTicket-' + selectedTicketId,
                        hidden: (availableTicketCount == 0) ? true : false,
                        handler: function () {
                            Ext.dispatch({
                                controller: jticketing.controllers.ticket,
                                action: 'addTicketToCart',
                                data: this.id.replace('buyTicket-','')
                            });
                        }
                    }
                ]
        });



        Ext.apply(this, {
            items: [
                    {
                        xtype: 'panel',
                        html: '<img style="float:left" width="100" height="100" src="' + ticketRecord.get('ticketImage') + '" /><div style="float:left">' + placeRecord.get('placeName') + '<br />   ' + ticketRecord.get('ticketName') + '<br/><br/>' + ticketRecord.get('ticketPrice') + '</div>'
                    },
                    {
                        xtype: 'formpanel',
                        style: {
                            marginTop: '90px'
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
                        items: [ticketAvailibilityLabelPanel, ticketAvailibilityPanel,
                                
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



        jticketing.views.AvailableTicket.superclass.constructor.apply(this, arguments);
    },

    initComponent: function () {
        jticketing.views.AvailableTicket.superclass.initComponent.apply(this, arguments);
    }
});

Ext.reg('availableticket', jticketing.views.AvailableTicket);