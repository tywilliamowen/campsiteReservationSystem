package com.techelevator.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ConversionTest {

	private Conversion convert; 
 	
	
	@Before
	public void setup() {
		convert = new Conversion();
	}
	
	@Test
	public void convert_boolean_to_yes_with_to_yes_na() {
		boolean isTrue = true; 
		Assert.assertEquals("Yes", convert.convertBooleanToYesNA(isTrue));
	}
	
	@Test
	public void convert_boolean_to_na_with_to_yes_na() {
		boolean isFalse = false; 
		Assert.assertEquals("N/A", convert.convertBooleanToYesNA(isFalse));
	}
	
	@Test
	public void convert_boolean_to_no_with_to_yes_no() {
		boolean isFalse = false; 
		Assert.assertEquals("No", convert.convertBooleanToYesNo(isFalse));
	}
	
	@Test
	public void convert_boolean_to_yes_with_to_yes_no() {
		boolean isTrue = true; 
		Assert.assertEquals("Yes", convert.convertBooleanToYesNo(isTrue));
	}
	
	@Test
	public void convert_int_val_one_with_campground_display_month() {
		String one = "1"; 
		Assert.assertEquals("January", convert.convertCampgrounDisplayMonth(one));
	}
	
	
	@Test
	public void convert_int_val_three_with_campground_display_month() {
		String three = "3"; 
		Assert.assertEquals("March", convert.convertCampgrounDisplayMonth(three));
	}
	
	@Test
	public void convert_int_val_zero_with_zerp_displays_na() {
		Assert.assertEquals("N/A", convert.convertIntToStringWhereZeroDisplaysNA(0));
	}
	
	@Test
	public void convert_int_val_737373_with_zerp_displays_na() {
		Assert.assertEquals("737373", convert.convertIntToStringWhereZeroDisplaysNA(737373));
	}
	
	@Test
	public void format_local_date_with_dashes() {
		LocalDate testDate = LocalDate.of(2014, Month.JANUARY, 14);
		Assert.assertEquals("01/14/2014", convert.formatLocalDateToMMddYYYYWithDashes(testDate));
	}
	
	@Test
	public void format_4_digit_number_with_correct_commas_visitors() {
		Assert.assertEquals("1,234", convert.formatParkVisitors(1234));
	}
	
	@Test
	public void format_9_digit_number_with_correct_commas_visitors() {
		Assert.assertEquals("123,456,789", convert.formatParkVisitors(123456789));
	}
	
	@Test
	public void format_1_digit_number_with_correct_commas_visitors() {
		Assert.assertEquals("0", convert.formatParkVisitors(0));
	}
	
	@Test
	public void format_4_digit_number_with_correct_commas_area() {
		Assert.assertEquals("1,234 sq km", convert.formatParkArea(1234));
	}
	
	@Test
	public void format_9_digit_number_with_correct_commas_area() {
		Assert.assertEquals("123,456,789 sq km", convert.formatParkArea(123456789));
	}
	
	@Test
	public void format_1_digit_number_with_correct_commas_area() {
		Assert.assertEquals("0 sq km", convert.formatParkArea(0));
	}
	
	@Test
	public void return_total_cost_4_night_stay_at_123_per_night() {
		LocalDate testDate = LocalDate.of(2014, Month.JANUARY, 14);
		LocalDate testDate2 = LocalDate.of(2014, Month.JANUARY, 18);
		BigDecimal costPerNight = new BigDecimal(123); 
		Assert.assertEquals("$492.00", convert.returnTotalCostOfStay(testDate, testDate2, costPerNight));
	}
	
	@Test
	public void return_total_cost_0_night_stay_at_123_per_night() {
		LocalDate testDate = LocalDate.of(2014, Month.JANUARY, 14);
		LocalDate testDate2 = LocalDate.of(2014, Month.JANUARY, 14);
		BigDecimal costPerNight = new BigDecimal(123); 
		Assert.assertEquals("$0.00", convert.returnTotalCostOfStay(testDate, testDate2, costPerNight));
	}
	
	
	
}
