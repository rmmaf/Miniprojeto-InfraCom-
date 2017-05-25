import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JProgressBar;
import javax.swing.JTextPane;

public class Servidor extends Thread {
	int porta;
	JProgressBar progressDown;
	JTextPane estima;

	public Servidor(JProgressBar progresso, int port, JTextPane estima) {
		porta = port;
		progressDown = progresso;
		this.estima = estima;
	}

	public void run(){//use a mesma logica do cliente, so que para receber agora
		byte[] buffer = new byte[1024 * 4];
		double tempofaltando;
		try {
			int valorBarra = 0;
			System.out.println("Esperando");
			ServerSocket tmpsoquete = new ServerSocket(porta);
			Socket soquete = tmpsoquete.accept();//esperando

			System.out.println("Esperando tam");
			DataInputStream receberTamanho= new DataInputStream(soquete.getInputStream());
			int tamanho = receberTamanho.readInt();
			progressDown.setMaximum(tamanho);//tamanho maximo da barra de download definido
			//receberTamanho.close();

			InputStreamReader receberNome = new InputStreamReader(soquete.getInputStream());
			BufferedReader le = new BufferedReader(receberNome);
			String nome = le.readLine();
			System.out.println(nome);

			InputStream lerDados = soquete.getInputStream();//lê o que tá sendo recebido
			FileOutputStream armazenar = new FileOutputStream(nome);
			System.out.println("comecando");
			int cont, tamanho2 = tamanho, barra = 0;
			while((cont = lerDados.read(buffer)) > 0){
				armazenar.write(buffer, 0, cont);
				barra = barra + cont;
				progressDown.setValue(barra);
			}
			File file = new File(nome);
			if(file.length() < tamanho2){
				file.delete();
			}
			estima.setText("Concluído");
			//progressDown.setValue(0);
			lerDados.close(); armazenar.close(); soquete.close(); tmpsoquete.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
