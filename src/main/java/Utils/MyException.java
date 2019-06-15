package Utils;


public class MyException extends Exception {
	public MyException () {
		super("There is CLI error.\n");
	}
	
	public MyException (String message) {
		super(message);
	}
}