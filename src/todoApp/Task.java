package todoApp;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Task {

	SimpleIntegerProperty id;
	SimpleStringProperty date,time;
	SimpleStringProperty task, status;
	public Task(SimpleIntegerProperty id, SimpleStringProperty date, SimpleStringProperty time, SimpleStringProperty task, SimpleStringProperty status) {
		
		super();
		this.id = id;
		this.date = date;
		this.time = time;
		this.task = task;
		this.status = status;
		
	}
	
	public Task(int id, String date, String time, String task, String status) {

		
	}

	public int getId() {
		return id.get();
	}
	public String getDate() {
		return date.get();
	}
	public String getTime() {
		return time.get();
	}
	public String getTask() {
		return task.get();
	}
	public String getStatus() {
		return status.get();
	}
	
	
}
