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

	public static void main(String args[]) throws IOException{
		Scanner reader = new Scanner(System.in);
		if(args.length == 2){
			pathOrJars = args[0];
		} 
		else {
			System.out.print("Enter PATHNAME for PATHNAME WITH JARFILE:");
			pathOrJars = reader.nextLine();
		}
	
		File pathOrJarFile = new File(pathOrJars);
		reader.close();
		
		DeclarationsAndReferencesCounter rt = new DeclarationsAndReferencesCounter(pathOrJars);
		rt.countInJarOrDirectory();
	}
}
