/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.model.entity;

public class BinaryData  implements java.io.Serializable {
	private static final long serialVersionUID = -6649241038030758181L;
	private long id;
    private byte[] binaryData;

    public BinaryData() {
    }

    public BinaryData(byte[] binaryData) {
       this.binaryData = binaryData;
    }
   
    public void setId(long id) {
        this.id = id;
    }
    public void setBinaryData(byte[] binaryData) {
        this.binaryData = binaryData;
    }

    public long getId() { 
    	return this.id; 
    }
    
    public byte[] getBinaryData() { 
    	return this.binaryData;
    }

}
