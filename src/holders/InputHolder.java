package holders;

import models.Input;

public class InputHolder {
	
	public static final Integer ACTION_CREATE=1;
	public static final Integer ACTION_EDIT=2;
	
	static Input input=null;
	public static Input getInput() {
		return input;
	}
	public static void setInput(Input input) {
		InputHolder.input = input;
	}
	public static Integer getAction() {
		//if there is no input, it's time to create it
		if(input==null)return ACTION_CREATE;
		//else it's time to edit it
		else return ACTION_EDIT;
	}
	
}
