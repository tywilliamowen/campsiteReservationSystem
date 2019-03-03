package com.techelevator.model.jdbc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAOTest {

	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private SiteDAO dao;
	private Long parkId;
	private Long siteId;
	private Long reservationId;
	private Long campgroundId;

	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new JDBCSiteDAO(dataSource);
// CREATE DUMMY ROWS ON TABLES
		String createParkSql = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (2000000, 'Test Park', 'Test Location', '1919-02-26', 2000000,  2000000, 'Test Park Description') RETURNING park_id ";
		String createCampgroundSql = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (2000000, 2000000, 'Test Campground', '01', '12', '30.00') RETURNING campground_id ";
		String createSiteSql = " INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) VALUES (2000000, 2000000, 2000000, 2000000,  true, 0 ,true) RETURNING site_id";
		String createReservationSql = "INSERT INTO reservation(reservation_id, site_id, name, from_date, to_date, create_date) VALUES (2000000, 2000000, 'Test Reservation', now(), now() + interval '2 day', now()) RETURNING reservation_id";

// STORE DUMMY INFO FROM INSERTS		
		parkId = jdbcTemplate.queryForObject(createParkSql, Long.class);
		campgroundId = jdbcTemplate.queryForObject(createCampgroundSql, Long.class);
		siteId = jdbcTemplate.queryForObject(createSiteSql, Long.class);
		reservationId = jdbcTemplate.queryForObject(createReservationSql, Long.class);

	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void returns_no_reservations_avaliable_exact_dates() {

		List<Site> avaliableSites = dao.getCampsiteAvalibilityByReservationDates(campgroundId, LocalDate.now(),
				LocalDate.now().plusDays(2));

		assertEquals(0, avaliableSites.size());
	}

	@Test
	public void returns_no_reservations_avaliable_overlaping_last_dates() {

		List<Site> avaliableSites = dao.getCampsiteAvalibilityByReservationDates(campgroundId,
				LocalDate.now().minusDays(10), LocalDate.now());

		assertEquals(0, avaliableSites.size());
	}

	@Test
	public void returns_no_reservations_avaliable_overlaping_first_dates() {

		List<Site> avaliableSites = dao.getCampsiteAvalibilityByReservationDates(campgroundId, LocalDate.now(),
				LocalDate.now().plusDays(10));

		assertEquals(0, avaliableSites.size());
	}

	@Test
	public void returns_no_reservations_avaliable_reservation_in_middle_of_selected_dates() {

		List<Site> avaliableSites = dao.getCampsiteAvalibilityByReservationDates(campgroundId,
				LocalDate.now().minusDays(1), LocalDate.now().plusDays(10));

		assertEquals(0, avaliableSites.size());
	}

	@Test
	public void returns_one_reservation_no_overlaping_dates_after_reservation() {

		List<Site> avaliableSites = dao.getCampsiteAvalibilityByReservationDates(campgroundId,
				LocalDate.now().plusDays(100), LocalDate.now().plusDays(104));

		assertEquals(1, avaliableSites.size());
	}

	@Test
	public void returns_one_reservation_no_overlaping_dates_before_reservation() {

		List<Site> avaliableSites = dao.getCampsiteAvalibilityByReservationDates(campgroundId,
				LocalDate.now().minusDays(100), LocalDate.now().minusDays(104));

		assertEquals(1, avaliableSites.size());
	}

}
