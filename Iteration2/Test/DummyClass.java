package Test;


public class DummyClass {
	
	public DummyClass(){
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
	
	public enum Victory {
		VENI, VIDI, VICI
	}
	
	interface theInterface{}
	
	 @Override
	 public String toString(){
	   return "Mafia Wins";
	 }
	 
	 @interface secretAgent{
		 String name();
		 String lastName();
	 }
}