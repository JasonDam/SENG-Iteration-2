package testFiles;

public class TestClassOne {
	
	// Declaring a class to test Main
	private class Hello {
		int f = 1;
		
		public void g() {
			System.out.println(f);
		}
		
	}
	
	// declaring primitive types to test the Main
	int a	= 10;
	char b = 'b';
	double c = 1.0;
	boolean d = true;
	String e = "eee";
	
	public void aa() {
		System.out.println(a);
	}
	public void bb() {
		System.out.println(b);
	}
	public void cc() {
		System.out.println(c);
	}
	public void dd() {
		System.out.println(d);
	}
	public void ee() {
		System.out.println(e);
	}
	public void h() {
		Hello hello =  new Hello();
		hello.g();
	}

}
