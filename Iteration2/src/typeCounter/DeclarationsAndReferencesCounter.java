// SENG300 Iteration2
// Members: Tony, Logan, Evan, Jason, Kristopher
// Run the program through Main

package typeCounter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

public class DeclarationsAndReferencesCounter {
	
	private String folderPath;
	private static ArrayList<String> fileList = new ArrayList<String>();
	private File currentDir;
	private static Map <String, ArrayList<Integer>> typeMap = new HashMap <String, ArrayList<Integer>>();
	private String destdir;
	
	// constructor 
	DeclarationsAndReferencesCounter(String path) throws IOException {
		
		this.folderPath = path;
		this.currentDir = new File(path);
		this.destdir = (this.folderPath.split(".jar")[0]);
	}
	
	/**
	 *  Checks whether the user inputs a pathname for a Jar or Directory, then calls appropriate methods
	 * @throws IOException
	 */
	public void countInJarOrDirectory() throws IOException {
	
		if (this.folderPath.endsWith(".jar")) {
	 		String result = this.folderPath.split(".jar")[0];
			System.out.println(result);
			this.extractJarFiles();
		
			this.displayDirectoryContents(new File(result));
			this.findDeclarationsAndReferences();
			this.printResults();
			this.deleteDirectory(new File(this.destdir));
		}
		else {
			
			this.displayDirectoryContents(currentDir);
			this.findDeclarationsAndReferences();
			this.printResults();
		}
}
	
	/**
	 * After the entries extracted from the jar files have been extracted, and has gone through the parser and counters
	 * the folder containing the extracted files are deleted so there is no overlap when the program is ran again
	 * source: 
	 * @param file
	 */
	private void deleteDirectory(File file ) {
		
		File[] contents = file.listFiles();
		if(contents != null) {
			for (File f: contents) {
				deleteDirectory(f);
			}
		}
		file.delete();
	}

	/**
	 * Declarations and References are printed, Dictionary was used to keep track of recurring declarations/references seen 
	 * throughout the compilation unit
	 */
	private void printResults(){
		for (String key: typeMap.keySet()) {
			ArrayList<Integer> currentArray = typeMap.get(key);
			//System.out.println(key+ " Declarations found: " + currentArray.get(0)+"; References found: " + currentArray.get(1)+".");
			System.out.printf("%8s Declarations found: %1d, References found: %1d \n", key, currentArray.get(0), currentArray.get(1));
		}
	}
	
