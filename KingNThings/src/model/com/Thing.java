package model.com;

import java.util.ArrayList;

import view.com.GameScreen;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import controller.com.Paintable;

public abstract class Thing extends GamePiece implements Paintable	{
	
	Color color;
	String owner;
	
	Thing(String name)	{
		super(name);
		owner = "Bag";
		color = Color.BLACK;
	}
	
	public static ArrayList<Thing> createThings()	{
		ArrayList<Thing> creatures = new ArrayList<Thing>();
		
		//Add swamp creatures
		creatures.add(new SwampCreature("giantlizard"));
		creatures.add(new SwampCreature("giantlizard"));
		creatures.add(new SwampCreature("crocodilesswamp"));
		creatures.add(new SwampCreature("ghost"));
		creatures.add(new SwampCreature("ghost"));
		creatures.add(new SwampCreature("ghost"));
		creatures.add(new SwampCreature("ghost"));
		creatures.add(new SwampCreature("vampirebat"));
		creatures.add(new SwampCreature("swamprat"));
		creatures.add(new SwampCreature("sprite"));
		creatures.add(new SwampCreature("giantsnakeswamp"));
		creatures.add(new SwampCreature("swampgas"));
		creatures.add(new SwampCreature("slimebeast"));
		creatures.add(new SwampCreature("will-o-wisp"));
		creatures.add(new SwampCreature("watersnake"));
		creatures.add(new SwampCreature("darkwizard"));
		creatures.add(new SwampCreature("poisonfrog"));
		creatures.add(new SwampCreature("pirates"));
		creatures.add(new SwampCreature("basilisk"));
		creatures.add(new SwampCreature("wingedpirhana"));
		creatures.add(new SwampCreature("spirit"));
		creatures.add(new SwampCreature("thing"));
		creatures.add(new SwampCreature("blackknight"));
		creatures.add(new SwampCreature("hugeleech"));
		creatures.add(new SwampCreature("giantmosquito"));
		
		//Add desert creatures
		creatures.add(new DesertCreature("sandworm"));
		creatures.add(new DesertCreature("giantspider"));
		creatures.add(new DesertCreature("nomads"));
		creatures.add(new DesertCreature("nomads"));
		creatures.add(new DesertCreature("vultures"));
		creatures.add(new DesertCreature("griffon"));
		creatures.add(new DesertCreature("skeletons"));
		creatures.add(new DesertCreature("skeletons"));
		creatures.add(new DesertCreature("babydragon"));
		creatures.add(new DesertCreature("dervish"));
		creatures.add(new DesertCreature("dervish"));
		creatures.add(new DesertCreature("giantwasp"));
		creatures.add(new DesertCreature("giantwasp1"));
		creatures.add(new DesertCreature("desertbat"));
		creatures.add(new DesertCreature("camelcorps"));
		creatures.add(new DesertCreature("genie"));
		creatures.add(new DesertCreature("dustdevil"));
		creatures.add(new DesertCreature("sphinx"));
		creatures.add(new DesertCreature("buzzard"));
		creatures.add(new DesertCreature("yellowknight"));
		creatures.add(new DesertCreature("olddragon"));
		
		//Add forest creatures
		creatures.add(new ForestCreature("pixies"));
		creatures.add(new ForestCreature("killerracoon"));
		creatures.add(new ForestCreature("druid"));
		creatures.add(new ForestCreature("elfmage"));
		creatures.add(new ForestCreature("bandits"));
		creatures.add(new ForestCreature("flyingsquirrel"));
		creatures.add(new ForestCreature("flyingsquirrel1"));
		creatures.add(new ForestCreature("greenknight"));
		creatures.add(new ForestCreature("pixies"));
		creatures.add(new ForestCreature("dryad"));
		creatures.add(new ForestCreature("elves"));
		creatures.add(new ForestCreature("elves1"));
		creatures.add(new ForestCreature("bears"));
		creatures.add(new ForestCreature("elves"));
		creatures.add(new ForestCreature("greatowl"));
		creatures.add(new ForestCreature("wildcat"));
		creatures.add(new ForestCreature("wyvern"));
		creatures.add(new ForestCreature("bigfoot"));
		creatures.add(new ForestCreature("unicorn"));
		creatures.add(new ForestCreature("forester"));
		creatures.add(new ForestCreature("walkingtree"));
		
		//Add mountain creatures
		creatures.add(new MountainCreature("goblins"));
		creatures.add(new MountainCreature("goblins"));
		creatures.add(new MountainCreature("goblins"));
		creatures.add(new MountainCreature("goblins"));
		creatures.add(new MountainCreature("dwarves"));
		creatures.add(new MountainCreature("dwarves1"));
		creatures.add(new MountainCreature("dwarves2"));
		creatures.add(new MountainCreature("troll"));
		creatures.add(new MountainCreature("greateagle"));
		creatures.add(new MountainCreature("browndragon"));
		creatures.add(new MountainCreature("mountainmen"));
		creatures.add(new MountainCreature("mountainmen"));
		creatures.add(new MountainCreature("giantroc"));
		creatures.add(new MountainCreature("giantcondor"));
		creatures.add(new MountainCreature("cyclops"));
		creatures.add(new MountainCreature("greathawk"));
		creatures.add(new MountainCreature("ogre"));
		creatures.add(new MountainCreature("brownknight"));
		creatures.add(new MountainCreature("littleroc"));
		creatures.add(new MountainCreature("giant"));
		creatures.add(new MountainCreature("mountainlion"));
		
		//Add plains creatures
		creatures.add(new PlainsCreature("villains"));
		creatures.add(new PlainsCreature("gypsies"));
		creatures.add(new PlainsCreature("gypsies1"));
		creatures.add(new PlainsCreature("whiteknight"));
		creatures.add(new PlainsCreature("tribesmen"));
		creatures.add(new PlainsCreature("tribesmen"));
		creatures.add(new PlainsCreature("tribesmen1"));
		creatures.add(new PlainsCreature("flyingbuffalo"));
		creatures.add(new PlainsCreature("greathunter"));
		creatures.add(new PlainsCreature("wolfpack"));
		creatures.add(new PlainsCreature("lionpride"));
		creatures.add(new PlainsCreature("farmers"));
		creatures.add(new PlainsCreature("farmers"));
		creatures.add(new PlainsCreature("farmers"));
		creatures.add(new PlainsCreature("farmers"));
		creatures.add(new PlainsCreature("eagles"));
		creatures.add(new PlainsCreature("buffaloherd"));
		creatures.add(new PlainsCreature("buffaloherd1"));
		creatures.add(new PlainsCreature("greathawk"));
		creatures.add(new PlainsCreature("giantbeetle"));
		creatures.add(new PlainsCreature("centaur"));
		creatures.add(new PlainsCreature("pegasus"));
		creatures.add(new PlainsCreature("pterodactyl"));
		creatures.add(new PlainsCreature("dragonfly"));
		creatures.add(new PlainsCreature("hunters"));
		
		//Add jungle creatures
		creatures.add(new JungleCreature("crawlingvines"));
		creatures.add(new JungleCreature("giantape"));
		creatures.add(new JungleCreature("giantape"));
		creatures.add(new JungleCreature("headhunter"));
		creatures.add(new JungleCreature("dinosaur"));
		creatures.add(new JungleCreature("pygmies"));
		creatures.add(new JungleCreature("witchdoctor"));
		creatures.add(new JungleCreature("tigers"));
		creatures.add(new JungleCreature("tigers"));
		creatures.add(new JungleCreature("pterodactylwarriors"));
		creatures.add(new JungleCreature("pterodactylwarriors"));
		creatures.add(new JungleCreature("crocodilesjungle"));
		creatures.add(new JungleCreature("watusi"));
		creatures.add(new JungleCreature("elephant"));
		creatures.add(new JungleCreature("giantsnakejungle"));
		creatures.add(new JungleCreature("birdofparadies"));
		
		//Add frozen waste creatures
		creatures.add(new FrozenWasteCreature("killerpuffins"));
		creatures.add(new FrozenWasteCreature("icegiant"));
		creatures.add(new FrozenWasteCreature("eskimos"));
		creatures.add(new FrozenWasteCreature("eskimos"));
		creatures.add(new FrozenWasteCreature("eskimos"));
		creatures.add(new FrozenWasteCreature("eskimos"));
		creatures.add(new FrozenWasteCreature("whitebear"));
		creatures.add(new FrozenWasteCreature("walrus"));
		creatures.add(new FrozenWasteCreature("dragonrider"));
		creatures.add(new FrozenWasteCreature("iceworm"));
		creatures.add(new FrozenWasteCreature("mammoth"));
		creatures.add(new FrozenWasteCreature("killerpenguins"));
		creatures.add(new FrozenWasteCreature("northwind"));
		creatures.add(new FrozenWasteCreature("wolves"));
		creatures.add(new FrozenWasteCreature("icebats"));
		creatures.add(new FrozenWasteCreature("elkherd"));
		creatures.add(new FrozenWasteCreature("whitedragon"));
		
		return creatures;
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
