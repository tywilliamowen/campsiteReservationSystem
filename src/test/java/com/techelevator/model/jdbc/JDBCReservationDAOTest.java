package com.techelevator.model.jdbc;

import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAOTest {
	private static SingleConnectionDataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private ReservationDAO dao;
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

	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dao = new JDBCReservationDAO(dataSource);
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
	public void reservation_is_created_successfully() {
		long reservationNumber = 0;
		reservationNumber = dao.makeCampsiteReservation(siteId, "ty", LocalDate.now().minusDays(100),
				LocalDate.now().minusDays(104));

		Assert.assertNotEquals(0, reservationNumber);
	}

}
