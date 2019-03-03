package com.techelevator.model.jdbc;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.CampgroundDAO;

public class JDBCCampgroundDAOTest {

	private static SingleConnectionDataSource dataSource;
	private CampgroundDAO dao;
	private JdbcTemplate jdbcTemplate;

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
		dao = new JDBCCampgroundDAO(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void get_all_campgrounds_by_park_id() {
		int originalSize = dao.getCampgroundByParkId(1).size();

		BigDecimal dailyFee = new BigDecimal(29.95);

		insertCampground(1, "Test Campground", "1", "12", dailyFee);

		Assert.assertEquals("Wrong number of Campgrounds Returned", originalSize + 1,
				dao.getCampgroundByParkId(1).size());
	}

	@Test
	public void check_for_zero_campgrounds() {

		// this will break if there is a park with park_id 2000000
		LocalDate today = LocalDate.now();
		insertPark("Test Park", "Test Location", today, 5, 5, "very cool test park");

		int zeroCampgrounds = dao.getCampgroundByParkId(2000000).size();

		Assert.assertEquals("Issue with campground count of Zero", 0, zeroCampgrounds);
	}

	private void insertCampground(int parkId, String name, String openFromMonth, String openToMonth,
			BigDecimal dailyFee) {
		String projectSql = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (default, ?, ?, ?, ?, ?) ";
		jdbcTemplate.update(projectSql, parkId, name, openFromMonth, openToMonth, dailyFee);

	}

	private void insertPark(String name, String location, LocalDate establishedDate, int area, int visitors,
			String description) {
		String projectSql = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (2000000, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(projectSql, name, location, establishedDate, area, visitors, description);

	}
}
