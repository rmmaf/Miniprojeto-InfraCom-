import java.net.*;
import java.io.*;
import javax.swing.JProgressBar;
public class Cliente extends Thread {
	private String endereco; //Endereco IP para conexao
	private String caminho;
	private int porta; //socket, a aplicacao funcionara no socket 2020
	private JProgressBar barra;
	private String nome;

	public Cliente (String adress, String caminho, String nome, int port, JProgressBar bar){
		porta = port;
		endereco = adress;
		barra = bar;
		this.caminho = caminho;
		this.nome = nome;
	}


	public void run(){
		byte[] buffer = new byte[1024 * 4];//buffer de 4 KB;
		try {
			int valorBarra = 0;//variavel que define o valor atual da barra de progresso de upload
			System.out.println("Conectando");
			Socket soquete = new Socket(endereco, porta);
			
			File file = new File(caminho + nome);//pegar arquivo com o caminho dado
			System.out.println("Enviando tamanho");
			
			int tamanho = (int) file.length();//pegar tamanho do arquivo
			barra.setMaximum(tamanho);//valor maximo da barra definido
			DataOutputStream enviarTamanhoeNome = new DataOutputStream(soquete.getOutputStream());
			enviarTamanhoeNome.writeInt(tamanho);
			enviarTamanhoeNome.writeUTF(nome);

			FileInputStream leitura = new FileInputStream(file);//sistema de leitura do arquivo
			int cont;
			while((cont = leitura.read(buffer)) > 0){
				//le o que ta no arquivo e joga pro buffer; essa leitura retorna o numero de bytes lidos, caso o valor seja zero, quer dizer que leu 0 bytes(fim do arquivo)
				enviarTamanhoeNome.write(buffer, 0, cont);
				valorBarra = valorBarra + cont;
				barra.setValue(valorBarra);//feito isso, eh atualizada a barra de progresso e le novamente o arquivo e joga pro buffer o que foi lido
			}
			leitura.close();
			enviarTamanhoeNome.close();
			soquete.close();
		} catch (ConnectException e){
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}
}
