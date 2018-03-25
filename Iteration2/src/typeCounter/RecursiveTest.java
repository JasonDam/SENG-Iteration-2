//ver2
package typeCounter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

public class RecursiveTest {
	
	String abs;
	String folderPath;
	static ArrayList<String> fileList = new ArrayList<String>();
	File currentDir;
	JarFile currentJar;
	private static Map <String, ArrayList<Integer>> typeMap = new HashMap <String, ArrayList<Integer>>();
	String destdir;
	
	public RecursiveTest(String path) throws IOException {
		this.folderPath = path;
		this.currentDir = new File(path);

	}
	
	public void countInJarOrDirectory() throws IOException {
		if (this.folderPath.endsWith(".jar")) {
			this.extractJarFiles();
			this.currentDir = new File("/Users/TonyTea/Desktop/destinationDirectoryOne");
			this.displayDirectoryContents(currentDir);
			this.findDeclarations();
			this.printResults();
			
			//	this.getJarElements();
		//	fileList = this.getJarElements();
		  //  this.findDeclarations();
		//	this.printResults();
		}
		else {
		this.displayDirectoryContents(currentDir);
		this.findDeclarations();
		this.printResults();
		}
}
	
	public void printResults(){
		for (String key: typeMap.keySet()) {
			ArrayList<Integer> currentArray = typeMap.get(key);
			System.out.println(key+ "Declarations found:" + currentArray.get(0)+"; References found: " + currentArray.get(1)+".");
		}
	}
	protected void findDeclarations(){
		for(String file:fileList){
			// creating ASTs for every file in the file list
			this.parse(file).accept(new ASTVisitor(){
				/**
				 * finds Type Declarations and adds to
				 * declarationCounter if
				 * the node string matches the specified
				 * declaration type for classes and interfaces
				 */
				public boolean visit(TypeDeclaration node){
					String nodeString = node.getName().getFullyQualifiedName();
					if(typeMap.containsKey(nodeString)) {
						ArrayList<Integer> currentArray = typeMap.get(nodeString);
						int currentValue = currentArray.get(0);
						currentArray.set(0, currentValue+1);
						
						typeMap.replace(nodeString, currentArray);
					}
					else {
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(1);
						intArray.add(0);
						typeMap.put(nodeString,intArray);
					}
					//System.out.println(nodeString);
				
					return true; 
				}
				
			/**
			 * find enum declarations 
			 */
				public boolean visit(EnumDeclaration node) {
					ITypeBinding typeBind = node.resolveBinding();
					String nodeString = typeBind.getQualifiedName();
					
					if(typeMap.containsKey(nodeString)) {
						ArrayList<Integer> currentArray = typeMap.get(nodeString);
						int currentValue = currentArray.get(0);
						currentArray.set(0, currentValue+1);
						
						typeMap.replace(nodeString, currentArray);
					}
					else {
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(1);
						intArray.add(0);
						typeMap.put(nodeString,intArray);
					}
					
					//System.out.println(nodeString);
					
					return true;
				}
				
				public boolean visit(AnnotationTypeDeclaration node) {
					ITypeBinding typeBind = node.resolveBinding();
					String nodeString = typeBind.getQualifiedName();
					if(typeMap.containsKey(nodeString)) {
						ArrayList<Integer> currentArray = typeMap.get(nodeString);
						int currentValue = currentArray.get(0);
						currentArray.set(0, currentValue+1);
						
						typeMap.replace(nodeString, currentArray);
					}
					else {
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(1);
						intArray.add(0);
						typeMap.put(nodeString,intArray);
					}
					//System.out.println(nodeString);
					return true;
				}
				
				/**
				 * for references
				 */
				public boolean visit(QualifiedName node) {
					String nodeString = node.getFullyQualifiedName();
					//System.out.println(nodeString);
					if (typeMap.containsKey(nodeString)) {
						ArrayList<Integer> currentArray = typeMap.get(nodeString);
						int currentValue = currentArray.get(1);
						currentArray.set(1, currentValue+1);
						
						typeMap.replace(nodeString, currentArray);
					}
					else {
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(0);
						intArray.add(1);
						typeMap.put(nodeString, intArray);
					}
					
					return true;
				}
				
				/**
				 * for references
				 */
				public boolean visit(VariableDeclarationFragment node) {
					IVariableBinding rNode = node.resolveBinding();
					String typeNode = rNode.getType().getQualifiedName();
					if (typeMap.containsKey(typeNode)) {
						ArrayList<Integer> currentArray = typeMap.get(typeNode);
						int currentValue = currentArray.get(1);
						currentArray.set(1, currentValue+1);
						
						typeMap.replace(typeNode, currentArray);
					}
					else {
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(0);
						intArray.add(1);
						typeMap.put(typeNode, intArray);
					}
				//	System.out.print(typeNode);
					return false;
				}
				
				});
			}
		}
	

	/**
	 * Outputs a compilation unit AST from a string
	 * of the input file
	 * @param fileName
	 * @return a Compilation Unit from the string
	 */
	private CompilationUnit parse(String fileName){
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(fileContentToCharArray(fileName));
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		parser.setEnvironment(null, null, null, true);
		parser.setUnitName("lol");
		
		
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		parser.setCompilerOptions(options);
///Users/TonyTea/Documents/GitHub/SENG-Iteration-2/Iteration2/src/JarFIleSecondTest/FolderInJar2ndTest/ClassInFolderOfFolderinJar2ndTest.java
		return (CompilationUnit) parser.createAST(null);
	}		
	
