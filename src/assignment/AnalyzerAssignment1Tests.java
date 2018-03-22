package assignment;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestAccessContentsOfADirectory.class, TestAnalyzerAssignment1Main.class, TestParse.class,
		TestReadFileToString.class })
public class AnalyzerAssignment1Tests {

}
