/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

jticketing.stores.placeStore = new Ext.data.Store({
    model: 'Place',
    sorters: 'placeName',
    proxy: {
            type: 'ajax',
            url: global.baseURL+'rest/places/search',
            reader: {
                type: 'json',
                root: 'placeResponceType'
        }
    },       
    getGroupString: function (record) {
        return record.get('placeName')[0];
    }
});