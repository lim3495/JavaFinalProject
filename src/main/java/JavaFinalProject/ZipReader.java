package JavaFinalProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import Utils.MyException;

public class ZipReader extends Thread{
	

	private String dataPath;// csv file to be analyzed
	private String resultPath; // the file path where the results are saved.
	private String resultPath2; // the file path where the results are saved.
	private String errorPath="./error.csv";
	private boolean help;
	
	ArrayList<String> saveFile = new ArrayList<String>();
	private String[] argument;
	private File[] resultList;
	
	   public void run() {
		try {
		   if(argument.length <2) {
			   throw new MyException();
		   }
		   
		   Options options = createOptions();
		   
			if(parseOption(options,argument)) {
				if(help) {
					System.out.println("help");
					printHelp(options);
					System.exit(0);
					
				}
		else {
			getZipFileList(dataPath);
			
			for(File f:resultList) {
				if(f.getName().contains("zip")) {
					saveFile.add(f.getName());
					readFileInZip(dataPath + f.getName());
					}
				}
			
			  Utils.Utils.writeAFile(saveFile, resultPath);
		   
			}
				
			}
		}catch (MyException e) {
			System.out.println(e.getMessage());
		}
			    
	   }
	   
	   public void setArg(String[] args) {
			argument = args;
		}

	   public void readFileInZip(String path) {
	      ZipFile zipFile;
	      int count=0;
	     
	      
	      try {
	         zipFile = new ZipFile(path);
	         Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();
	         
//	         if(zipFile == null)
//				throw new MyException();

	          while(entries.hasMoreElements()){
	        	  
	              ZipArchiveEntry entry = entries.nextElement();
	              InputStream stream = zipFile.getInputStream(entry);
	          
	              ExcelReader myReader = new ExcelReader();
	              
	              
	              
	              for(String value:myReader.getData(stream)) {
	            	  saveFile.add(value);
	              }
	         	  saveFile.add("");
	            //  if(count == 0)
		         //     Utils.Utils.writeAFile(temp, result);
	             //  else if(count == 1)
		          //    Utils.Utils.writeAFile(temp, result2);
	               
	              // count ++;
	          }
	          
	          
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      } 
	   }
	   
		public File[] getZipFileList(String path) {
			File file = new File(path);
			resultList = file.listFiles();
			
			return resultList;
		}

	   private void printError(String path) throws IOException {
		   try (CSVPrinter printer = new CSVPrinter(new FileWriter(path), CSVFormat.DEFAULT)){
               printer.printRecord("Error path:"+path);
               printer.printRecord("\n\n\n");
		   }
	   }

		private void printHelp(Options options) {
			HelpFormatter formatter = new HelpFormatter();
			String header = "Java Final HW";
			String footer = "";
			formatter.printHelp("Java Final HW",header,options,footer,true);
		}
		
		private boolean parseOption(Options options, String[] args) {
			DefaultParser parser = new DefaultParser();
			
			try {
				CommandLine cmd = parser.parse(options, args);
				
				dataPath = cmd.getOptionValue("i");
				resultPath = cmd.getOptionValue("o");
				//resultPath2 = cmd.getOptionValue("o2");
				help = cmd.hasOption("h");
						
			} catch(Exception e) {
				printHelp(options);
				return false;
			}
			
			return true;
			
		}
		
		private Options createOptions() {
			Options options = new Options();
			
			options.addOption(Option.builder("i").longOpt("input")
					.desc("Set an input file path")
					.hasArg()
					.argName("Input path")
					.required()
					.build());
			
			options.addOption(Option.builder("o").longOpt("output")
					.desc("Set an output file path")
					.hasArg()
					.argName("Output path")
					.required()
					.build());
			
//			options.addOption(Option.builder("o2").longOpt("output2")
//					.desc("Set an output file2 path")
//					.hasArg()
//					.argName("Output2 path")
//					.required()
//					.build());
			 
			options.addOption(Option.builder("h").longOpt("help")
					.desc("Show a Help page")
					.argName("Help")
					.build());
			
			return options;
		}
}
