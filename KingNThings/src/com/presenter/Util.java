
package com.presenter;

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
import com.util.RuntimeTypeAdapterFactory;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

// misc. global static functions
public class Util {	

	private final static boolean DEBUG = true;
	final public static boolean AUTOMATE = false;
//	private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
	// Mapper for GamePiece hierarchy
	
	
	private static class ColorInstanceCreator implements InstanceCreator<Color> {
		@Override
		public Color createInstance(Type arg0) {
			// Default to black since there is no default color constructor.
			return Color.BLACK;
		}
	}
	
	private static class ColorSerializer implements JsonSerializer<Color> {
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
	}
	private static class ColorDeserializer implements JsonDeserializer<Color> {
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
	
	public static class CreatureSerializer implements JsonSerializer<Creature> {

		@Override
		public JsonElement serialize(Creature c, Type t,
				JsonSerializationContext ctx) {
			
			return new Gson().toJsonTree(c);
		}
	}
	private static class CreatureAdapter implements InstanceCreator<Creature> {

		@Override
		public Creature createInstance(Type t) {
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
			
			return new SwampCreature();
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
//			Type type = new TypeToken<Creature>(){}.getType();
//			hex.setArmies((Map<Player, List<Creature>>) gson.fromJson(armies, type));
//			Map<Player, List<Creature>> armies = new HashMap<>();
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
	
	private static final RuntimeTypeAdapterFactory<Thing> gamePieceAdapter =
			RuntimeTypeAdapterFactory
			.of(Thing.class)
			.registerSubtype(Thing.class)
			.registerSubtype(IncomeCounter.class)
			.registerSubtype(Treasure.class)
			.registerSubtype(Creature.class)
			.registerSubtype(DesertCreature.class)
			.registerSubtype(ForestCreature.class)
			.registerSubtype(FrozenWasteCreature.class)
			.registerSubtype(JungleCreature.class)
			.registerSubtype(MountainCreature.class)
			.registerSubtype(PlainsCreature.class)
			.registerSubtype(SwampCreature.class);
	private static final RuntimeTypeAdapterFactory<Hex> hexAdapter = 
			RuntimeTypeAdapterFactory
				.of(Hex.class)
				.registerSubtype(Hex.class)
				.registerSubtype(HexDesert.class)
				.registerSubtype(HexForest.class)
				.registerSubtype(HexFrozenWaste.class)
				.registerSubtype(HexJungle.class)
				.registerSubtype(HexMountain.class)
				.registerSubtype(HexPlains.class)
				.registerSubtype(HexSea.class)
				.registerSubtype(HexSwamp.class);
	
	public static final GsonBuilder GSON_BUILDER = new GsonBuilder()
		// TODO Use InstanceCreator for GamePiece Hierarchy?
		.registerTypeAdapter(Color.class, new ColorInstanceCreator())
		.registerTypeAdapter(Color.class, new ColorSerializer())
		.registerTypeAdapter(Color.class, new ColorDeserializer())
		.registerTypeAdapter(Block.class, new BlockAdapter())
		.registerTypeAdapter(Hex.class, new HexAdapter())
		.registerTypeAdapter(SwampCreature.class, new CreatureAdapter())
//		.registerTypeAdapterFactory(gamePieceAdapter)
//		.registerTypeAdapterFactory(hexAdapter)
		.setPrettyPrinting();
//		.registerTypeAdapter(Player.class, new PlayerDeserializer())
//		.registerTypeAdapter(Thing.class, new GamePieceInstanceCreator())
//		.registerTypeAdapter(Creature.class, new GamePieceInstanceCreator())
//		.registerTypeAdapter(DesertCreature.class, new CreatureSerializer())
//		.registerTypeAdapter(ForestCreature.class, new CreatureSerializer())
//		.registerTypeAdapter(FrozenWasteCreature.class, new CreatureSerializer())
//		.registerTypeAdapter(JungleCreature.class, new CreatureSerializer())
//		.registerTypeAdapter(MountainCreature.class, new CreatureSerializer())
//		.registerTypeAdapter(PlainsCreature.class, new CreatureSerializer())
//		.registerTypeAdapter(SwampCreature.class, new CreatureSerializer())
	public static final Gson GSON = GSON_BUILDER.create();
	
	
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
	static void playDiceRollSound(){
//		diceRoll.play();
	}
	
//	private final static AudioClip swordSound = new AudioClip(Util.class.getResource("sword.wav").toString());
		static void playSwordSound(){
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
	
	public static void main(String[] args) {
		Hex hex = new Hex(0, HexType.DESERT);
		List<Thing> list = Thing.createThings();
		
		hex.addCreatToArmy((Creature) list.get(0), new Player("test"));
		
		String s = GSON.toJson(hex);
		System.out.println(s);
		
		hex = GSON.fromJson(s, Hex.class);
		System.out.println();
//		Block b = new Block();
//		List<Thing> things = Thing.createThings();
//		System.out.println(things.size());
//		for (Thing t : things) {
//			b.addThing(t, "");
//		}
//		
//		String s = GSON.toJson(b);
//		System.out.println(s);
//		
//		b = GSON.fromJson(s, Block.class);
//		System.out.println(b.getListOfThings().size());
	}
}
