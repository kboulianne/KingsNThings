package model.com;

import java.util.*;

public abstract class Creature extends Thing	{
	private static final Map<String, Integer> combatVals;
	static	{
		combatVals = new HashMap<String, Integer>();
		//Insert desert creatures
		combatVals.put("babydragon", 3);
		combatVals.put("dervish", 2);
		combatVals.put("dustdevil", 4);
		combatVals.put("giantspider", 1);
		combatVals.put("giantwasp", 2);
		combatVals.put("giantwasp1", 4);
		combatVals.put("nomads", 1);
		combatVals.put("sandworm", 3);
		combatVals.put("sphinx", 4);
		combatVals.put("yellowknight", 3);
		combatVals.put("buzzard", 1);
		combatVals.put("desertbat", 1);
		combatVals.put("genie", 4);
		combatVals.put("griffon", 2);
		combatVals.put("olddragon", 4);
		combatVals.put("skeletons", 1);
		combatVals.put("vultures", 1);
		//Insert forest creatures
		combatVals.put("bandits", 2);
		combatVals.put("bears", 2);
		combatVals.put("bigfoot", 5);
		combatVals.put("druid", 3);
		combatVals.put("dryad", 1);
		combatVals.put("elfmage", 2);
		combatVals.put("elves", 3);
		combatVals.put("elves1", 2);
		combatVals.put("flyingsquirrel", 2);
		combatVals.put("flyingsquirrel1", 1);
		combatVals.put("forester", 2);
		combatVals.put("greatowl", 2);
		combatVals.put("greenknight", 4);
		combatVals.put("killerracoon", 2);
		combatVals.put("unicorn", 4);
		combatVals.put("walkingtree", 5);
		combatVals.put("wildcat", 2);
		combatVals.put("wyvern", 3);
		//Insert frozen waste creatures
		combatVals.put("dragonrider", 3);
		combatVals.put("elkherd", 2);
		combatVals.put("eskimos", 2);
		combatVals.put("icebats", 1);
		combatVals.put("icegiant", 5);
		combatVals.put("iceworm", 4);
		combatVals.put("killerpenguins", 3);
		combatVals.put("killerpuffins", 2);
		combatVals.put("mammoth", 5);
		combatVals.put("northwind", 2);
		combatVals.put("walrus", 4);
		combatVals.put("whitebear", 4);
		combatVals.put("whitedragon", 5);
		combatVals.put("wolves", 3);
		//Insert jungle creatures
		combatVals.put("birdofparadise", 1);
		combatVals.put("crawlingvines", 6);
		combatVals.put("crocodilesjungle", 2);
		combatVals.put("dinsosaur", 4);
		combatVals.put("elephant", 4);
		combatVals.put("giantape", 5);
		combatVals.put("giantsnakejungle", 3);
		combatVals.put("headhunter", 2);
		combatVals.put("pterodactylwarriors", 2);
		combatVals.put("pygmies", 2);
		combatVals.put("tigers", 3);
		combatVals.put("watusi", 2);
		combatVals.put("witchdoctor", 2);
		//Insert mountain creatures
		combatVals.put("browndragon", 3);
		combatVals.put("brownknight", 4);
		combatVals.put("cyclops", 5);
		combatVals.put("dwarves", 3);
		combatVals.put("dwarves1", 2);
		combatVals.put("dwarves2", 3);
		combatVals.put("giant", 4);
		combatVals.put("giantcondor", 3);
		combatVals.put("giantroc", 3);
		combatVals.put("goblins", 1);
		combatVals.put("greateagle", 2);
		combatVals.put("greathawk", 1);
		combatVals.put("littleroc", 2);
		combatVals.put("mountainlion", 2);
		combatVals.put("mountainmen", 1);
		combatVals.put("ogre", 2);
		combatVals.put("troll", 4);
		//Insert plains creatures
		combatVals.put("buffaloherd", 3);
		combatVals.put("centaur", 2);
		combatVals.put("dragonfly", 2);
		combatVals.put("eagles", 2);
		combatVals.put("farmers", 1);
		combatVals.put("flyingbuffalo", 2);
		combatVals.put("greathunter", 4);
		combatVals.put("gypsies", 2);
		combatVals.put("gypsies1", 1);
		combatVals.put("hunters", 1);
		combatVals.put("lionpride", 3);
		combatVals.put("pegasus", 2);
		combatVals.put("pterodactyl", 3);
		combatVals.put("tribesmen", 2);
		combatVals.put("tribesmen1", 1);
		combatVals.put("villains", 2);
		combatVals.put("whiteknight", 3);
		combatVals.put("wolfpack", 3);
		//Insert swamp creatures
		combatVals.put("basilisk", 3);
		combatVals.put("blackknight", 3);
		combatVals.put("crocodilesswamp", 2);
		combatVals.put("darkwizard", 1);
		combatVals.put("ghost", 1);
		combatVals.put("giantlizard", 2);
		combatVals.put("giantmosquito", 2);
		combatVals.put("giantsnakeswamp", 3);
		combatVals.put("hugeleech", 2);
		combatVals.put("pirates", 2);
		combatVals.put("poisonfrog", 1);
		combatVals.put("slimebeast", 3);
		combatVals.put("spirit", 2);
		combatVals.put("sprite", 1);
		combatVals.put("swampgas", 1);
		combatVals.put("swamprat", 1);
		combatVals.put("thing", 2);
		combatVals.put("vampirebat", 4);
		combatVals.put("watersnake", 1);
		combatVals.put("will-o-wisp", 2);
		combatVals.put("wingedpiranha", 3);
	}
	private int combatVal;
	private String domain;

	Creature(String name)	{
		super(name);
		//Set combat value to value from map associating creatures with their combat values
		setCombatVal(combatVals.get(name));
		//Set image to matching image for creature name
		setImage("view/com/assets/pics/gamepieces/things/creatures/" + name + ".jpeg");
	}
	
	//get and set methods
	public int getCombatVal()	{
		return combatVal;
	}
	
	public void setCombatVal(int val)	{
		this.combatVal = val;
	}
	
	public String getDomain()	{
		return domain;
	}
	
	public void setDomain(String dom)	{
		this.domain = dom;
	}

}
