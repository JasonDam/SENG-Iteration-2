package AnalysisToolProject;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class AnalysisTool {
	
	static String pathname;
	public static void main(String[] args) {
		  for(int i = 0; i < args.length; i++) {
			  
			  pathname = args[i];
		  }
		  System.out.println("Usage: java Driver <pathname>" + pathname);
		  	  
		
		AnalysisTool testy = new AnalysisTool();
		testy.test1();

	}
	public void test1(){
		int count = 0;
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setSource(fileToChar("input.java"));
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		
		
		final CompilationUnit cu = (CompilationUnit)parser.createAST(null);
	
		cu.accept(new ASTVisitor() {
		 
			Set names = new HashSet();
	
			public boolean visit(VariableDeclarationFragment node) {
				
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());
				System.out.println("Declaration of '" + name + "' at line"
						+ cu.getLineNumber(name.getStartPosition()));
				System.out.println(name.getFullyQualifiedName());
				//System.out.println(name.isDeclaration());
				//System.out.println(node.getNodeType());
				/*if (node.getNodeType() == ASTNode.PRIMITIVE_TYPE)
				{
					System.out.println("Its primitive");
				}*/
				switch (node.getNodeType()) {
				//case ASTNode.BOOLEAN_LITERAL:
					
				case ASTNode.CHARACTER_LITERAL:
				//case ASTNode.NULL_LITERAL:
				//case ASTNode.NUMBER_LITERAL:
				{
					System.out.println("it is char");
					//name.getFullyQualifiedName();
					return true;
				}
					

				default:
					return false;
			}
				
				
				//return false; // do not continue 
			}
	
			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
					System.out.println("Usage of '" + node + "' at line "
							+ cu.getLineNumber(node.getStartPosition()));
				}
				return true;
			}
		});
	}
	
	public char[] fileToChar(String file) {
	    // char array to store the file contents in
	    char[] contents = null;
	    try {
	        BufferedReader br = new BufferedReader(new FileReader(file));
	            StringBuffer sb = new StringBuffer();
	        String line = "";
	        while((line = br.readLine()) != null) {
	            // append the content and the lost new line.
	            sb.append(line + "\n");
	        }
	        contents = new char[sb.length()];
	        sb.getChars(0, sb.length()-1, contents, 0);
	 
	        assert(contents.length > 0);
	    }
	    catch(IOException e) {
	            System.out.println(e.getMessage());
	    }
	 
	    return contents;
	}

}
