package exceptions;

public class ResourceException extends Exception{

	public ResourceException() {
		System.out.println("");
	}
	
	public ResourceException(String msg) {
		super(msg);
	}
	
	public ResourceException(String element,int food_cost, int wood_cost, int iron_cost, int food, int wood, int iron) {
		super(String.format("Cannot build %s. \n"
				+ "Missing: Food=%d, Wood=%d, Iron=%d. \n"
				+ "Resources on hand: Food=%d, Wood=%d, Iron=%d.", element, food_cost, wood_cost, iron_cost, 
				food, wood, iron));
	}
	
}
