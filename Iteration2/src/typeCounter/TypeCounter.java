package typeCounter;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.*;

/**
 * Class for counting declarations and references
 * for a specified type declaration
 * @author Elvin
 *
 */
public class TypeCounter {
	

	
	private String fullDeclarationType;
	private String folderPath;
	private String declarationType;
	private ArrayList<String> fileList;
	
	private int declarationCounter = 0;
	private int referenceAndDeclarationCounter = 0;
	
	/**
	 * Constructor sets the folderPath, type, and 
	 * file list to use for finding and printing
	 * the declarations and references
	 * @param folderPath
	 * @param type
	 */
	public TypeCounter(String folderPath, String type){
		this.fullDeclarationType = type;
		this.folderPath = findFolder(folderPath, type);
		this.declarationType = abridgeType(type);
		this.fileList = changeFileList(this.folderPath);
		
		
	}

	/**
	 * Calls findDeclarations and findReferences followed
	 * by printDeclarationsAndReferences
	 */
	public void findAndPrintDeclarationsAndReferences(){
		this.findDeclarations();
		this.findReferences();
		this.printDeclarationsAndReferences();
	}
	
	/**
	 * Prints the declaration type requested, found
	 * declarations, and found references 
	 */
	public void printDeclarationsAndReferences(){
		System.out.print(getFullDeclarationType() + ". ");
		System.out.print("Declarations: " + getDeclarations() + ";");
		System.out.println("references: " + getReferences() + ".");
	}

	

	/**
	 * Searches through the file list to find instances of
	 * declaration to the declaration type specified
	 */
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
					
					// counter for declaration type of interest
					if (nodeString.equals(declarationType))
						declarationCounter++;	 
					
					
					
					return true; // true -- explore subnodes
				}

				/**
				 * finds Type Declarations and adds to
				 * declarationCounter if
				 * the node string matches the specified
				 * declaration type for enumerations
				 */ 
				 public boolean visit(EnumDeclaration node){
					String nodeString = node.getName().getFullyQualifiedName();

					
					// counter for declaration type of interest
					if (nodeString.equals(declarationType))
						declarationCounter++;	 
					
					
					return true; // true -- explore subnodes
				 }
				 

					/**
					 * finds Type Declarations and adds to
					 * declarationCounter if
					 * the node string matches the specified
					 * declaration type for annotations
					 */
				 public boolean visit(AnnotationTypeDeclaration node){
					String nodeString = node.getName().getFullyQualifiedName();
					
					// counter for declaration type of interest
					if (nodeString.equals(declarationType))
						declarationCounter++;	 
					
					
					return true; // true -- explore subnodes
				 }
			});
		}
	}
	
	/**
	 * Searches through the file list to find instances of
	 * references to the declaration type specified
	 */
	protected void findReferences(){
		for(String file:fileList){
			
			// creating ASTs for every file in the file list
			this.parse(file).accept(new ASTVisitor(){

				/**
				 * finds Simple Names and adds to
				 * referenceAndDeclarationCounter if
				 * the node string matches the specified
				 * declaration type
				 */
				public boolean visit(SimpleName node){
					String nodeString = node.getFullyQualifiedName();
					if(declarationType.equals(nodeString)){
						referenceAndDeclarationCounter++;
					}
				
					return true;
				}

				/**
				 * visits primitive nodes
				 */
				public boolean visit(PrimitiveType node){
					String nodeString = node.getPrimitiveTypeCode().toString();
					if(declarationType.equals(nodeString)){
						referenceAndDeclarationCounter++;
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
	 * updates the list of files in the specifies folder path
	 * @param folderPath
	 * @return fileList -- an array list of the names of the file
	 */
	private ArrayList<String> changeFileList(String folderPath){
		ArrayList<String> fileList = new ArrayList<String>();
		File dir = new File(folderPath);
		
		
		// Filter files that end with .java
		final File[] files = dir.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.toLowerCase().endsWith(".java");
			}
		});
		

		// files.length if file is null will result in an exception
		if(files == null) return fileList;
		
	
		
		// adds to fileList the files in the specified path
		for(int i = 0; i < files.length; i++){
			String filePath = files[i].getPath();
		
			fileList.add(filePath);
		}
		return fileList;
	}
	
	/**
	 * Changes the full type including packages to 
	 * the type without the packages
	 * @param fullType
	 * @return the abridged type
	 */
	private String abridgeType(String fullType){
		final char[] fullTypeInChar = fullType.toCharArray();
		String result = fullType;
		
		// find the last .
		for(int i = fullType.length() - 1; i >= 0; i--){
			if(fullTypeInChar[i] == (".".toCharArray()[0])){
				
				// characters after the . is the abridged type
				result = fullType.substring(i+1);
				break;
			}
		}
		return result;
	}
	
	/**
	 * Finds the full path inside the packages
	 * @param path
	 * @param type
	 * @return the full path
	 */
	private String findFolder(String path, String type) {
		final char[] typeInChar = type.toCharArray();
		ArrayList<String> toPath = new ArrayList<String>();
		
		int previousI = 0;
		for(int i = 0; i < type.length(); i++){
			// find the . in the type
			if(typeInChar[i] == (".".toCharArray()[0])){
				// append characters before the . to path
				toPath.add("\\" + type.substring(previousI, i));
				
				// ensures that the substring is in between the "."s
				previousI = i + 1;
			}
		}
		
		// create new path
		for(String folders : toPath)
			path = path + folders;
		
		
		return path;
	}
	
	//Getters and setters\\

	public String getFolderPath(){
		return this.folderPath;
	}
	
	public String getDeclarationType(){
		return this.declarationType;
	}
	
	public String getFullDeclarationType(){
		return this.fullDeclarationType;
	}
	
	public ArrayList<String> getFileList(){
		ArrayList<String> result = new ArrayList<String>();
		for(String file: fileList)
			result.add(file);
		
		return result;
	}
	
	public int getDeclarations(){
		return declarationCounter;
	}
	
	public int getReferences(){
		return (referenceAndDeclarationCounter - declarationCounter);
	}
	
	public int getReferencesAndDeclarations(){
		return referenceAndDeclarationCounter;
	}
}
