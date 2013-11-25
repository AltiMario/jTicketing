/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.utilities;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class HttpUtils {
	
	private static Logger log = Logger.getLogger(HttpUtils.class);


	@SuppressWarnings("unchecked")
	public static String postForm( String urlString,
								   Map requestHeaders,
								   Map formParameters ) throws MalformedURLException, ProtocolException, IOException {
		return post( urlString, requestHeaders, formParameters, null );
	}

	@SuppressWarnings("unchecked")
	public static String postContents( String urlString,
									   Map requestHeaders,
									   String contents ) throws MalformedURLException, ProtocolException, IOException {
		return post( urlString, requestHeaders, null, contents );
	}

  
	@SuppressWarnings({ "unchecked", "deprecation" })
	public static String post( String urlString,
							 Map requestHeaders,
							 Map formParameters,
							 String requestContents )
	  throws MalformedURLException, ProtocolException, IOException {
	// open url connection
	URL url = new URL( urlString );
	HttpURLConnection con = (HttpURLConnection) url.openConnection();

	// set up url connection to post information and
	// retrieve information back
	con.setRequestMethod( "POST" );
	con.setDoInput( true );
	con.setDoOutput( true );

	// add all the request headers
	if( requestHeaders != null ) {
	  Set headers = requestHeaders.keySet();
	  for( Iterator it = headers.iterator(); it.hasNext(); ) {
		String headerName = (String) it.next();
		String headerValue = (String) requestHeaders.get( headerName );
		con.setRequestProperty( headerName, headerValue );
	  }
	}

	// add url form parameters
	DataOutputStream ostream = null;
	try {
	  ostream = new DataOutputStream( con.getOutputStream() );
	  if( formParameters != null ) {
		Set parameters = formParameters.keySet();
		Iterator it = parameters.iterator();
		StringBuffer buf = new StringBuffer();

		for( int i = 0, paramCount = 0; it.hasNext(); i++ ) {
		  String parameterName = (String) it.next();
		  String parameterValue = (String) formParameters.get( parameterName );

		  if( parameterValue != null ) {
			parameterValue = URLEncoder.encode( parameterValue );
			if( paramCount > 0 ) {
			  buf.append( "&" );
			}
			buf.append( parameterName );
			buf.append( "=" );
			buf.append( parameterValue );
			++paramCount;
		  }
		}
		log.info( "adding post parameters: " + buf.toString() );
		ostream.writeBytes( buf.toString() );
	  }

	  if( requestContents != null ) {
		ostream.writeBytes( requestContents );
	  }

	}
	finally {
	  if( ostream != null ) {
		ostream.flush();
		ostream.close();
	  }
	}

	Object contents = con.getContent();
	InputStream is = (InputStream) contents;
	StringBuffer buf = new StringBuffer();
	int c;
	while( ( c = is.read() ) != -1 ) {
	  buf.append( (char) c );
	}
	con.disconnect();
	return buf.toString();
  }
}