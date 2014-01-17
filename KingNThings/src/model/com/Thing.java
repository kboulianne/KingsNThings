package model.com;

public abstract class Thing extends GamePiece{
	
	private String name;
	
	Thing(String name){

		setName(name);
		//for now
		setImage("view/com/assets/pics/bears.png");
		// or
		//setImage("view/com/assets/pics/"+name+".png");
	}
	
	
	// getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
