package com.model;

import java.util.*;

public abstract class Creature extends Thing	{
	
	private String domain;
	private boolean fly;
	private boolean ranged;
	private boolean charge;
	private boolean magic;
	private int combatVal;
	private int numberOfMovesAvailable;
	private static final Map<String, Integer> combatVals;
	
	static	{
		combatVals = new HashMap<>();
		//Insert desert creatures
		combatVals.put("babydragon", 3);
		combatVals.put("dervish", 2);
		combatVals.put("dustdevil", 4);
		combatVals.put("camelcorps", 3);
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
		combatVals.put("pixies", 1);
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
		combatVals.put("dinosaur", 4);
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
		combatVals.put("greathawkmountains", 1);
		combatVals.put("littleroc", 2);
		combatVals.put("mountainlion", 2);
		combatVals.put("mountainmen", 1);
		combatVals.put("ogre", 2);
		combatVals.put("troll", 4);
		//Insert plains creatures
		combatVals.put("buffaloherd", 3);
		combatVals.put("buffaloherd1", 4);
		combatVals.put("centaur", 2);
		combatVals.put("dragonfly", 2);
		combatVals.put("eagles", 2);
		combatVals.put("greathawkplains", 2);
		combatVals.put("farmers", 1);
		combatVals.put("flyingbuffalo", 2);
		combatVals.put("greathunter", 4);
		combatVals.put("gypsies", 2);
		combatVals.put("gypsies1", 1);
		combatVals.put("giantbeetle", 2);
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
		combatVals.put("wingedpirhana", 3);
		//Insert Special Characters	
		combatVals.put("elflord", 6);
		combatVals.put("icelord", 4);
		combatVals.put("archcleric", 5);
		combatVals.put("archmage", 6);
		combatVals.put("assassinprimus", 4);
		combatVals.put("baronmunchausen", 4);
		combatVals.put("deerhunter", 4);
		combatVals.put("desertmaster", 4);
		combatVals.put("dwarfking", 5);
		combatVals.put("forestking", 4);
		combatVals.put("ghaogll", 6);
		combatVals.put("grandduke", 4);
		combatVals.put("junglelord", 4);
		combatVals.put("warlord", 5);
		combatVals.put("sirlancealot", 5);
		combatVals.put("marksman", 5);
		combatVals.put("masterthief", 4);
		combatVals.put("swordmaster", 4);
		combatVals.put("mountainking", 4);
		combatVals.put("swampking", 4);
		combatVals.put("plainslord", 4);
		combatVals.put("lordoftheeagles", 5);
	}
	private static final Set<String> flyingCreat;
	static	{
		flyingCreat = new HashSet<>();
		flyingCreat.add("wyvern");
		flyingCreat.add("wingedpirhana");
		flyingCreat.add("vultures");
		flyingCreat.add("vampirebat");
		flyingCreat.add("swampgas");
		flyingCreat.add("pterodactylwarriors");
		flyingCreat.add("pterodactyl");
		flyingCreat.add("pegasus");
		flyingCreat.add("pixies");
		flyingCreat.add("olddragon");
		flyingCreat.add("northwind");
		flyingCreat.add("littleroc");
		flyingCreat.add("killerpuffins");
		flyingCreat.add("icebats");
		flyingCreat.add("griffon");
		flyingCreat.add("greatowl");
		flyingCreat.add("greathawkmountains");
		flyingCreat.add("greathawkplains");
		flyingCreat.add("greateagle");
		flyingCreat.add("giantwasp");
		flyingCreat.add("giantwasp1");
		flyingCreat.add("giantroc");
		flyingCreat.add("giantmosquito");
		flyingCreat.add("giantcondor");
		flyingCreat.add("ghost");
		flyingCreat.add("giantbeetle");
		flyingCreat.add("flyingsquirrel");
		flyingCreat.add("flyingsquirrel");
		flyingCreat.add("flyingbuffalo");
		flyingCreat.add("eagles");
		flyingCreat.add("dustdevil");
		flyingCreat.add("dragonrider");
		flyingCreat.add("dragonfly");
		flyingCreat.add("desertbat");
		flyingCreat.add("darkwizard");
		flyingCreat.add("buzzard");
		flyingCreat.add("browndragon");
		flyingCreat.add("birdofparadise");
		flyingCreat.add("babydragon");
		flyingCreat.add("ghaogll");
		flyingCreat.add("lordoftheeagles");

	}
	private static final Set<String> rangedCreat;
	static	{
		rangedCreat = new HashSet<String>();
		rangedCreat.add("tribesmen1");
		rangedCreat.add("pterodactylwarriors");
 		rangedCreat.add("icegiant");
		rangedCreat.add("hunters");
		rangedCreat.add("headhunter");
		rangedCreat.add("greathunter");
		rangedCreat.add("forester");
		rangedCreat.add("giant");
		rangedCreat.add("elves");		
		rangedCreat.add("elves1");
		rangedCreat.add("dwarves1");
		rangedCreat.add("dwarves");
		rangedCreat.add("dragonrider");
		rangedCreat.add("elflord");
		rangedCreat.add("marksman");
	}
	private static final Set<String> chargingCreat;
	static	{
		chargingCreat = new HashSet<String>();
		chargingCreat.add("yellowknight");
		chargingCreat.add("whiteknight");
 		chargingCreat.add("mammoth");
		chargingCreat.add("greenknight");
		chargingCreat.add("elephant");
		chargingCreat.add("dwarves2");
		chargingCreat.add("brownknight");
		chargingCreat.add("blackknight");
		chargingCreat.add("sirlancealot");
	}
	private static final Set<String> magicCreat;
	static	{
		magicCreat = new HashSet<String>();
		magicCreat.add("witchdoctor");
		magicCreat.add("will-o-wisp");
 		magicCreat.add("whitedragon");
		magicCreat.add("sprite");
		magicCreat.add("spirit");
		magicCreat.add("sphinx");
		magicCreat.add("olddragon");
		magicCreat.add("northwind");
		magicCreat.add("iceworm");		
		magicCreat.add("gypsies1");
		magicCreat.add("gypsies");
		magicCreat.add("genie");
		magicCreat.add("elfmage");
		magicCreat.add("dryad");
		magicCreat.add("druid");
		magicCreat.add("dervish");
		magicCreat.add("darkwizard");
		magicCreat.add("basilisk");
		magicCreat.add("archcleric");
		magicCreat.add("archmage");
	}
	
