package TestPackage;

public class ExtractJars {
	
	public static void main(String[] args) throws java.io.IOException {
	    java.util.jar.JarFile jarfile = new java.util.jar.JarFile(new java.io.File("/Users/TonyTea/Desktop/It5.jar")); //jar file path(here sqljdbc4.jar)
	    java.util.Enumeration<java.util.jar.JarEntry> enu= jarfile.entries();
	    while(enu.hasMoreElements())
	    {
	        String destdir = "/Users/TonyTea/Desktop/destinationDirectory";     //abc is my destination directory
	        java.util.jar.JarEntry je = enu.nextElement();

	        System.out.println(je.getName());

	        java.io.File fl = new java.io.File(destdir, je.getName());
	        if(!fl.exists())
	        {
	            fl.getParentFile().mkdirs();
	            fl = new java.io.File(destdir, je.getName());
	        }
	        if(je.isDirectory())
	        {
	            continue;
	        }
	        java.io.InputStream is = jarfile.getInputStream(je);
	        java.io.FileOutputStream fo = new java.io.FileOutputStream(fl);
	        while(is.available()>0)
	        {
	            fo.write(is.read());
	        }
	        fo.close();
	        is.close();
	    }

	  }
}
