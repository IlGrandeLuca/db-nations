/**
 * 
 */
package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);

		// Open server's connection
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

//			System.out.print(con.isClosed());
			System.out.print("Enter string to search: ");
			String stringToSearch = in.nextLine();

			String selectNations = "select\r\n" + "	c2.country_id as Id,\r\n" + "	c2.name as Country,\r\n"
					+ "	r.name as Region,\r\n" + "	c.name as Continent\r\n" + "from\r\n" + "	continents c\r\n"
					+ "left join regions r on\r\n" + "	c.continent_id = r.continent_id\r\n"
					+ "left join countries c2 on\r\n" + "	r.region_id = c2.region_id\r\n" + "where\r\n"
					+ "	c2.name like ?\r\n" + "order by\r\n" + "	c2.name;";

			// Insert string to search
			try (PreparedStatement psNation = con.prepareStatement(selectNations)) {

				psNation.setString(1, "%" + stringToSearch + "%");

				// Create output table
				try (ResultSet rsNation = psNation.executeQuery()) {

					String leftAlignFormat = "| %-4d | %-45s | %-26s | %-15s |%n";

					System.out.format(
							"+------+-----------------------------------------------+----------------------------+-----------------+%n");
					System.out.format(
							"| ID   | Country                                       | Region                     | Continent       |%n");
					System.out.format(
							"+------+-----------------------------------------------+----------------------------+-----------------+%n");

					if (rsNation.next() == false)
						System.out.println("Empty ResultSet...");
					do {

						System.out.format(leftAlignFormat, rsNation.getInt(1), rsNation.getString(2),
								rsNation.getString(3), rsNation.getString(4));

					} while (rsNation.next());
					System.out.format(
							"+------+-----------------------------------------------+----------------------------+-----------------+%n");

				}
			}

			System.out.print("Choose a country id: ");
			int countryId = Integer.parseInt(in.nextLine());
			selectLanguage(con, countryId);
			selectStats(con, countryId);

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		in.close();
	}

	// Get country's language
	private static void selectLanguage(Connection con, int countryId) throws SQLException {

		String selectLanguage = "select\r\n" + "	c.country_id,\r\n" + "	c.name,\r\n" + "	l.`language`\r\n"
				+ "from\r\n" + "	countries c\r\n" + "left join country_languages cl on\r\n"
				+ "	c.country_id = cl.country_id\r\n" + "left join languages l on\r\n"
				+ "	cl.language_id = l.language_id\r\n" + "where\r\n" + "	c.country_id = ?;";

		try (PreparedStatement psLanguage = con.prepareStatement(selectLanguage)) {

			psLanguage.setInt(1, countryId);

			try (ResultSet rsLanguage = psLanguage.executeQuery()) {

				if (rsLanguage.next() == false)
					System.out.println("Empty ResultSet...");
				System.out.println("\nDetails for country: " + rsLanguage.getString(2));
				System.out.print("Languages: ");
				do {

					if (rsLanguage.isLast())
						System.out.print(rsLanguage.getString(3));
					else
						System.out.print(rsLanguage.getString(3) + ", ");

				} while (rsLanguage.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Get country's stats
	private static void selectStats(Connection con, int countryId) throws SQLException {

		String selectStats = "select\r\n" + "	cs.`year`,\r\n" + "	cs.population,\r\n" + "	cs.gdp\r\n" + "from\r\n"
				+ "	countries c\r\n" + "inner join country_stats cs on\r\n" + "	c.country_id = cs.country_id\r\n"
				+ "where\r\n" + "	c.country_id = ?\r\n" + "order by\r\n" + "	cs.`year` desc\r\n" + "limit 1;";

		try (PreparedStatement psStats = con.prepareStatement(selectStats)) {

			psStats.setInt(1, countryId);

			try (ResultSet rsStats = psStats.executeQuery()) {

				System.out.println("\nMost recent stats");

				if (rsStats.next() == false)
					System.out.println("Empty ResultSet...");
				System.out.println("Year: " + rsStats.getInt(1));
				System.out.println("Population: " + rsStats.getLong(2));
				System.out.println("GDP: " + rsStats.getBigDecimal(3));
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

}
