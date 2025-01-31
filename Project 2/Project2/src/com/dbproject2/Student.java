/*
 * Author: 1. John Stephen Gutam (G01413212)
 * 		   2. Leonel Richie Vullamparthi (G01464792)
 */

package com.dbproject2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

import org.apache.ibatis.jdbc.ScriptRunner;

public class Student {
	static Connection con;
	static Statement stmt;
	
	public static void main(String[] args) {
		connectToDatabase();
	}

	public static void connectToDatabase(){
		String driverPrefixURL="jdbc:oracle:thin:@";
		String jdbc_url="artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu";	
		Scanner scanner = new Scanner(System.in);
        
        try{
    	    //Register Oracle driver
                DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            } catch (Exception e) {
                System.out.println("Failed to load JDBC/ODBC driver.");
                return;
        }
        
        try{
        	System.out.println("Connecting to database..");
        	System.out.println("Enter username: ");
        	String username = scanner.nextLine();
        	System.out.println("Enter password: ");
        	String password = scanner.nextLine();
        	
        	
            System.out.println(driverPrefixURL+jdbc_url);
            con=DriverManager.getConnection(driverPrefixURL+jdbc_url, username, password);
            DatabaseMetaData dbmd=con.getMetaData();
            stmt=con.createStatement();

            System.out.println("Connected.");

            if(dbmd==null){
                System.out.println("No database meta data");
                return;
            }
            else {
                System.out.println("Database Product Name: "+dbmd.getDatabaseProductName());
                System.out.println("Database Product Version: "+dbmd.getDatabaseProductVersion());
                System.out.println("Database Driver Name: "+dbmd.getDriverName());
                System.out.println("Database Driver Version: "+dbmd.getDriverVersion());
            }
            
            System.out.println("Enter the location of paper.sql script file: ");
            String scriptLocation = scanner.nextLine();
            
            ScriptRunner sr = new ScriptRunner(con);
            //Creating a reader object
            Reader reader = new BufferedReader(new FileReader(scriptLocation));
            //Running the script
            sr.runScript(reader);

            System.out.println("Database is ready for search.");
            
            while(true){
            	System.out.println("\nMenu:");
                System.out.println("1. View table contents");
                System.out.println("2. Search by PUBLICATIONID");
                System.out.println("3. Search by one or more attributes");
                System.out.println("4. Exit");
                
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                switch (choice) {
                case 1:
                    viewTableContents(con, scanner);
                    break;
                case 2:
                    searchByPublicationID(con, scanner);
                    break;
                case 3:
                    searchByAttributes(con, scanner);
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                }
            }
        } catch( Exception e) {
        	e.printStackTrace();
        } finally {
        	try {
                if (stmt != null) stmt.close();
                if (con != null) con.close();
                scanner.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
	
	private static void viewTableContents(Connection conn, Scanner scanner) throws SQLException {
		System.out.print("View PUBLICATIONS (Yes/No): ");
		String viewPublications = scanner.nextLine().trim().toLowerCase();
		System.out.print("View AUTHORS (Yes/No): ");
		String viewAuthors = scanner.nextLine().trim().toLowerCase();
		
		if (viewPublications.equals("yes")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM PUBLICATIONS");
            System.out.println("\n--- PUBLICATIONS ---");
            while (rs.next()) {
                System.out.println("PUBLICATIONID: " + rs.getInt("PUBLICATIONID") +
                        ", YEAR: " + rs.getInt("YEAR") +
                        ", TYPE: " + rs.getString("TYPE") +
                        ", TITLE: " + rs.getString("TITLE") +
                        ", SUMMARY: " + rs.getString("SUMMARY"));
            }
            rs.close();
            stmt.close();
        }
		
		if (viewAuthors.equals("yes")) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM AUTHORS");
            System.out.println("\n--- AUTHORS ---");
            while (rs.next()) {
                System.out.println("PUBLICATIONID: " + rs.getInt("PUBLICATIONID") +
                        ", AUTHOR: " + rs.getString("AUTHOR"));
            }
            rs.close();
            stmt.close();
        }
	}
	
	private static void searchByPublicationID(Connection conn, Scanner scanner) throws SQLException {
		System.out.print("Enter PUBLICATIONID: ");
		int publicationID = scanner.nextInt();
		scanner.nextLine();
		
		PreparedStatement stmt = conn.prepareStatement("SELECT P.* " +
                "FROM PUBLICATIONS P " +
                "WHERE P.PUBLICATIONID = ?");
		stmt.setInt(1, publicationID);
		ResultSet rs = stmt.executeQuery();
		
		if (rs.next()) {
            System.out.println("\n--- SEARCH RESULT ---");
            System.out.println("PUBLICATIONID: " + rs.getInt("PUBLICATIONID") +
                    ", YEAR: " + rs.getInt("YEAR") +
                    ", TYPE: " + rs.getString("TYPE") +
                    ", TITLE: " + rs.getString("TITLE") +
                    ", SUMMARY: " + rs.getString("SUMMARY"));
        } else {
            System.out.println("No result found.");
        }
		
		rs.close();
        stmt.close();
	}
	
	private static void searchByAttributes(Connection conn, Scanner scanner) throws SQLException {
		System.out.println("Input fields:");
		System.out.print("AUTHOR: ");
		String author = scanner.nextLine();
		System.out.print("TITLE: ");
		String title = scanner.nextLine();
		System.out.print("YEAR: ");
		int year = scanner.nextInt();
		scanner.nextLine();
		System.out.print("TYPE: ");
		String type = scanner.nextLine();
		
		// Get output fields
	    System.out.println("Output fields:");
	    System.out.print("PUBLICATIONID (Yes/No): ");
	    String includePubID = scanner.nextLine().trim().toLowerCase();
	    System.out.print("AUTHOR (Yes/No): ");
	    String includeAuthor = scanner.nextLine().trim().toLowerCase();
	    System.out.print("TITLE (Yes/No): ");
	    String includeTitle = scanner.nextLine().trim().toLowerCase();
	    System.out.print("YEAR (Yes/No): ");
	    String includeYear = scanner.nextLine().trim().toLowerCase();
	    System.out.print("TYPE (Yes/No): ");
	    String includeType = scanner.nextLine().trim().toLowerCase();
	    System.out.print("SUMMARY (Yes/No): ");
	    String includeSummary = scanner.nextLine().trim().toLowerCase();
	    
	    // Get sorted field
	    System.out.print("Sorted by: ");
	    String sortedBy = scanner.nextLine().trim().toLowerCase();
	    
	    // Build SQL query dynamically based on user input
	    StringBuilder queryBuilder = new StringBuilder("SELECT ");
	    if (includePubID.equals("yes")) queryBuilder.append("P.PUBLICATIONID, ");
	    if (includeAuthor.equals("yes")) queryBuilder.append("AUTHOR, ");
	    if (includeTitle.equals("yes")) queryBuilder.append("TITLE, ");
	    if (includeYear.equals("yes")) queryBuilder.append("YEAR, ");
	    if (includeType.equals("yes")) queryBuilder.append("TYPE, ");
	    if (includeSummary.equals("yes")) queryBuilder.append("SUMMARY, ");
	    queryBuilder.deleteCharAt(queryBuilder.length() - 2); // Remove extra comma and space
	    queryBuilder.append(" FROM PUBLICATIONS P ");
	    
	    
	    if (!author.isEmpty() || !title.isEmpty() || year != 0 || !type.isEmpty()) {
	        queryBuilder.append("JOIN AUTHORS A ON P.PUBLICATIONID = A.PUBLICATIONID WHERE ");
	        if (!author.isEmpty()) queryBuilder.append("UPPER(A.AUTHOR) LIKE UPPER(?) AND ");
	        if (!title.isEmpty()) queryBuilder.append("UPPER(P.TITLE) LIKE UPPER(?) AND ");
	        if (year != 0) queryBuilder.append("P.YEAR = ? AND ");
	        if (!type.isEmpty()) queryBuilder.append("UPPER(P.TYPE) LIKE UPPER(?) AND ");
	        queryBuilder.delete(queryBuilder.length() - 5, queryBuilder.length()); // Remove the last " AND "
	    }
	    
	    // Add sorting
	    if (!sortedBy.isEmpty()) {
	        queryBuilder.append(" ORDER BY ");
	        queryBuilder.append(sortedBy);
	    }
	    
	    PreparedStatement stmt = conn.prepareStatement(queryBuilder.toString());
	    
	    
	 // Set parameters for input fields
	    int parameterIndex = 1;
	    if (!author.isEmpty()) stmt.setString(parameterIndex++, "%" + author + "%");
	    if (!title.isEmpty()) stmt.setString(parameterIndex++, "%" + title + "%");
	    if (year != 0) stmt.setInt(parameterIndex++, year);
	    if (!type.isEmpty()) stmt.setString(parameterIndex++, "%" + type + "%");
	
	    ResultSet rs = stmt.executeQuery();
	    
	 // Print search results
	    System.out.println("\n--- SEARCH RESULTS ---");
	    while (rs.next()) {
	        StringBuilder result = new StringBuilder();
	        if (includePubID.equals("yes")) result.append("PUBLICATIONID: ").append(rs.getInt("PUBLICATIONID")).append(", ");
	        if (includeAuthor.equals("yes")) result.append("AUTHOR: ").append(rs.getString("AUTHOR")).append(", ");
	        if (includeTitle.equals("yes")) result.append("TITLE: ").append(rs.getString("TITLE")).append(", ");
	        if (includeYear.equals("yes")) result.append("YEAR: ").append(rs.getInt("YEAR")).append(", ");
	        if (includeType.equals("yes")) result.append("TYPE: ").append(rs.getString("TYPE")).append(", ");
	        if (includeSummary.equals("yes")) result.append("SUMMARY: ").append(rs.getString("SUMMARY")).append(", ");
	        System.out.println(result.substring(0, result.length() - 2)); // Remove extra comma and space
	    }

	    rs.close();
	    stmt.close();
	}	
}