	/**
	 * Outputs contents of a file as a string
	 * @param path -- path of the file
	 * @exception when file cannot be found
	 * @return contents of the file in a string
	 */
	private char[] fileContentToCharArray(String path) {
		
		
		String content = null;
		try{
			content = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e){
			e.printStackTrace();
			;}
		
		return content.toCharArray();
	}
	

	
	
	public static void displayDirectoryContents(File dir) {
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
								if(name.toLowerCase().endsWith(".java"))
									return name.toLowerCase().endsWith("java");
							// this is NOT what was required, instead of automatically looking in Jar, files, ask USER to
							// input jar File of INTEREST and look into that. 
								else
									return name.toLowerCase().endsWith("jar");
							} 		
					 });
					 
					 for(int i = 0; i < files.length; i++){
							String filePath = files[i].getPath();
					//	System.out.println(filePath);
							fileList.add(filePath);
					 }
					}
				}
			//Users/TonyTea/Documents/GitHub/SENG-Iteration-2/Iteration2/src/JarFIleSecondTest/FolderInJar2ndTest/ClassInFolderOfFodlerinJar2ndTestNumberTow.java, 
			//Users/TonyTea/Documents/GitHub/SENG-Iteration-2/Iteration2/src/JarFIleSecondTest/FolderInJar2ndTest/ClassInFolderOfFolderinJar2ndTest.java, 
			//Users/TonyTea/Documents/GitHub/SENG-Iteration-2/Iteration2/src/JarFIleSecondTest/FolderInJar2ndTest/FolderinFolderinJar2ndTest/LastFolderClass.java, 
			//Users/TonyTea/Documents/GitHub/SENG-Iteration-2/Iteration2/src/JarFIleSecondTest/ClassInJarFile2ndTest.java]
			// used THIS to print out that .java files are filtered out from EACH directory given, recursively
			System.out.println(Arrays.toString(files));
		hs.addAll(fileList);
		fileList.clear();
		fileList.addAll(hs);
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.println(Arrays.deepToString(fileList.toArray()));	
	}
	
	
	
	
	public String extractJarFiles() throws IOException {
		 java.util.jar.JarFile jarfile = new java.util.jar.JarFile(new java.io.File(this.folderPath)); //jar file path(here sqljdbc4.jar)
		    java.util.Enumeration<java.util.jar.JarEntry> enu= jarfile.entries();
		    while(enu.hasMoreElements())
		    {
		    		// get user to enter directory
		        String destdir = "/Users/TonyTea/Desktop/destinationDirectoryOne";     //abc is my destination directory
		        java.util.jar.JarEntry je = enu.nextElement();

		        System.out.println(je.getName());

		        java.io.File fl = new java.io.File(destdir, je.getName());
		        if(!fl.exists())
		        {
		            fl.getParentFile().mkdirs();
		            fl = new java.io.File(destdir, je.getName());
		        }
		        if(je.isDirectory())
		        {
		            continue;
		        }
		        java.io.InputStream is = jarfile.getInputStream(je);
		        java.io.FileOutputStream fo = new java.io.FileOutputStream(fl);
		        while(is.available()>0)
		        {
		            fo.write(is.read());
		        }
		        fo.close();
		        is.close();
		    }
			return destdir;
	}
	
	public ArrayList<String> getJarElements() throws IOException {
		Set<String> hs = new HashSet<>();
		ArrayList <String> classNames = new ArrayList<String>();
		
		 final ZipFile zf = new ZipFile(this.folderPath);
		    try {
		        for (ZipEntry ze : Collections.list(zf.entries())) {
		            final String path = ze.getName();
		            if (path.endsWith(".java")) {
		                final StringBuilder buf = new StringBuilder(path);
		               // buf.delete(path.lastIndexOf('/'), path.length()); //removes the name of the class to get the path only
		                //if (!path.startsWith("/")) { //you may omit this part if leading / is not required
		                  //  buf.insert(0, '/');
		               // }
		                System.out.println(buf.toString());
		                
		                File file = new File(buf.toString());
		                String fileName = file.getAbsolutePath();
		                classNames.add(fileName);
		             //   System.out.print(fileName);
		              
		            }
		        }
		    } finally {
		        zf.close();
		    }
		//ZipInputStream zip = new ZipInputStream(new FileInputStream(this.folderPath));
		//for (ZipEntry entry = zip.getNextEntry(); entry!= null; entry = zip.getNextEntry()) {
	
	//		if (entry.isDirectory()) {
	//			System.out.print("HEY");
	//		String filePath = entry.getName();
	//		File dir = new File(filePath);
	//		String javaFileInFolder = getJavaStuff(dir);
	//		String justNameOne = javaFileInFolder.substring(javaFileInFolder.lastIndexOf("/") +1);
	//		File nameFileOne = new File(justNameOne);
	//		String absPathOne = nameFileOne.getAbsolutePath();
	//		classNames.add(absPathOne);
			
			
	//		}
			
		//	 if (!entry.isDirectory() && entry.getName().endsWith(".java")) {
		//		String className = entry.getName();
		//		System.out.println(className);
		//		String justName = className.substring(className.lastIndexOf("/") +1);
		//		File nameFile = new File(justName);
			//	System.out.println(justName);
				//String absPath = nameFile.getCanonicalPath();
				//System.out.println(absPath);
				//classNames.add(absPath);

		//	}
		//}
		
		//hs.addAll(classNames);
		//classNames.clear();
		//classNames.addAll(hs);
	//	System.out.print(classNames);
		
	//	 for(int i = 0; i < classNames.size(); i++){
	///			System.out.println(classNames.get(i));
				
		// }
		    System.out.print(classNames);
		return classNames;
	}
}

