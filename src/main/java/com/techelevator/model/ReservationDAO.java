package com.techelevator.model;

import java.time.LocalDate;

public interface ReservationDAO {
	
	/**
	 * Make a reservation and add it to the database.
	 * 
	 * @return serial primary key reservation_id to be used as the confirmation number
	 */

	public Long makeCampsiteReservation(long campsiteId, String name, LocalDate reservationStartDate,
			LocalDate reservationEndDate);
}
