package com.model;

import java.util.ArrayList;

import view.com.GameScreen;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import com.presenter.Paintable;

public abstract class Thing extends GamePiece implements Paintable	{
	
	Color color;
	String owner;
	
	
	Thing(String name)	{
		super(name);
		owner = "Bag";
		color = Color.BLACK;
	}
	
	public static ArrayList<Thing> createThings()	{
		ArrayList<Thing> things = new ArrayList<Thing>();
		
		//Add swamp creatures
		things.add(new SwampCreature("giantlizard"));
		things.add(new SwampCreature("giantlizard"));
		things.add(new SwampCreature("crocodilesswamp"));
		things.add(new SwampCreature("ghost"));
		things.add(new SwampCreature("ghost"));
		things.add(new SwampCreature("ghost"));
		things.add(new SwampCreature("ghost"));
		things.add(new SwampCreature("vampirebat"));
		things.add(new SwampCreature("swamprat"));
		things.add(new SwampCreature("sprite"));
		things.add(new SwampCreature("giantsnakeswamp"));
		things.add(new SwampCreature("swampgas"));
		things.add(new SwampCreature("slimebeast"));
		things.add(new SwampCreature("will-o-wisp"));
		things.add(new SwampCreature("watersnake"));
		things.add(new SwampCreature("darkwizard"));
		things.add(new SwampCreature("poisonfrog"));
		things.add(new SwampCreature("pirates"));
		things.add(new SwampCreature("basilisk"));
		things.add(new SwampCreature("wingedpirhana"));
		things.add(new SwampCreature("spirit"));
		things.add(new SwampCreature("thing"));
		things.add(new SwampCreature("blackknight"));
		things.add(new SwampCreature("hugeleech"));
		things.add(new SwampCreature("giantmosquito"));
		
		//Add desert things
		things.add(new DesertCreature("sandworm"));
		things.add(new DesertCreature("giantspider"));
		things.add(new DesertCreature("nomads"));
		things.add(new DesertCreature("nomads"));
		things.add(new DesertCreature("vultures"));
		things.add(new DesertCreature("griffon"));
		things.add(new DesertCreature("skeletons"));
		things.add(new DesertCreature("skeletons"));
		things.add(new DesertCreature("babydragon"));
		things.add(new DesertCreature("dervish"));
		things.add(new DesertCreature("dervish"));
		things.add(new DesertCreature("giantwasp"));
		things.add(new DesertCreature("giantwasp1"));
		things.add(new DesertCreature("desertbat"));
		things.add(new DesertCreature("camelcorps"));
		things.add(new DesertCreature("genie"));
		things.add(new DesertCreature("dustdevil"));
		things.add(new DesertCreature("sphinx"));
		things.add(new DesertCreature("buzzard"));
		things.add(new DesertCreature("yellowknight"));
		things.add(new DesertCreature("olddragon"));
		
		//Add forest things
		things.add(new ForestCreature("pixies"));
		things.add(new ForestCreature("killerracoon"));
		things.add(new ForestCreature("druid"));
		things.add(new ForestCreature("elfmage"));
		things.add(new ForestCreature("bandits"));
		things.add(new ForestCreature("flyingsquirrel"));
		things.add(new ForestCreature("flyingsquirrel1"));
		things.add(new ForestCreature("greenknight"));
		things.add(new ForestCreature("pixies"));
		things.add(new ForestCreature("dryad"));
		things.add(new ForestCreature("elves"));
		things.add(new ForestCreature("elves1"));
		things.add(new ForestCreature("bears"));
		things.add(new ForestCreature("elves"));
		things.add(new ForestCreature("greatowl"));
		things.add(new ForestCreature("wildcat"));
		things.add(new ForestCreature("wyvern"));
		things.add(new ForestCreature("bigfoot"));
		things.add(new ForestCreature("unicorn"));
		things.add(new ForestCreature("forester"));
		things.add(new ForestCreature("walkingtree"));
		
		//Add mountain things
		things.add(new MountainCreature("goblins"));
		things.add(new MountainCreature("goblins"));
		things.add(new MountainCreature("goblins"));
		things.add(new MountainCreature("goblins"));
		things.add(new MountainCreature("dwarves"));
		things.add(new MountainCreature("dwarves1"));
		things.add(new MountainCreature("dwarves2"));
		things.add(new MountainCreature("troll"));
		things.add(new MountainCreature("greateagle"));
		things.add(new MountainCreature("browndragon"));
		things.add(new MountainCreature("mountainmen"));
		things.add(new MountainCreature("mountainmen"));
		things.add(new MountainCreature("giantroc"));
		things.add(new MountainCreature("giantcondor"));
		things.add(new MountainCreature("cyclops"));
		things.add(new MountainCreature("greathawk"));
		things.add(new MountainCreature("ogre"));
		things.add(new MountainCreature("brownknight"));
		things.add(new MountainCreature("littleroc"));
		things.add(new MountainCreature("giant"));
		things.add(new MountainCreature("mountainlion"));
		
		//Add plains things
		things.add(new PlainsCreature("villains"));
		things.add(new PlainsCreature("gypsies"));
		things.add(new PlainsCreature("gypsies1"));
		things.add(new PlainsCreature("whiteknight"));
		things.add(new PlainsCreature("tribesmen"));
		things.add(new PlainsCreature("tribesmen"));
		things.add(new PlainsCreature("tribesmen1"));
		things.add(new PlainsCreature("flyingbuffalo"));
		things.add(new PlainsCreature("greathunter"));
		things.add(new PlainsCreature("wolfpack"));
		things.add(new PlainsCreature("lionpride"));
		things.add(new PlainsCreature("farmers"));
		things.add(new PlainsCreature("farmers"));
		things.add(new PlainsCreature("farmers"));
		things.add(new PlainsCreature("farmers"));
		things.add(new PlainsCreature("eagles"));
		things.add(new PlainsCreature("buffaloherd"));
		things.add(new PlainsCreature("buffaloherd1"));
		things.add(new PlainsCreature("greathawk"));
		things.add(new PlainsCreature("giantbeetle"));
		things.add(new PlainsCreature("centaur"));
		things.add(new PlainsCreature("pegasus"));
		things.add(new PlainsCreature("pterodactyl"));
		things.add(new PlainsCreature("dragonfly"));
		things.add(new PlainsCreature("hunters"));
		
		//Add jungle things
		things.add(new JungleCreature("crawlingvines"));
		things.add(new JungleCreature("giantape"));
		things.add(new JungleCreature("giantape"));
		things.add(new JungleCreature("headhunter"));
		things.add(new JungleCreature("dinosaur"));
		things.add(new JungleCreature("pygmies"));
		things.add(new JungleCreature("witchdoctor"));
		things.add(new JungleCreature("tigers"));
		things.add(new JungleCreature("tigers"));
		things.add(new JungleCreature("pterodactylwarriors"));
		things.add(new JungleCreature("pterodactylwarriors"));
		things.add(new JungleCreature("crocodilesjungle"));
		things.add(new JungleCreature("watusi"));
		things.add(new JungleCreature("elephant"));
		things.add(new JungleCreature("giantsnakejungle"));
		things.add(new JungleCreature("birdofparadise"));
		
		//Add frozen waste things
		things.add(new FrozenWasteCreature("killerpuffins"));
		things.add(new FrozenWasteCreature("icegiant"));
		things.add(new FrozenWasteCreature("eskimos"));
		things.add(new FrozenWasteCreature("eskimos"));
		things.add(new FrozenWasteCreature("eskimos"));
		things.add(new FrozenWasteCreature("eskimos"));
		things.add(new FrozenWasteCreature("whitebear"));
		things.add(new FrozenWasteCreature("walrus"));
		things.add(new FrozenWasteCreature("dragonrider"));
		things.add(new FrozenWasteCreature("iceworm"));
		things.add(new FrozenWasteCreature("mammoth"));
		things.add(new FrozenWasteCreature("killerpenguins"));
		things.add(new FrozenWasteCreature("northwind"));
		things.add(new FrozenWasteCreature("wolves"));
		things.add(new FrozenWasteCreature("icebats"));
		things.add(new FrozenWasteCreature("elkherd"));
		things.add(new FrozenWasteCreature("whitedragon"));
		
		//Add income counters
		/*things.add(new IncomeCounter("timberland"));
		things.add(new IncomeCounter("silvermine"));
		things.add(new IncomeCounter("silvermine"));
		things.add(new IncomeCounter("peatbog"));
		things.add(new IncomeCounter("oilfield"));
		things.add(new IncomeCounter("goldmine"));
		things.add(new IncomeCounter("farmlands"));
		things.add(new IncomeCounter("elephantsgraveyard"));
		things.add(new IncomeCounter("diamondfield"));
		things.add(new IncomeCounter("coppermine"));
		
		//Add treasure
		things.add(new Treasure("treasurechest"));
		things.add(new Treasure("sapphire"));
		things.add(new Treasure("ruby"));
		things.add(new Treasure("pearl"));
		things.add(new Treasure("emerald"));
		things.add(new Treasure("diamond"));*/
		
		//Add magic items
		
		return things;
	}
	
