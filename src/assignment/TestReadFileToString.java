package assignment;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TestReadFileToString {

	private static String BASEDIR = "\\some-path\\"; 
	
	@Test (expected = FileNotFoundException.class)
	public void testNoFilePath() throws FileNotFoundException, IOException {
		AnalyzerAssignment1.readFileToString("");
	}

	@Test (expected = FileNotFoundException.class)
	public void testInvalidFilePath() throws FileNotFoundException, IOException {
		AnalyzerAssignment1.readFileToString("a");
	}
	
	@Test
	public void testValidFilePath() throws FileNotFoundException, IOException {
		File[] contents = AnalyzerAssignment1.accessContentsOfADirectory(BASEDIR);
		String filePath = contents[0].getAbsolutePath();
		String fileContent = AnalyzerAssignment1.readFileToString(filePath);
		assertNotNull(fileContent);
	}
}
