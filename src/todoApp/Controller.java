package todoApp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class Controller implements Initializable{


	

    @FXML
    private TabPane tabPane;
	
	 @FXML
	 private Tab createTaskTab;

	 @FXML
	 private TextArea textArea;

	 @FXML
     private Button saveBtn;

	@FXML
    private Tab viewTasksTab;

	@FXML
    private Button deleteAllBtn;
	 
    @FXML
    private TableView<Task> tableView;

    @FXML
    private TableColumn<Task, Integer> idColumn;

    @FXML
    private TableColumn<Task, String> dateColumn;

    @FXML
    private TableColumn<Task, String> timeColumn;

    @FXML
    private TableColumn<Task, String> taskColumn;

    @FXML
    private TableColumn<Task, String> statusColumn;


    @FXML
    public void deleteAll(ActionEvent event)
    {
    	MysqlConnection myconn = new MysqlConnection();
    	myconn.truncateTable();
    	refreshTable();
    }
    
    @FXML
    void saveTask(ActionEvent event) {

    	String getTask = textArea.getText();
    	
    	if(getTask.length() > 0)
    	{
    		MysqlConnection mysqlConnection = new MysqlConnection();
    		int count = mysqlConnection.insertTask(getTask);
    		if(count > 0)
    		{
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Saved");
        		alert.setContentText("Task Saved");
        		alert.show();
        		textArea.clear();
    		}
    	}
    	else
    	{
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Empty TextArea");
    		alert.setContentText("Please write the task");
    		alert.show();
    	}
    }

    @FXML
    public void viewTasks(Event e)
    {
    	try{
    		
    		if(viewTasksTab.isSelected())
    		{
    			refreshTable();
    		}

    	}catch(Exception exception)
    	{
    		System.out.println("Exception in viewTasks");
    	}

    }
    
    @FXML
    protected void refreshTable() {
		
    	MysqlConnection myconn = new MysqlConnection();
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
		taskColumn.setCellValueFactory(new PropertyValueFactory<>("task"));
		statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableView.setItems(myconn.fetchTasks());
	}
    
	@FXML
    public void onEdit(MouseEvent event)
    {
    	if(event.getClickCount() == 2){
    		
    		Task selectedTask = tableView.getSelectionModel().getSelectedItem();
    		Scene scene;
    		Stage stage;
    		try {
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("DialogBox.fxml"));
				Parent root = (Parent) loader.load();
				DialogBoxController dialog = loader.getController();
				dialog.editTask(selectedTask);
				dialog.setController(this);
				scene = new Scene(root);
				stage = new Stage();
				stage.setTitle("Modify");
				Image icon = new Image("/stylingFiles/modify.jpg");
				stage.getIcons().add(icon);
				stage.setResizable(false);
				stage.initModality(Modality.APPLICATION_MODAL);
				stage.setScene(scene);
				stage.showAndWait();
				
			} catch (IOException e) {
				
				System.out.println("Exception in onEdit");
			}
    	}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	
	}

}
