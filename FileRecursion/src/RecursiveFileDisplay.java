
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class RecursiveFileDisplay {

	public static void main(String[] args) {
		System.out.print("Enter a path: ");
		Scanner reader = new Scanner(System.in);
		String folderPath = reader.nextLine();
		File currentDir = new File(folderPath); // directory as an input
		displayDirectoryContents(currentDir);
	}

	public static void displayDirectoryContents(File dir) {
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					displayDirectoryContents(file);
				} else {
					System.out.println("     file:" + file.getCanonicalPath());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}