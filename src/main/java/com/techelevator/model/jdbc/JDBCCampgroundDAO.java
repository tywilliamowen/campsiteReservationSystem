package com.techelevator.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Campground;
import com.techelevator.model.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> getCampgroundByParkId(long parkId) {
		List<Campground> campgrounds = new ArrayList<Campground>();

		String sql = "SELECT campground_id, name, open_from_mm, open_to_mm, daily_fee FROM campground WHERE park_id = ? ORDER BY campground.campground_id";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, parkId);

		while (results.next()) {
			Campground campground = mapRowToCampground(results);
			campgrounds.add(campground);
		}

		return campgrounds;
	}

	private Campground mapRowToCampground(SqlRowSet results) {
		Campground campground = new Campground();
		campground.setId(results.getLong("campground_id"));
		campground.setName(results.getString("name"));
		campground.setOpenFromMonth(results.getString("open_from_mm"));
		campground.setOpenToMonth(results.getString("open_to_mm"));
		campground.setDailyFee(results.getBigDecimal("daily_fee"));

		return campground;
	}

}
