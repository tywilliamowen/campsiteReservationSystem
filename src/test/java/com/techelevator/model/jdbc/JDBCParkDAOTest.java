package com.techelevator.model.jdbc;

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
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.ParkDAO;

public class JDBCParkDAOTest {

	private static SingleConnectionDataSource dataSource;
	private ParkDAO dao;
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
		dao = new JDBCParkDAO(dataSource);
	}

	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	@Test
	public void get_all_parks_after_inserting_one_park() {
		int originalSize = dao.getAllParks().size();

		LocalDate today = LocalDate.now();
		insertPark("Test Park", "Test City", today, 100, 100, "Great Park, wow.");

		Assert.assertEquals("Wrong number of Parks Returned", originalSize + 1, dao.getAllParks().size());
	}

	private void insertPark(String name, String location, LocalDate establishedDate, int area, int visitors,
			String description) {
		String projectSql = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description) VALUES (Default, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(projectSql, name, location, establishedDate, area, visitors, description);

	}

}
