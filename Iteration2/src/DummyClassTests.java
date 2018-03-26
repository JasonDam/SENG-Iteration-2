package Test;

import java.io.File;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import typeCounter.TypeCounter;

@SuppressWarnings("deprecation")
/**
 * Tests the Dummy Classes
 * @author Riike
 *
 */
class DummyClassTests {

	final String BASEDIR = new File("src\\typeCounter").getAbsolutePath();
	
	/**
	 * Tests for correct output for declarations within packages
	 */
	@Test
	void TestPackage() {
		TypeCounter tc = new TypeCounter(BASEDIR, "Other.OtherDummyClass");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getReferencesAndDeclarations());
	}
	
	/**
	 * Tests for correct output for annotation declarations
	 */
	@Test
	void TestAnnotation() {
		TypeCounter tc = new TypeCounter(BASEDIR, "Test");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getReferencesAndDeclarations());
	}
	
	/**
	 * Tests for correct output for enumeration declarations
	 */
	@Test
	void TestEnum() {
		TypeCounter tc = new TypeCounter(BASEDIR, "DummyClass.Victory");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getReferencesAndDeclarations());
	}
	
	/**
	 * Tests for correct output for class constructor references
	 */
	@Test
	void TestClassConstructor() {
		TypeCounter tc = new TypeCounter(BASEDIR, "DummyClass.DummyCLass");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getReferencesAndDeclarations());
	}
	
	/**
	 * Tests for correct output for subclasses declaration
	 */
	@Test
	void TestClassWithinClass() {
		TypeCounter tc = new TypeCounter(BASEDIR, "DummyClass.add");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getReferencesAndDeclarations());
	}
	
	/**
	 * Test for correct output for interface declaration
	 */
	@Test
	void TestClassInterface() {
		TypeCounter tc = new TypeCounter(BASEDIR, "theInterface");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getDeclarations());
	}
	
	/**
	 * Tests for correct output for the primitive type int
	 */
	@Test
	void TestPrimitiveInt() {
		TypeCounter tc = new TypeCounter(BASEDIR, "int");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getReferences());
	}
	

	/**
	 * Tests for correct output for the primitive type char
	 */
	@Test
	void TestPrimitiveChar() {
		TypeCounter tc = new TypeCounter(BASEDIR, "char");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getReferences());
	}
	

	/**
	 * Tests for correct output for the primitive type boolean
	 */
	@Test
	void TestPrimitiveBoolean() {
		TypeCounter tc = new TypeCounter(BASEDIR, "boolean");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getReferences());
	}
	

	/**
	 * Tests for correct output for the primitive type float
	 */
	@Test
	void TestPrimitiveFloat() {
		TypeCounter tc = new TypeCounter(BASEDIR, "float");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertEquals(0,tc.getReferences());
	}
}
