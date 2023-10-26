package application;

import javafx.event.ActionEvent;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.scene.control.ComboBox;

import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import javafx.scene.control.PasswordField;

import javafx.scene.control.Hyperlink;

public class FormLoginController {
    private UIManager uiManager;
	@FXML
	private AnchorPane formAnimation;
	@FXML
	private Button btnCreateAcc;
	@FXML
	private AnchorPane formLogin;
	@FXML
	private TextField txtUsername;
	@FXML
	private PasswordField txtPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Hyperlink hpForgotPassword;
	@FXML
	private AnchorPane formRegister;
	@FXML
	private TextField txtUsername_rg;
	@FXML
	private PasswordField txtPassword_rg;
	@FXML
	private Button btnSignUp;
	@FXML
	private ComboBox cbbRole;
	@FXML
	private TextField txtEmail;
    @FXML
    private Button btnLogin_anmt;
	
    public void setUIManager(UIManager uiManager) {
    	this.uiManager = uiManager;
    }
    @FXML
    public void btnClickedLogin(ActionEvent event) {
    	if(txtUsername.getText().contains("1") && txtPassword.getText().contains("1")) {
    		uiManager.showForm("RecordScreen");    		
    	} else if(txtUsername.getText().contains("2") && txtPassword.getText().contains("2")) {
    		uiManager.showForm("ShowRecorded");
    	} else {
			Alert alert = new Alert(Alert.AlertType.WARNING);
	        alert.setTitle("WARNING");
	        alert.setHeaderText(null);
	        alert.setContentText("Login Failed! \n Please try again!");

	        alert.showAndWait();
    	}
    }
    public void switchForm(ActionEvent event) {
    	TranslateTransition slider = new TranslateTransition();
    	
    	if(event.getSource() == btnCreateAcc) {
    		slider.setNode(formAnimation);
    		slider.setToX(300);
    		slider.setDuration(Duration.seconds(.4));
    		
    		slider.setOnFinished((ActionEvent e) -> {
    			formRegister.setVisible(true);
    			formLogin.setVisible(false);
    			btnLogin_anmt.setVisible(true);
    			btnCreateAcc.setVisible(false);
    		});
    		
    		slider.play();
    	} else if(event.getSource() == btnLogin_anmt) {
    		slider.setNode(formAnimation);
    		slider.setToX(0);
    		slider.setDuration(Duration.seconds(.4));
    		
    		slider.setOnFinished((ActionEvent e) -> {
    			formRegister.setVisible(false);
    			formLogin.setVisible(true);
    			btnCreateAcc.setVisible(true);
    			btnLogin_anmt.setVisible(false);
    		});
    		
    		slider.play();
    	}
    }
}