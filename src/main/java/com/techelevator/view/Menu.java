package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.techelevator.model.Campground;
import com.techelevator.model.Conversion;
import com.techelevator.model.Park;
import com.techelevator.model.Site;

public class Menu {

	private PrintWriter out;
	private Scanner in;
	private Conversion convert;
	private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	public Menu(InputStream input, OutputStream output) {
		this.convert = new Conversion();
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Park getParkChoiceFromOptions(List<Park> allParks) {
		Park choice = null;

		while (choice == null) {
			displayParkMenuOptions(allParks);
			choice = getParkChoiceFromUserInput(allParks);
		}
		return choice;
	}
	
	
	private void displayParkMenuOptions(List<Park> allParks) {
		out.println("View Parks Interface");
		out.println("Select a Park for Further Details");
		for (int i = 0; i < allParks.size(); i++) {
			int numOfOption = 1 + i;
			out.println("   " + numOfOption + ") " + allParks.get(i).getName());
		}
		out.println("   Q) quit");
		out.flush();
	}
	
	private Park getParkChoiceFromUserInput(List<Park> allParks) {
		Park choice = null;
		String userInput = in.nextLine();
		try {
			if (userInput.equals("Q")) {
				System.exit(0);
			}
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption <= allParks.size()) {
				choice = allParks.get(selectedOption - 1);
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}


	// ************************************// END (1st) INITAL CHOOSE PARK
	// MENU******************************//

	// ************************************// BEGIN (2nd) PARK INFORMATION
	// SCREEN*****************************//
	
	public void displayParkInfo(Park park) {
		out.println();
		out.println("Park Information Screen");
		out.println(park.getName() + " National Park");
		out.println("Location:        " + park.getLocation());
		out.println("Established:     " + convert.formatLocalDateToMMddYYYYWithDashes(park.getEstablishDate()));
		out.println("Area:            " + convert.formatParkArea(park.getArea()));
		out.println("Annual Visitors: " + convert.formatParkVisitors(park.getVisitors()));
		out.println();
		List<String> parkDescription = convert.wrapString(park.getDescription(), 80);
		for (String eachLine : parkDescription) {
			out.println(eachLine);
		}
	}


	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			out.print("\nSelect a Command");
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			int optionNum = i + 1;
			out.println("   " + optionNum + ") " + options[i]);
		}
		out.flush();
	}
	
	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption > 0 && selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	// *******************************************// END (2nd) PARK INFORMATION
	// SCREEN*************************************//

	// ****************************************// BEGIN (3rd) / (4th) PARK
	// CAMPGROUNDS SCREEN**********************************************//

	public void displayCampgroundsInfo(List<Campground> allCamps, Park park) {
		out.println("Park Campgrounds");
		out.println(park.getName() + " National Park Campgrounds");
		out.println();
		out.printf("%8s%33s%11s%16s", "Name", "Open", "Close", "Daily Fee\n");
		for (int i = 0; i < allCamps.size(); i++) {
			int numOfOption = 1 + i;
			Campground camp = allCamps.get(i);
			String fromMonth = convert.convertCampgrounDisplayMonth(camp.getOpenFromMonth());
			String toMonth = convert.convertCampgrounDisplayMonth(camp.getOpenToMonth());

			out.printf("#%-3s%-33s%-10s%-11s$%.2f\n", numOfOption, camp.getName(), fromMonth, toMonth,
					camp.getDailyFee());
		}
		out.flush();
	}

	
	

	public Object getCampChoiceFromOptions(List<Campground> allCamps) {
		Object choice = null;
		while (choice == null) {
			displayCampgroundsSearchInfo(allCamps);
			choice = getCampChoiceFromUserInput(allCamps);
		}
		return choice;
	}

	private void displayCampgroundsSearchInfo(List<Campground> allCamps) {
		out.println("Search for Campground Reservation");
		out.printf("%8s%33s%11s%16s", "Name", "Open", "Close", "Daily Fee\n");
		for (int i = 0; i < allCamps.size(); i++) {
			int numOfOption = 1 + i;
			Campground camp = allCamps.get(i);
			String fromMonth = camp.getOpenFromMonth();
			String toMonth = camp.getOpenToMonth();

			out.printf("#%-3s%-33s%-10s%-11s$%.2f\n", numOfOption, camp.getName(),
					convert.convertCampgrounDisplayMonth(fromMonth), convert.convertCampgrounDisplayMonth(toMonth),
					camp.getDailyFee());
		}
		out.flush();
	}

