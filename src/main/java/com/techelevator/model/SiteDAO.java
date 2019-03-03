package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	/**
	 * Get top five avaliable campsites at a given campground
	 * during a given reservation span. 
	 * 
	 * @return 5 Sites as Site objects in a List
	 */

	
	public List<Site> getCampsiteAvalibilityByReservationDates(long campgroundId, LocalDate reservationStartDate, LocalDate reservationEndDate);
	
}
