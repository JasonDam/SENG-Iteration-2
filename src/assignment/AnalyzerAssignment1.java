package assignment;

/**
 * Seng 300 Group 11 Assignment 1
 * 
 * @author Sidney Shane Dizon, Sunah Kim, Kristopher Tang
 * 
 * This is a java application analysis tool that will find the number of occurrences of the declarations of and references to a Java type (as a 
 * fully qualified name, like java.lang.String), within a specified directory.
 *
 *	Input:
 * This takes in pathname to indicate a directory of interest.
 * Takes a string to indicate a fully qualified name of a Java type.
 *	
 *	Process:
 * The java application counts the number of declarations of that type within that directory and counts that number of references to each occurrence of that 
 * type within that directory.
 * 
 *	Output:
 * The output is a string written to the console stating the name of the type, the count of declarations, and the count of references, such as:
 * 
 * 		<type>. Declarations found: <count>; references found: <count>.
 * 
 * where the <type> and two <count>s are to be replaced with the relevant values.
 * 
 * Link Sources:
 * 	https://stackoverflow.com/questions/5603966/how-to-make-filefilter-in-java
 * 	https://www.tutorialspoint.com/java/java_file_class.htm
 * 	https://www.webucator.com/how-to/how-display-the-contents-of-directory-java.cfm
 * 	https://www.programcreek.com/2011/11/use-jdt-astparser-to-parse-java-file/
 */

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


public class AnalyzerAssignment1 {
	public static int referenceCount = 0;
	public static int declarationCount = 0;

	public static void main(String[] args) throws IOException, FileNotFoundException {
		
		//Check if the command line arguments include a pathname and java type
		if (args.length > 1) {
			//Get the pathname from the user
			String pathname = args[0];
			//Get the Java type from the user
			String javaType = args[1];
			
			//Count the number oof declarations and references
			int declarationCount = 0;
			int referenceCount = 0;
			
			//Open the directory specified by the user and get contents
			File[] directoryContents = accessContentsOfADirectory(pathname);
			
			//Check if we have a valid directory; if we have null then terminate the program
			if (directoryContents == null) {
				System.exit(0);
			} //iterate on the contents of the directory
			else {
				String filePath = null;
				// list for sourcepathEntries for setEnvironment()
				String[] sourcePath = {pathname};
				for (File object : directoryContents) {
					filePath = object.getAbsolutePath();
					if (object.isFile()) {
						//Parse for the contents of the file
						parse(readFileToString(filePath), sourcePath, object.getName(), javaType);
					}
				}
				

				System.out.println(javaType + " Declaration: " + AnalyzerAssignment1.declarationCount + " Reference: " + AnalyzerAssignment1.referenceCount);
			}
		
		}
		else {
			System.out.println("There are no pathname or java type input on the command line arguments. Retry again.");
		}
	}
	
