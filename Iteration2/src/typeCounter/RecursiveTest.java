//ver1 16:37 mar 21
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

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

public class RecursiveTest {
	
	String folderPath;
	static ArrayList<String> fileList = new ArrayList<String>();

	
	public RecursiveTest(String path) {
		this.folderPath = path;
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
					
					// counter for declaration type of interes
					

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

