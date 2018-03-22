package typeCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

public class CounterInJar {

	String nameOfFile;
	File jarFilesOne;
	JarFile jars;
	static ArrayList<String> jarFileList = new ArrayList <String>();
	
	public CounterInJar (String fileName) throws IOException {
		this.nameOfFile = fileName;
		this.jarFilesOne = new File(nameOfFile);
		this.jars = new JarFile(jarFilesOne);

	}
	
	public String getJavaStuff(File dir) {
		File[] files = dir.listFiles();
		for (File file: files) {
			if (file.isDirectory()) {
				getJavaStuff(dir);
			}
			else {
				 files = dir.listFiles(new FilenameFilter(){
						public boolean accept(File dir, String name){
							if(name.toLowerCase().endsWith(".java"))
								return name.toLowerCase().endsWith("java");
						// this is NOT what was required, instead of automatically looking in Jar, files, ask USER to
						// input jar File of INTEREST and look into that. 
							else
								return name.toLowerCase().endsWith("jar");
						} 		
				 });
				 
				
				}
			
		}
		return null;
	}
	
	// this method should recurisvely display the ".java" files in the .jar files
	public List<String> getJarElements() throws IOException {
		List <String> classNames = new ArrayList<String>();
		ZipInputStream zip = new ZipInputStream(new FileInputStream(this.nameOfFile));
		for (ZipEntry entry = zip.getNextEntry(); entry!= null; entry = zip.getNextEntry()) {
			if (entry.isDirectory()) {
			String filePath = entry.getName();
			File dir = new File(filePath);
			classNames.add(getJavaStuff(dir));
			System.out.print(getJavaStuff(dir));
			}
			else if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
				String className = entry.getName();
				System.out.println(className);
				classNames.add(className);
			}
		}
		return classNames;
	}
	
	
	
	
}
