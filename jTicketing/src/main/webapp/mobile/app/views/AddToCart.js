/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.AddTicketToCart = Ext.extend(Ext.Panel, {
    layout: {
        type: 'vbox',
        align: 'left'
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
                    layout: 'fit',
                    html: '<img style="float:left" width="100px" height="100px" src="' + ticketRecord.get('ticketImage') + '" /><div style="float:left">' + placeRecord.get('placeName') + '<br />   ' + ticketRecord.get('ticketName') + '<br/><br/>' + ticketRecord.get('ticketPrice') + '</div>'
                }, {
                    xtype: 'panel',
                    html: '<div>' + ticketRecord.get('ticketDesc') + '</div>'
                }, {
                    xtype: 'formpanel',
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'ticketQuantity',
                            fullscreen: false,
                            disabled: true,
                            value: ticketRecord.get('ticketPrice'),
                            label: 'Price'
                        },
                         {
                             xtype: 'textfield',
                             name: 'ticketQuantity',
                             id: 'ticketQuantity',
                             fullscreen: false,
                             label: 'Quantity'
                         }, {
                             xtype: 'button',
                             ui: 'dark',
                             text: 'Add to Cart',
                             id: 'btnAddToCart',
                             handler: function () {
                                 Ext.dispatch({
                                     controller: jticketing.controllers.ticket,
                                     action: 'addToCart',
                                     data: { ticketId: ticketRecord.get("ticketId"), quantity: Ext.getCmp('ticketQuantity').getValue(true) }
                                 });
                             }
                         }
                    ]
                }
            ]
        });



        jticketing.views.AddTicketToCart.superclass.constructor.apply(this, arguments);
    },

    initComponent: function () {
        jticketing.views.AddTicketToCart.superclass.initComponent.apply(this, arguments);
    }
});

Ext.reg('addtocart', jticketing.views.AddTicketToCart);