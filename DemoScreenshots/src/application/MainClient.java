package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class MainClient extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FormLogin.fxml"));
            Parent root = loader.load();
            
            FormLoginController loginController = loader.getController();
            UIManager uiManager = new UIManager(primaryStage);
            loginController.setUIManager(uiManager);
            
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}