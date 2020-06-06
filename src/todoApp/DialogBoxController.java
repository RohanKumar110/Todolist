package todoApp;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DialogBoxController implements Initializable{
 
	
    @FXML
    private Label idLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextArea textAreaDialog;

    @FXML
    private Button updateBtn;

    @FXML
    private Button deleteBtn;

    private Controller controller;
   
   public void setController(Controller controller)
   {
	  this.controller = controller;
   }
    
	private void closeStage(ActionEvent event) {
        
		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();
		stage.close();
    }
    
    public void editTask(Task task)
    {

    	idLabel.setText(""+task.getId());
    	dateLabel.setText(task.getDate());
    	timeLabel.setText(task.getTime());
    	textAreaDialog.setText(task.getTask());
    	statusComboBox.setValue(task.getStatus());
    	textAreaDialog.setEditable(false);
    }
    
    @FXML
    public void update(ActionEvent event){
    	
    	int taskID = Integer.parseInt(idLabel.getText());
    	String status = statusComboBox.getSelectionModel().getSelectedItem().toString();
    	MysqlConnection myconn = new MysqlConnection();
    	myconn.updateTask(taskID, status);
    	controller.refreshTable();
    	closeStage(event);
    }
    
    @FXML
    public void delete(ActionEvent event){
    	
    	int taskID = Integer.parseInt(idLabel.getText());
    	MysqlConnection myconn = new MysqlConnection();
    	myconn.deleteTask(taskID);
    	controller.refreshTable();
    	closeStage(event);

    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		statusComboBox.getItems().addAll("pending","done","aborted");
		
		
	}

}