	/**
	 * finds and adds declarations and references to a counter in the dictionary.
	 */
	protected void findDeclarationsAndReferences(){
		
		for(String file:fileList){
			this.parse(file).accept(new ASTVisitor(){
				/**
				 * finds Type Declarations and adds to
				 * declarationCounter if
				 * the node string matches the specified
				 * declaration type for classes and interfaces
				 */
				public boolean visit(TypeDeclaration node) {
					ITypeBinding typeBind = node.resolveBinding();
					String nodeName = typeBind.getQualifiedName();
					if(typeMap.containsKey(nodeName)) {
						
						ArrayList<Integer> currentArray = typeMap.get(nodeName); 
						int currentValue = currentArray.get(0);
						currentArray.set(0, currentValue+1);
						typeMap.replace(nodeName, currentArray);
					}
					else {
						
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(1);
						intArray.add(0);
						typeMap.put(nodeName,intArray);
					}
					return true;
				}
				
				/**
				 * Used for Enum declarations
				 * Gets the name of the node and compares to
				 * the type to look for, if the same
				 * increases dCount by 1
				 * @param node of the type EnumDeclaration
				 * @return true to visit the children nodes
				 */
				public boolean visit(EnumDeclaration node) {
					ITypeBinding typeBind = node.resolveBinding();
					
					String typeNode = typeBind.getQualifiedName();
					if(typeMap.containsKey(typeNode)) {
						ArrayList<Integer> currentArray = typeMap.get(typeNode); //declerations
						int currentValue = currentArray.get(0);
						currentArray.set(0, currentValue+1);
						
						typeMap.replace(typeNode, currentArray);
					}
					else {
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(1);
						intArray.add(0);
						typeMap.put(typeNode,intArray);
					}
					
					return true;
				}
				
				/**
				 * Used for Annotation declarations
				 * Gets the name of the node and compares to
				 * the type to look for, if the same
				 * increases dCount by 1
				 * @param node of the type AnnotationTypeDeclaration
				 * @return true to visit the children nodes
				 */
				public boolean visit(AnnotationTypeDeclaration node) {
					ITypeBinding typeBind = node.resolveBinding();
					String typeNode = typeBind.getQualifiedName();				
					if(typeMap.containsKey(typeNode)) {
						ArrayList<Integer> currentArray = typeMap.get(typeNode); //declerations
						int currentValue = currentArray.get(0);
						currentArray.set(0, currentValue+1);
						
						typeMap.replace(typeNode, currentArray);
					}
					else{
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(1);
						intArray.add(0);
						typeMap.put(typeNode,intArray);
						}
					return true;
				}
				
			
				
				/**
				 * @param node of type VariableDeclarationFragment
				 * Resolves the bindings so we can acquire the node's full name
				 * @return false, don't visit children
				 */
				public boolean visit(VariableDeclarationFragment node) {
					IVariableBinding rNode = node.resolveBinding();
					String typeNode = rNode.getType().getQualifiedName();
					if(typeMap.containsKey(typeNode)) {
						ArrayList<Integer> currentArray = typeMap.get(typeNode); //rcount
						int currentValue = currentArray.get(1);
						currentArray.set(1, currentValue+1);
						
						typeMap.replace(typeNode, currentArray);
					}
					else { 
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(0);
						intArray.add(1);
						typeMap.put(typeNode,intArray);
					}
					return false;
				}
				
				/**
				 * node of type SimpleType
				 */
				public boolean visit(SimpleType node) {
					ITypeBinding  typeBind = node.resolveBinding();
					String typeNode = typeBind.getQualifiedName();
					if(typeMap.containsKey(typeNode)) {
						ArrayList<Integer> currentArray = typeMap.get(typeNode); //rcount
						int currentValue = currentArray.get(1);
						currentArray.set(1, currentValue+1);
						
						typeMap.replace(typeNode, currentArray);
					}
					else { 
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(0);
						intArray.add(1);
						typeMap.put(typeNode,intArray);
					}
					return true;
				}
				
				
				/**
				 * Visits the import declarations, adds to the counter
				 */
				public boolean visit(ImportDeclaration node) {
					IBinding typeBind = node.resolveBinding();
					String typeNode = typeBind.getName();
					if(typeMap.containsKey(typeNode)) {
						ArrayList<Integer> currentArray = typeMap.get(typeNode); //rcount
						int currentValue = currentArray.get(1);
						currentArray.set(1, currentValue+1);
						
						typeMap.replace(typeNode, currentArray);
					}
					else { 
						ArrayList<Integer> intArray = new ArrayList<Integer>();
						intArray.add(0);
						intArray.add(1);
						typeMap.put(typeNode,intArray);
					}
					return true;
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
	
	/**
	 * File recursion is handled here, folders within folders are checked for .java files
	 * the files are added to an array list which is returned later to be parsed and have their
	 * declarations and references to be counted
	 * @param dir
	 */
	public static void displayDirectoryContents(File dir) {
		
		Set<String> hs = new HashSet<>();
		
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				displayDirectoryContents(file); // recursive call if the file is a directory
			} else {
				 files = dir.listFiles(new FilenameFilter(){
						public boolean accept(File dir, String name){
							if(name.toLowerCase().endsWith(".java"))
								return name.toLowerCase().endsWith("java");
							else
								return name.toLowerCase().endsWith("jar");
						} 		
				 });
				 
				 for(int i = 0; i < files.length; i++){
						String filePath = files[i].getPath();
				
						fileList.add(filePath);
				 }
			   }
			 }
	
		hs.addAll(fileList);
		fileList.clear();
		fileList.addAll(hs);
	}
	
	
	
	/**
	 * source from https://stackoverflow.com/questions/1529611/how-to-write-a-java-program-which-can-extract-a-jar-file-and-store-its-data-in-s EDIT CODE
	 * @return
	 * @throws IOException
	 */
	public void extractJarFiles() throws IOException {
		 java.util.jar.JarFile jarfile = new java.util.jar.JarFile(new java.io.File(this.folderPath)); //jar file path(here sqljdbc4.jar)
		    java.util.Enumeration<java.util.jar.JarEntry> enu= jarfile.entries();
		    while(enu.hasMoreElements())
		    {
		    		File file = new File(this.folderPath);
		    		String result = file.getName().split(".jar")[0];
		    		System.out.println(result);
		    		File dir = new File(result);
		        dir.mkdir();
		  
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
			
	}
}

