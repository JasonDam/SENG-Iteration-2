package typeCounter;
//SENG Iteration 2
//Made by: Evan, Jason, Logan, Tony, Kris
//Testing DeclarationsAndReferencesCounter
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSuite {
	/**
	 * Create a new string[] to pass as in line command
	 */
	private static final String[] arg = new String[1]; 
	
	/**
	 * to be set up before every test, setting up the file location (could be jar or directory)
	 */
	@Before
	public void initialization() {
		arg[0] = "C:\\Users\\Jason\\Desktop\\testJAVA.jar";
	}
	
	/**
	 * First test to see if our program can deal with a null argument by setting the in-line argument as null
	 * @throws IOException
	 */
	@Test (expected=NullPointerException.class)
	public void testArgNull() throws IOException {
		arg[0] = null;
		Main.main(arg);
	}
	
	/**
	 * Thest to check if program handles a non-existant jar by setting the in-line command to non-existant file
	 * @throws IOException
	 */
	@Test (expected = FileNotFoundException.class)
	public void testFileDoesntExist() throws IOException {
		arg[0] = "idonotexist.jar";
		Main.main(arg);
	}
	
	/**
	 * Test retrieving primitive types by first creating a new DeclarationsAndReferencesCounter object, creating a map which grabs the map from
	 * our counter program, after it has parsed all files. Afterwards, we get all the values with a key, using int to test this case, and store it into an object
	 * We then create an array list, add values which the program should get for declaration and references, then assertEquals the parsed values, the number of declarations and references
	 * and compare them to our array list values. 
	 * @throws IOException
	 */
	@Test
	public void testPrimitiveTypes() throws IOException {
		DeclarationsAndReferencesCounter rt = new DeclarationsAndReferencesCounter(arg[0]);
		Map map = rt.getMap();
		Object ASTvalues =  map.get("int");
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();
		expectedValues.add(0);
		expectedValues.add(5);
		assertEquals(expectedValues, ASTvalues);
	}
	
	/**
	 * Functions much like the primitive type test, except key is now testing for annotation declarations.
	 * @throws IOException
	 */
	@Test
	public void testAnnotationDeclarations() throws IOException {
		DeclarationsAndReferencesCounter rt = new DeclarationsAndReferencesCounter(arg[0]);
		Map map = rt.getMap();
		Object ASTvalues =  map.get("dummyRecur.interdummy");
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();
		expectedValues.add(1);
		expectedValues.add(0);
		assertEquals(expectedValues, ASTvalues);
	}
	
	/**
	 * Functions much like the primitive type test, except key is now testing for annotation references.
	 * @throws IOException
	 */
	@Test
	public void testAnnotationReferences() throws IOException {
		DeclarationsAndReferencesCounter rt = new DeclarationsAndReferencesCounter(arg[0]);
		Map map = rt.getMap();
		Object ASTvalues =  map.get("interdummy");
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();
		expectedValues.add(0);
		expectedValues.add(1);
		assertEquals(expectedValues, ASTvalues);
	}
	
	/**
	 * Functions much like the primitive type test, except key is now testing declarations and references for type class.
	 * @throws IOException
	 */
	@Test
	public void testDeclarationsReferences() throws IOException{
		DeclarationsAndReferencesCounter rt = new DeclarationsAndReferencesCounter(arg[0]);
		Map map = rt.getMap();
		Object ASTvalues =  map.get("testJason.dummyTest.A");
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();
		expectedValues.add(1);
		expectedValues.add(3);
		assertEquals(expectedValues, ASTvalues);
	}
	
	/**
	 * Functions much like the primitive type test, except key is now testing for both enumeration types, as well as file recursion, testing
	 * to see if we can find declaration and references of enums in a file which is inside a folder of a jar file.
	 * @throws IOException
	 */
	@Test
	public void testFileRecursion() throws IOException{
		DeclarationsAndReferencesCounter rt = new DeclarationsAndReferencesCounter(arg[0]);
		rt.countInJarOrDirectory();
		Map map = rt.getMap();
		Object ASTvalues =  map.get("enumDummy");
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();
		expectedValues.add(1);
		expectedValues.add(0);
		assertEquals(expectedValues, ASTvalues);
	}
	
	/**
	 * Functions much like the primitive type test, except key is now testing for more enumerations.
	 * @throws IOException
	 */
	@Test
	public void testEnumerations() throws IOException {
		DeclarationsAndReferencesCounter rt = new DeclarationsAndReferencesCounter(arg[0]);
		Map map = rt.getMap();
		Object ASTvalues =  map.get("enumDummy.tests");
		ArrayList<Integer> expectedValues = new ArrayList<Integer>();
		expectedValues.add(1);
		expectedValues.add(0);
		assertEquals(expectedValues, ASTvalues);
	}
}