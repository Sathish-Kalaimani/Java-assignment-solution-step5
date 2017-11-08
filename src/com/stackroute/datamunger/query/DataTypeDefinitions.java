package com.stackroute.datamunger.query;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

/*
 * implementation of DataTypeDefinitions class. This class contains a method getDataTypes() 
 * which will contain the logic for getting the datatype for a given field value. This
 * method will be called from QueryProcessors.   
 * In this assignment, we are going to use Regular Expression to find the 
 * appropriate data type of a field. 
 * Integers: should contain only digits without decimal point 
 * Double: should contain digits as well as decimal point 
 * Date: Dates can be written in many formats in the CSV file. 
 * However, in this assignment,we will test for the following date formats('dd/mm/yyyy',
 * 'mm/dd/yyyy','dd-mon-yy','dd-mon-yyyy','dd-month-yy','dd-month-yyyy','yyyy-mm-dd')
 */
public class DataTypeDefinitions {

	// method stub
	public static Object getDataType(String input) {

		String[] datatype = getDatatypes(input);

		return datatype;
	}

	public static String[] getDatatypes(String s) {
		Object obj = new Object();
		if (s.endsWith(",")) {
			s = s + " ";
		} else {
			
		}
		String[] arr = s.split(",");
		for (int i = 0; i < arr.length; i++) {
			if (Pattern.matches("(\\s+)", arr[i])) {
				Object a = new Object();
				arr[i] = a.getClass().getName();
			} else if (Pattern.matches("(\\d+)(.*\\..*)|(\\d+)|[a-zA-Z\\s+]+", arr[i])) {
				try {
					obj = Integer.parseInt(arr[i]);
					arr[i] =obj.getClass().getName();
				} catch (NumberFormatException e) {
					try {
						obj = Double.parseDouble(arr[i]);
						arr[i] = obj.getClass().getName();
					} catch (Exception ex) {
						arr[i] = arr[i].getClass().getName();
					}
				}
			} else if (Pattern.matches("(..*)[-/\\.](..*)[-/\\.](..*)", arr[i]))
				try {
					Date date = DateFormat(arr[i]);
					arr[i] = date.getClass().getName();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return arr;
	}

	public static Date DateFormat(String d) {
		ArrayList<SimpleDateFormat> date = new ArrayList<SimpleDateFormat>();
		date.add(new SimpleDateFormat("dd/MM/yyyy"));
		date.add(new SimpleDateFormat("MM/dd/yyyy"));
		date.add(new SimpleDateFormat("dd-MMM-yy"));
		date.add(new SimpleDateFormat("dd-MMM-yyyy"));
		date.add(new SimpleDateFormat("dd-MMMM-yy"));
		date.add(new SimpleDateFormat("dd-MMMM-yyyy"));
		date.add(new SimpleDateFormat("yyyy-MM-dd"));
		Date a = null;
		for (SimpleDateFormat format : date) {
			try {
				a = format.parse(d);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
		return a;
	}

}
