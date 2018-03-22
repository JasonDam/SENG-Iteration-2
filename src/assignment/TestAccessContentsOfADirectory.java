package assignment;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

public class TestAccessContentsOfADirectory {
	private static String BASEDIR = "\\some-path\\"; 
	
	@Test
	public void testInvalidPathName() throws IOException {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		AnalyzerAssignment1.accessContentsOfADirectory("a");
		assertEquals("The pathname specified is not valid. Try running the code again and enter a valid one." + System.getProperty("line.separator"), outContent.toString());
	}
	
	@Test
	public void testPathToValidDirectoryNoJavaFiles() throws IOException {
		File[] contents = AnalyzerAssignment1.accessContentsOfADirectory("");
		assertNull(contents);
	}
	
	@Test
	public void testPathToValidDirectoryWithJavaFiles() throws IOException {
		File[] contents = AnalyzerAssignment1.accessContentsOfADirectory(BASEDIR);
		assertNotNull(contents);
	}

}
