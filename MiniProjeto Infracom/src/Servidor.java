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

			double tempo1, ultimaVelocidade, velocidadeMedia = 0;//variaveispara o tempo de download estimado
			int cont, contagem = 0;
			while ((cont = lerDados.read(buffer)) > 0){//mesma logica do cliente, so que para a leitura agora
				tempo1 = System.nanoTime();
				
				armazenar.write(buffer, 0, cont);
				//tempo 
				ultimaVelocidade = (System.nanoTime() - tempo1)/1000000000;//tempo para a chegada de 4 Kbytes em segundos: 1 nanosegundo é igual a 10^-9 segundos
				ultimaVelocidade = 4/ultimaVelocidade;//Kbyte por segundo
				if(contagem == 0){//o primeiro valor da valociadadeMedia é o primeiro de ultimaVelocidade
					velocidadeMedia = ultimaVelocidade;
				}
				else
					velocidadeMedia = 0.125*ultimaVelocidade + (1 - 0.125)*velocidadeMedia;
				tempofaltando = ((tamanho/1000)/(velocidadeMedia));// t = s/v
				if(contagem >= 1250 && contagem%1250 == 0){//a cada MB (se cada ciclo recebe atualiza o valor do tempo a cada segundo)
					estima.setText(String.valueOf(tempofaltando));
				}
				tamanho = tamanho - cont;//o que resta para enviar
				valorBarra = valorBarra + cont;
				progressDown.setValue(valorBarra);
				contagem++;
			}
			File file = new File(nome);
			if(file.length() < tamanho){
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
