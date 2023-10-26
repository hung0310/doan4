package ServerUDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Server {
	
	private static DatagramSocket socket;
	private static InetAddress ipAddress;
	private static byte[] incoming = new byte[1024];
	private static ArrayList<Integer> users = new ArrayList<>();
	private static final int SERVER_PORT = 9999;
	
	static {
		try {
			socket = new DatagramSocket(SERVER_PORT);
		} catch(Exception e) {}
	}
	
	static {
		try {
			ipAddress = InetAddress.getByName("192.168.1.112");
		} catch(Exception e) {}
	}
	
	public static void main(String[] args) {
		System.out.println("Server starting to port: " + SERVER_PORT);
		while(true) {
			DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
			try {
				socket.receive(packet);
			} catch(Exception e) {}
			String message = new String(packet.getData(), 0, packet.getLength());
			System.out.println("Server received: " + message);
			
			String part[] = message.split(" ");
			
			if(part[0].contains("#connect")) {
				users.add(packet.getPort());
				
				byte[] byteMessage = ("Connected Successfull!").getBytes();
				DatagramPacket forward = new DatagramPacket(byteMessage, byteMessage.length, packet.getAddress(), packet.getPort());
				try {
					socket.send(forward);
				} catch(Exception e) {}
			} //else {
//				int userPort = packet.getPort();
//				byte[] byteMessage = message.getBytes();
//				
//				for(int forward_port : users) {
//					if(forward_port != userPort) {
//						DatagramPacket forward = new DatagramPacket(byteMessage, byteMessage.length, ipAddress, forward_port);
//						try {
//							socket.send(forward);
//						} catch(Exception e) {}
//					}
//				}
//			}
		}	
	}
}





