
package com.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import com.model.Block;
import com.model.Creature;
import com.model.DesertCreature;
import com.model.ForestCreature;
import com.model.FrozenWasteCreature;
import com.model.GamePiece;
import com.model.Hex;
import com.model.HexDesert;
import com.model.HexForest;
import com.model.HexFrozenWaste;
import com.model.HexJungle;
import com.model.HexMountain;
import com.model.HexPlains;
import com.model.HexSea;
import com.model.HexSwamp;
import com.model.IncomeCounter;
import com.model.JungleCreature;
import com.model.MountainCreature;
import com.model.PlainsCreature;
import com.model.Player;
import com.model.SpecialCharacter;
import com.model.Treasure;
import com.model.Hex.HexType;
import com.model.Player.PlayerId;











import com.model.SwampCreature;
import com.model.Thing;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

// misc. global static functions
public class Util {	

	private final static boolean DEBUG = true;
	final public static boolean AUTOMATE = false;

	/**
	 * Handles the creation, serialization and deserialization of Color objects to and from JSON.
	 * @author kurt
	 *
	 */
	private static class ColorAdapter implements InstanceCreator<Color>, JsonSerializer<Color>, JsonDeserializer<Color> {
		@Override
		public Color createInstance(Type arg0) {
			// Default to black since there is no default color constructor.
			return Color.BLACK;
		}
		
		@Override
		public JsonElement serialize(Color color, Type arg1,
				JsonSerializationContext arg2) {
			JsonArray array = new JsonArray();
			array.add(new JsonPrimitive(color.getRed()));
			array.add(new JsonPrimitive(color.getGreen()));
			array.add(new JsonPrimitive(color.getBlue()));
			array.add(new JsonPrimitive(color.getOpacity()));
			
			return array;
		}
		
		@Override
		public Color deserialize(JsonElement json, Type arg1,
				JsonDeserializationContext arg2) throws JsonParseException {
			JsonArray array = json.getAsJsonArray();
			
			return new Color(
				array.get(0).getAsDouble(),
				array.get(1).getAsDouble(),
				array.get(2).getAsDouble(),
				array.get(3).getAsDouble());
		}
	}
	
	private static class ThingAdapter implements InstanceCreator<Thing>, JsonSerializer<Thing>, JsonDeserializer<Thing> {

		@Override
		public Thing createInstance(Type t) {
			if (t.equals(DesertCreature.class))
				return new DesertCreature();
			else if (t.equals(ForestCreature.class))
				return new ForestCreature();
			else if (t.equals(FrozenWasteCreature.class))
				return new FrozenWasteCreature();
			else if (t.equals(JungleCreature.class))
				return new JungleCreature();
			else if (t.equals(MountainCreature.class))
				return new MountainCreature();
			else if (t.equals(PlainsCreature.class))
				return new PlainsCreature();
			else if (t.equals(SpecialCharacter.class))
				return new SpecialCharacter();
			else if (t.equals(IncomeCounter.class))
				return new IncomeCounter();
			else if (t.equals(SwampCreature.class))
				return new SwampCreature();
			else
				System.err.println("Add me in CreatureAdapter: " + t.getClass().getCanonicalName());
			
			return new Thing();
		}
		
		@Override
		public JsonElement serialize(Thing thing, Type arg1,
				JsonSerializationContext arg2) {
			Gson gson = GSON_BUILDER.create();
			// Serialize the class type so it can be recovered when deserializing.
			JsonObject obj = gson.toJsonTree(thing).getAsJsonObject();
			
			obj.addProperty("classType", thing.getClass().getCanonicalName());
			
			return obj;
		}
		
		@Override
		public Thing deserialize(JsonElement elem, Type arg1,
				JsonDeserializationContext arg2) throws JsonParseException {
			Thing t = null;
			
			Gson gson = GSON_BUILDER.create();
			
			// Fetch and remove the class
			try {
				Class<?> clazz = Class.forName(elem.getAsJsonObject().remove("classType").getAsString());
				t = (Thing) gson.fromJson(elem, clazz); 
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return t;
		}
	}
	private static class BlockAdapter implements JsonSerializer<Block>, JsonDeserializer<Block> {

