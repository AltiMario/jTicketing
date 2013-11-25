/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.utilities;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

public class DataConverter {

	static Logger log = Logger.getLogger("DataConverter");

	public static boolean stringToBooleanConverter(String data) {
		if (data != null && data.trim().equalsIgnoreCase("S")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static String booleanToStringConverter(boolean data) {
		if (data) {
			return "S";
		} else {
			return "N";
		}
	}

	public static java.sql.Date sqlDateConverter(String data) {
		java.sql.Date sqlDate = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
			java.util.Date date = sdf.parse(data);
			sqlDate = new java.sql.Date(date.getTime());
		} catch (Exception e) {
			log.error(e);
		}
		return sqlDate;
	}

	public static int booleanIntConverter(Boolean data) {
		if (data) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public static boolean intBooleanConverter(int data) {
		if (data==1) {
			return true;
		} else {
			return false;
		}
	}

	public static int parseInt(String data) {
		int dataInt = 0;
		if (data != null) {
			try {
				dataInt = Integer.parseInt(data);
			} catch (Exception e) {
				return dataInt;
			}
		}
		return dataInt;
	}

	public static long parseLong(String data) {
		long dataInt = 0;
		if (data != null) {
			try {
				dataInt = Long.parseLong(data);
			} catch (Exception e) {
				return dataInt;
			}
		}
		return dataInt;
	}
	
	public static String parseCombo(String data, String nullValue){
		String result="";
		if (data == null || data.trim().length()==0) {
			try {
				result=nullValue;
			} catch (Exception e) {
				return result;
			}
		}else result=data;
		return result;
	}

	@SuppressWarnings("deprecation")
	public static String dateFormat(java.sql.Date date) {
		String data =null;
		try {
			String day;
			String month;
			if(date.getDate()<=9) day="0"+date.getDate();
			else day=""+date.getDate();
			if((date.getMonth()+1)<=9) month="0"+(date.getMonth()+1);
			else month=""+(date.getMonth()+1);
			
			data = day+"/"+month+"/"+(date.getYear()+1900);
			
		} catch (Exception e) {
			log.error(e);
		}
		return data;

	}

}
