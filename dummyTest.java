package typeCounter;
import typeCounter.dummyTest2;

public class dummyTest implements dummyInterface{
	int a = 2;
	int b = 3;
	String str = "sf";
	
	
	int dec() {
		int c = 5;
		c = a + c;
		str = str + str;
		return 2;
	}
	
	class A() {
		void run() {
			
		}
	}
	
	int recur() {
		for(int i = 0; i < 3; i++) {
			if (i == 3) {
				return 2;
			}
		recur();
		}
	}
	
	void testA() {
		new A();
		A typesA = new A();
		typesA.run();
	}

	@Override
	public int interf1() {
		
		return 0;
	}

	@Override
	public String inter2() {
		
		return null;
	}
	
	
}
