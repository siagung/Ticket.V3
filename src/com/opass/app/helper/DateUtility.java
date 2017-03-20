/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opass.app.helper;

/**
 *
 * @author agung
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {
	
/* Add Day/Month/Year to a Date
add() is used to add  values to a Calendar object. 
You specify which Calendar field is to be affected by the operation 
(Calendar.YEAR, Calendar.MONTH, Calendar.DATE). 
*/
	public static final String DATE_FORMAT = "dd-MM-yyyy";	//See Java DOCS for different date formats
//	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static void addToDate(){
		System.out.println("1. Add to a Date Operation\n");
		SimpleDateFormat sdf =  new SimpleDateFormat(DATE_FORMAT);

		//		Gets a calendar using the default time zone and locale.
		Calendar c1 = Calendar.getInstance();
		Date d1 = new Date();
                
                System.out.println("=============== AGUNG PUNYA ===================");
                System.out.println("JAM ALL : "+c1.getTime());
                System.out.println("jam  : "+c1.getTime().getHours());
                System.out.println("Menit : "+c1.getTime().getMinutes());
                System.out.println("=============== AGUNG PUNYA END ===================");
		System.out.println("Todays date in Calendar Format : "+c1);
		System.out.println("c1.getTime() : "+c1.getTime());
		System.out.println("c1.get(Calendar.YEAR): " + c1.get(Calendar.YEAR));
		
		System.out.println("Todays date in Date Format : "+d1);
		c1.set(1999,0 ,20); 		//(year,month,date)
		System.out.println("c1.set(1999,0 ,20) : "+c1.getTime());
		c1.add(Calendar.DATE,20);
		System.out.println("Date + 20 days is : " + sdf.format(c1.getTime()));
		System.out.println();
		System.out.println("-------------------------------------------------------");
	}
	
	
/*Substract Day/Month/Year to a Date
	
	roll() is used to substract values to a Calendar object. 
	You specify which Calendar field is to be affected by the operation 
	(Calendar.YEAR, Calendar.MONTH, Calendar.DATE). 
	
	Note: To substract, simply use a negative argument. 
    roll() does the same thing except you specify if you want to roll up (add 1) 
    or roll down (substract 1) to the specified Calendar field. The operation only
    affects the specified field while add() adjusts other Calendar fields. 
    See the following example, roll() makes january rolls to december in the same 
    year while add() substract the YEAR field for the correct result. Hence add() 
    is preferred even for subtraction by using a negative element.

*/
	
	public static void subToDate(){
		System.out.println("2. Subtract to a date Operation\n");
		
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Calendar c1 = Calendar.getInstance(); 
		c1.set(1999, 0 , 20); 
		System.out.println("Date is : " + sdf.format(c1.getTime()));
		
		// roll down, substract 1 month
		c1.roll(Calendar.MONTH, false); 
		System.out.println("Date roll down 1 month : " + sdf.format(c1.getTime())); 

		c1.set(1999, 0 , 20); 
		System.out.println("Date is : " + sdf.format(c1.getTime()));
		c1.add(Calendar.MONTH, -1); 
		// substract 1 month
		System.out.println("Date minus 1 month : " + sdf.format(c1.getTime())); 
		System.out.println();
		System.out.println("-------------------------------------------------------");
	}
	
	public static void daysBetween2Dates(){
		System.out.println("3. No of Days between 2 dates\n");
		Calendar c1 = Calendar.getInstance(); 	//new GregorianCalendar();
		Calendar c2 = Calendar.getInstance(); 	//new GregorianCalendar();
	    c1.set(1999, 0 , 20); 
	    c2.set(1999, 0 , 22); 
	    System.out.println("Days Between "+c1.getTime()+" and "+ c2.getTime()+" is");
	    System.out.println((c2.getTime().getTime() - c1.getTime().getTime())/(24*3600*1000));
	    System.out.println();
	    System.out.println("-------------------------------------------------------");
	}
	
	public static void daysInMonth() {
		System.out.println("4. No of Days in a month for a given date\n");
		Calendar c1 = Calendar.getInstance(); 	//new GregorianCalendar();
	    c1.set(1999, 6 , 20); 
	    int year = c1.get(Calendar.YEAR);
	    int month = c1.get(Calendar.MONTH);
//	    int days = c1.get(Calendar.DATE);
		int [] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
		daysInMonths[1] += DateUtility.isLeapYear(year) ? 1 : 0;
		System.out.println("Days in "+month+"th month for year "+year+" is "+ daysInMonths[c1.get(Calendar.MONTH)]);
	    System.out.println();
	    System.out.println("-------------------------------------------------------");
	}
	
	public static void validateAGivenDate() {
		System.out.println("5. Validate a given date\n");
		String dt = "20011223";   
		String invalidDt = "20031315";
		String dateformat = "yyyyMMdd";   
		Date dt1=null , dt2=null;
		try {     
			SimpleDateFormat sdf = new SimpleDateFormat(dateformat);    
			sdf.setLenient(false);    
			dt1 = sdf.parse(dt);  
			dt2 = sdf.parse(invalidDt);   
			System.out.println("Date is ok = " + dt1 + "(" + dt + ")");     
		}  
		catch (ParseException e) {     
			System.out.println(e.getMessage()); 
		}  
		catch (IllegalArgumentException e) {    
			System.out.println("Invalid date");     
		}
	    System.out.println();
		System.out.println("-------------------------------------------------------");
	}
	
	public static void compare2Dates(){
		System.out.println("6. Comparision of 2 dates\n");
		SimpleDateFormat fm = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.set(2000, 02, 15);
		c2.set(2001, 02, 15);
		
		System.out.print(fm.format(c1.getTime())+" is ");
		if(c1.before(c2)){
			System.out.println("less than "+fm.format(c2.getTime()));
		}else if(c1.after(c2)){
			System.out.println("greater than "+fm.format(c2.getTime()));
		}else if(c1.equals(c2)){
			System.out.println("is equal to "+fm.format(c2.getTime()));
		}
		System.out.println();
		System.out.println("-------------------------------------------------------");
	}
	
	public static void getDayofTheDate() {
		System.out.println("7. Get the day for a given date\n");
		Date d1 = new Date();
		String day = null;
	    DateFormat f = new SimpleDateFormat("EEEE");
	    try {
	     day = f.format(d1);
	    }
	    catch(Exception e) {
	      e.printStackTrace();
	    }
	    System.out.println("The day for "+d1+" is "+day);
	    System.out.println();
	    System.out.println("-------------------------------------------------------");
	}
	
	//Utility Method to find whether an Year is a Leap year or Not
	public static boolean isLeapYear(int year){
		 if((year%100 != 0) || (year%400 == 0)){
			 return true;
		 }
		 return false;
	}

	public static void main(String args[]){
		addToDate();	//Add day, month or year to a date field.
		subToDate();	//Subtract day, month or year to a date field.
		daysBetween2Dates();	//The "right" way would be to compute the julian day number of both dates and then do the substraction.
		daysInMonth();	//Find the number of days in a month for a given date
		validateAGivenDate();	//Check whether the date format is proper
					// Convert String to Date using parse function
		compare2Dates();	//Compare 2 dates
		getDayofTheDate();
	}
	
}
