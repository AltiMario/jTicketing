/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regController("place", {
    loadPlaces: function () {
        
        //jticketing.views.mainCarousel.setVisible(true);
        jticketing.views.mainCarousel.removeAll();        
        //jticketing.views.topContainer.setVisible(false);
        //jticketing.views.topContainer.removeAll();

        var count = jticketing.stores.placeStore.getCount();
        for (var i = 0; i < count; i++) {
            jticketing.views.mainCarousel.add({
                xtype: 'panel',                
                id: "place" + jticketing.stores.placeStore.getAt(i).get('placeId'),
                items: [
                        {
                            xtype: 'panel',
                            html: jticketing.stores.placeStore.getAt(i).get('placeName')
                        },
                        {
                            xtype: 'panel',
                            html: '<br /><img width="100" style="float:left" height="100" src="' + global.baseURL + jticketing.stores.placeStore.getAt(i).get('placeImage') + '" /><div style="float:left">' + jticketing.stores.placeStore.getAt(i).get('placeDesc') + '</div>'                            
                        },
                        {
                            xtype: 'panel',
                            dockedItems: [{
                                    xtype: 'button',
                                    fullscreen: false,
                                    ui: 'dark',
                                    dock: 'right',
                                    stretch: false,
                                    text: 'Select',
                                    id: 'selectPlace-' + jticketing.stores.placeStore.getAt(i).get('placeId'),
                                    handler: function (button, event) {
                                        Ext.dispatch({
                                            controller: jticketing.controllers.place,
                                            action: 'loadPlaceTicket',
                                            data: button.id.replace('selectPlace-', '')
                                        });
                                    }
                                }
                            ]
                        }
                    ]                        
            });
        }
        jticketing.views.mainCarousel.doComponentLayout();
    },

    loadPlaceTicket: function (options) {
        var placeId = options.data;
        
        var placeRecord = jticketing.stores.placeStore.getById(placeId);
        

        //jticketing.views.topContainer.doLayout();
        jticketing.views.mainCarousel.removeAll();
        //jticketing.views.mainCarousel.setVisible(true);        

        var count = jticketing.stores.ticketStore.getCount();

        for (var i = 0; i < count; i++) {
            if (jticketing.stores.ticketStore.getAt(i).get("placeId") == placeId) {
                jticketing.views.mainCarousel.add({
                    xtype: 'panel',                
                    id: "ticket" + jticketing.stores.ticketStore.getAt(i).get('ticketId'),
                    items: [
                        {
                            xtype: 'panel',
                            html: jticketing.stores.ticketStore.getAt(i).get('ticketDesc')
                        },
                        {
                            xtype: 'panel',
                            html: '<br /><img width="100" height="100" style="float:left" src="' + jticketing.stores.ticketStore.getAt(i).get('ticketImage') + '" /><div style="float:left">' + jticketing.stores.ticketStore.getAt(i).get('ticketName') + '</div>'
                            
                        },
                        {
                            xtype : 'panel',
                            dockedItems : [{
                                        xtype: 'button',
                                        ui: 'dark',
                                        text: 'Go Back',
                                        dock: 'right',
                                        fullscreen: false,
                                        stretch: false,
                                        handler: function () {
                                            Ext.dispatch({
                                                controller: jticketing.controllers.place,
                                                action: 'loadPlaces'
                                            });
                                        }
                                    },{
                                        xtype: 'button',
                                        ui: 'dark',
                                        text: 'Select',
                                        dock: 'right',
                                        fullscreen : false,
                                        stretch: false,
                                        id: 'selectTicket-' + jticketing.stores.ticketStore.getAt(i).get('ticketId'),
                                        handler: function (button, event) {
                                            Ext.dispatch({
                                                controller: jticketing.controllers.ticket,
                                                action: 'selectTicket',
                                                data: button.id.replace('selectTicket-', '')
                                            });
                                        }
                                    }]
                                },
                        
                    ]
                });
            }
        }
        jticketing.views.mainCarousel.doComponentLayout();
    }
});

jticketing.controllers.place = Ext.ControllerManager.get("place");