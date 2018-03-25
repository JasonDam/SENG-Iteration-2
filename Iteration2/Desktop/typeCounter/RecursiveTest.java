//ver2
package typeCounter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarFile;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

public class RecursiveTest {
	
	String folderPath;
	static ArrayList<String> fileList = new ArrayList<String>();
	File currentDir;
	JarFile currentJar;
	
	public RecursiveTest(String path) throws IOException {
		this.folderPath = path;
		this.currentDir = new File(path);
		if (path.endsWith(".jar")) {
	//	this.currentJar = new JarFile(path);
	
	//	System.out.print("JAR!");
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
					// delete this later
					System.out.println(nodeString);
				
					return true; // true -- explore subnodes
				}				});
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
	
	public void countInJarOrDirectory() {
		//if (this.folderPath.toLowerCase().endsWith(".jar"))
			// just add this one file to fileList, so it can be parsed(just one file if a Jar of interest)
		//	System.out.print("Read Jar");
		
	//	else
			this.displayDirectoryContents(currentDir);
			this.findDeclarations();
	}
	
	
	public static void displayDirectoryContents(File dir) {
		// USE set to eliminate element doubles that might come from a loop
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
}

