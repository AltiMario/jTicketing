/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

Ext.regController("ticket", {
    selectTicket: function (options) {
        var ticketId = options.data;
        jticketing.views.topContainer.removeAll();
        jticketing.views.topContainer.removeDocked(jticketing.views.topContainer.dockedItems.items[0]);
        
         jticketing.viewport.doLayout();
        var selectTicketView = new jticketing.views.SelectTicket(ticketId);

        jticketing.views.topContainer.add(selectTicketView);
        jticketing.views.topContainer.doLayout();
        jticketing.viewport.setActiveItem(2);
    },
    showTicketAvailibility: function (options) {
        var ticketId = options.data.ticketId;
        var selectedDate = options.data.date;

        jticketing.views.topContainer.removeAll();
        
        jticketing.views.topContainer.removeDocked(jticketing.views.topContainer.dockedItems.items[0]);
        

        var selectTicketView = new jticketing.views.AvailableTicket({ ticketId: ticketId, date: selectedDate });

        jticketing.views.topContainer.add(selectTicketView);
        jticketing.views.topContainer.doLayout();
    },

    addTicketToCart: function (options) {
        var ticketId = options.data;
        jticketing.views.topContainer.removeAll();
        
        jticketing.views.topContainer.removeDocked(jticketing.views.topContainer.dockedItems.items[0]);
        

        var addTicketToCart = new jticketing.views.AddTicketToCart(ticketId);

        jticketing.views.topContainer.add(addTicketToCart);
        jticketing.views.topContainer.doLayout();
    },
    addToCart: function (options) {
        var ticketId = options.data.ticketId;
        var ticketQuantity = options.data.quantity;
        jticketing.stores.cartStore.add({ cartItem: ticketId, cartItemQuantity: ticketQuantity });

        jticketing.views.topContainer.removeAll();
        
        jticketing.views.topContainer.removeDocked(jticketing.views.topContainer.dockedItems.items[0]);
        

        jticketing.views.bottomToolbar.setActiveItem(1);
    },
    removeFromCart: function (options) {
        var ticketId = options.data;        
        jticketing.stores.cartStore.remove(jticketing.stores.cartStore.getById(ticketId));
        jticketing.views.topContainer.setVisible(true);
        jticketing.views.topContainer.removeAll();
        
        jticketing.views.topContainer.removeDocked(jticketing.views.topContainer.dockedItems.items[0]);
        

        var cart = new jticketing.views.Cart();

        jticketing.views.topContainer.add(cart);
        jticketing.views.topContainer.doLayout();
    },
    showCart: function () {       
        jticketing.views.topContainer.setVisible(true);
        jticketing.views.topContainer.removeAll();
        
        jticketing.views.topContainer.removeDocked(jticketing.views.topContainer.dockedItems.items[0]);
        

        var cart = new jticketing.views.Cart();

        jticketing.views.topContainer.add(cart);
        jticketing.views.topContainer.doLayout();
    },

});

jticketing.controllers.ticket = Ext.ControllerManager.get("ticket");