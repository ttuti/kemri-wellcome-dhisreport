package org.kemri.wellcome.dhisreport.api.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * 
 * @author Timothy Tuti
 *
 */
public class DateUtils {	
	
	protected static final Logger logger = Logger.getLogger(DateUtils.class);
	
	public static Calendar dateToCalendar(Date date){
		Calendar calendar=Calendar.getInstance();
	    calendar.setTime(date);
	    return calendar;
	}
	
	public static String stringToAlertDate(Date thedate){
		DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d");
	    String alertDate = dateFormat.format(thedate.getTime()); 
	    return alertDate;
	}
	
	public static String getToday(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String convertedDate = dateFormat.format(new Date());
		return convertedDate;
	}
	
	public static Date calendarToDate(Calendar calendar){
		return calendar.getTime();
	}
	
	public static Calendar stringToCalendar(String date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		} 
	    Calendar calendar=Calendar.getInstance();
	    calendar.setTime(convertedDate);
	    return calendar;
	}
	public static String calendarToString(Calendar calendar){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String date = dateFormat.format(calendar.getTime()); 
	    return date;
	}
	public static String calendarToStringKenya(Calendar calendar){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String date = dateFormat.format(calendar.getTime()); 
	    return date;
	}
	
	public static String dateToString(Date date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    String convertedDate = dateFormat.format(date); 
	    return convertedDate;
	}
	
	public static Date stringToDate(String date){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date convertedDate = null;
		try {
			convertedDate = dateFormat.parse(date);
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
	    return convertedDate;
	}
	public static String getTodayDate() {
		Date sundayDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.SUNDAY);
		sundayDate = cal.getTime();
		String format = new SimpleDateFormat("yyyy-MM-dd").format(sundayDate);
		return format;
	}
}