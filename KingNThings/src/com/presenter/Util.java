package com.presenter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
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
import com.model.Block;
import com.model.Hex;
import com.model.Player;
import com.model.Player.PlayerId;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

// misc. global static functions
public class Util {	

	private final static boolean DEBUG = true;
	final public static boolean AUTOMATE = false;
//	private static final Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
	
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
	private static class PlayerDeserializer implements JsonDeserializer<Player> {
		@Override
		public Player deserialize(JsonElement json, Type arg1,
				JsonDeserializationContext context) throws JsonParseException {
			JsonObject jPlayer = json.getAsJsonObject();
			Player player = null;
			
			if (jPlayer.has("id")) {
				PlayerId id = PlayerId.valueOf(jPlayer.get("id").getAsString());
				player = new Player(id, jPlayer.getAsJsonPrimitive("name").getAsString());
			}
			else {
				player = new Player(jPlayer.getAsJsonPrimitive("name").getAsString());
			}
			
			// Normal serialization
			player.setBlock(context.<Block>deserialize(jPlayer.get("block"), Block.class));
			player.setStartPos(context.<Hex>deserialize(jPlayer.get("startPos"), Hex.class));
			
			
			// Make sure to rebuild circular reference to owner in hex
			if (player.getStartPos() != null)
				player.getStartPos().setOwner(player);
			
			return player;
		}
		
	}
//	private static class HexSerializer implements JsonSerializer<Hex> {
//
//		@Override
//		public JsonElement serialize(Hex hex, Type arg1,
//				JsonSerializationContext context) {
//			// Serialize normally, skipping transient fields.
//			JsonObject elem = (JsonObject) context.serialize(hex);
//			
//			// Fields marked transient that should be included.
//			// Serialize owner as string.
//			elem.addProperty("hexOwner", hex.getHexOwner().getName());
//			
//			return elem;
//		}
//		
//	}
//	private static class HexDeserializer implements JsonDeserializer<Hex> {
//
//		@Override
//		public Hex deserialize(JsonElement json, Type arg1,
//				JsonDeserializationContext context) throws JsonParseException {
//			// Use standard deserializer.
//			Hex hex = context.<Hex>deserialize(json, Hex.class);
//			
//			// Deserialize the hexOwner
//			return null;
//		}
//		
//	}
	
	public static final Gson GSON = new GsonBuilder()
		// TODO Use InstanceCreator for GamePiece Hierarchy?
		.registerTypeAdapter(Color.class, new ColorInstanceCreator())
		.registerTypeAdapter(Color.class, new ColorSerializer())
		.registerTypeAdapter(Color.class, new ColorDeserializer())
		.registerTypeAdapter(Player.class, new PlayerDeserializer())
	.create();
	
	
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
	public static <T> ArrayList<T> getRandomList(ArrayList<T> list) {
		int rand = 0;
		int size = list.size();
		Random rnd = new Random();
		ArrayList<T> newList = new ArrayList<T>();
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
}
