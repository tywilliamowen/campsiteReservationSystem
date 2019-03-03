package com.techelevator.model.jdbc;

import java.time.LocalDate;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.ReservationDAO;

public class JDBCReservationDAO implements ReservationDAO {
	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Long makeCampsiteReservation(long campsiteId, String name, LocalDate reservationStartDate,
			LocalDate reservationEndDate) {
		String sql = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date) VALUES (default, ?, ?, ?, ?, now()) RETURNING reservation_id";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campsiteId, name, reservationStartDate,
				reservationEndDate);
		results.next();

		long resId = results.getLong("reservation_id");

		return resId;
	}

}
