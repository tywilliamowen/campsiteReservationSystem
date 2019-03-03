package com.techelevator.model;

import java.util.List;

public interface ParkDAO {

	/**
	 * Get all parks from the database.
	 * 
	 * @return all parks as Park objects in a List
	 */
	public List<Park> getAllParks();

}
