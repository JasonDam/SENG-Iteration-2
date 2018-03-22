package typeCounter;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Driver accepting the path and type
 * @author Tony
 *
 */
public class Main {
	private static String jars;
	private static String path;
	
	

	public static void main(String args[]) throws IOException{
		Scanner reader = new Scanner(System.in);
		if(args.length == 2){
			// Use arguments if they exist
			//path = args[0];
			jars = args[0];
		} else {
			// Else ask for arguments
			//System.out.println("No arguments specified.");
			System.out.print("Enter PATHNAME for JARFILE:");
	///		path = reader.nextLine();
			jars = reader.nextLine();
				}
	
		//File currentDir = new File(path);
		File jarFile = new File(jars);
	//	reader.close();
		
		// Passes args/user input to TypeCounter
		//final TypeCounter tc = new TypeCounter(path,type);
		//tc.findAndPrintDeclarationsAndReferences();
	//	RecursiveTest rt = new RecursiveTest(path);// LET THIS ONE RUN 1 FOR DIRECTORY TYPES
	//	rt.displayDirectoryContents(currentDir);
 	//rt.countInJarOrDirectory();// LET THIS ONE RUN 1 FOR DIRECTORY TYPES
		CounterInJar ct = new CounterInJar(jars);
		ct.getJarElements();
		
	}
}
