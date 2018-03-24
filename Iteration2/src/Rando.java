import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Rando {
	public static void main(String args[]) throws IOException {
		
		
		
		    final ZipFile zf = new ZipFile("/Users/TonyTea/Desktop/It5.jar");
		    try {
		        for (ZipEntry ze : Collections.list(zf.entries())) {
		            final String path = ze.getName();
		            if (path.endsWith(".java")) {
		                final StringBuilder buf = new StringBuilder(path);
		               // buf.delete(path.lastIndexOf('/'), path.length()); //removes the name of the class to get the path only
		                //if (!path.startsWith("/")) { //you may omit this part if leading / is not required
		                  //  buf.insert(0, '/');
		               // }
		                System.out.println(buf.toString());
		                
		                File file = new File(buf.toString());
		                String fileName = file.getAbsolutePath();
		                System.out.print(fileName);
		            }
		        }
		    } finally {
		        zf.close();
		    }
		    
		}
	///Users/TonyTea/Documents/GitHub/SENG-Iteration-2/Iteration2/src/JarFIleSecondTest/FolderInJar2ndTest/ClassInFolderOfFolderinJar2ndTest.java
}