	/**
	 * This opens and gets the contents of the directory that has .java extension
	 * @param pathname - the pathname of the directory
	 * @return - the array of .java file extension located inside the directory
	 * @throws IOException - throws IOException if the filePath doesn't exist
	 */
	public static File[] accessContentsOfADirectory(String pathname) throws IOException {
		final String extension = ".java";
		File directory = new File(pathname);
		File[] contentsOfDirectory = null;

		//check if the Directory exists
		if (directory.canRead()) {
			//Only get the files that have a .java extension
			contentsOfDirectory = directory.listFiles((File path) -> path.getName().endsWith(extension));	
		} else {
			//Otherwise terminate the program and tell the user.
			System.out.println("The pathname specified is not valid. Try running the code again and enter a valid one.");
		}
		return contentsOfDirectory;
	}
	
	
	/**
	 * This method read file content into a string
	 * @param filePath - the path to the file to be read
	 * @return - the contents of the file read as String
	 * @throws IOException - if the file doesn't exist then this will throw an exception
	 */
	public static String readFileToString(String filePath) throws IOException, FileNotFoundException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		
		char[] buffer = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buffer)) != -1) {
			String readData = String.valueOf(buffer, 0, numRead);
			fileData.append(readData);
			buffer = new char[1024];
		}
		
		reader.close();
		return fileData.toString();
	}
	
	
	 /**
	  *  This method parses the contents of the .java file for declarations
	  * @param fileContent - contents of the .java as String
	  * @param sourcePath - sourcePath to be used for setting environment
	  * @param fileName - name of the .java file
	  * @param javaType - java Type to look for 
	  */
	public static void parse(String fileContent, String[] sourcePath, String fileName, String javaType) {
		
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(fileContent.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		
		String unitName = fileName;
		parser.setUnitName(unitName);
		
		parser.setEnvironment(null, sourcePath, null, false);
		
		 Map options = JavaCore.getOptions();
		 JavaCore.setComplianceOptions(JavaCore.VERSION_1_5, options);
		 parser.setCompilerOptions(options);
		
		final CompilationUnit compUnit = (CompilationUnit) parser.createAST(null);
		// for visiting nodes
		compUnit.accept(new ASTVisitor() {
			
			// finds when class or interface
			public boolean visit(TypeDeclaration node) {
				SimpleName name = node.getName();
				
				//bindings to get the qualified name
				ITypeBinding bind = node.resolveBinding();
				if (bind.getQualifiedName().equalsIgnoreCase(javaType)) {
					declarationCount++;
				}
				return true;
			}
			// finds when enum type declared
			public boolean visit(EnumDeclaration node) {
				SimpleName name = node.getName();
				
				//bindings to get the qualified name
				ITypeBinding bind = node.resolveBinding();
				if (bind.getQualifiedName().equalsIgnoreCase(javaType)) {
					declarationCount++;
				}
				return true;
			}
			
			// finds when annotation type declared
			public boolean visit(AnnotationTypeDeclaration node) {
				SimpleName name = node.getName();
			
				//binding issues, gets a NullPointerException
				/*ITypeBinding bind = node.resolveTypeBinding();
				if (bind.getQualifiedName().equalsIgnoreCase(javaType)) {
					declarationCount++;
				}
				*/
				
				if (name.toString().equalsIgnoreCase(javaType)) {
					declarationCount++;
				}
				return true;
			}
			
			// finds reference of annotation type
			public boolean visit(MarkerAnnotation node) {
				Name name = node.getTypeName();
	
				//binding issues, gets a NullPointerException
				/*ITypeBinding bind = node.resolveTypeBinding();
				if (bind.getQualifiedName().equalsIgnoreCase(javaType)) {
					referenceCount++;
				}
				*/
				
				if (name.toString().equalsIgnoreCase(javaType)) {
					referenceCount++;
				}
				return true;
			}
			
			// finds reference of class: eg. A a = new A(); would be 2 references of A
			public boolean visit(SimpleType node) {
				Name name = node.getName();
			
				//bindings to get the qualified name
				ITypeBinding bind = node.resolveBinding();
				if (bind.getQualifiedName().equalsIgnoreCase(javaType)) {
					referenceCount++;
					System.out.println(javaType + " Declaration: " + declarationCount + " Reference: " + referenceCount);
				}
				return true; 
			}
			
			// finds reference of annotation type
			public boolean visit(SingleMemberAnnotation node) {
				Name name = node.getTypeName();

				//binding issues, gets a NullPointerException
				/*ITypeBinding bind = node.resolveTypeBinding();
				if (bind.getQualifiedName().equalsIgnoreCase(javaType)) {
					referenceCount++;
				}
				*/
				
				if (name.toString().equalsIgnoreCase(javaType)) {
					referenceCount++;
				}
				return true;
			}
		
			// finds reference of annotation type
			public boolean visit(NormalAnnotation node) {
				Name name = node.getTypeName();
				
				//binding issues, gets a NullPointerException
				ITypeBinding bind = node.resolveTypeBinding();
				if (bind.getQualifiedName().equalsIgnoreCase(javaType)) {
					referenceCount++;
				}
				return true;
			}
			
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name = node.getName();
	
				//binding to get qualified name
				ITypeBinding bind = name.resolveTypeBinding();
				String qualifiedName = bind.getQualifiedName();
				//check if it is a primitive type
				if (qualifiedName.equalsIgnoreCase("int") || qualifiedName.equalsIgnoreCase("short") || qualifiedName.equalsIgnoreCase("byte") || 
						qualifiedName.equalsIgnoreCase("boolean") || qualifiedName.equalsIgnoreCase("long") || qualifiedName.equalsIgnoreCase("char") || 
						qualifiedName.equalsIgnoreCase("float") || qualifiedName.equalsIgnoreCase("double")) {
					if (qualifiedName.equalsIgnoreCase(javaType)) {
						referenceCount++;
					}
				}
				
				return true;
			}
			
		});	
		
	}
}
