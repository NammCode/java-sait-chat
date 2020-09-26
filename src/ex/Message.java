package ex;

import java.io.Serializable;

public class Message implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7887050907471427419L;
	
	private String userName;
	private String message;
	
	public Message(String userName, String message) {
		super();
		this.userName = userName;
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	@Override
	public String toString() {
		return userName + ": " + message;
	}	
	
}
