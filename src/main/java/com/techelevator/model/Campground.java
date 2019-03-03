package com.techelevator.model;

import java.math.BigDecimal;

public class Campground {

	private Long id;
	private long parkId;
	private String name;
	private String openFromMonth;
	private String openToDate;
	private BigDecimal dailyFee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getParkId() {
		return parkId;
	}

	public void setParkId(long parkId) {
		this.parkId = parkId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOpenFromMonth() {
		return openFromMonth;
	}

	public void setOpenFromMonth(String openFromMonth) {
		this.openFromMonth = openFromMonth;
	}

	public String getOpenToMonth() {
		return openToDate;
	}

	public void setOpenToMonth(String openToDate) {
		this.openToDate = openToDate;
	}

	public BigDecimal getDailyFee() {
		return dailyFee;
	}

	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}

	public String toString() {
		return this.name;
	}

}
