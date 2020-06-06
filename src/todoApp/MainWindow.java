package todoApp;


import java.io.IOException;

import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainWindow extends Application{

	
	
	
	@Override
	public void start(Stage stage) throws IOException {
	
		
        boolean check;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TodolistFrame.fxml"));
		Parent root;
		root = loader.load();
		check = MysqlConnection.getMySqlConn();
		
		ImageIcon logo = new ImageIcon(getClass().getClassLoader().getResource("stylingFiles/logo.png"));
	
		
		if(check)
		{
		
			
			Scene scene = new Scene(root);
			Image icon = new Image("/stylingFiles/icon.jpg");
			stage.getIcons().add(icon);
			stage.setTitle("Todo"); 
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
		}
		else {
			
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Connection Failed");
			alert.setContentText("Check the conection.");
			alert.show();
		}
		
		
		stage.setOnCloseRequest(x->{
			
			MysqlConnection.closeConnection();
			Platform.exit();
			System.exit(0);
			System.out.println("Disconnected");
		});
	}
	
	
	public static void main(String[] args) {
	
		
		launch(args); 
	}

}
