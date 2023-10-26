package application;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ClientThread extends Thread{
	
	private DatagramSocket socket;
	private byte[] incoming = new byte[1024];
	
	public ClientThread(DatagramSocket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		System.out.println("Starting thread...");
		while(true) {
			DatagramPacket packet = new DatagramPacket(incoming, incoming.length);
			try {
				socket.receive(packet);
				String message = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Server receive: " + message);
			} catch(Exception e) {}
		}
	}
}