	public void paint(Pane pane){
		double thingWidth = 60;
		
		StackPane stack = new StackPane();
		
		Rectangle borderRect = new Rectangle();
		borderRect.setX(0);
		borderRect.setY(0);
		borderRect.setWidth(thingWidth);
		borderRect.setHeight(thingWidth);
		borderRect.setArcWidth(20);
		borderRect.setArcHeight(20);
		
		borderRect.setFill(Color.WHITE);
		
		final Rectangle coloredRect = new Rectangle();
		coloredRect.setX(0);
		coloredRect.setY(0);
		coloredRect.setWidth(thingWidth-1);
		coloredRect.setHeight(thingWidth-1);
		coloredRect.setArcWidth(20);
		coloredRect.setArcHeight(20);
		coloredRect.setFill(color);
		
		ImageView img = new ImageView(image);
		img.setFitWidth(thingWidth-7); 
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
        img.getStyleClass().add("thing");
       
		
		stack.getChildren().addAll(borderRect, coloredRect, img);
		
		
		//lastThingIndexSelected= node.getChildren().size();
		pane.getChildren().add(stack);
		
		
		img.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event arg0) {
				// TODO Auto-generated method stub
				/*if (lastThingRect != null){
					lastThingRect.setFill(Color.GREEN);
					//paintThing(lastThingIndexSelected, node);//(lastHexSelected,Color.DARKGRAY);
				}
				if (lastHexSelected != -1){
					paintHex(lastHexSelected,Color.DARKGRAY);
				}*/
				//lastThingRect = coloredRect;
				paintThingInDetails(GameScreen.detailsBox);
				//coloredRect.setFill(Color.WHITE);
			}
		});
	}
	
	public void paintThingInDetails(Pane detailsBox){
		detailsBox.getChildren().clear();
		
		ImageView img = new ImageView(image);
		img.setFitWidth(260); 
        img.setPreserveRatio(true);
        img.setSmooth(true);
        img.setCache(true);
		
		Label thingNameLbl = new Label(name);
		Label typeLbl = new Label("Type: TODO");
		Label ownerLbl = new Label("Owner: " + owner);
		
		detailsBox.getChildren().addAll(img, thingNameLbl, typeLbl, ownerLbl);
	}
	
	//setters and getters

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
}
