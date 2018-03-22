package assignment;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.Test;

public class TestAnalyzerAssignment1Main {

	@Test
	public void TestNoCommandLineArgs() throws FileNotFoundException, IOException {
		String[] args = {""};
	
	    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	    System.setOut(new PrintStream(outContent));
	
		AnalyzerAssignment1.main(args);
	    assertEquals("There are no pathname or java type input on the command line arguments. Retry again." + System.getProperty("line.separator"), outContent.toString());
	}
	
	@Test
	public void TestOneCommandLineArgs() throws FileNotFoundException, IOException {
		String[] args = {"a"};
		 
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		AnalyzerAssignment1.main(args);
		assertEquals("There are no pathname or java type input on the command line arguments. Retry again." + System.getProperty("line.separator"), outContent.toString());
	}
/*
	@Test 
	public void TestTwoCommandLineArgs() throws FileNotFoundException, IOException {
		String[] args = {"a", "a"};
		 
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		
		AnalyzerAssignment1.main(args);
		assertNotEquals("There are no pathname or java type input on the command line arguments. Retry again." + System.getProperty("line.separator"), outContent.toString());
	}
*/
}
