/*
 jTicketing is a highly configurable solution for the management of online booking, electronic ticket and box office.

 Copyright (C) 2010-2012 OpenPRJ s.r.l.
 All rights reserved

 Site: http://www.openprj.it
 Contact:  info@openprj.it
 */
package it.openprj.jTicketing.blogic.utilities;

import java.sql.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class DateUtilities {

	public static final String DEFAULT_PATTERN_DATE = "dd/MM/yyyy";

	//Costanti relative al formato della Data
	public static final String DD_MM_YY_NO_SEP = "ddMMyy"; //es. 121003
	public static final String DD_MMMMM_YYYY = "dd MMMMM yyyy";
	//es. 12 luglio 2001
	public static final String YYYYMMDD = "yyyyMMdd"; //es. 20010712
	public static final String YYYYMM = "yyyyMM"; //es. 200107
	public static final String YYYY = "yyyy"; //es. 2001
	public static final String MM = "MM"; //es. 07
	public static final String DD = "DD"; //es. 07
	public static final String DDMMYY = "dd.MM.yy"; //es. 12.07.2001
	public static final String DD_MM_YY = "dd/MM/yy"; //es. 12/07/01
	public static final String DD_MM_YYYY = "dd/MM/yyyy"; //es. 12/07/2001
	public static final String DD_MM_YYYYbis = "dd-MM-yyyy"; //es. 12-07-2001
	public static final String MM_DD_YY = "MM/dd/yy"; //es. 07/12/2001
	public static final String DDMMYYYY_NO_SEP = "ddMMyyyy"; //es. 12072001
	public static final String YYYYMMDDH = "yyyy.MM.dd G 'at' hh:mm:ss z"; //es. 2001.07.12 dopo Cristo at 04:51:08 GMT-01:00
	public static final String EEEMMMDYY = "EEE, MMM d, ''yy"; //es. gio, lug 12, '01
	public static final String H = "h:mm a"; //es. 5:09 PM
	public static final String HMMSS = "H:mm:ss:SSS"; //es. 17:10:13:825
	public static final String KMM = "K:mm a,z"; //es. 5:11 PM,GMT-01:00
	public static final String YYYYMMMMMDDH = "yyyy.MMMMM.dd GGG hh:mm aaa";
	//es. 2001.luglio.12 dopo Cristo 05:12 PM
	public static final String YYYY_MM_DD_H = "yyyy-MM-dd HH:mm:ss";
	//es. 2001-07-12 16:05:25
	public static final String DD_MM_YYYY_H = "dd-MM-yyyy HH:mm:ss";
	//es. 12-07-2001 16:05:25
	public static final String YYYYMMDD_H = "yyyyMMdd_HHmmss";
	//es. 20010712_160525

	private static SimpleDateFormat dateFormat = null;

	/** Funzione per l'aggiunta degli apici al parametro 
	 * stringa da passare ad una query.
	 * @param st stringa senza apici.
	 * @return stringa con apici.
	 * @throws Exception se si verifica un errore. 
	 */
	public static String ChPerSelect(String st) throws Exception {

		if (st != null && !st.equals("")) {
			st = st.trim(); //.toUpperCase();
			int pos = st.indexOf("'");
			//if(pos >= 0)
			//System.out.println("[FMVUtilities][ChPerSelect] - Stringa da formattare: " + st);
			while (pos >= 0) {
				st = st.substring(0, pos) + "'" + st.substring(pos);
				pos = st.indexOf("'", pos + 2);
			} // end while
		}
		return st;
	}

	/**
	 * 
	 * @param str string da elaborare
	 * @param chrIni carattere da sostituire
	 * @param chrFin serie di caratteri da inserire
	 * @return la stringa elaborata
	 */
	public static String replaceChr(String str, String chrIni, String chrFin) {
		String strIni = "";
		String strFin = str == null ? "" : str;
		int pos = strFin.indexOf(chrIni);
		while (pos >= 0) {
			strIni = strIni + strFin.substring(0, pos) + chrFin;
			strFin = strFin.substring(pos + 1);
			pos = strFin.indexOf(chrIni);
			//System.out.println(strIni);
		}

		strIni = strIni + strFin;
		return strIni;
	}

	/**
	 *  Ritorna:
	 *  -1 se date1 e' antecedente a date2
	 *   0 se date1 e' equivalente a date2
	 *   1 se date1 e' posteriore a date2
	 * @param date1
	 * @param date2
	 * @param date1Format
	 * @param date2Format
	 * @return
	 */
	public static int compareDates(String date1, String date2, String date1Format, String date2Format) throws Exception {
		int code = 0;
		@SuppressWarnings("unused")
		Calendar cal1 = Calendar.getInstance();
		@SuppressWarnings("unused")
		Calendar cal2 = Calendar.getInstance();
		Date data1 = null;
		Date data2 = null;
		data1 = DateUtilities.formatDate(date1);
		data2 = DateUtilities.formatDate(date2);
		if(data1.after(data2)) code=1;
		if(data1.before(data2)) code=-1;
		if(data1.equals(data2)) code=0;
		return code;

	}

	public static String patternConversion(String pattern) {
		String patternConverted = null;
		if (pattern.equalsIgnoreCase("gg/mm/aaaa"))
			patternConverted = DD_MM_YYYY;
		if (pattern.equalsIgnoreCase("ggmmaaaa"))
			patternConverted = DDMMYYYY_NO_SEP;
		if (pattern.equalsIgnoreCase("aaaammgg"))
			patternConverted = YYYYMMDD;
		if (pattern.equalsIgnoreCase("gg-mm-aaaa"))
			patternConverted = DD_MM_YYYY_H;
		if (pattern.equalsIgnoreCase("aaaa-mm-gg"))
			patternConverted = YYYY_MM_DD_H;
		if (pattern.equalsIgnoreCase("aaaa-mm-gg"))
			patternConverted = YYYY_MM_DD_H;
		if (pattern.equalsIgnoreCase("aaaa"))
			patternConverted = YYYY;
		if (pattern.equalsIgnoreCase("mm"))
			patternConverted = MM;
		if (pattern.equalsIgnoreCase("gg"))
			patternConverted = DD;
		return patternConverted;

	}

	/**
	 * 
	 * @param str stringa da utilizzare come parametro negli statement SQL
	 * @return la stringa con i caratteri sensibili sostituiti
	 */
	public static String replaceChrPerDB(String str) {
		str = replaceChr(str, "'", "''");
		str = replaceChr(str, "\n", " ");
		str = replaceChr(str, "\t", " ");

		return str;
	}

	/** Funzione che restituisce una stringa non null.
	 * Se la stringa passata e' null, restituisce una stringa vuota.
	 * Se contiene la scritta 'null',  restituisce una stringa vuota.
	 * Atrimenti restituisce il valore passato senza spazi all'inzio e fine.
	 * @param str stringa da verificare.
	 * @return stringa senza null.
	 * @throws Exception se si verifica un errore.
	 */
	public static String removeNull(String str) throws Exception {

		String myStr = "";
		if (str == null)
			return myStr;
		else if (str != null && str.equalsIgnoreCase("null"))
			return myStr;
		else
			return str.trim();
	}

	/**
	 * Funzione per la conversione dei caratteri
	 * @param String s
	 * @param int ch1
	 * @param int ch2
	 * @return String
	 * @throws Exception
	 */
	public static String ChangeChar(String s, int ch1, int ch2) throws Exception {

		String rchar = "";
		if (s != null) {
			for (int i = 0; i < s.length(); i++) {
				char fchar = s.charAt(i);

				if (fchar == (char) ch1)
					rchar = rchar + String.valueOf((char) ch2);
				else
					rchar = rchar + String.valueOf((char) fchar);
			}
			return rchar;
		} else
			return s;
	}

	/**
	 * Ritorna il numero di caratteri passati in ingresso 'ch' per quanto e'
	 * il numero specificato nell'argomento stlen
	 * @param int stlen
	 * @param String ch
	 * @return String
	 * @throws Exception
	 */
	public static String formatCode(int stlen, String ch) throws Exception {

		String numzero = "";

		for (int i = 0; i < stlen; i++)
			numzero += ch;

		return numzero;
	}

	/** Restituisce la data di sistema nel formato (gg-mm-aaaa).
	  * @return data di sistema.
	  */
	public static java.sql.Date dataOdierna() {

		Calendar dataOdierna = Calendar.getInstance();

		return new java.sql.Date(dataOdierna.getTime().getTime());
	}

	/**
	 * Metodo per formattare il layout delle date, passando il formato della visualizzazione 
	 * 
	 * @param data - Oggetto data da formattare
	 * @param pattern - La stringa con il formato della visualizzazione della data
	 * 
	 * @return Stringa con la data formattata
	 */
	public static String formatDate(Date data, String pattern) {

		if (data == null)
			return "";

		dateFormat = new SimpleDateFormat(pattern);
		synchronized (dateFormat) {
			try {
				DateUtilities.dateFormat.applyPattern(pattern);
			} catch (Exception e) {
				DateUtilities.dateFormat.applyPattern(DEFAULT_PATTERN_DATE);
			}
		}

		return DateUtilities.dateFormat.format(data);
	}

	/**
	 * Metodo per formattare il layout delle date, passando il formato della visualizzazione 
	 * 
	 * @param data - Oggetto data da formattare
	 * @param pattern - La stringa con il formato della visualizzazione della data
	 * 
	 * @return Stringa con la data formattata
	 */
	public static String formatDate(String data, String pattern) {

		if (data == null)
			return "";

		dateFormat = new SimpleDateFormat(pattern);
		synchronized (dateFormat) {
			try {
				DateUtilities.dateFormat.applyPattern(pattern);
			} catch (Exception e) {
				DateUtilities.dateFormat.applyPattern(DEFAULT_PATTERN_DATE);
			}
		}

		return DateUtilities.dateFormat.format(data);
	}

	/**
	* Metodo che converte un oggetto String in un java.sql.Date
	* @return Ritorna un  java.sql.Date.
	*/
	public static Date formatDate(String data) {

		if (data.equals(""))
			return null;

		GregorianCalendar gc = new GregorianCalendar();
		int day = 0;
		int month = 0;
		int year = 0;
		StringTokenizer token = new StringTokenizer(data, "/");
		while (token.hasMoreTokens()) {
			day = new Integer(token.nextToken()).intValue();
			month = new Integer(token.nextToken()).intValue();
			year = new Integer(token.nextToken()).intValue();
		}
		gc.set(year, month - 1, day);
		Date date = new Date(gc.getTime().getTime());

		return date;
	}

	/**
	 * Metodo per la formattazione della data dal formato YYYY-MM-DD al formato YYYYMMDD
	 * 
	 * @param str
	 * @return
	 */
	public static String formattaDate(String str) throws Exception {

		String toDate = null;
		//System.out.println("[WFUtilities][formattaDate] - date: " + str);
		if (str != null) {
			String year = null;
			String mounth = null;
			String day = null;
			if (str.indexOf("-") != -1) {
				year = str.substring(0, str.indexOf("-"));
				if (year.length() > 4) {
					//System.out.println("[WFUtilities][formattaDate] - year: " + year);
					return null;
				} else {
					str = str.substring(str.indexOf("-") + 1, str.length());
					if (str.indexOf("-") != -1) {
						mounth = str.substring(0, str.indexOf("-"));
						if (mounth.length() > 2) {
							//System.out.println("[WFUtilities][formattaDate] - mounth: "+ mounth);
							return null;
						} else {
							str = str.substring(str.indexOf("-") + 1, str.length());
							day = str;
							if (day.length() > 2) {
								//System.out.println("[WFUtilities][formattaDate] - day: "+ day);
								return null;
							} else {
								//toDate = day + mounth + year;
								toDate = year + mounth + day;
							}
						}
					} else {
						return null;
					}
				}
			} else {
				return null;
			}
			//SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			//toDate = format.format(stringa);
		} /* if ( date != null ) */

		return toDate;
	}

	/**
	  * Metodo per la formattazione della data dal formato YYYY-MM-DD al formato dd/mm/yyyy
	  * 
	  * @param str
	  * @return 
	  */
	public static String formatDataString(String str) {
		String toDate = "";
		try {

			//System.err.println("[WFUtilities][formatDataString] - DATA: " + str);
			if (str.indexOf("-") != -1) {
				//System.err.println("[WFUtilities][formatDataString] - DATA: " + str);
				if (str.indexOf(" ") != -1)
					str = str.substring(0, str.indexOf(" "));

				//System.err.println("[WFUtilities][formatDataString] - DATA dopo substring: " + str);

				StringTokenizer st = new StringTokenizer(str, "-");
				String anno = st.nextToken();
				String mese = st.nextToken();
				String giorno = st.nextToken();
				toDate = giorno + "/" + mese + "/" + anno;
			}
		} catch (NullPointerException npe) {
			toDate = "";
		}
		return toDate;
	}

	/**
	 * Metodo per la formattazione della data dal formato iniziale passato sInitDateFormat al formato
	 * finale YYYYMMDD
	 * 
	 * @param str
	 * @return
	 */
	public static String formattaDate(String str, String sInitDateFormat) throws Exception {

		@SuppressWarnings("unused")
		final int sDayLength = 2;
		@SuppressWarnings("unused")
		final int sMounthLength = 2;
		@SuppressWarnings("unused")
		final int sYearLength = 2;

		String toDate = null;
		//System.out.println("[WFUtilities][formattaDate] - date: " + str);
		if (str != null) {

			if (sInitDateFormat.equalsIgnoreCase(DateUtilities.DD_MM_YYYYbis)) //	dd-MM-yyyy
				toDate = DateUtilities.formatDateDDMMYYYYless(str);
			else if (sInitDateFormat.equalsIgnoreCase(DateUtilities.DD_MM_YYYY)) //   dd/MM/yyyy
				toDate = DateUtilities.formatDateDDMMYYYYslash(str);

			//System.err.println("[WFUtilities][formattaDate] - toDate: " + toDate);
		} /* if ( date != null ) */

		return toDate;
	}

	/**
	 * Formattazione della stringa di ingresso da DD-MM-YYYY al formato YYYYMMDD
	 * 
	 * @param String str
	 * @param String format
	 * @return String
	 */
	private static String formatDateDDMMYYYYless(String str) {
		String toDate = null;

		if (str.indexOf("-") != -1) {
			StringTokenizer st = new StringTokenizer(str, "-");
			String giorno = st.nextToken();
			String mese = st.nextToken();
			String anno = st.nextToken();
			toDate = anno + mese + giorno;
		} else
			return null;

		return toDate;
	}

	/**
	 * Formattazione della stringa di ingresso da DD/MM/YYYY al formato YYYYMMDD
	 * 
	 * @param String str
	 * @param String format
	 * @return String
	 */
	private static String formatDateDDMMYYYYslash(String str) {
		String toDate = null;

		if (str.indexOf("/") != -1) {
			StringTokenizer st = new StringTokenizer(str, "/");
			String giorno = st.nextToken();
			String mese = st.nextToken();
			String anno = st.nextToken();
			toDate = anno + mese + giorno;
		} else
			return null;

		return toDate;
	}

	/**
	 * Ritorna la data e/o ora di sistema nel formato richiesto
	 */
	public static String getSysDate(String format) {
		/**
		 * on some JDK, the default TimeZone is wrong
		 * we must set the TimeZone manually!!!
		 * Example:
		 *   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
		 */
		Calendar cal = Calendar.getInstance(TimeZone.getDefault());

		return (toString(cal.getTime(), format));
	}

	/**
	 * Ritorna la data e/o ora di sistema nel formato richiesto per il paese specificato
	 */
	public static String getSysDate(String format, Locale country) {
		/**
		 * on some JDK, the default TimeZone is wrong
		 * we must set the TimeZone manually!!!
		 * Example:
		 *   Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("EST"));
		 */
		Calendar cal = Calendar.getInstance(TimeZone.getDefault(), country);

		return (toString(cal.getTime(), format, country));
	}

	/**
	 * Converte la data e/o ora dal formato "java.util.Date" al formato "stringa"
	 */
	public static String toString(java.util.Date d, String format, Locale country) {
		if (d == null)
			return ("");

		//String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
		//String DATE_FORMAT = "dd-MM-yyyy";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(format, country);

		/**
		 * on some JDK, the default TimeZone is wrong
		 * we must set the TimeZone manually!!!
		 * Example:
		 *     sdf.setTimeZone(TimeZone.getTimeZone("EST"));
		 */
		sdf.setTimeZone(TimeZone.getDefault());

		return (sdf.format(d));
	}

	/**
	 * Converte la data e/o ora dal formato "java.sql.Date" al formato "stringa"
	 */
	public static String toString(java.sql.Date d, String format, Locale country) {
		return (toString((java.util.Date) d, format, country));
	}

	/**
	 * Converte la data e/o ora dal formato "java.util.Date" al formato "stringa"
	 */
	public static String toString(java.util.Date d, String format) {
		return toString(d, format, Locale.getDefault());
	}

	/**
	 * Converte la data e/o ora dal formato "java.sql.Date" al formato "stringa"
	 */
	public static String toString(java.sql.Date d, String format) {
		return (toString((java.util.Date) d, format));
	}

	/**
	 * Converte la data e/o ora dal formato "stringa" al formato "java.util.Date"
	 */
	static public java.util.Date toDate(String dateString, String format) {
		java.util.Date date1;

		// Format the current time.
		SimpleDateFormat formatter = new SimpleDateFormat(format);

		ParsePosition pos = new ParsePosition(0);
		date1 = formatter.parse(dateString, pos);

		return (date1);
	}

	/**
	 * Converte la data e/o ora dal formato "stringa" al formato "java.util.Date"
	 */
	static public java.util.Date toDate(String dateString, String dateFormat, String timeString, String timeFormat) throws Exception {
		@SuppressWarnings("unused")
		java.util.Date date;
		@SuppressWarnings("unused")
		java.util.Date time;
		java.util.Date dateTime;
		String dateTimeFormat;

		/**
		 * Decode the DATE
		 */
		try {
			if ((dateFormat == null) || (dateFormat.length() == 0)) {
				date = toDate(dateString, "dd/MM/yyyy");
				dateTimeFormat = "dd/MM/yyyy";
			} else {
				date = toDate(dateString, dateFormat);
				dateTimeFormat = dateFormat;
			}
		} catch (Exception e) {
			throw new Exception("Incorrect date format");
		}

		/**
		 * Decode the TIME
		 */
		try {
			if ((timeFormat == null) || (timeFormat.length() == 0)) {
				time = toDate(timeString, "kk:mm:ss");
				dateTimeFormat = dateTimeFormat + " " + "kk:mm:ss";
			} else {
				time = toDate(timeString, timeFormat);
				dateTimeFormat = dateTimeFormat + " " + timeFormat;
			}
		} catch (Exception e) {
			throw new Exception("Incorrect time format");
		}

		try {
			SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
			dateTime = formatter.parse(dateString + " " + timeString);
		} catch (Exception e) {
			throw new Exception("Incorrect date or time format");
		}

		return (dateTime);
	}

	/**
	 * Converte la data e/o ora dal formato "stringa" al formato "java.sql.Date"
	 */
	static public java.sql.Date toDateSql(String dateString, String format) throws Exception {
		if (dateString != null && !dateString.equals("")) {
			java.util.Date d1 = toDate(dateString, format);
			java.sql.Date d2 = new java.sql.Date(d1.getTime());
			return (d2);
		} else
			return null;
	}

	/**
	 * Converte la data e/o ora dal formato "stringa" al formato "java.sql.Date"
	 */
	@SuppressWarnings("deprecation")
	public static java.sql.Date toDateSql(String dateString) {
		java.util.Date d1 = null;
		java.sql.Date d2 = null;
		@SuppressWarnings("unused")
		SimpleDateFormat formatter = null;

		try {
			if (dateString != null) {

				if (!dateString.equals("")) {
					java.util.Date d3 = new java.util.Date(dateString);
					d3.getTime();
					d1 = new java.util.Date(dateString);
					d2 = new java.sql.Date(d1.getTime());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (d2);
	}

	/**
	 * Confronta due date espresse in millisecondi
	 */
	static public long diff(long gmtTime1, long gmtTime2, boolean subtractionFlag) {
		int utilHH1, utilMM1, utilSS1, utilMS1;
		int utilHH2, utilMM2, utilSS2, utilMS2;
		int gmtTimeInt1, gmtTimeInt2;

		gmtTimeInt1 = (int) (gmtTime1 / 1000);
		gmtTimeInt2 = (int) (gmtTime2 / 1000);

		gmtTimeInt1 = gmtTimeInt1 % 86400;
		gmtTimeInt2 = gmtTimeInt2 % 86400;

		utilMS1 = 0;
		utilSS1 = gmtTimeInt1 % 60;
		utilMM1 = (gmtTimeInt1 / 60) % 60;
		utilHH1 = (gmtTimeInt1 / 60) / 60;

		utilMS2 = 0;
		utilSS2 = gmtTimeInt2 % 60;
		utilMM2 = (gmtTimeInt2 / 60) % 60;
		utilHH2 = (gmtTimeInt2 / 60) / 60;

		return diff(utilHH1, utilMM1, utilSS1, utilMS1, utilHH2, utilMM2, utilSS2, utilMS2, subtractionFlag);
	}

	/**
	 * Confronta due date espresse in "java.util.Date"
	 */
	static public long diff(java.util.Date utilDate1, java.util.Date utilDate2, boolean subtractionFlag) {
		return diff(utilDate1.getTime(), utilDate2.getTime(), subtractionFlag);
	}

	/**
	 * Confronta due date espresse in "java.sql.Date"
	 */
	static public long diff(java.sql.Date sqlDate1, java.sql.Date sqlDate2, boolean subtractionFlag) {
		return diff(sqlDate1.getTime(), sqlDate2.getTime(), subtractionFlag);
	}

	/**
	 * Confronta due date espresse in "java.util.Date" e in "java.sql.Date"
	 */
	static public long diff(java.util.Date utilDate, java.sql.Date sqlDate, boolean subtractionFlag) {
		return diff(utilDate.getTime(), sqlDate.getTime(), subtractionFlag);
	}

	/**
	 * Confronta due date
	 */
	static private long diff(int HH1, int MM1, int SS1, int MS1, int HH2, int MM2, int SS2, int MS2, boolean subtractionFlag) {
		int sign = 1;
		long msec3, msec2, msec1;

		if (subtractionFlag == true) {
			sign = -1;
		}

		msec1 = ((((HH1 * 60 + MM1) * 60) + SS1) * 1000) + MS1;
		msec2 = ((((HH2 * 60 + MM2) * 60) + SS2) * 1000) + MS2;

		msec3 = msec1 + sign * msec2;

		return (msec3);
	}

	/**
	 * Determina se un anno e' bisestile
	 */

	public static boolean isBisestile(int anno) {
		int div004 = anno % 4;

		int div100 = anno % 100;

		int div400 = anno % 400;

		if (div400 == 0)
			return true;

		if (div004 == 0 && div100 > 0)
			return true;

		return false;
	}

	/**
	 * Aggiunge giorni al dataInput passato in ingresso
	 * @param giorni numero di giorni da incrementare 
	 * @param dataInput
	 * @return java.sql.Date
	 */

	public static java.sql.Date aggiungiGiorni(int giorni, java.sql.Date dataInput) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataInput);
		cal.add(Calendar.DATE, giorni);
		java.sql.Date dataOutput = trasformaInData(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
		return dataOutput;
	}

	/**
	 * Restutiesce un Date 
	 * @param anno
	 * @param mese
	 * @param giorno
	 * @return java.sql.Date
	 */

	public static java.sql.Date trasformaInData(int anno, int mese, int giorno) {
		String mese_s;
		String giorno_s;

		if (mese < 10) {
			mese_s = "0" + java.lang.String.valueOf(mese);
		} else {
			mese_s = java.lang.String.valueOf(mese);
		}

		if (giorno < 10) {
			giorno_s = "0" + java.lang.String.valueOf(giorno);
		} else {
			giorno_s = java.lang.String.valueOf(giorno);
		}

		java.sql.Date data_s = java.sql.Date.valueOf(java.lang.String.valueOf(anno) + "-" + mese_s + "-" + giorno_s);

		return data_s;
	}

}
