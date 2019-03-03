package com.techelevator.model;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.model.jdbc.JDBCParkDAO;
import com.techelevator.model.jdbc.JDBCReservationDAO;
import com.techelevator.model.jdbc.JDBCSiteDAO;

public class ReservationSystem3 {
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservationDAO reservationDAO;

	public ReservationSystem3() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		this.parkDAO = new JDBCParkDAO(dataSource);
		this.campgroundDAO = new JDBCCampgroundDAO(dataSource);
		this.siteDAO = new JDBCSiteDAO(dataSource);
		this.reservationDAO = new JDBCReservationDAO(dataSource);
	}

	public List<Campground> getCampgroundByParkId(long parkId) {
		return campgroundDAO.getCampgroundByParkId(parkId);
	}

	public List<Park> getAllParks() {
		return parkDAO.getAllParks();
	}

	public Long makeCampsiteReservation(long campsiteId, String name, LocalDate reservationStartDate,
			LocalDate reservationEndDate) {
		return reservationDAO.makeCampsiteReservation(campsiteId, name, reservationStartDate, reservationEndDate);
	}

	public List<Site> getCampsiteAvalibilityByReservationDates(long campgroundId, LocalDate reservationStartDate,
			LocalDate reservationEndDate) {
		return siteDAO.getCampsiteAvalibilityByReservationDates(campgroundId, reservationStartDate, reservationEndDate);
	}
}
