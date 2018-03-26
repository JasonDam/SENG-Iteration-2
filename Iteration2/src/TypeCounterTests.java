package Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import typeCounter.Logger;
import typeCounter.TypeCounter;


/**
 * Tests the type counter for functionality
 * @author Riike
 *
 */
@SuppressWarnings("deprecation")
public class TypeCounterTests {

	 final String BASEDIR = new File("src\\typeCounter").getAbsolutePath();
	 final Logger $ = new Logger();
	 
	 @Before
	 public void init() {
		 $.log(BASEDIR);
	 }
	
	 /**
		 * test that the File list is empty when an invalid path is given
		 * 
		 */
	@Test
	public void IncorrectPath() {
		
		TypeCounter tc = new TypeCounter(BASEDIR+"/fake", "int");
		tc.findAndPrintDeclarationsAndReferences();
		
		Assert.assertTrue(tc.getFileList().isEmpty());
	}
	/**
	 * test that the Declaration counter is never incremented, remains at 0, when an invalid type is given
	 * 
	 */
	@Test
	public void NonExistingType() {  
		TypeCounter tc = new TypeCounter(BASEDIR, "ENUM");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertEquals(0, tc.getDeclarations());
	}
	/**
	 * test that the References counter is never increment, remains at 0, when an invalid type is given
	 * 
	 */
	@Test
	public void NonExistingType2() {  
		TypeCounter tc = new TypeCounter(BASEDIR, "ENUM");
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertEquals(0, tc.getReferences());
	}
	/**
	 * test that you cannot when the parameters type AND path are not valid, or null
	 * that the constructor set the type is still empty
	 */
	@Test(expected = NullPointerException.class)
	public void SettingFolderPathNoType() {
		TypeCounter tc = new TypeCounter(BASEDIR, null);
		assertNull(tc.getFullDeclarationType());
	}
	/**
	 * testing the getFolderPath method to see that the parameters path is equal to the 
	 * value in the field folderPath
	 */
	@Test
	public void SettingFolderPathType() {
		TypeCounter tc = new TypeCounter(BASEDIR, "int");
		assertEquals(BASEDIR, tc.getFolderPath());
	}
	/**
	 * testing that if the path entered is null, the   getFolderPath() is not reached
	 */
	@Test(expected = NullPointerException.class)
	public void SettingTypeNoPath() {
		TypeCounter tc = new TypeCounter(null, "int");
		tc.getFolderPath();
	}
	/**
	 * testing that if the type entered, the getter for the declarationType is correct, equals the correct value
	 */
	@Test
	public void SettingTypePath() {
		String type = "int";
		TypeCounter tc = new TypeCounter(BASEDIR, type);
		Assert.assertEquals(type, tc.getDeclarationType());
	}
	/**
	 * testing that the references count and/or declaration count
	 * are incremented when a valid type is found
	 */
	@Test
	public void GetReferencesDeclarations() {
		TypeCounter tc1 = new TypeCounter(BASEDIR, "int");
		tc1.findAndPrintDeclarationsAndReferences();
		String nameoffirstinlist = (tc1.getFileList().get(0)).replace(".java", "");
		nameoffirstinlist = nameoffirstinlist.replace(BASEDIR +"/", "");
		
		$.log(nameoffirstinlist);
		TypeCounter tc = new TypeCounter(BASEDIR, nameoffirstinlist);
		tc.findAndPrintDeclarationsAndReferences();
		Assert.assertNotSame("0",tc.getDeclarations());
		Assert.assertNotSame("0", tc.getReferencesAndDeclarations());
	}
}
