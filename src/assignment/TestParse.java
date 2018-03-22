package assignment;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestParse {
	private static String BASEDIR = "\\some-path\\"; 
	String[] sourcePath = {BASEDIR};
	
	File file = new File(BASEDIR + File.separator + "dummy.java");
	
	
	@Before
	public void setup() throws IOException {
		FileWriter writer = new FileWriter(file);
		writer.write("public class Dummy {" + System.getProperty("line.separator") + "" + 
				"public int a;" + System.getProperty("line.separator") + "" + 
				"public void method(){" + System.getProperty("line.separator") + "" + 
				"String aString = \"string\";" + System.getProperty("line.separator") + "" + 
				"}" + System.getProperty("line.separator") + "" + 
				"}" + System.getProperty("line.separator") + "" + 
				"@interface Annot {}" + System.getProperty("line.separator") + "" + 
				"@Annot()" + System.getProperty("line.separator") + "" + 
				"@Annot" + System.getProperty("line.separator") + "" + 
				"@Annot(false)" + System.getProperty("line.separator") + "" + 
				"public interface Interface1 {}" + System.getProperty("line.separator") + "" + 
				"public enum MyEnum {" + System.getProperty("line.separator") + "" + 
				"One one;" + System.getProperty("line.separator") + "" + 
				"public MyEnum(One one) {" + System.getProperty("line.separator") + "" + 
				"this.one = one;" + System.getProperty("line.separator") + "" + 
				"}" + System.getProperty("line.separator") + "" + 
				"MyEnum two = new MyEnum(One.HELLO);" + System.getProperty("line.separator") + "" + 
				"public class Other {" + System.getProperty("line.separator") + "" + 
				"public void amethod(){" + System.getProperty("line.separator") + "" + 
				"Interface1 i;}" + System.getProperty("line.separator") + "" + 
				"}");
		writer.close();
	}
	
	@After
	public void takedown() {
		AnalyzerAssignment1.referenceCount = 0;
		AnalyzerAssignment1.declarationCount = 0;
	}

	@Test
	public void testInt() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "int");
		assertEquals(1, AnalyzerAssignment1.referenceCount);
	}

	@Test
	public void testClassDeclaration() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "Dummy");
		assertEquals(1, AnalyzerAssignment1.declarationCount);
	}
	
	@Test 
	public void testClassReference() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "String");
		assertEquals(1, AnalyzerAssignment1.referenceCount);
	}
	
	@Test
	public void testAnnotationDeclaration() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "Annot");
		assertEquals(1, AnalyzerAssignment1.declarationCount);
	}
	
	@Test
	public void testAnnotationReference() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "Annot");
		assertEquals(3, AnalyzerAssignment1.referenceCount);
	}
	
	@Test
	public void testEnumDeclaration() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "MyEnum");
		assertEquals(1, AnalyzerAssignment1.declarationCount);
	}
	
	@Test
	public void testEnumReference() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "MyEnum");
		assertEquals(2, AnalyzerAssignment1.referenceCount);
	}
	
	@Test
	public void testInterfaceDeclaration() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "Interface1");
		assertEquals(1, AnalyzerAssignment1.declarationCount);
	}
	
	@Test
	public void testInterfaceReference() throws FileNotFoundException, IOException {
		String filecontent = AnalyzerAssignment1.readFileToString(BASEDIR + File.separator + "dummy.java");
		AnalyzerAssignment1.parse(filecontent, sourcePath, "dummy.java", "Interface1");
		assertEquals(1, AnalyzerAssignment1.referenceCount);
	}
	
}
