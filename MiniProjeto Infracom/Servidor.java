import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
		try {
			System.out.println("Esperando");
			ServerSocket tmpsoquete = new ServerSocket(porta);
			Socket soquete = tmpsoquete.accept();//esperando

			System.out.println("Esperando tam");
			DataInputStream receberTamanhoeNome = new DataInputStream(soquete.getInputStream());
			int tamanho = receberTamanhoeNome.readInt();
			progressDown.setMaximum(tamanho);//tamanho maximo da barra de download definido
			String nome = receberTamanhoeNome.readUTF();

			//InputStream lerDados = soquete.getInputStream();//l� o que t� sendo recebido
			FileOutputStream armazenar = new FileOutputStream(nome);
			System.out.println("comecando");
			int cont, tamanho2 = tamanho, barra = 0, pacotes = 0;
			long tempoInicial = System.currentTimeMillis(), tempoAtual;
			double yo;
			while((cont = receberTamanhoeNome.read(buffer)) > 0){
				armazenar.write(buffer, 0, cont);
				pacotes = pacotes + cont;
				tamanho = tamanho - cont;
				if((tempoAtual = System.currentTimeMillis() - tempoInicial) > 1000){//atualiza de um em um segundo
					yo = tamanho/pacotes;
					yo = tempoAtual*yo/1000;
					estima.setText(yo + " s");
					tempoInicial = System.currentTimeMillis();
					pacotes = 0;
				}
				barra = barra + cont;
				progressDown.setValue(barra);
			}
			File file = new File(nome);
			if(file.length() < tamanho2){
				file.delete();
			}
			estima.setText("Conclu�do");
			armazenar.close(); 
			receberTamanhoeNome.close();
			soquete.close();
			tmpsoquete.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
