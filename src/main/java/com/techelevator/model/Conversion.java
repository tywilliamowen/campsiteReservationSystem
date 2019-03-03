package com.techelevator.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Conversion {

	public String convertCampgrounDisplayMonth(String month) {
		int monthAsInt = Integer.parseInt(month);
		String openFromMonthStringLowerCase = Month.of(monthAsInt).name().toLowerCase();
		String openFromMonthStringStringCapitalFirstLetter = Character
				.toUpperCase(openFromMonthStringLowerCase.charAt(0)) + openFromMonthStringLowerCase.substring(1);
		return openFromMonthStringStringCapitalFirstLetter;
	}

	public String formatParkVisitors(int visitors) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		return numberFormat.format(visitors);
	}

	public String formatParkArea(int parkArea) {
		NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
		return numberFormat.format(parkArea) + " sq km";
	}

	public String convertBooleanToYesNo(boolean booleanToConvert) {
		String booleanAsString = String.valueOf(booleanToConvert);
		if (booleanAsString.contentEquals("true")) {
			booleanAsString = "Yes";
		} else if (booleanAsString.contentEquals("false")) {
			booleanAsString = "No";
		}
		return booleanAsString;
	}

	public String convertBooleanToYesNA(boolean booleanToConvert) {
		String booleanAsString = String.valueOf(booleanToConvert);
		if (booleanAsString.contentEquals("true")) {
			booleanAsString = "Yes";
		} else if (booleanAsString.contentEquals("false")) {
			booleanAsString = "N/A";
		}
		return booleanAsString;
	}

	public String convertIntToStringWhereZeroDisplaysNA(int intToConvert) {
		String returnString;
		if (intToConvert == 0) {
			returnString = "N/A";
		} else {
			returnString = Integer.toString(intToConvert);
		}
		return returnString;
	}

	public String formatLocalDateToMMddYYYYWithDashes(LocalDate date) {
		String establishedDateOriginal = date.toString();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM-dd-yyyy");
		Date properlyFormattedDateWithDashes = null;
		try {
			properlyFormattedDateWithDashes = format1.parse(establishedDateOriginal);
		} catch (ParseException e) {
		}
		String properlyFormattedDateWithSlashes = format2.format(properlyFormattedDateWithDashes).toString()
				.replace("-", "/");
		return properlyFormattedDateWithSlashes;
	}

	public String returnTotalCostOfStay(LocalDate arrival, LocalDate departure, BigDecimal dailyCost) {
		BigDecimal lengthOfStay = new BigDecimal(ChronoUnit.DAYS.between(arrival, departure));
		BigDecimal totalCostOfStay = dailyCost.multiply(lengthOfStay);
		return NumberFormat.getCurrencyInstance().format(totalCostOfStay);
	}

	public List<String> wrapString(String string, int desiredSpace) {
		String[] splatString = string.split(" ");
		List<String> wrappedLines = new ArrayList<String>();
		String printString = "";
		int lengthCount = 0;
		for (String word : splatString) {
			lengthCount++;
			if (printString.length() < desiredSpace) {
				printString += word + " ";
			}
			if (printString.length() >= desiredSpace) {
				wrappedLines.add(printString);
				printString = "";
			}
			if (lengthCount == splatString.length) {
				wrappedLines.add(printString);
			}
		}
		return wrappedLines;
	}

}
