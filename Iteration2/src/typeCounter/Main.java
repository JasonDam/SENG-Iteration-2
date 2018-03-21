package typeCounter;
import java.io.File;
import java.util.Scanner;

/**
 * Driver accepting the path and type
 * @author Elvin
 *
 */
public class Main {
	private static String type;
	private static String path;
	final static Logger $ = new Logger();
	
	/**
	 * Driver passing input to TypeCounter.
	 * Prompts for input if not is received in args
	 * @param args
	 */
	public static void main(String args[]){
		Scanner reader = new Scanner(System.in);
		if(args.length == 2){
			// Use arguments if they exist
			path = args[0];
			type = args[1];
		} else {
			// Else ask for arguments
			System.out.println("No arguments specified.");
			System.out.print("Enter path:");
			path = reader.nextLine();
			System.out.print("Enter type:");
			type = reader.nextLine();
			System.out.println();
		}
	
		File currentDir = new File(path);
	//	reader.close();
		
		// Passes args/user input to TypeCounter
		//final TypeCounter tc = new TypeCounter(path,type);
		//tc.findAndPrintDeclarationsAndReferences();
		RecursiveTest rt = new RecursiveTest(path);
		rt.displayDirectoryContents(currentDir);
		rt.findDeclarations();
	}
}
