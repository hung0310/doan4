package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ShowRecordedController {
//	static final String IMAGE_DIRECTORY = "D:\\Compression_JavaFX";
//	File imageFolder = new File(IMAGE_DIRECTORY);
//	File[] imageFiles = imageFolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg"));
	private static String ipAddress;
	private static int Port;
	private static DatagramSocket clientSocket;
	private byte[] incoming = new byte[1024];
	@FXML
	private ImageView imgView;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnPlay;
	// Event Listener on Button[#btnExit].onAction
	
    @FXML
    public void initialize() {
        try {
        	Scanner sc = new Scanner(System.in);
        	System.out.println("Nhap dia chi ip va cong port tuong ung: ");
        	String ipAddress = sc.next();
        	int Port = sc.nextInt();
            clientSocket = new DatagramSocket();
            
            System.out.println("Starting connect...");
            byte[] message = ("Connection request").getBytes();
            InetAddress IP = InetAddress.getByName(ipAddress);
            DatagramPacket sendMessgae = new DatagramPacket(message, message.length, IP, Port);
            clientSocket.send(sendMessgae);
            
			DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
			clientSocket.receive(packet);
			String msg = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Server receive: " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	@FXML
	public void btnClickedExit(ActionEvent event) {
		try {
	    	Stage currentStage = (Stage) imgView.getScene().getWindow();
	    	currentStage.close();		
		} catch(Exception e) {}
	}
	@FXML
	public void btnClickedPlay(ActionEvent event) {
        //Arrays.sort(imageFiles, Comparator.comparingLong(File::lastModified));
        loadImagethread.setDaemon(true);
        loadImagethread.start();	
	}
	public void updateImageView(Image image) {
		Platform.runLater(() -> imgView.setImage(image));
	}
	Thread loadImagethread = new Thread( ()-> {
    	while(true) {
            try {
                // Nhận số lượng gói và kích thước mỗi gói
            	System.out.println("Da nhan anh thanh cong tu server");
            	byte[] receiveData = new byte[1024];
                DatagramPacket headerPacket = new DatagramPacket(receiveData, receiveData.length);
                try {
    				clientSocket.receive(headerPacket);

    	            String header = new String(headerPacket.getData(), 0, headerPacket.getLength());
    	            String[] parts = header.split(" ");
    	            int numChunks = Integer.parseInt(parts[0]);
    	            //int chunkSize = Integer.parseInt(parts[1]);
    	
    	            // Nhận và ghi từng phần dữ liệu từ các gói tin UDP
    	            FileOutputStream fos = new FileOutputStream("received_image.jpg");
    	            for (int i = 0; i < numChunks; i++) {
    	                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
    	                clientSocket.receive(receivePacket);
    	                byte[] dataReceived = Arrays.copyOfRange(receivePacket.getData(), 0, receivePacket.getLength());
    	                fos.write(dataReceived);
    	            }
    	            fos.close();
    			} catch (IOException e) {}


                //System.out.println("Đã nhận tệp ảnh thành công.");
                //clientSocket.close(); 
                File imagePath = new File("received_image.jpg");
                Image image = new Image("file:" + imagePath.getAbsolutePath());
                updateImageView(image);
                Thread.sleep(200); // Đợi 250ms trước khi hiển thị hình ảnh tiếp theo
                //System.out.println("file:" + imagePath.getAbsolutePath());
                //imagePath.delete();
            } catch (Exception e) {}
    	}
	});
//	public void LoadImage() {
//		while(true) {
//			try {
//				Thread.sleep(200);
//				File imagePath = imageFiles[Index];
//				Image image = new Image("file:" + imagePath.getAbsolutePath());
//				updateImageView(image);
//				++Index;
//				if(Index >= imageFiles.length) {
//					break;
//				}
//			}catch(Exception e) {}
//		}
//	}
//	Thread ImageReceive = new Thread( ()-> {
//        try {
//        	Scanner sc = new Scanner(System.in);
//        	System.out.println("Nhap dia chi ip va cong port tuong ung: ");
//        	String ipAddress = sc.next();
//        	int Port = sc.nextInt();
//            DatagramSocket clientSocket = new DatagramSocket();
//            byte[] receiveData = new byte[1024];
//            
//            System.out.println("Starting connect...");
//            byte[] message = ("Connection request").getBytes();
//            InetAddress IP = InetAddress.getByName(ipAddress);
//            DatagramPacket sendMessgae = new DatagramPacket(message, message.length, IP, Port);
//            clientSocket.send(sendMessgae);
//
//            while(flag) {
//	            // Nhận số lượng gói và kích thước mỗi gói
//	            DatagramPacket headerPacket = new DatagramPacket(receiveData, receiveData.length);
//	            clientSocket.receive(headerPacket);
//	            String header = new String(headerPacket.getData(), 0, headerPacket.getLength());
//	            String[] parts = header.split(" ");
//	            int numChunks = Integer.parseInt(parts[0]);
//	            //int chunkSize = Integer.parseInt(parts[1]);
//	
//	            // Nhận và ghi từng phần dữ liệu từ các gói tin UDP
//	            FileOutputStream fos = new FileOutputStream("received_image.jpg");
//	            for (int i = 0; i < numChunks; i++) {
//	                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//	                clientSocket.receive(receivePacket);
//	                byte[] dataReceived = Arrays.copyOfRange(receivePacket.getData(), 0, receivePacket.getLength());
//	                fos.write(dataReceived);
//	            }
//	            fos.close();
//	
//	            //System.out.println("Đã nhận tệp ảnh thành công.");
//	            //clientSocket.close();           	
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//	});
}