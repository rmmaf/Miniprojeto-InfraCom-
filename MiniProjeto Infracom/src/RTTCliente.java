import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JTextPane;

public class RTTCliente extends Thread {
	String endereco;
	JTextPane Rtt;
	public RTTCliente(String endereco, JTextPane rtt){
		this.endereco = endereco;
		Rtt = rtt;
	}

	public void run(){
		double estRtt = 0;
		String retorna;
		try {
			DatagramSocket clientSocket = new DatagramSocket();
			InetAddress IPServer = InetAddress.getByName(endereco);
			byte[] sendData;
			sendData = ("1").getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPServer, 5000);
			boolean primeiro = true;
			double valor;
			while(true){
				long tempoAtual = System.nanoTime();
				clientSocket.send(sendPacket); 
				byte[] receiveData = new byte[1];
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				clientSocket.receive(receivePacket);
				valor =  ((System.nanoTime() - tempoAtual) / 1000000);
				if(primeiro){//primeiro valor do estRtt
					estRtt = valor;
					primeiro = false;//so iguala uma vez
				}
				estRtt = (0.8755)*estRtt + 0.125*valor;
				retorna = String.valueOf(estRtt);
				Rtt.setText(retorna);
				Thread.sleep(500);
			}
		}catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}



}