		@Override
		public JsonElement serialize(Block block, Type t,
				JsonSerializationContext ctx) {
			JsonArray json = new JsonArray();
			
			for (Thing th : block.getListOfThings()) {
				JsonElement elem = ctx.serialize(th);
				// Add the class type.
				elem.getAsJsonObject().add("classType", new JsonPrimitive(th.getClass().getCanonicalName()));
				
				json.add(elem);
			}

			return json;
		}

		@Override
		public Block deserialize(JsonElement json, Type t,
				JsonDeserializationContext ctx) throws JsonParseException {
			Block b = new Block();
			JsonArray array = json.getAsJsonArray();
			Iterator<JsonElement> it = array.iterator();
			while (it.hasNext()) {
				JsonElement elem = it.next();
				String owner = elem.getAsJsonObject().get("owner").getAsString();
//				String name = elem.getAsJsonObject().get("name").getAsString();
				String classType = elem.getAsJsonObject().get("classType").getAsString();
				
				try {
					Class<?> c = Class.forName(classType);
					Thing th = null;
					
					th = ctx.deserialize(elem, c);

					if (th != null)
						b.addThing(th, owner);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return b;
		}
		
	}
	
	// The map represented as:
	/*{(Map)
	 * 		nameOfPlayer: {(Player1)
	 * 			player: {player}
	 * 			armies: [creatures]
	 * 		}(Player1)
	 * 		nameOfOtherPlayer: {(Player2)
	 * 			...
	 * 		}(Player2)
	 * }(Map)
	 */
	private static class HexAdapter implements JsonSerializer<Hex>, JsonDeserializer<Hex> {

		@Override
		public Hex deserialize(JsonElement json, Type t,
				JsonDeserializationContext ctx) throws JsonParseException {
			// These need special deserialization
			JsonElement color = json.getAsJsonObject().remove("color");
			JsonElement armies = json.getAsJsonObject().remove("armies");
			
			Gson gson = new Gson();
			// TODO: Hex name does not seem to be serialized.
			Hex hex = gson.fromJson(json, Hex.class);
			
			gson = GSON_BUILDER.create();

			for (Map.Entry<String, JsonElement> entry : armies.getAsJsonObject().entrySet()) {
				System.err.println(entry);
				
				Player p = gson.fromJson(entry.getValue().getAsJsonObject().get("player"), Player.class);
				
				
				// Loop the json array that contains creatures.
				JsonElement array = entry.getValue().getAsJsonObject().get("armies");
				
				for (JsonElement elem : array.getAsJsonArray()){
					try {
						Class<?> clazz = Class.forName(elem.getAsJsonObject().remove("classType").getAsString());
						Creature c = (Creature) gson.fromJson(elem, clazz);
						
						
						hex.addCreatToArmy(c, p);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
				}
			}
			
			
			hex.setColor(gson.fromJson(color, Color.class));
			
			return hex;
		}

		@Override
		public JsonElement serialize(Hex hex, Type t,
				JsonSerializationContext ctx) {
			
			JsonElement obj = null;
			// Use default serialization. Avoids infinite loop.
			Gson gson = new Gson();
			obj = gson.toJsonTree(hex, Hex.class);
			obj.getAsJsonObject().remove("color");
			obj.getAsJsonObject().add("color", GSON_BUILDER.create().toJsonTree(hex.getColor()));
			// Transients are ignored
//			obj = ctx.serialize(hex, Hex.class);
			
			gson = GSON_BUILDER.create();

			JsonObject map = new JsonObject();
			// Loop the armies and serialize
			for (Map.Entry<Player, List<Creature>> entry : hex.getArmies().entrySet()) {
//				JsonObject playerEntry = new JsonObject();
				// Serialize the player and store it as player in map
				JsonElement player = gson.toJsonTree(entry.getKey());
				
//				playerEntry.add(property, value);
				
				
				JsonArray armies = new JsonArray();
				
				for (Creature c : entry.getValue()) {
					JsonElement elem = gson.toJsonTree(c); 
					
					elem.getAsJsonObject().addProperty("classType", c.getClass().getCanonicalName());
					
					armies.add(elem);
				}
				
				// Create object to contain player and armies
				JsonObject playerEntry = new JsonObject();
				playerEntry.add("player", player);
				playerEntry.add("armies", armies);
				// Player name to playerEntry
				map.add(entry.getKey().getName(), playerEntry);
			}

			obj.getAsJsonObject().add("armies", map);
			
			return obj;
		}
		
	}
	
	public static final GsonBuilder GSON_BUILDER = new GsonBuilder()
		// TODO Use InstanceCreator for GamePiece Hierarchy?
		.registerTypeAdapter(Color.class, new ColorAdapter())
		.registerTypeAdapter(Block.class, new BlockAdapter())
		.registerTypeAdapter(Hex.class, new HexAdapter())
		.registerTypeAdapter(Thing.class, new ThingAdapter());
//		.registerTypeAdapter(DesertCreature.class, new ThingAdapter())
//		.registerTypeAdapter(ForestCreature.class, new ThingAdapter())
//		.registerTypeAdapter(FrozenWasteCreature.class, new ThingAdapter())
//		.registerTypeAdapter(JungleCreature.class, new ThingAdapter())
//		.registerTypeAdapter(MountainCreature.class, new ThingAdapter())
//		.registerTypeAdapter(PlainsCreature.class, new ThingAdapter())
//		.registerTypeAdapter(SwampCreature.class, new ThingAdapter())
//		.registerTypeAdapter(IncomeCounter.class, new ThingAdapter());

//	public static final Gson GSON = GSON_BUILDER.create();
	
	
	public static void log(String message) {
		if(DEBUG)
			System.out.println(message);
	}

	public static double distanceBtwTwoPts(double x1, double y1, double x2, double y2) {
		return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
	}

	public static int randomNumber(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	/**
	 * Integer comparator from high to low. (reverse sort)
	 */
	public static final class ReverseIntegerSortComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer i1, Integer i2) {
			if (i1 > i2) {
				return -1;
			}
			if (i1 < i2) {
				return 1;
			}

			return -1;
		}

	}

	/**
	 * creates random list
	 *
	 * @param list
	 * @return
	 */
	public static <T> List<T> getRandomList(List<T> list) {
		int rand = 0;
		int size = list.size();
		Random rnd = new Random();
		List<T> newList = new ArrayList<T>();
		for (int i = 0; i < size; i++) {
			rand = rnd.nextInt(list.size());
			newList.add(list.get(rand));
			list.remove(rand);
		}
		return newList;
	}
	
	
	// Sounds
//	private final static AudioClip clickSound = new AudioClip(Util.class.getResource("click.wav").toString());
	public static void playClickSound(){
//		clickSound.play();
	}
	
//	private final static AudioClip hexClickSound = new AudioClip(Util.class.getResource("click2.wav").toString());
	public static void playHexClickSound(){
//		hexClickSound.play();
	}
	
//	private final static AudioClip startBattleSound = new AudioClip(Util.class.getResource("battle.wav").toString());
	public static void playStartBattleSound(){
//		startBattleSound.play();
	}
	
//	private final static AudioClip diceRoll = new AudioClip(Util.class.getResource("dice.wav").toString());
	public static void playDiceRollSound(){
//		diceRoll.play();
	}
	
//	private final static AudioClip swordSound = new AudioClip(Util.class.getResource("sword.wav").toString());
		public static void playSwordSound(){
//			swordSound.play();
	}
	
	//TODO as a joke change later
//	private final static AudioClip deathSound = new AudioClip(Util.class.getResource("dyingmanLOL.wav").toString());
	static void playDeathSound(){
//		deathSound.play();
	}
	
//	private final static Media audioFile = new Media( Util.class.getResource("test.mp3").toString() );    
//	final static MediaPlayer player = new MediaPlayer(audioFile);
	public static void playMusic(){
//		player.setVolume(0.2);
//        player.play();
//        player.setCycleCount(MediaPlayer.INDEFINITE);
	}
	
//	private final static Media audioFile2 = new Media( Util.class.getResource("battlemusic.mp3").toString() );    
//	final static MediaPlayer player2 = new MediaPlayer(audioFile2);
	public static void playBattleMusic(){
//		player.stop();    
//		player2.play();
//		player2.setCycleCount(MediaPlayer.INDEFINITE);
	}
	public static void stopBattleMusic(){
//		player2.stop();    
//		playMusic();
	}
}
