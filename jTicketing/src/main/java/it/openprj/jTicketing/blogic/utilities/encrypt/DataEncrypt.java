/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.utilities.encrypt;

import it.openprj.jTicketing.blogic.exceptions.SystemException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public final class DataEncrypt {
	private static DataEncrypt instance;

	public static synchronized DataEncrypt getInstance() // step 1
	{
		if (instance == null) {
			instance = new DataEncrypt();
		}
		return instance;
	}

	private DataEncrypt() {
	}

	public synchronized String encrypt(String plaintext) throws SystemException {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA"); // step 2
		} catch (NoSuchAlgorithmException e) {
			throw new SystemException(e.getMessage());
		}
		try {
			md.update(plaintext.getBytes("UTF-8")); // step 3
		} catch (UnsupportedEncodingException e) {
			throw new SystemException(e.getMessage());
		}

		byte raw[] = md.digest(); // step 4
		String hash = (new BASE64Encoder()).encode(raw); // step 5
		return hash; // step 6
	}

}
