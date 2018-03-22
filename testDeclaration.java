package test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class testDeclaration {

	/* The getFile method takes in a String parameter and declares a newBufferedReader and 
	 * StringBuilder to read .java files
	 */
	public static String getFile(String filePath) throws FileNotFoundException, IOException{
		BufferedReader read = new BufferedReader(new FileReader(filePath));
		StringBuilder build = new StringBuilder();
		String line = read.readLine();
		while (line != null){
			build.append(line);
			build.append(System.lineSeparator());
			line = read.readLine();
		}
		
		return build.toString();
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		//declare a specific directory and puts the names of files in a list
		File filepath = new File("/Users/parkerwong/Documents/workspace/TestAstParser/src/test");
		File[] allFiles = filepath.listFiles();{
	    for (int i = 0; i < allFiles.length; i++) {
		     if (allFiles[i].isFile()) {
		    	 String fileToCheck = allFiles[i].getName();
		    	 String isJava = fileToCheck.substring(fileToCheck.lastIndexOf("."));
		    	 if(isJava.equals(".java")){
		    		 System.out.println("File " + allFiles[i].getName());
		    	 }
		    	 } else if (allFiles[i].isDirectory()) {
		    		 System.out.println("No .java files found");
		    	 }
		    }
	}
		//Parses the file 
		List<String> declarations = new ArrayList();
		for (int i = 0; i < allFiles.length; i++){
		String filePath = allFiles[i].getName();
		System.out.println(filePath);
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		char[] fileContent = getFile(filePath).toCharArray();
		parser.setSource(fileContent);
		CompilationUnit unit = (CompilationUnit) parser.createAST(null);
						
		unit.accept(new ASTVisitor(){
		
			//Gets the name of each variable and adds it to a list
			public boolean visit(VariableDeclarationStatement node){
				Type type = node.getType();
				//int lineNumber = unit.getLineNumber(name.getStartPosition());
				declarations.add(type.toString());
				System.out.println(declarations);
				//System.out.println("Line number: " + lineNumber);
				return false;
			}
		
		});
		//Puts the declarations of every variable into a Hashset and counts the frequency of each one
		Set<String> uniqueSet = new HashSet<String>(declarations);
		for (String type : uniqueSet) {
			System.out.println(type + " Declarations found: " + Collections.frequency(declarations, type));	
	
		}
	}}
}
