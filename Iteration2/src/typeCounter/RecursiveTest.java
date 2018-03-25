//ver2
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
		this.destdir = (this.folderPath.split(".jar")[0]);

	}
	
	public void countInJarOrDirectory() throws IOException {
		if (this.folderPath.endsWith(".jar")) {
	 		String result = this.folderPath.split(".jar")[0];
			System.out.println(result);
			this.extractJarFiles();
		;
			this.displayDirectoryContents(new File(result));
			this.findDeclarations();
			this.printResults();
			this.deleteDirectory(new File(this.destdir));
			
		}
		else {
		this.displayDirectoryContents(currentDir);
		this.findDeclarations();
		this.printResults();
		}
}
	
	private void deleteDirectory(File file ) {
		
		File[] contents = file.listFiles();
		if(contents != null) {
			for (File f: contents) {
				deleteDirectory(f);
			}
		}
		file.delete();
	}

	public void printResults(){
		for (String key: typeMap.keySet()) {
			ArrayList<Integer> currentArray = typeMap.get(key);
			System.out.println(key+ " Declarations found: " + currentArray.get(0)+"; References found: " + currentArray.get(1)+".");
		}
	}
	protected void findDeclarations(){
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
			
			System.out.println(Arrays.toString(files));
		hs.addAll(fileList);
		fileList.clear();
		fileList.addAll(hs);
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		System.out.println(Arrays.deepToString(fileList.toArray()));	
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

