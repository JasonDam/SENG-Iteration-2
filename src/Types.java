public class Types {
	public String name;
	public int declarations;
	public int references;
	
	public Types (String typeName) {
		name = typeName;
		declarations = 0;
		references = 0;
	}
	
	public Types (String typeName, int dec, int ref) {
		name = typeName;
		declarations = dec;
		references = ref;
	}
}