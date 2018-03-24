package test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import static org.junit.Assert.*;
import test.testDeclaration;
import org.junit.Test;

public class testMain {
	
	private static String BASEDIR = "/Users/parkerwong/Documents/workspace/TestAstParser/src/test";

	@Test (expected = FileNotFoundException.class)
	public void testGetFile() throws FileNotFoundException, IOException{
		testDeclaration.getFile(BASEDIR);
	}
	
	@Test
	public void testGetFileJava() throws FileNotFoundException, IOException {
		testDeclaration.getFile("testDeclaration.java");
	}
	

	@Test
	public void testMain() throws FileNotFoundException, IOException {
		String[] args = {BASEDIR};
		testDeclaration.main(args);
	}

	@Test(expected = NullPointerException.class)
	public void testMain1() throws FileNotFoundException, IOException {
		//testDeclaration newTest = new testDeclaration();
		testDeclaration.main(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testMain2() throws FileNotFoundException, IOException {
		String[] args = {"C:/Users/"};
		testDeclaration.main(args);
	}
}
