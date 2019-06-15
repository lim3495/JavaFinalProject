package Utils;


public class MyException extends Exception {
	public MyException () {
		super("File is not existing.\n");
	}
	
	public MyException (String message) {
		super(message);
	}
}