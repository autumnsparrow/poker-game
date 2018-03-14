package com.sky.game.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatetimeUtils {

	public DatetimeUtils() {
		// TODO Auto-generated constructor stub
	}
	public static final String DATE_FORMAT_0="yyyyMMddHHmmss";
	
	public static String convertDate(Date date,String pattern){
		String dateString=null;
		SimpleDateFormat format=new SimpleDateFormat(pattern);
		dateString=format.format(date);
		return dateString;
	}
}
