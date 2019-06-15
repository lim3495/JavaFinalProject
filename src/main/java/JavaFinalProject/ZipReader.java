package JavaFinalProject;

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

import Utils.MyException;
public class ZipReader {
	

	private String dataPath;// csv file to be analyzed
	private String resultPath; // the file path where the results are saved.
	private boolean help;

	   public void run(String[] args) {
		   Options options = createOptions();
		try {
		   if(args.length <2) {
			   throw new MyException();
		   }
		}catch (MyException e) {
			System.out.println(e.getMessage());
		}
			
			if(parseOption(options,args)) {
				if(help) {
					System.out.println("help");
					printHelp(options);
					System.exit(0);
					
				}
	      
	      readFileInZip(dataPath);
	      
	   }
	   }

	   public void readFileInZip(String path) {
	      ZipFile zipFile;
	      String resultPath = this.resultPath;	      
	      
	      ArrayList<String> temp = new ArrayList<String>();
	      
	      try {
	         zipFile = new ZipFile(path);
	         Enumeration<? extends ZipArchiveEntry> entries = zipFile.getEntries();

	          while(entries.hasMoreElements()){
	             ZipArchiveEntry entry = entries.nextElement();
	              InputStream stream = zipFile.getInputStream(entry);
	          
	              ExcelReader myReader = new ExcelReader();
	              
	              
	              
	              for(String value:myReader.getData(stream)) {
	                 temp.add(value);
	              }
	              

	              
	              Utils.Utils.writeAFile(temp, resultPath);
	          }
	          
	          
	      } catch (IOException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
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
			 
			options.addOption(Option.builder("h").longOpt("help")
					.desc("Show a Help page")
					.argName("Help")
					.build());
			
			return options;
		}
}
