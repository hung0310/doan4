package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.event.ActionEvent;

public class RecordScreenController {
	private Image_Compression imageCompression = new Image_Compression();
	private static InetAddress ipAddress;
	private static InetAddress clientAddress;
	private static int clientPort;
	private static final int PORT_SERVER = 9999;
	private static DatagramSocket serverSocket;
	private static byte[] incoming = new byte[1024];
	static int count = 0;
	static boolean flag = false;
	
	static {
		try {
	        serverSocket = new DatagramSocket(PORT_SERVER);
		} catch(Exception e) {}
	}
	
	static {
		try {
			ipAddress = InetAddress.getByName("localhost"); //192.168.57.1
		} catch(Exception e) {}
	}
	
	@FXML
	public void initialize() throws UnknownHostException {
    	
		System.out.println("Server starting to port: " + PORT_SERVER);
		DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
		try {
			serverSocket.receive(packet);
		} catch(Exception e) {}
		String message = new String(packet.getData(), 0, packet.getLength());
		System.out.println("Server received: " + message);
		
		byte[] byteMessage = ("Connected Successfull!").getBytes();
		DatagramPacket forward = new DatagramPacket(byteMessage, byteMessage.length, packet.getAddress(), packet.getPort());
		try {
			serverSocket.send(forward);
		} catch(Exception e) {}
		
        clientAddress = InetAddress.getByName(packet.getAddress().getHostAddress());
        clientPort = packet.getPort();
	}
	
	@FXML
	private Button btnFlag;
	@FXML
	private Button btnPlay;
	
	// Event Listener on Button[#btnFlag].onAction
	@FXML
	public void btnClickedFlag(ActionEvent event) {
		
    	++count;
        if(count == 1) {
    		
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null); // Không có tiêu đề phụ
            alert.setContentText("Trình ghi hình sẽ bắt đầu sau khi bạn xác nhận");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            System.out.println("Tiến trình ghi hình đang chạy...");
            if(result.get() == buttonTypeYes) {
            	flag = true;
            	Screenshots.start();           	
            }
        } else if(count == 2) {
            ButtonType buttonTypeYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("No", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null); // Không có tiêu đề phụ
            alert.setContentText("Bạn có muốn dừng việc ghi hình lại không?");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Optional<ButtonType> result = alert.showAndWait();
            System.out.println("Tiến trình ghi hình đã kết thúc");
            if(result.get() == buttonTypeYes) {
            	flag = false;            	
            }
        } else {
        	System.out.println("Đã hoàn tất việc ghi hình và dừng ghi hình");
        }

	}
	// Event Listener on Button[#btnPlay].onAction
	@FXML
	public void btnClickedPlay(ActionEvent event) {
		try {
//	    	Stage currentStage = (Stage) btnFlag.getScene().getWindow();
//	    	currentStage.hide();
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowRecorded.fxml"));
	        Parent root = loader.load();
	        Scene scene = new Scene(root);
	        Stage stage = new Stage();
	        stage.setScene(scene);
	        stage.show();			
		} catch(Exception e) {}
	}
	Thread Screenshots = new Thread( ()-> {

		while(flag) {
			try {
		        
//		        byte[] receive = new byte[1024];
//		        DatagramPacket packet = new DatagramPacket(receive, receive.length);
//		        serverSocket.receive(packet);
//				String message = new String(packet.getData(), 0, packet.getLength());
//				System.out.println("Server receive: " + message);
		        
				//Folder chup anh
//				String folderPath = "D:\\Screenshots_JavaFX";
//				File folder = new File(folderPath);
//				if(!folder.exists()) {
//					folder.mkdir();
//				}
				
		        //Folder gui anh da nen
//				String folderRecord = "D:\\Compression_JavaFX";
//				File folderRecordScreenshot = new File(folderRecord);
//				if(!folderRecordScreenshot.exists()) {
//					folderRecordScreenshot.mkdir();
//				}
				
				//Thu vien chup anh man hinh
				Robot robot = new Robot();
				BufferedImage screenshots = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
				
//				File outputFile = new File(folder, "screenshot" + sttScreenshot + ".jpg");
//				String FileName = outputFile.getAbsolutePath();
//				System.out.println(FileName);
//				ImageIO.write(screenshots, "jpg", outputFile);
//				imageCompression.Compression(FileName, sttScreenshot);
				
				File outputFile = new File("screenshot.jpg");
				String FileName = outputFile.getAbsolutePath();
//				System.out.println("Da chup anh thanh cong");
				ImageIO.write(screenshots, "jpg", outputFile);
//				imageCompression.Compression(FileName);
				
				//
		        File file = imageCompression.Compression(FileName);
		        FileInputStream fis = new FileInputStream(file);
		        byte[] fileData = new byte[(int) file.length()];
		        fis.read(fileData);
		        fis.close();
		
		        // Lấy địa chỉ IP của client và cổng 192.168.1.112
		
		        // Chia nhỏ dữ liệu và gửi qua gói tin UDP
		        int chunkSize = 1024; // Kích thước mỗi phần dữ liệu
		        int numChunks = (int) Math.ceil((double) fileData.length / chunkSize); // Số lượng gói cần gửi
		
		        // Gửi số lượng gói cho client biết
		        String header = numChunks + " " + chunkSize;
		        byte[] headerData = header.getBytes();
		        DatagramPacket headerPacket = new DatagramPacket(headerData, headerData.length, clientAddress, clientPort);
		        serverSocket.send(headerPacket);
		
		        // Gửi từng phần dữ liệu qua các gói tin UDP
		        for (int i = 0; i < numChunks; i++) {
		            int start = i * chunkSize;
		            int end = Math.min(fileData.length, (i + 1) * chunkSize);
		            byte[] sendData = new byte[end - start];
		            System.arraycopy(fileData, start, sendData, 0, end - start);
		
		            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
		            serverSocket.send(sendPacket);
//		            System.out.println("Da gui thanh cong qua client");
		        }
				Thread.sleep(40);
				//Delete_Image(outputFile);
				//Delete_Image(file);
			} catch(Exception ex) {
				System.out.println("Loi");
			}			
		}
	});
	public void Delete_Image(File filePath) {
		filePath.delete();
	}
}










