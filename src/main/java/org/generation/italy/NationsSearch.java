/**
 * 
 */
package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lucai
 *
 */
public class NationsSearch {

	private final static String DB_URL = "jdbc:mysql://localhost:3306/nations";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "rootpassword";

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String leftAlignFormat = "| %-4d | %-50s | %-30s | %-15s |%n";

		System.out.format(
				"+------+----------------------------------------------------+--------------------------------+-----------------+%n");
		System.out.format(
				"| ID   | Country                                            | Region                         | Continent       |%n");
		System.out.format(
				"+------+----------------------------------------------------+--------------------------------+-----------------+%n");

		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

//			System.out.print(con.isClosed());

			String selectNations = "select\r\n" + "	c2.country_id as Id,\r\n" + "	c2.name as Country,\r\n"
					+ "	r.name as Region,\r\n" + "	c.name as Continent\r\n" + "from\r\n" + "	continents c\r\n"
					+ "left join regions r on\r\n" + "	c.continent_id = r.continent_id\r\n"
					+ "left join countries c2 on\r\n" + "	r.region_id = c2.region_id\r\n" + "order by\r\n"
					+ "	c2.name asc;";

			try (PreparedStatement psNation = con.prepareStatement(selectNations)) {

//				psNation.setInt(0, 0);

				try (ResultSet rsNation = psNation.executeQuery()) {

					if (rsNation.next() == false)
						System.out.println("Empty ResultSet");
					do {

//						Country c = new Country(rsNation.getInt(1), rsNation.getString(2), rsNation.getInt(3), null,
//								rsNation.getString(5), rsNation.getString(6), rsNation.getInt(7));

						System.out.format(leftAlignFormat, rsNation.getInt(1), rsNation.getString(2),
								rsNation.getString(3), rsNation.getString(4));

					} while (rsNation.next());
					System.out.format(
							"+------+----------------------------------------------------+--------------------------------+-----------------+%n");

				}
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
