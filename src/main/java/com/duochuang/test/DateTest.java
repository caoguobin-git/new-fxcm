/***********************************************
 * File Name: DateTest
 * @author: caoguobin
 * mail: caoguobin@live.com
 * Created Time: 13 12 2019 下午 1:43
 ***********************************************/

package com.duochuang.test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;

public class DateTest {
	public static void main(String[] args) {
		Calendar calendar= GregorianCalendar.getInstance(TimeZone.getTimeZone("GMT+8:00"));
		System.out.println(calendar.getTime().toString());
		calendar.set(Calendar.YEAR, 2018);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH)-1);
		System.out.println(calendar.get(Calendar.MONTH));
		System.out.println(calendar.getTime().toString());
		ConcurrentHashMap<String,String> map=new ConcurrentHashMap<>(12);

	}
}
