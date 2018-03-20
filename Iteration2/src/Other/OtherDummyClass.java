package Other;

public class OtherDummyClass {
	
	public OtherDummyClass(){
		System.out.println("Hello World");
	
		int a;
		char b;
		byte c;
	}
	
	public int add(){
		return 1 + 1;
	}
	
	public void add(int i){
		int sum = 1 + i;
	}
	
	enum Quark {
		UP, DOWN, CHARM, STRANGE, TOP, BOTTOM
	}
	
	interface theInterface{}
}
