package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javafx.event.ActionEvent;

public class FormConnectController {
    private UIManager uiManager;
	private static DatagramSocket socket;
	private static InetAddress address;
	@FXML
	private TextField txtIPAddress;
	@FXML
	private TextField txtPort;
	@FXML
	private Button btnConnect;
	
	static {
		try {
			socket = new DatagramSocket();
		} catch(Exception e) {}
	}
	
    public void setUIManager(UIManager uiManager) {
    	this.uiManager = uiManager;
    }
	
	@FXML
	public void btnClickedConnect(ActionEvent event) {
		String txt = "#connect " + txtIPAddress.getText() + " " + txtPort.getText();

		try {
			byte[] msg = txt.getBytes();
			
			address = InetAddress.getByName(txtIPAddress.getText());
			int port = Integer.parseInt(txtPort.getText());
			
			DatagramPacket send = new DatagramPacket(msg, msg.length, address, port);
			socket.send(send);		
			
			byte[] receiveMSG = new byte[1024];
			DatagramPacket packet = new DatagramPacket(receiveMSG, receiveMSG.length);
			socket.receive(packet);
			String message = new String(packet.getData(), 0, packet.getLength());

			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("INFORMATION");
	        alert.setHeaderText(null);
	        alert.setContentText(message);

	        alert.showAndWait();
		} catch(Exception e) {}
    	Stage currentStage = (Stage) btnConnect.getScene().getWindow();
    	currentStage.hide();

        uiManager.showForm("RecordScreen");	  
	}
}














