package com.sathwik.project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class DataBase {
    
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/java_project";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
    }
    
//to count the orders that were placed today.
    public static String countTodayOrders() {
    	String count="";
    	try (Connection con = getConnection();){
    		
    		Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery("select count(billdate) from bills where billdate =" + "'" + LocalDate.now().toString() + "'");
			
			while(res.next()) {
				count=res.getString(1);
			}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return count;
    }

    
//to calculate todays total orders value
    public static String todayOrdersValue() {
    	String value="";
    	try (Connection con = getConnection();){
    		
			Statement stmt = con.createStatement();
			ResultSet res=stmt.executeQuery("select sum(total) from bills where billdate =" + "'" + LocalDate.now().toString() + "'");
			float count=0;
			//String value;
			try {
			while(res.next()) {
				count+=Float.parseFloat( res.getString(1));
			}
			value=""+count;
			}
			catch(NullPointerException e2) {
				value="0";
			}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	return value;
    }    

    
    
//to get today buyers details to add them into today buyers table in main page
    public static Object[][] todayBuyerDetails(){
    	Object[][] todayBuyerAmount = {{}};
    	try (Connection con = getConnection();){
    		
    		Statement stmt = con.createStatement();
			int i=0;
			ArrayList<String> names=new ArrayList<>();
			ArrayList<String> amounts=new ArrayList<>();
			ResultSet res= stmt.executeQuery("select outletname,sum(total) from bills where billdate=current_date() group by outletname");			
			while(res.next()) {
				names.add(res.getString(1));
				amounts.add(res.getString(2));
				//i++;
			}
			
			todayBuyerAmount = new Object[names.size()][2];
			for(i=0;i<names.size();i++) {
				todayBuyerAmount[i][0]=names.get(i);
				todayBuyerAmount[i][1]=amounts.get(i);
			}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return todayBuyerAmount;
    }
    
    
//to get the detailed view of sales done today
    public static Object[][] todaySalesDetails(){
    	
    	Object[][] todaySales = {{}};
    	try (Connection con = getConnection();){
    		
    		Statement stmt = con.createStatement();
			int i=0;
			ArrayList<String> inletnames=new ArrayList<>();
			ArrayList<String> outletnames=new ArrayList<>();
			ArrayList<String> productnames=new ArrayList<>();
			ArrayList<String> amounts=new ArrayList<>();
			ResultSet res= stmt.executeQuery("select inletname,outletname,productname,total from bills where billdate=current_date() ");			
			while(res.next()) {
				inletnames.add(res.getString(1));
				outletnames.add(res.getString(2));
				productnames.add(res.getString(3));
				amounts.add(res.getString(4));
				//i++;
			}
			
			todaySales = new Object[productnames.size()][4];
			for(i=0;i<productnames.size();i++) {
				todaySales[i][0]=inletnames.get(i);
				todaySales[i][1]=outletnames.get(i);
				todaySales[i][2]=productnames.get(i);
				todaySales[i][3]=amounts.get(i);
			}
    	}
    	catch(SQLException e) {
    		e.printStackTrace();
    	}
    	
    	return todaySales;
    }
    
    
//to add the outlet details.
    public static void addOutletsDetails(String outletName, String ownerName, String address, String contact) {
        String statement = "INSERT INTO outlets (shopname, ownername, address, mobilenumber) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(statement)) {
            stmt.setString(1, outletName);
            stmt.setString(2, ownerName);
            stmt.setString(3, address);
            stmt.setString(4, contact);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addInletsDetails(String inletName, String ownerName, String contact, String address) {
        String statement = "INSERT INTO inlets (inletname, ownername, address, mobilenumber) VALUES (?, ?, ?, ?)";
        String createTableSQL = "CREATE TABLE " + inletName + " (" +
                "productname VARCHAR(50) PRIMARY KEY, " +
                "quantity VARCHAR(50), " +
                "costprice VARCHAR(50), " +
                "sellingprice VARCHAR(50))";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(statement);
             Statement createStmt = con.createStatement()) {
            stmt.setString(1, inletName);
            stmt.setString(2, ownerName);
            stmt.setString(3, address);
            stmt.setString(4, contact);
            stmt.executeUpdate();
            createStmt.execute(createTableSQL);
            System.out.println("Table created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNewProduct(String inletName, String productName, String quantity, String costPrice, String sellingPrice) {
        String statement = "INSERT INTO " + inletName + " (productname, quantity, costprice, sellingprice) VALUES (?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(statement)) {
            stmt.setString(1, productName);
            stmt.setString(2, quantity);
            stmt.setString(3, costPrice);
            stmt.setString(4, sellingPrice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String[] availableInletsNames() {
        String query = "SELECT inletname FROM inlets";
        return getResults(query);
    }

    public static String[] availableOutletsNames() {
        String query = "SELECT shopname FROM outlets";
        return getResults(query);
    }

    public static String[] availableProductsNames(String inlet) {
        String query = "SELECT productname FROM " + inlet;
        return getResults(query);
    }

    private static String[] getResults(String query) {
        List<String> results = new ArrayList<>();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                results.add(res.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results.toArray(new String[0]);
    }

    public static void addExistingProducts(String inletName, String productName, String quantity) {
        String selectQuery = "SELECT quantity FROM " + inletName + " WHERE productname = ?";
        String updateQuery = "UPDATE " + inletName + " SET quantity = ? WHERE productname = ?";
        try (Connection con = getConnection();
             PreparedStatement selectStmt = con.prepareStatement(selectQuery);
             PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
            selectStmt.setString(1, productName);
            ResultSet res = selectStmt.executeQuery();
            if (res.next()) {
                int existingQuantity = Integer.parseInt(res.getString(1));
                int newQuantity = existingQuantity + Integer.parseInt(quantity);
                updateStmt.setString(1, String.valueOf(newQuantity));
                updateStmt.setString(2, productName);
                updateStmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String sellingPriceOfProduct(String inletName, String productName) {
        String query = "SELECT sellingprice FROM " + inletName + " WHERE productname = ?";
        return getSingleResult(query, productName);
    }

    public static String availableQuantityOfProduct(String inletName, String productName) {
        String query = "SELECT quantity FROM " + inletName + " WHERE productname = ?";
        return getSingleResult(query, productName);
    }

    private static String getSingleResult(String query, String parameter) {
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, parameter);
            ResultSet res = stmt.executeQuery();
            if (res.next()) {
                return res.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void reduceQuantityOfProduct(String inletName, String productName, String newQuantity) {
        String updateQuery = "UPDATE " + inletName + " SET quantity = ? WHERE productname = ?";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(updateQuery)) {
            stmt.setString(1, newQuantity);
            stmt.setString(2, productName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBill(String inletName, String productName, String outletName, String quantity, String cgst, String sgst, String total) {
        String statement = "INSERT INTO bills (inletname, productname, outletname, quantity, cgst, sgst, total, billdate) VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_DATE)";
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(statement)) {
            stmt.setString(1, inletName);
            stmt.setString(2, productName);
            stmt.setString(3, outletName);
            stmt.setString(4, quantity);
            stmt.setString(5, cgst);
            stmt.setString(6, sgst);
            stmt.setString(7, total);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DefaultCategoryDataset last7DaysAnalysis() {
        String query = "SELECT sum(total), billdate FROM bills GROUP BY billdate ORDER BY billdate DESC LIMIT 7";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                double amount = res.getDouble(1);
                LocalDate date = res.getDate(2).toLocalDate();
                dataset.addValue(amount, "Sales", date.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public static DefaultCategoryDataset top10OutletsSales() {
        String query = "SELECT sum(total), outletname FROM bills WHERE MONTH(billdate) = MONTH(CURDATE()) GROUP BY outletname ORDER BY sum(total) DESC LIMIT 10";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                double amount = res.getDouble(1);
                String outletName = res.getString(2);
                dataset.addValue(amount, "Sales", outletName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public static DefaultCategoryDataset addressWiseSales() {
        String query = "SELECT sum(b.total), o.address FROM bills b LEFT JOIN outlets o ON o.shopname = b.outletname GROUP BY o.address";
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                double amount = res.getDouble(1);
                String address = res.getString(2);
                dataset.addValue(amount, "Sales", address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }

    public static DefaultPieDataset last6MonthsSales() {
        String query = "SELECT sum(total), MONTH(billdate) FROM bills GROUP BY MONTH(billdate) ORDER BY MONTH(billdate) DESC LIMIT 6";
        DefaultPieDataset dataset = new DefaultPieDataset();
        try (Connection con = getConnection();
             Statement stmt = con.createStatement();
             ResultSet res = stmt.executeQuery(query)) {
            while (res.next()) {
                double amount = res.getDouble(1);
                String month = res.getString(2);
                dataset.setValue(month, amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataset;
    }
    
	static void addbill(String inletName, String productName, String outletName, String quantity, String cgst, String sgst, String total) {
		Connection con;
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/java_project", "root", "1234");
			//Statement stmt = con.createStatement();
			String statement = "insert into bills values (?,?,?,?,?,?,?,current_date());";
			PreparedStatement stmt = con.prepareStatement(statement);
			stmt.setString(1, inletName);
			stmt.setString(2, productName);
			stmt.setString(3, outletName);
			stmt.setString(4, quantity);
			stmt.setString(5, cgst);
			stmt.setString(6, sgst);
			stmt.setString(7, total);
			
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

    public static void main(String[] args) {
        // Example usage
        // addExistingProducts("shop", "product 1", "10");
        // addBill("shop", "product", "sathwik", "10", "1", "2", "100");
        DefaultCategoryDataset dataset = last7DaysAnalysis();
        // Use the dataset as needed
    }
}
