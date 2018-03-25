package Test;
import java.io.File;

import org.junit.Test;

import junit.framework.Assert;
import typeCounter.Main;

/**
 * Tests the main class for argument handling
 * @author Riike
 *
 */
public class MainClassTests {
	 
	final String BASEDIR = new File("src\\typeCounter").getAbsolutePath();
	/**
	 * test the main class when no arguments/null arguments are given
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void TestArgumentsDNE() {
		String[] args = {null, null};
		Main.main(args);
	}
	/**
	 * test the main class when no type is given
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void TestArgumentsPath() {
		String[] args = {BASEDIR, null};
		Main.main(args);
	}
	/**
	 * test the main class when no path is given
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void TestArgumentsType() {
		String[] args = {null, "int"};
		Main.main(args);
	}
	/**
	 * test the main class when a path and type are given, which are valid
	 * 
	 */
	@Test
	public void TestArgumentsCorrect() {
		String[] args = {BASEDIR, "int"};
		Main.main(args);
	}

}
