package ex;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.JButton;
import javax.swing.JTextArea;

public class ClientGUI extends JFrame implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2421438319707602608L;
	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField txtSend;
	private JButton btnSend;
	private JButton btnConnect;
	private JButton btnDisconnect;
	private JTextArea txtBoard;

	private static Socket socket;
	private ObjectOutputStream oos;
	private static InputListener lis;

	private static boolean connect;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI();
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
	public ClientGUI() {
		// generate code
		setTitle("GUI Message Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 316, 342);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMsSend = new JLabel("Message to Send:");
		lblMsSend.setBounds(10, 11, 114, 14);
		contentPane.add(lblMsSend);

		btnSend = new JButton("Send");
		btnSend.setBounds(100, 67, 89, 23);
		contentPane.add(btnSend);

		txtSend = new JTextField();
		txtSend.setBounds(10, 36, 283, 20);
		contentPane.add(txtSend);
		txtSend.setColumns(10);

		JLabel lblMsBoard = new JLabel("Message Board");
		lblMsBoard.setBounds(10, 116, 114, 14);
		contentPane.add(lblMsBoard);

		btnConnect = new JButton("Connect");
		btnConnect.setBounds(20, 254, 89, 23);
		contentPane.add(btnConnect);

		btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(154, 254, 114, 23);
		contentPane.add(btnDisconnect);

		txtBoard = new JTextArea();
		txtBoard.setBounds(10, 141, 283, 102);
		contentPane.add(txtBoard);

		// my code
		btnSend.setEnabled(false);
		btnDisconnect.setEnabled(false);
		btnSend.addActionListener(new ButtonSendListener());
		btnConnect.addActionListener(new ButtonConnectListener());
		btnDisconnect.addActionListener(new ButtonDisconnectListener());
	}

	private class ButtonSendListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Message ms = new Message("Client", txtSend.getText());
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				oos.writeObject(ms);
				txtBoard.append("You: " + ms.getMessage() + "\n");
				txtSend.setText("");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class ButtonConnectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				//192.168.1.78
				socket = new Socket("localhost", 5555);
				connect = true;
				if (connect) {
					btnSend.setEnabled(true);
					btnConnect.setEnabled(false);
					btnDisconnect.setEnabled(true);
				}
				createInputListener(socket, 0);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			;
		}

	}

	private class ButtonDisconnectListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				oos = new ObjectOutputStream(socket.getOutputStream());
				Message ms = new Message("client", "DISSS");
				oos.writeObject(ms);
				btnSend.setEnabled(false);
				btnConnect.setEnabled(true);
				btnDisconnect.setEnabled(false);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	
	public void createInputListener (Socket socket, int number){
		lis = new InputListener(socket, this, number);
		Thread th = new Thread(lis);
		th.start();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		txtBoard.append("She: " + evt.getPropertyName() + "\n");
	}
}
