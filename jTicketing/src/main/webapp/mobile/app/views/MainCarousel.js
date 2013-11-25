/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.views.MainCarousel = Ext.extend(Ext.Carousel, {
    indicator: true,
    centered: true,
    items: [
    ],

    direction: 'vertical',
    initComponent: function () {
        jticketing.views.MainCarousel.superclass.initComponent.apply(this, arguments);
    },

    listeners: {
        afterrender: function (ca) {
            jticketing.stores.placeStore.on('load', function () {
                Ext.dispatch({
                    controller: jticketing.controllers.place,
                    action: 'loadPlaces'
                });
            });
            jticketing.stores.placeStore.load();
        }
    }
});

Ext.reg('carouselmain', jticketing.views.MainCarousel);