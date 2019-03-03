package com.techelevator;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.techelevator.model.Campground;
import com.techelevator.model.Park;
import com.techelevator.model.ReservationSystem3;
import com.techelevator.model.Site;
import com.techelevator.view.Menu;

public class CampgroundCLI {

	private static final String PARK_INFORMATION_VIEW_CAMPGROUND = "View Campgrounds";
	private static final String PARK_INFORMATION_SEARCH_RESERVATION = "Search for Reservation";
	private static final String RETURN_TO_PREVIOUS_SCREEN = "Return to Previous Screen";
	private static final String[] PARK_INFORMATION_SCREEN = { PARK_INFORMATION_VIEW_CAMPGROUND,
			PARK_INFORMATION_SEARCH_RESERVATION, RETURN_TO_PREVIOUS_SCREEN };

	private static final String CAMPGROUND_RESERVATION_SEARCH = "Search for Available Reservation";
	private static final String[] CAMPGROUND_SELECTION_SCREEN = { CAMPGROUND_RESERVATION_SEARCH,
			RETURN_TO_PREVIOUS_SCREEN };

	private Menu menu;
	private ReservationSystem3 reservationSystem;

	public static void main(String[] args) throws IOException {
		Menu menu = new Menu(System.in, System.out);
		CampgroundCLI application = new CampgroundCLI(menu);

		application.run();
	}

	public CampgroundCLI(Menu menu) {
		this.menu = menu;
		reservationSystem = new ReservationSystem3();

	}

	private void run() {
		// BEGIN (1st) INITAL CHOOSE PARK MENU
		while (true) {
			Campground userSelectedCamp;
			List<Park> allParks = reservationSystem.getAllParks();
			Park userSelectedPark = menu.getParkChoiceFromOptions(allParks);

			// BEGIN (2nd) PARK INFORMATION SCREEN
			menu.displayParkInfo(userSelectedPark);
			while (true) {
				String parkInfoScreenChoice = (String) menu.getChoiceFromOptions(PARK_INFORMATION_SCREEN);
				List<Campground> listOfCampgrounds = reservationSystem.getCampgroundByParkId((long) userSelectedPark.getId());
				if (parkInfoScreenChoice.equals(RETURN_TO_PREVIOUS_SCREEN)) {
					break;
				}
				if (parkInfoScreenChoice.equals(PARK_INFORMATION_VIEW_CAMPGROUND)) {

					// BEGIN (3rd) PARK CAMPGROUNDS SCREEN
					menu.displayCampgroundsInfo(listOfCampgrounds, userSelectedPark);
					while (true) {
						String campgroundsScreenChoice = (String) menu
								.getChoiceFromOptions(CAMPGROUND_SELECTION_SCREEN);
						if (campgroundsScreenChoice.equals(RETURN_TO_PREVIOUS_SCREEN)) {
							break;
						}
						if (campgroundsScreenChoice.equals(CAMPGROUND_RESERVATION_SEARCH)) {
							// BEGIN (4TH) SEARCH RESERVATION SCREEN
							while (true) {
								int userCampChoice = (int) menu.getCampChoiceFromOptions(listOfCampgrounds);
								if (userCampChoice == 0) {
									break;
								} else {
									userSelectedCamp = listOfCampgrounds.get(userCampChoice - 1);
								}

								LocalDate arrivalDate = menu.waitForValidArrivalDateFromUser();
								LocalDate departureDate = menu.waitForValidDepartureDateFromUser(arrivalDate);
								List<Site> avaliableSites = reservationSystem.getCampsiteAvalibilityByReservationDates(
										userSelectedCamp.getId(), arrivalDate, departureDate);
								menu.displayAvaliableSites(avaliableSites, userSelectedCamp, arrivalDate,
										departureDate);
								// BEGIN (5TH) MAKE RESERVATION SCREEN
								while (true) {
									int userCampsiteChoice = (int) menu
											.getReservationsChoiceFromOptions(avaliableSites);
									if (userCampsiteChoice == 0) {
										break;
									} else {
										String userReservationName = (String) menu
												.getReservationsNameFromOptions(avaliableSites);
										long userSelectedCampsiteID = avaliableSites.get(userCampsiteChoice)
												.getSiteId();
										long confirmationNumber = reservationSystem.makeCampsiteReservation(
												userSelectedCampsiteID, userReservationName, arrivalDate,
												departureDate);
										menu.displayReservationComfinationNumber(confirmationNumber);
									}

								}
							}
						}
					}
				}
			}
		}
	}
}
