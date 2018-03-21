//ver1
package typeCounter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RecursiveTest {
	
	String folderPath;
	static ArrayList<String> fileList = new ArrayList<String>();

	
	public RecursiveTest(String path) {
		this.folderPath = path;
	}
	
	
	public static void displayDirectoryContents(File dir) {
		
		// USE set to elminate doubles that might come from a loop
		Set<String> hs = new HashSet<>();
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					displayDirectoryContents(file);
				} else {
					 files = dir.listFiles(new FilenameFilter(){
							public boolean accept(File dir, String name){
								return name.toLowerCase().endsWith(".java");
							}
					 });
					 
					 for(int i = 0; i < files.length; i++){
							String filePath = files[i].getPath();
							System.out.print(filePath);
							fileList.add(filePath);
					 }
					}
				}
			// used THIS to print out that .java files are filtered out from EACH directory given, recursively
		//	System.out.println(Arrays.toString(files));
		hs.addAll(fileList);
		fileList.clear();
		fileList.addAll(hs);
		System.out.println(Arrays.deepToString(fileList.toArray()));	
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	
	public ArrayList<String> changeFileList(String folderPath){
		ArrayList<String> fileList = new ArrayList<String>();
		File dir = new File(folderPath);
		File[] files = dir.listFiles();	
		// Filter files that end with .java
		 files = dir.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.toLowerCase().endsWith(".java");
			}
		});
		// files.length if file is null will result in an exception
		if(files == null) return fileList;
		
		// adds to fileList the files in the specified path
		for(int i = 0; i < files.length; i++){
			String filePath = files[i].getPath();
			System.out.print(filePath);
			fileList.add(filePath);
		}
		return fileList;
	}
}
