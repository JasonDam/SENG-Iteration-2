
// @autour : Masroor Hussain Syed
//

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class MainTest {
	
	public String BASEDIR = "C:\\Users\\jicka_000\\eclipse-workspace\\SENG300_group_a1\\src\\";
	
	/**
	 * Checks that parse method parses string and counts reference to ints.
	 */
	@Test
	public void testParseForIntTypeReference() {
		String type = "int";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nint a;\nint b;}", testType, type);
		assertEquals(2, testType.references);
	}
	
	/**
	 * Checks that parse method parses string and counts declarations of ints.
	 */
	@Test
	public void testParseForIntTypeDeclarations() {
		String type = "int";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nint a;\nint b;}", testType, type);
		assertEquals(0, testType.declarations);
	}
	
	/**
	 * Checks that parse method parses string and counts references of boolean.
	 */
	@Test
	public void testParseForBooleanTypeReference() {
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\nint b;}", testType, type);
		assertEquals(1, testType.references);
		
	}
	
	/**
	 * Checks that parse method parses string and counts declarations of boolean.
	 */
	@Test
	public void testParseForBooleanTypeDeclarations() {
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\nint b;}", testType, type);
		assertEquals(0, testType.declarations);
		
	}

	/**
	 * Checks that parse method parses string and counts references of string.
	 */
	@Test
	public void testParseForStringTypeReference() {
		String type = "String";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\n String b;}", testType, type);
		assertEquals(1, testType.references);
	}
	
	/**
	 * Checks that parse method parses string and counts references of class.
	 */
	@Test
	public void testParseForClassTypeReference() {
		String type = "B";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\nprivate class B {}\nB b = new B();b = b;}", testType, type);
		assertEquals(2, testType.references);
	}
	
	/**
	 * Checks that parse method parses string and counts declaration of class.
	 */
	@Test
	public void testParseForClassTypeDeclarations() {
		String type = "A";
		Types testType = new Types(type);
		testType = Main.parse("public class A {\nboolean a;\nprivate class B {}\nB b = new B();b = b;}", testType, type);
		assertEquals(1, testType.declarations);
	}
	
	/**
	 * Checks that parseFileInDir method parses files in the directory
	 * and counts references of a type.
	 */
	@Test
	public void testParseFilesInDirForDirWithJavaFilesForReference() throws IOException {
		String dirPath = BASEDIR + "testFiles";
		String type = "int";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
		assertEquals(4, testType.references);

	}
	
	/**
	 * Checks that parseFileInDir method parses files in the directory
	 * and counts declaration of a type.
	 */
	@Test
	public void testParseFilesInDirForDirWithJavaFilesForDeclarations() throws IOException {
		String dirPath = BASEDIR + "testFiles";
		String type = "int";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
		assertEquals(0, testType.declarations);
	}
	
	/**
	 * Checks that parseFileInDir method parses file and 
	 * counts reference of a type and returns {@link NullPointerException} when
	 * the folder is empty
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testParseFilesInDirForDirNoFilesForReference() throws IOException {
		String dirPath = BASEDIR + "empty";
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
		
	}
	
	/**
	 * Checks that parseFileInDir method parses file and 
	 * counts declaration of a type and returns {@link NullPointerException} when
	 * the folder is empty
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testParseFilesInDirForDirNoFilesForDeclarations() throws IOException {
		String dirPath = BASEDIR + "empty";
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);		
	}
	
	/**
	 * Checks that parseFileInDir returns {@link NullPointerException} when
	 * the folder is invalid
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testParseFilesInDirForInvalidFolderForReference() throws IOException {
		String dirPath = BASEDIR + "Invalid";
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
	}
	
	/**
	 * Checks that parseFileInDir returns {@link NullPointerException} when
	 * the folder is invalid
	 * @throws IOException
	 */
	@Test(expected = NullPointerException.class)
	public void testParseFilesInDirForInvalidFolderForDeclarations() throws IOException {
		String dirPath = BASEDIR + "Invalid";
		String type = "boolean";
		Types testType = new Types(type);
		testType = Main.ParseFilesInDir(dirPath, testType,type);
	}
	
	/**
	 *  Checks readFiletoString returns string representation of a valid file
	 */
	@Test
	public void testreadFileToStringForValidFile() throws IOException {
		String filePath = BASEDIR + "testFiles\\helloTest.txt";
		String actual	= Main.readFileToString(filePath);
		String expected = "hello its me!";
		
		assertEquals(expected, actual);
	}
	
	/**
	 *  Checks readFiletoString returns {@link NullPointerException} when
	 *  called with an invalid file
	 */
	@Test(expected = FileNotFoundException.class)
	public void testreadFileToStringForInvalidFile() throws IOException {
		String filePath = BASEDIR + "testFiles\\invalid.txt";
		String actual	= Main.readFileToString(filePath);
		String expected = "hello its me!";
	}
	
}
