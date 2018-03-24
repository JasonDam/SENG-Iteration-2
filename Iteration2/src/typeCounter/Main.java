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
	private static String pathOrJars;
//	private static String path;
	
	

	public static void main(String args[]) throws IOException{
		Scanner reader = new Scanner(System.in);
		if(args.length == 2){
			// Use arguments if they exist
			//path = args[0];
			pathOrJars = args[0];
		} else {
			// Else ask for arguments
			//System.out.println("No arguments specified.");
			System.out.print("Enter PATHNAME for JARFILE:");
	///		path = reader.nextLine();
			pathOrJars = reader.nextLine();
				}
	
		//File currentDir = new File(path);
		File pathOrJarFile = new File(pathOrJars);
	//	reader.close();
		
		
		
		// Passes args/user input to TypeCounter
		if(pathOrJars.endsWith(".jar")) {
			CounterInJar ct = new CounterInJar(pathOrJars);
			ct.getJarElements();
		}
		else {
			RecursiveTest rt = new RecursiveTest(pathOrJars);// LET THIS ONE RUN 1 FOR DIRECTORY TYPES
			rt.displayDirectoryContents(pathOrJarFile);
		 	rt.countInJarOrDirectory();// LET THIS ONE RUN 1 FOR DIRECTORY TYPES
		 	
		}
		//final TypeCounter tc = new TypeCounter(path,type);
		//tc.findAndPrintDeclarationsAndReferences();
	//	RecursiveTest rt = new RecursiveTest(path);// LET THIS ONE RUN 1 FOR DIRECTORY TYPES
	
				
	}
}
