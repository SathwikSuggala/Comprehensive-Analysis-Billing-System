package com.sathwik.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LearnJdbc {
	
//	static ResultSet getGivenColumnsFromCustomersTable( Connection con, String... columns) {
//		String query = "SELECT city, customerName FROM customers WHERE city = 'Las Vegas'";
//		try {
//			PreparedStatement stmt = con.prepareStatement(query);
//			stmt.setString(1, "Las Vegas");
//			return stmt.executeQuery();
//		} catch (SQLException e) {
//		
//			e.printStackTrace();
//		}
//		
//		
//		
//		return null;
//	}
	
	public static void main(String[] args) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels", "root", "");
			Statement stmt = con.createStatement();
			
			//int rows = stmt.executeUpdate("");
			
			ResultSet res = stmt.executeQuery("SELECT city, state, customerName FROM customers;");
			while (res.next()) {
				System.out.println(res.getString("city") + " " + res.getString(2) + " " + res.getString(3));
			}
			
			//ResultSet res = getGivenColumnsFromCustomersTable(con, "city", "state", "customerName");

			while (res.next()) {
				System.out.println(res.getString(1) + " " + res.getString(2));
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
