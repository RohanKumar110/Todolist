package todoApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MysqlConnection {

	private static Connection conn;
	
	
	public static boolean getMySqlConn()
	{
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolistdb?serverTimezone=UTC", "root", "");
			return true;
		} catch (SQLException e) {
			
			return false;
		}
	}
	
	public int insertTask(String task)
	{
	
		PreparedStatement ps;
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String getDate = formatter.format(calendar.getTime());
		formatter = new SimpleDateFormat("HH:mm:ss");
		String getTime = formatter.format(calendar.getTime());
		int count=0;
		try {
			ps = conn.prepareStatement("INSERT INTO tasks(t_date,t_time,task,t_status) VALUES(?,?,?,?)");
			ps.setString(1, getDate);
			ps.setString(2, getTime);
			ps.setString(3, task);
			ps.setString(4, "pending");
			count = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Exception while inserting");
		}
		
		return count;
	}

	
	public ObservableList<Task> fetchTasks()
	{
		
		PreparedStatement ps;
		ObservableList<Task> tasksList = null;
		try {
			ps = conn.prepareStatement("SELECT * FROM tasks");
			tasksList = FXCollections.observableArrayList();
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				SimpleIntegerProperty id = new SimpleIntegerProperty(rs.getInt("t_id"));
				SimpleStringProperty date = new SimpleStringProperty(rs.getString("t_date"));
				SimpleStringProperty time = new SimpleStringProperty(rs.getString("t_time"));
				SimpleStringProperty task = new SimpleStringProperty( rs.getString("task"));
				SimpleStringProperty status = new SimpleStringProperty(rs.getString("t_status"));
				
				tasksList.add(new Task(id,date,time,task,status));
			}
			
			ps.close();
		} catch (SQLException e) {
			System.out.println("Exception while fetching");
		}
		
		return tasksList;
	}
	
	public void updateTask(int taskid,String status)
	{

		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("Update tasks SET t_status=? WHERE t_id=?");
			ps.setString(1, status);
			ps.setInt(2, taskid);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Exception in updateTask");
		}
	}
	
	
	public void deleteTask(int taskid)
	{
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("DELETE from tasks WHERE t_id=?");
			ps.setInt(1, taskid);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Exception in DeleteTask");
		}
		
	}
	
	public static  void closeConnection() {
		
		try {
			if(conn != null)
			conn.close();
		} catch (SQLException e) {
			System.out.println("Exception while closing");
		}
	}
	
	public void truncateTable()
	{
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement("Truncate tasks");
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			
			System.out.println("Exception in truncateTable");
		}
		
	}
}
