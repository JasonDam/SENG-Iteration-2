package Test;

public enum DummyEnum {
	MAFIA(1),
	TOWNIE(2),
	DETECTIVE(3),
	DOCTOR(4),
	BARMAN(5);
	
   private int value;
   private DummyEnum(int value) {
      this.value = value;
   }
   public int getValue() {
      return value;
   }
   
   @Override
   public String toString(){
	   return "Mafia";
   }
}
