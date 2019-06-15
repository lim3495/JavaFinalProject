package JavaFinalProject;

public class Main {

	public static void main(String[] args)  {
		int numberOfThread = 5;
		Thread[] thread = new Thread[numberOfThread];
		
		for(int i=0 ; i<numberOfThread ; i++) {
			ZipReader analyzer = new ZipReader();
			analyzer.setArg(args);
			
			thread[i] = new Thread(analyzer);
			thread[i].start();
		}
	}

}
