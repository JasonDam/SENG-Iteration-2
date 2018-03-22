import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
 
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Main {
	
	public static String BASEDIR = "C:\\Users\\Nuro\\Documents\\GitHub\\SENG300_group_a1\\src\\";
	public static String dirPath;

	public static void main(String[] args) {
		
		String typeArg = args[1];
		Types aType = new Types(typeArg);
		
		try {
			dirPath = BASEDIR + args[0];
			aType = ParseFilesInDir(dirPath,aType,typeArg);
			
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Error with filename");
		} catch (IOException ioe) {
			System.out.println("Error with IO");
		}
		
		System.out.println("Type: " + aType.name + 
					", Declarations: " + aType.declarations + 
					", References: " + aType.references);
		
	}
	
	//use ASTParse to parse string
	public static Types parse(String strFromFile, final Types aType, String typeArg) {
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setSource(strFromFile.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() {
						
			// Class declarations
			public boolean visit(TypeDeclaration node) {
				String name = node.getName().toString();
				setDeclaration(aType,name);
				return true;
			}
			
			// Class declarations
			public boolean visit(SimpleType node) {
				String name = node.toString();
				setReference(aType,name);
				return true;
			}
			
			// Primitive types (byte,short,char,int,long,float,double,boolean,void)
			public boolean visit(PrimitiveType node) {
				String name = node.getPrimitiveTypeCode().toString();
				setReference(aType, name);
				return true;
			}
			
			// Array types **causes error**
//			public boolean visit(ArrayType node) {
//				String name = node.toString();
//				setReference(aType, name);
//				return true;
//			}
			
			// Variable declarations
			public boolean visit(NullLiteral node) {
				String name = node.toString();
				setReference(aType,name);
				return true;
			}
			
			// Variable declarations
			public boolean visit(StringLiteral node) {
				String name = "java.lang.String";
				setReference(aType,name);
				return true;
			}
			
		});
		
		return aType;
	}
	
	//read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
 
		return fileData.toString();	
	}
 
	//loop directory to get file list
	public static Types ParseFilesInDir(String dirPath, Types aTypes, String typeArg) throws IOException{ 
		File root = new File(dirPath);
		File[] files = root.listFiles();
		String filePath = null;
 
		 for (File f : files ) {
			 filePath = f.getAbsolutePath();
			 if(f.isFile()){
				 // Get new types from each file and add to allTypes list
				 aTypes = parse(readFileToString(filePath),aTypes,typeArg);
//				 for (Types aType : newTypes) {
//					 allTypes.add(aType);
//				 }
			 }
		 }
		 
		 return aTypes;
	}
	
	public static void setDeclaration(Types aType, String name) {
		// If type already exists add to declaration count
		if (aType.name.matches(name)) {
			aType.declarations += 1;
		}
	}
	
	public static void setReference(Types aType, String name) {
		if (aType.name.matches(name)) {
			aType.references += 1;
		}
	}
}
