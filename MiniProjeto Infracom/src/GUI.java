import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ipDestino;
	public JProgressBar progressDown;
	public JProgressBar progressUp;
	private JTextField porta;
	private final Action botaoReber = new SwingAction();
	private final Action botaoEnviar = new SwingAction_1();
	JTextPane rtt;
	JTextPane tempoEstimado;
	JFileChooser chooser;
	JTextPane arquivo;
	private File file;
	private final Action escolher = new SwingAction_2();
	JTextPane downName;
	private final Action escolheUp = new SwingAction_3();
	private JTextPane localdoDown;
	private JFileChooser chooserDown;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 724, 496);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		rtt = new JTextPane();
		rtt.setBackground(Color.LIGHT_GRAY);
		rtt.setBounds(496, 46, 176, 31);
		contentPane.add(rtt);

		tempoEstimado = new JTextPane();
		tempoEstimado.setBackground(Color.LIGHT_GRAY);
		tempoEstimado.setBounds(495, 109, 177, 31);
		contentPane.add(tempoEstimado);

		arquivo = new JTextPane();
		arquivo.setBounds(43, 392, 512, 20);
		contentPane.add(arquivo);

		progressDown = new JProgressBar();
		progressDown.setForeground(Color.GREEN);
		progressDown.setBounds(43, 138, 512, 24);
		contentPane.add(progressDown);

		progressUp = new JProgressBar();
		progressUp.setForeground(Color.GREEN);
		progressUp.setBounds(43, 328, 512, 24);
		contentPane.add(progressUp);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.setAction(botaoEnviar);
		btnEnviar.setBounds(43, 423, 608, 23);
		contentPane.add(btnEnviar);

		JButton btnReceber = new JButton("Receber");
		btnReceber.setAction(botaoReber);
		btnReceber.setBounds(43, 263, 608, 23);
		contentPane.add(btnReceber);

		JButton btnEscolherArquivo = new JButton("Escolher");
		btnEscolherArquivo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnEscolherArquivo.setAction(escolher);
		btnEscolherArquivo.setBounds(562, 389, 89, 23);
		contentPane.add(btnEscolherArquivo);

		ipDestino = new JTextField();
		ipDestino.setBounds(160, 46, 177, 20);
		contentPane.add(ipDestino);
		ipDestino.setColumns(10);

		porta = new JTextField();
		porta.setColumns(10);
		porta.setBounds(160, 10, 177, 20);
		contentPane.add(porta);

		JLabel lblRtt = new JLabel("RTT (milisegundos)");
		lblRtt.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		lblRtt.setBounds(484, 13, 137, 22);
		contentPane.add(lblRtt);

		JLabel lblTempoRestanteEstimado = new JLabel("Tempo Restante Estimado (segundos)");
		lblTempoRestanteEstimado.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		lblTempoRestanteEstimado.setBounds(391, 84, 281, 14);
		contentPane.add(lblTempoRestanteEstimado);

		JLabel lblIpDeDestino = new JLabel("IP de Destino:");
		lblIpDeDestino.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		lblIpDeDestino.setBounds(43, 49, 148, 14);
		contentPane.add(lblIpDeDestino);

		JLabel lblProgressoDoDownload = new JLabel("Progresso do Download");
		lblProgressoDoDownload.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		lblProgressoDoDownload.setBounds(43, 113, 177, 14);
		contentPane.add(lblProgressoDoDownload);

		JLabel lblProgressoDoUpload = new JLabel("Progresso do Upload");
		lblProgressoDoUpload.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		lblProgressoDoUpload.setBounds(43, 303, 177, 14);
		contentPane.add(lblProgressoDoUpload);

		JLabel lblArquivo = new JLabel("Arquivo\r\n para upload:\r\n");
		lblArquivo.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		lblArquivo.setBounds(46, 357, 228, 24);
		contentPane.add(lblArquivo);

		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		lblPorta.setBounds(43, 11, 148, 14);
		contentPane.add(lblPorta);

		downName = new JTextPane();
		downName.setBackground(Color.LIGHT_GRAY);
		downName.setBounds(43, 173, 629, 31);
		contentPane.add(downName);

		localdoDown = new JTextPane();
		localdoDown.setBounds(43, 232, 512, 20);
		contentPane.add(localdoDown);

		JButton localUp = new JButton("Escolher");
		localUp.setAction(escolheUp);
		localUp.setBounds(562, 229, 89, 23);
		contentPane.add(localUp);

		JLabel lblLocalDeDownload = new JLabel("Local de download:");
		lblLocalDeDownload.setFont(new Font("Comic Sans MS", Font.BOLD | Font.ITALIC, 15));
		lblLocalDeDownload.setBounds(43, 197, 228, 24);
		contentPane.add(lblLocalDeDownload);

	}

	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "Receber");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			int port = Integer.parseInt(porta.getText());//numero da porta passado para int

			new Thread(new RTTServidor()).start();
			new Thread(new Servidor(progressDown, port, tempoEstimado, downName, localdoDown.getText())).start();

		}
	}
	private class SwingAction_1 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction_1() {
			putValue(NAME, "Enviar");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			new Thread(new RTTCliente(ipDestino.getText(), rtt)).start();
			int port = Integer.parseInt(porta.getText());
			new Thread(new Cliente (file, ipDestino.getText(), port, progressUp)).start();

		}
	}
	private class SwingAction_2 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction_2() {
			putValue(NAME, "Escolher");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			chooser = new JFileChooser();
			int value = chooser.showOpenDialog(null);
			if(value == JFileChooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				arquivo.setText(file.getName());
			}
		}
	}
	private class SwingAction_3 extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction_3() {
			putValue(NAME, "Escolher");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
			chooserDown = new JFileChooser();
			chooserDown.setCurrentDirectory(new java.io.File("."));
			chooserDown.setDialogTitle("choosertitle");
			chooserDown.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooserDown.setAcceptAllFileFilterUsed(false);
			if (chooserDown.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				localdoDown.setText(chooserDown.getSelectedFile().getAbsolutePath());
			} else {
				localdoDown.setText("Nada selecionado");
			}
		}
	}
}
