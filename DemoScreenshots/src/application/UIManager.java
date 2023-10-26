package application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UIManager {
	private Stage primaryStage;
	
	public UIManager(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	public void showForm(String nameForm) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(nameForm + ".fxml"));
			Parent root = loader.load();
			
//	        RecordScreenController record = loader.getController();
//	        UIManager uiManager = new UIManager(primaryStage);
//	        loginController.setUIManager(uiManager);
			
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch(Exception ex) {}
	}
	public void hideForm() {
	    primaryStage.hide();
	}
}
