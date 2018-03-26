package typeCounter;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestSuite {
	private static final String[] arg = new String[1]; 
	
	@Before
	public void initialization() {
		arg[0] = "C:\\Users\\Evan\\Desktop\\testJAVA.jar";
	}
	
	
	@Test (expected=NullPointerException.class)
	public void testArgNull() throws IOException {
		arg[0] = null;
		Main.main(arg);
	}
	
	@Test (expected = FileNotFoundException.class)
	public void testFileDoesntExist() throws IOException {
		arg[0] = "idonotexist.jar";
		Main.main(arg);
	}
	
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
