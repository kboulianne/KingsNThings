package model.com;

import view.com.GameScreen;
import controller.com.Paintable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

//TODO Use Factory Pattern?
public class Hex extends GamePiece implements Paintable {
	
    int id; // location on grid
	private Player owner;
	
	// This is in player?
	Color color;
	/** Is the owner's start position. */
	private boolean startPosition;
	//boolean facedUp; // is right side up?
	boolean selected;
	HexType type;
	int movementWeight = 1;
	Hex[] joiningHexes;
	
	// list of armies for all players
	// list of misc Things

	public enum HexType {
		JUNGLE_HEX("Jungle"),
		FROZEN_WASTE_HEX("Frozen Waste"),
		FOREST("Forest"),
		PLAINS("Plains"),
		SWAMP("Swamp"),
		MOUNTAIN("Mountain"),
		DESERT("Desert"),
		SEA("Sea"); 
		private final String typeName;
		HexType(String n) {
			typeName = n;
		}
	}
	
	protected Hex() {
	    startPosition = false;
	}
	public Hex(HexType type){ // doesn't specify id 
	    this();
		this.type = type;
		color = Color.DARKGRAY;
		name = type.typeName;
	}
	
	public Hex(int id, HexType type){
	    this();
		this.type = type;
		this.id= id;
		color = Color.DARKGRAY;
		name = type.typeName;
	}

	
    // setters and getters
    public final Player getOwner() {
	return owner;
    }
    
    public final void setOwner(final Player player) {
	this.owner = player;
    }
	
    public Color getColor() {
	    return color;
    }
    public void setColor(Color color) {
	    this.color = color;
    }
    public boolean isSelected() {
	    return selected;
    }
    public void setSelected(boolean selected) {
	    this.selected = selected;
    }

    public final HexType getType() {
    	return type;
    }

    public final boolean isStartPosition() {
	return startPosition;
    }
	
    public void setStartPosition(boolean b) {
	this.startPosition = b;
    }

	@Override
	public void paint(Pane pane) {
		
		//pane not needed we need a canvas object
		
		// TODO Auto-generated method stub
		GraphicsContext gc =GameScreen.playingArea.getGraphicsContext2D();
		double height = GameScreen.getHexHeight();//HEX_HEIGHT;
		double HEX_WIDTH = GameScreen.getHexWidth();
		double choosenMapping[][] = GameScreen.getChoosenMapping();
		
		double xOffset = choosenMapping[id][0]*0.75*HEX_WIDTH-40.0; //40 move whole grid
		double yOffset = choosenMapping[id][1]*0.5*height-30.0; //30 moves whole grid
		//gc.fillOval(xOffset, yOffset, xOffset+200, yOffset+200);
		
		//outer polygon
		gc.setFill(Color.BLACK);
		gc.fillPolygon(new double[]{(xOffset+(HEX_WIDTH*0.25)), (xOffset+(HEX_WIDTH*0.75)), (xOffset+HEX_WIDTH), 	
									 xOffset+(HEX_WIDTH*0.75), xOffset+(HEX_WIDTH*0.25), xOffset},
					   new double[]{yOffset, yOffset, yOffset+(height*0.5), 		
									yOffset+height, yOffset+height, yOffset+(height*0.5)}, 6);
		//inner polygon
		double gap=HEX_WIDTH*0.05;
		double temp_width = HEX_WIDTH;
		xOffset+=gap;
		yOffset+=gap;
		height-=(gap*2);//
		temp_width-=(gap*2);
		gc.setFill(color);
		gc.fillPolygon(new double[]{(xOffset+(temp_width*0.25)), (xOffset+(temp_width*0.75)), (xOffset+temp_width), 	
				 					xOffset+(temp_width*0.75), xOffset+(temp_width*0.25), xOffset},
				 	   new double[]{yOffset, yOffset, yOffset+(height*0.5), 		
									yOffset+height, yOffset+height, yOffset+(height*0.5)}, 6);
		//image
		gap=temp_width*0.05;
		double imageAdjust=4.0;
		gc.drawImage(image, xOffset+gap+(imageAdjust/2), yOffset+gap, temp_width-(gap*2.0)-imageAdjust, height-(gap*2.0));
		
		
	}
	
	
	public void paintHexInDetails(Pane detailsBox){
		detailsBox.getChildren().clear();
		
		ImageView img = new ImageView(image);
		img.setFitWidth(300); 
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
		
		Label nameLbl = new Label("Type: "+name);
		Label ownerLbl = new Label("Owner: Not owned");
		if (owner != null){
			ownerLbl.setText("Owner:"+ owner.getName());
		}
		
		detailsBox.getChildren().addAll(img, nameLbl, ownerLbl);
	}
}
