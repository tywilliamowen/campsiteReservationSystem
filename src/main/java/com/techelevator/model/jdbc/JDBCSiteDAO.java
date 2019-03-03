package com.techelevator.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.Site;
import com.techelevator.model.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getCampsiteAvalibilityByReservationDates(long campgroundId, LocalDate reservationStartDate,
			LocalDate reservationEndDate) {
		List<Site> avaliableSites = new ArrayList<Site>();

		String sql = "Select distinct * from site\n"
				+ "join campground on site.campground_id = campground.campground_id " + "where site.campground_id = ? "
				+ "and site_id not in " + "(select site.site_id from site "
				+ "join reservation on reservation.site_id = site.site_id "
				+ "where (? >= reservation.from_date AND ? <= reservation.to_date) OR (? >= reservation.from_date AND ? <= reservation.to_date)) order by daily_fee "
				+ "LIMIT 5";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campgroundId, reservationStartDate, reservationEndDate,
				reservationEndDate, reservationStartDate);

		while (results.next()) {
			Site site = mapRowToSite(results);
			avaliableSites.add(site);
		}
		return avaliableSites;
	}

	private Site mapRowToSite(SqlRowSet results) {
		Site site = new Site();
		site.setSiteId(results.getLong("site_id"));
		site.setCampgroundId(results.getLong("campground_id"));
		site.setSiteNumber(results.getInt("site_number"));
		site.setMaxOccupancy(results.getInt("max_occupancy"));
		site.setAccessaible(results.getBoolean("accessible"));
		site.setMaxRvLength(results.getInt("max_rv_length"));
		site.setUtilities(results.getBoolean("utilities"));
		return site;
	}

}
