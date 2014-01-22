package com.model;

import java.util.ArrayList;
import java.util.List;

import com.presenter.Paintable;

import view.com.GameScreen;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

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
	boolean selectable;
	boolean highlighted;
	HexType type;
	int movementWeight = 1;
	
	int[] joiningHexes; // Integer array of hex id's, size 6
	
	//      __0__        
	//   5 /     \ 1
    //    /       \      value=-1 if no Hex
	//    \       /
	//   4 \_____/ 2
	//        3	
	static final int joiningHexes37Mapping[][] = {
		{1,2,3,4,5,6}, // 0, radius 0
		{8,9,2,0,6,7},{9,10,11,3,0,1},{2,11,12,13,4,0},{0,3,13,14,15,5},
		{6,0,4,15,16,17},{7,1,0,5,17,18}, // 1-6, radius 1,
		{20,8,1,6,18,19},{21,22,9,1,7,20},{22,23,10,2,1,8},{23,24,25,11,2,9}, // 7-10
		{10,25,26,12,3,2},{11,26,27,28,13,3},{3,12,28,29,14,4},{4,13,29,30,31,15}, // 11-14
		{5,4,14,31,32,16},{17,5,15,32,33,34},{18,6,5,16,34,35},{19,7,6,17,35,36},// 15-18, radius 2
		{-1,20,7,18,36,-1},{-1,21,8,7,19,-1},{-1,-1,22,8,20,-1},{-1,-1,23,9,8,21}, // 19-22
		{-1,-1,24,10,9,22},{-1,-1,-1,25,10,23},{24,-1,-1,26,11,10},{25,-1,-1,27,12,11}, // 23-26
		{26,-1,-1,-1,28,12},{12,27,-1,-1,29,13},{13,28,-1,-1,30,14},{14,29,-1,-1,-1,31},// 27-30
		{15,14,30,-1,-1,32},{16,15,31,-1,-1,33},{34,16,32,-1,-1,-1},{35,17,16,33,-1,-1},// 31-34
		{36,18,17,34,-1,-1},{-1,19,18,35,-1,-1}};
	
	
	static final Image START_IMAGE = new Image("view/com/assets/pics/tiles/start.png");
	// list of armies for all players
	// list of misc Things
	List<Creature> player1Army; // can be maps
	List<Creature> player2Army;
	List<Creature> player3Army;
	List<Creature> currentPlayerArmy;

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
	
	public Hex(int id, HexType type){
		this.type = type;
		this.id= id;
		color = Color.DARKGRAY;
		name = type.typeName;
		startPosition =  false;
		selected = false;
		selectable = true; // may have to change for startup
		setJoiningHexes();
		player1Army = new ArrayList<Creature>(); 
		player2Army = new ArrayList<Creature>(); 
		player3Army = new ArrayList<Creature>(); 
		currentPlayerArmy = new ArrayList<Creature>();
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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		setJoiningHexes();
	}
	
	public int[] getJoiningHexes() {
		return joiningHexes;
	}
	public void setJoiningHexes() {
		//TODO
		//if 37 mapping
		joiningHexes = new int[6];
		for(int i=0; i<6; i++){
			joiningHexes[i] = joiningHexes37Mapping[id][i];
		}
		//else 17 do later
	}
	
	public boolean isSelectable() {
		return selectable;
	}

	public void setSelectable(boolean selectable) {
		this.selectable = selectable;
	}

	public boolean isHighlighted() {
		return highlighted;
	}

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}
	
	public void addThingToHex(Thing thing, String key){
		//TODO
		currentPlayerArmy.add((Creature) thing);
		//setOwner(player);
	}
	
	//paint
    
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
		if(highlighted){
			gc.setFill(Color.LIGHTBLUE);
		}else if(selected){
			gc.setFill(Color.WHITESMOKE);
		}else{
			gc.setFill(color);
		}
		gc.fillPolygon(new double[]{(xOffset+(temp_width*0.25)), (xOffset+(temp_width*0.75)), (xOffset+temp_width), 	
				 					xOffset+(temp_width*0.75), xOffset+(temp_width*0.25), xOffset},
				 	   new double[]{yOffset, yOffset, yOffset+(height*0.5), 		
									yOffset+height, yOffset+height, yOffset+(height*0.5)}, 6);
		//image
		gap=temp_width*0.05;
		double imageAdjust=4.0;
		if(startPosition)
			gc.drawImage(START_IMAGE, xOffset+gap+(imageAdjust/2), yOffset+gap, temp_width-(gap*2.0)-imageAdjust, height-(gap*2.0));
		else
			gc.drawImage(image, xOffset+gap+(imageAdjust/2), yOffset+gap, temp_width-(gap*2.0)-imageAdjust, height-(gap*2.0));
	}
	
	
	public void paintHexInDetails(Pane detailsBox){
		detailsBox.getChildren().clear();
		
		ImageView img = new ImageView(image);
		img.setFitWidth(300); 
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
		
        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("block");
        contentBox.setAlignment(Pos.CENTER);
        
		Label nameLbl = new Label("Type: "+name);
		String test = "";
		for (int i: joiningHexes){
			test+=i+", "; 
		}
		Label testLbl = new Label("removeLater: id="+id+" joins with:"+test);
		Label ownerLbl = new Label("Owner: Not owned");
		if (owner != null){
			ownerLbl.setText("Owner:"+ owner.getName());
		}
		
		contentBox.getChildren().addAll(img, nameLbl, ownerLbl, testLbl);
		
		drawArmy(player1Army, contentBox, detailsBox);
		drawArmy(player2Army, contentBox, detailsBox);
		drawArmy(player3Army, contentBox, detailsBox);
		drawArmy(currentPlayerArmy, contentBox, detailsBox);
		
		StackPane sp = new StackPane();
		
		sp.getChildren().addAll(img, contentBox);
		detailsBox.getChildren().add(sp);
		
	}
	
	private void drawArmy(List<Creature> army, Pane contentBox, final Pane detailsBox){
		if(!army.isEmpty()){
			HBox armyBox = new HBox();
			armyBox.getStyleClass().add("army");
			
			StackPane circleStackPane = new StackPane();
			Circle circle = new Circle();
			circle.setRadius(22);
			circle.setFill(Color.RED);//color of player 
			Label armySizeLbl = new Label(Integer.toString(army.size()));
			circleStackPane.getChildren().addAll(circle,armySizeLbl);
			armyBox.getChildren().add(circleStackPane);
			armyBox.setAlignment(Pos.CENTER);
			for(final Thing t:army){
				ImageView thingImg = new ImageView(t.image);
				thingImg.setFitWidth(50); 
				thingImg.setPreserveRatio(true);
				thingImg.setSmooth(true);
				thingImg.setCache(true);
				thingImg.getStyleClass().add("thing");
				armyBox.getChildren().add(thingImg);
				//t.paintThingInDetails(detailsBox);
				thingImg.setOnMouseClicked(new EventHandler<Event>() {
					@Override
					public void handle(Event arg0) {
						detailsBox.getChildren().clear();
						t.paintThingInDetails(detailsBox);	
					}
				});
			}	
			
			contentBox.getChildren().add(armyBox);
		}	
	}

	
	
}
