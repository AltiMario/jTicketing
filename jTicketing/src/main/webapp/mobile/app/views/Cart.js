/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.Cart = Ext.extend(Ext.Panel, {
    id: 'jticketingCartPage',
    fullscreen: true,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },

    defaults: {
        flex: 1,
        pack: 'center',
        align: 'center'
    },
    listeners: {
        afterrender: function () {

        }
    },
    initComponent: function () {
        var count = jticketing.stores.cartStore.getCount();
        var availableTicketCount = 0;
        var itemArray = new Array();
        var cartItem = new Ext.Panel({
            layout: {
                type: 'hbox',
                align: 'stretch'
            },

            defaults: {
                flex: 1,
                pack: 'center',
                align: 'center'
            },
            items: [
                    {
                        xtype: 'panel',
                        html: 'No'
                    }, {
                        xtype: 'panel',
                        html: 'Ticket'
                    }, {
                        xtype: 'panel',
                        html: 'Date'
                    }, {
                        xtype: 'panel',
                        html: 'Quantity'
                    }, {
                        xtype: 'panel',
                        html: 'Price'
                    }, {
                        xtype: 'panel',
                        text: 'Remove'
                    }
                ]
        });
        itemArray[itemArray.length] = cartItem;
        var total = 0;
        for (var i = 0; i < count; i++) {
            var record = jticketing.stores.cartStore.getAt(i);
            var ticketId = record.get('cartItem');
            var ticketQuantity = record.get('cartItemQuantity');
            var ticketRecord = jticketing.stores.ticketStore.getById(ticketId);
            var placeRecord = jticketing.stores.placeStore.getById(ticketRecord.get("placeId"));
            var ticketDate = jticketing.stores.availableTicketStore.getById(ticketId).get("ticketDate");
            total = total + (parseInt(ticketRecord.get('ticketPrice').replace(' EURO')) * ticketQuantity);
            var cartItem = new Ext.Panel({
                layout: {
                    type: 'hbox',
                    align: 'stretch'
                },

                defaults: {
                    flex: 1,
                    pack: 'center',
                    align: 'center'
                },
                items: [
                    {
                        xtype: 'panel',
                        html: ticketId
                    }, {
                        xtype: 'panel',
                        html: placeRecord.get("placeName") + '-' + ticketRecord.get('ticketName')
                    }, {
                        xtype: 'panel',
                        html: ticketDate
                    }, {
                        xtype: 'panel',
                        html: ticketQuantity
                    }, {
                        xtype: 'panel',
                        html: ticketRecord.get('ticketPrice')
                    }, {
                        xtype: 'panel',
                        dockedItems: [{
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [{
                                xtype: 'button',
                                id: 'btnRemoveTicket-' + ticketId,
                                text: 'Remove',
                                fullscreen: false,
                                handler: function () {
                                    Ext.dispatch({
                                        controller: jticketing.controllers.ticket,
                                        action: 'removeFromCart',
                                        data: this.id.replace('btnRemoveTicket-', '')
                                    });
                                }
                            }]
                        }]
                    }
                ]
            });

            itemArray[itemArray.length] = cartItem;

        }

        itemArray[itemArray.length] = new Ext.Panel({
            layout: {
                type: 'hbox',
                align: 'stretch'
            },

            defaults: {
                flex: 1,
                pack: 'center',
                align: 'center'
            },
            items: [
                    {
                        xtype: 'panel',
                        html: 'Total : ' + total + ' EURO'
                    }]
        });

        Ext.apply(this, {
            items: itemArray
        });


        jticketing.views.Cart.superclass.initComponent.apply(this, arguments);
    }
});

Ext.reg('cartpage', jticketing.views.Cart);