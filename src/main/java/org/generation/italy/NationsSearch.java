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

		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {

//			System.out.print(con.isClosed());
			System.out.print("Enter string to search: ");
			String stringToSearch = in.nextLine();

			String selectNations = "select\r\n" + "	c2.country_id as Id,\r\n" + "	c2.name as Country,\r\n"
					+ "	r.name as Region,\r\n" + "	c.name as Continent\r\n" + "from\r\n" + "	continents c\r\n"
					+ "left join regions r on\r\n" + "	c.continent_id = r.continent_id\r\n"
					+ "left join countries c2 on\r\n" + "	r.region_id = c2.region_id\r\n" + "where\r\n"
					+ "	c2.name like ?\r\n" + "order by\r\n" + "	c2.name;";

			try (PreparedStatement psNation = con.prepareStatement(selectNations)) {

				psNation.setString(1, "%" + stringToSearch + "%");

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
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		in.close();
	}

}
