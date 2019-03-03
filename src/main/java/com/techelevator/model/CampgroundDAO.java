package com.techelevator.model;

import java.util.List;

public interface CampgroundDAO {

	/**
	 * Get campgrounds at a given park from the database.
	 * 
	 * @return campgrounds as Campground objects in a List
	 */

	public List<Campground> getCampgroundByParkId(long parkId);

}
