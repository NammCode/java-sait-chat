package ex;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable, PropertyChangeListener {

	private Socket socket1;
	private Socket socket2;
	private ObjectOutputStream oos1;
	private ObjectOutputStream oos2;
	private InputListener lis1;
	private InputListener lis2;

	public ClientHandler(ArrayList<Socket> socketList) {
		socket1 = socketList.get(0);
		socket2 = socketList.get(1);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		lis1 = new InputListener(socket1, this, 1);
		lis2 = new InputListener(socket2, this, 2);
		Thread th1 = new Thread(lis1);
		Thread th2 = new Thread(lis2);
		th1.start();
		th2.start();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		int number = (int) evt.getOldValue();
		String text = evt.getPropertyName();
		try {
			if (number == 1) {
				if (text.equals("DISSS")) {
					oos1 = new ObjectOutputStream(socket1.getOutputStream());
					oos1.writeObject(text);
					oos1.close();
				} else {
					oos2 = new ObjectOutputStream(socket2.getOutputStream());
					Message ms = new Message("Client", text);
					oos2.writeObject(ms);
				}
			} else if (number == 2) {
				if (text.equals("DISSS")) {
					oos2 = new ObjectOutputStream(socket2.getOutputStream());
					oos2.writeObject(text);
					oos2.close();
				} else {
					oos1 = new ObjectOutputStream(socket1.getOutputStream());
					Message ms = new Message("Client", text);
					oos1.writeObject(ms);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