	public Creature() {}
	
	Creature(String name)	{
		super(name);
		setNumberOfMovesAvailable(4);
		
		//Set combat value to value from map associating creatures with their combat values
		setCombatVal(combatVals.get(name));
		
		
		
		//Set fly boolean
		setFly(flyingCreat.contains(name));
		
		//Set ranged boolean
		setRanged(rangedCreat.contains(name));
		
		//Set charge boolean
		setCharge(chargingCreat.contains(name));
		
		//Set magic boolean
		setMagic(magicCreat.contains(name));	
		
		//Set image to matching image for creature name
		if(!(this instanceof SpecialCharacter))
			setImage("view/com/assets/pics/gamepieces/things/creatures/" + name + ".jpeg");
	}
	
	//get and set methods
	public String getAbilitiesString(){
		String abilities = "";
	    
	    if (isFlying()) {
	    	abilities += " Flying";
	    }
	    if (isRanged()) {
	    	abilities += " Ranged";
	    }
	    if (isCharge()) {
	    	abilities += " Charging";
	    }
	    if(isMagic()){
	    	abilities += " Magic";
	    }
	    if(abilities.isEmpty()){
	    	abilities = " None";
	    }
	    return abilities;
	}
	
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
	public boolean isFlying()	{
		return fly;
	}
	public void setFly(boolean bool){
		fly = bool;
	}
	public boolean isRanged()	{
		return ranged;
	}
	public void setRanged(boolean bool){
		ranged = bool;
	}
	public boolean isCharge()	{
		return charge;
	}
	public void setCharge(boolean bool){
		charge = bool;
	}
	public boolean isMagic()	{
		return magic;
	}
	public void setMagic(boolean bool){
		magic = bool;
	}
	public int getNumberOfMovesAvailable() {
		return numberOfMovesAvailable;
	}
	public void setNumberOfMovesAvailable(int numberOfMovesAvailable) {
		this.numberOfMovesAvailable = numberOfMovesAvailable;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Creature) {
			Creature c = (Creature)obj;
			
			return super.equals(c)
					&& Objects.equals(domain, c.domain)
					&& fly == c.fly
					&& ranged == c.ranged
					&& charge == c.charge
					&& magic == c.magic
					&& combatVal == c.combatVal
					&& numberOfMovesAvailable == c.numberOfMovesAvailable;
		}
		
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		int hash = 50654;
//		hash = 59 * hash + Objects.hashCode(this.domain);
		hash = 59 * hash + (this.fly ? 1 : 0);
		hash = 59 * hash + (this.ranged ? 1 : 0);
		hash = 59 * hash + (this.charge ? 1 : 0);
		hash = 59 * hash + (this.magic ? 1 : 0);
		hash = 59 * hash + this.combatVal;
		hash = 59 * hash + this.numberOfMovesAvailable;
		return hash;
	}
}
