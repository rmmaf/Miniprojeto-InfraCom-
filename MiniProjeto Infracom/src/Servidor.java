import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JProgressBar;
import javax.swing.JTextPane;

public class Servidor extends Thread {
	private int porta;
	private JProgressBar progressDown;
	private JTextPane estima;
	private JTextPane nomeDown;
	private String local;
	
	public Servidor(JProgressBar progresso, int port, JTextPane estima, JTextPane nomeDown, String local) {
		porta = port;
		progressDown = progresso;
		this.estima = estima;
		this.nomeDown = nomeDown;
		this.local = local;
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
			nomeDown.setText("Baixando: " + nome);
			//InputStream lerDados = soquete.getInputStream();//lê o que tá sendo recebido
			FileOutputStream armazenar = new FileOutputStream(local + "\\" + nome);
			System.out.println("comecando");
			int cont, tamanho2 = tamanho, barra = 0, pacotes = 0;
			long tempoInicial = System.currentTimeMillis(), tempoAtual;
			double tesmpoEstimado;
			while((cont = receberTamanhoeNome.read(buffer)) > 0){
				armazenar.write(buffer, 0, cont);
				pacotes = pacotes + cont;
				tamanho = tamanho - cont;
				if((tempoAtual = System.currentTimeMillis() - tempoInicial) > 1000){//atualiza de um em um segundo
					tesmpoEstimado = tamanho/pacotes;
					tesmpoEstimado = tempoAtual*tesmpoEstimado/1000;
					estima.setText(tesmpoEstimado + " s");
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
			estima.setText("Concluído");
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