	private Object getCampChoiceFromUserInput(List<Campground> allCamps) {
		Object choice = null;
		out.print("\nWhich campground (enter 0 to cancel)? ");
		out.flush();
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption <= allCamps.size()) {
				choice = selectedOption;
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	public LocalDate waitForValidArrivalDateFromUser() {
		LocalDate date = null;
		while (date == null) {
			date = getArrivalDateUserInput();
		}
		return date;
	}

	private LocalDate getArrivalDateUserInput() {
		LocalDate choice = null;
		out.print("What is the arrival date? ");
		out.flush();
		String userInput = in.nextLine();
		try {
			choice = LocalDate.parse(userInput, dateTimeFormat);
		} catch (DateTimeParseException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	public LocalDate waitForValidDepartureDateFromUser(LocalDate arrivalDate) {
		LocalDate date = null;
		while (date == null) {
			date = getDepartureDateUserInput();
		}
		if (date.isBefore(arrivalDate)) {
			out.println("***Invalid departure date " + date + " is on or before the arrival date***");
			 waitForValidDepartureDateFromUser(arrivalDate); 
		}
		return date;
	}

	private LocalDate getDepartureDateUserInput() {
		LocalDate choice = null;
		out.print("What is the departure date? ");
		out.flush();
		String userInput = in.nextLine();
		try {
			choice = LocalDate.parse(userInput, dateTimeFormat);
		} catch (DateTimeParseException e) {
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");	
		}
		
		return choice;
	}

	// ****************************************// END (3rd) / (4th) CAMPGROUND
	// SCREEN**********************************************//
	// ****************************************// BEGIN (5th) )SEARCH FOR CAMPSITE
	// SCREEN**********************************************//

	public void displayAvaliableSites(List<Site> allSites, Campground selectedCampground, LocalDate ArrivalDate,
			LocalDate departureDate) {
		out.println("Search for Campground Reservation");
		out.printf("%-13s%-12s%-17s%-17s%-11s%-1s\n", "Site No.", "Max Occup.", "Accessable?", "Max RV Length",
				"Utility", "Cost");
		for (int i = 0; i < allSites.size(); i++) {
			int numOfOption = 1 + i;
			Site site = allSites.get(i);

			out.printf("%-13s%-12s%-17s%-17s%-11s%-11s\n", numOfOption, site.getMaxOccupancy(),
					convert.convertBooleanToYesNo(site.isAccessaible()),
					convert.convertIntToStringWhereZeroDisplaysNA(site.getMaxRvLength()),
					convert.convertBooleanToYesNA(site.isUtilities()),
					convert.returnTotalCostOfStay(ArrivalDate, departureDate, selectedCampground.getDailyFee()));
		}
		out.flush();
	}

	// ****************************************// END (5th) )SEARCH FOR CAMPSITE
	// SCREEN **********************************************//
	// ****************************************// BEGIN (6th) )BOOK CAMPSITE
	// **********************************************//

	public Object getReservationsChoiceFromOptions(List<Site> allSites) {
		Object choice = null;
		while (choice == null) {
			choice = getSiteChoiceFromUserInput(allSites);
		}
		return choice;
	}

	private Object getSiteChoiceFromUserInput(List<Site> allSites) {
		Object choice = null;
		out.print("\nWhich site should be reserved (enter 0 to cancel)? ");
		out.flush();
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if (selectedOption <= allSites.size())
				;
			{
				choice = selectedOption;
			}
		} catch (NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will
			// be null
		}
		if (choice == null) {
			out.println("\n*** " + userInput + " is not a valid option ***\n");
		}
		return choice;
	}

	public Object getReservationsNameFromOptions(List<Site> allSites) {
		Object choice = null;
		while (choice == null) {
			choice = getReservationNameFromUserInput(allSites);
		}
		return choice;
	}

	private Object getReservationNameFromUserInput(List<Site> allSites) {
		Object choice = null;
		out.print("\nWhat name should the reservation be made under?");
		out.flush();
		String userInput = in.nextLine();
		choice = userInput;
		;

		if (choice == null) {
			out.println("\n*** Please enter a valid reservation name ***\n");
		}
		return choice;
	}

	public void displayReservationComfinationNumber(long confirmationNumber) {
//	
		out.println("\nThe reservation has been made and the confirmation id is " + confirmationNumber + "\n");
	}
}
