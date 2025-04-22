package Generic_Utilies;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class DataBase_Utility {
	
	
	Connection con;
	//public Connection getDatabaseConnection(String url,String username, String password) {
	public Connection getDatabaseConnection() {
		Driver dobj=null;
		try {
			dobj=new Driver();
			DriverManager.registerDriver(dobj);
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","prasanna@123");
		}
		catch(SQLException e) {
			System.out.println("Dtabase Connection has done");
		}
			
	
		return con;
	}
	
public void closeDatabaseConnection() {
	try {con.close();
	
	}catch(Exception e) {
		System.out.println("databasre connection hasnt clodsed");
	}
}
	public ResultSet excecuteSelectQuery(String query) {
		ResultSet result=null;
		try {
		Statement stat=con.createStatement();
			result=stat.executeQuery(query);
			
			
			
		}catch(Exception e) {
			System.out.println("select query not executed");
		}
		
		return result;
	}
	public int executeNonSelectQuery(String query) {
		int result=0;
		try {
			Statement stat=con.createStatement();
			result=stat.executeUpdate(query);
		}catch(Exception e) {
			System.out.println("nonselect query not executed");
			
			
		}
		return result;
	}

}
