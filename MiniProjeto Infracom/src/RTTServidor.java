import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class RTTServidor extends Thread{
	public RTTServidor(){

	}
	@SuppressWarnings("resource")
	public void run(){
		
		try{
			DatagramSocket serverSocket = new DatagramSocket(5000);
			byte[] receiveData = new byte[1];
			byte[] sendData;
			InetAddress clientIP;
			int port;
			while(true){
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				serverSocket.receive(receivePacket);
				clientIP = receivePacket.getAddress();
				port = receivePacket.getPort();
				sendData = ("2").getBytes();
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, port);
				serverSocket.send(sendPacket);
			}
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
