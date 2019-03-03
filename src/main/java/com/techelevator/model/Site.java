package com.techelevator.model;

public class Site {

	private long siteId;
	private long campgroundId;
	private int siteNumber;
	private int maxOccupancy;
	private boolean accessaible;
	private int maxRvLength;
	private boolean utilities;

	public long getSiteId() {
		return siteId;
	}

	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}

	public long getCampgroundId() {
		return campgroundId;
	}

	public void setCampgroundId(long campgroundId) {
		this.campgroundId = campgroundId;
	}

	public int getSiteNumber() {
		return siteNumber;
	}

	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}

	public int getMaxOccupancy() {
		return maxOccupancy;
	}

	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}

	public boolean isAccessaible() {
		return accessaible;
	}

	public void setAccessaible(boolean accessaible) {
		this.accessaible = accessaible;
	}

	public int getMaxRvLength() {
		return maxRvLength;
	}

	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}

	public boolean isUtilities() {
		return utilities;
	}

	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}

	public String toString() {
		String siteNumberToString = Integer.toString(siteNumber);
		return siteNumberToString;
	}

}
