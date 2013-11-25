/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */

package it.openprj.jTicketing.blogic.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.SQLException;

/**
 * @Author Edoardo Zecchi
 */

public class ClobManipulator {

	public static String clobToString(Clob cl) throws IOException, SQLException {
		if (cl == null)
			return "";

		StringBuffer strOut = new StringBuffer();
		String aux;

		// We access to stream, as this way we don't have to use the CLOB.length() which is slower...
		BufferedReader br = new BufferedReader(cl.getCharacterStream());

		while ((aux = br.readLine()) != null)
			strOut.append(aux);

		return strOut.toString();
	}

}
