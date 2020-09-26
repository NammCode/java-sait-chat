package ex;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

public class InputListener implements Runnable {

	private Socket socket;
	ObjectInputStream ois;
	private ClientGUI listener;
	private ClientHandler clientHandler;
	private int number;
	private boolean alive;
	Message ms;
	InputStream is;

	
	public InputListener(Socket socket, ClientHandler clientHandler, int number) {
		this.socket = socket;
		this.clientHandler = clientHandler;
		this.number = number;
		this.alive = true;
	}

	public InputListener(Socket socket, ClientGUI listener, int number) {
		this.socket = socket;
		this.listener = listener;
		this.number = number;
		this.alive = true;
	}

	@Override
	public void run() {
		try {
			is = socket.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (alive) {
			try {
				ois = new ObjectInputStream(is);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ms = (Message) ois.readObject();
				notifyListeners(ms.getMessage());
				if (ms.getMessage().equals("DISSS")) {
					try {
						ois.close();
						socket.close();
						alive = false;
					} catch(IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void notifyListeners(String text) {
		if (number == 0) {
			listener.propertyChange(new PropertyChangeEvent(this, text, number, null));
		} else {
			clientHandler.propertyChange(new PropertyChangeEvent(this, text, number, null));
		}
	}

}
