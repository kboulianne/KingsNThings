/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;

import com.util.Util;
import com.view.customcontrols.PlayerLabel;
import com.main.KNTAppFactory;
import com.model.Board;
import com.model.Cup;
import com.model.Fort;
import com.model.Game;
import com.model.Hex;
import com.model.Player;
import com.model.SpecialCharacter;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author kurtis
 */
public class SidePaneView extends VBox {
    // Current detailsview being displayed in the view
	// TODO Create abstract DetailsView?
	private StackPane content;

	private PlayerLabel opp1Lbl;
	private PlayerLabel opp2Lbl;
	private PlayerLabel opp3Lbl;

	public SidePaneView() {
		buildView();
		addHandlers();
	}

	private void buildView() {
		// Initial setup
		setId("sidePane");
		//FIXME: Hardcoded
		setMinWidth(1280 * 0.5 - 20);

		// Opponents
		HBox otherPlayerInfo = new HBox();
		otherPlayerInfo.setId("otherPlayerInfo");
		otherPlayerInfo.setAlignment(Pos.TOP_CENTER);
		opp1Lbl = new PlayerLabel();
		opp2Lbl = new PlayerLabel();
		opp3Lbl = new PlayerLabel();
		otherPlayerInfo.getChildren().addAll(opp1Lbl, opp2Lbl, opp3Lbl);

		// TODO make Content take the background image.
		content = new StackPane();
		content.setId("detailsBox");
		content.setMinHeight(560);

		// Adds opponents and details container
		getChildren().addAll(otherPlayerInfo, content);

	}

	private void addHandlers() {
		EventHandler<MouseEvent> playerLabelHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent t) {
				PlayerLabel lbl = (PlayerLabel) t.getSource();
				// TODO create a custom event.
				KNTAppFactory.getSidePanePresenter().showOpponentInfo(lbl.getPlayer());
			}
		};
		opp1Lbl.setOnMouseEntered(playerLabelHandler);
		opp2Lbl.setOnMouseEntered(playerLabelHandler);
		opp3Lbl.setOnMouseEntered(playerLabelHandler);

		EventHandler<MouseEvent> exitHandler = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				KNTAppFactory.getSidePanePresenter().dismissOpponentInfo();
			}
		};
		opp1Lbl.setOnMouseExited(exitHandler);
		opp2Lbl.setOnMouseExited(exitHandler);
		opp3Lbl.setOnMouseExited(exitHandler);
	}

	public void setOpponents(List<Player> players) {
		// FIXME: Problem if not four player.
		if (!players.isEmpty()) {
			opp1Lbl.setPlayer(players.get(0));
			opp2Lbl.setPlayer(players.get(1));
			opp3Lbl.setPlayer(players.get(2));
		}
	}
	
	public void showGoldCollection(int hexGold, int fortGold, int counterGold, int specCharGold){
		int totalGold = (hexGold + fortGold + counterGold + specCharGold);
		Label title = new Label("Gold Income:");
		title.getStyleClass().add("title");
		
		ImageView im = new ImageView(Game.GOLD_IMAGE);
		im.setFitWidth(300);
		im.setPreserveRatio(true);
		
		Label hexes = new Label("Hex Income:  " + hexGold);
		Label forts = new Label("Fort Income:  " + fortGold);
		Label counters = new Label("Counter Income:  " + counterGold);
		Label specChars = new Label("Character Income:  " + specCharGold);
		Label divider = new Label(" ");
		Label total = new Label("Total Income:  " + totalGold);
		VBox labels = new VBox();
		
		labels.setAlignment(Pos.CENTER);
		labels.getChildren().addAll(hexes, forts, counters, specChars, divider, total);

		VBox vbox = new VBox();
		//popupVbox.setPadding(new Insets(15));
		vbox.setSpacing(20);//getStyleClass().add("block");
		vbox.getChildren().addAll(title, im, labels);
		vbox.setAlignment(Pos.CENTER);
		vbox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		
		content.getChildren().clear();
		content.getChildren().add(vbox);
	}
		
	public void showSpecialCharRecruitment(SpecialCharacter thing, Player current) {
		content.getChildren().clear();
		content.getChildren().add(KNTAppFactory.getSpecialCharacterPresenter().getView().setScreen2(thing, current));
	}
	
	public void showSpecialCharRecruitment(Player current, List<SpecialCharacter> playersSChars, Cup cup){
		content.getChildren().clear();
		content.getChildren().add(KNTAppFactory.getSpecialCharacterPresenter().getView().setScreen1(current, playersSChars, cup));
	}
	
	public void setThingRecruitment(ThingRecruitmentView view) {
		this.content.getChildren().clear();
		content.getChildren().add(view);
	}
	
	public void showBuildMenu(final Hex h, final Player player, final Board board)	{
		Label title = new Label("Build Options:");
		title.getStyleClass().add("title");
		
		Button build = new Button("Build Fort (5 gold)");
		Button upgrade = new Button("Upgrade Fort (5 gold)");
		
		Label counters = new Label("To upgrade to a citadel you must \n have a gold income of 20 or more");
		VBox cont = new VBox();
		
		cont.setAlignment(Pos.CENTER);
		cont.getStyleClass().add("largeSpacing");
		cont.getChildren().addAll(build, upgrade, counters);

		VBox vbox = new VBox();
		vbox.getStyleClass().add("largeSpacing");
		vbox.setAlignment(Pos.CENTER);
		vbox.getChildren().addAll(title, cont);
		vbox.setMaxSize(Control.USE_PREF_SIZE, Control.USE_PREF_SIZE);
		
		if(player.getGold() < 5)	{
			upgrade.setDisable(true);
			build.setDisable(true);
		}
		
		if((h.getFort() != null) && h.getFort().upgraded())	{
			upgrade.setDisable(true);
			build.setDisable(true);
		}		
		
		if ((h.getFort() != null) && h.getFort().getFortType() == Fort.FortType.CITADEL)	{
			upgrade.setDisable(true);
			build.setDisable(true);
		}
		
		if((h.getFort() != null))	{
			build.setDisable(true);
		}
		
		if(h.getFort() == null)	{
			upgrade.setDisable(true);
		}
		
		if((h.getFort() != null) && h.getFort().getFortType() == Fort.FortType.CASTLE && 
				player.calculateIncome(board) < 20)	{
			upgrade.setDisable(true);
		}
		
		if((h.getFort() != null) && h.getFort().getFortType() == Fort.FortType.CASTLE && 
				player.isCitadelOwner())	{
			upgrade.setDisable(true);
		}		
		
		upgrade.setOnAction(new EventHandler<ActionEvent>()	{
			public void handle(ActionEvent e)	{
				// Should be in presenter.
				Util.playClickSound();
				h.upgradeFort();
				player.removeGold(5);
				h.getFort().setUpgraded(true);
				
				if(h.getFort().getFortType() == Fort.FortType.CITADEL)	{
					player.setCitadelOwner(true);
					player.setCitadelOwner(true);
				}
				
				KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);
				KNTAppFactory.getBoardPresenter().getView().setBoard(board);
				showBuildMenu(h, player, board);
			}
		});
		
		build.setOnAction(new EventHandler<ActionEvent>()	{
			public void handle(ActionEvent e)	{
				Util.playClickSound();
				h.setFort(Fort.create());
				h.getHexOwner().removeGold(5);
				h.getFort().setUpgraded(true);
				KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);
				KNTAppFactory.getBoardPresenter().getView().setBoard(board);
//				KNTAppFactory.getBoardPresenter().getView().setBoard(GameService.getInstance().getGame().getBoard());
				showBuildMenu(h, player, board);
			}
		});
		
		content.getChildren().clear();
		content.getChildren().add(vbox);
	}	
	
	public void showRolls(SortedMap<Integer, Player> rolls){
		Iterator<Integer> it = rolls.keySet().iterator();
		if(it.hasNext()){
			VBox display = new VBox();
			display.setMinHeight(500);
			ImageView iv = new ImageView(Game.DICE_IMAGE);
			iv.setFitHeight(200.0);
			iv.setPreserveRatio(true);
			display.getChildren().add(iv);
			while(it.hasNext()){
				int roll = it.next();
				Player p = rolls.get(roll);
				if(p==null)
					Util.log("why is player null?");
				Label lbl = new Label("Player has rolled a " + roll);
				display.getChildren().add(lbl);
			}
			display.getStyleClass().add("block");
			display.setAlignment(Pos.CENTER);
			content.getChildren().clear();
			content.getChildren().add(display);
		}
	}
	

	
	public void showArbitraryView(String title, Image img){
		VBox display = new VBox();
		ImageView iv = new ImageView(img);
		iv.setFitHeight(200.0);
		iv.setPreserveRatio(true);
		Label lbl = new Label(title);
		display.getChildren().addAll(iv, lbl);
		display.getStyleClass().add("largeSpacing");
		display.setAlignment(Pos.CENTER);
		content.getChildren().clear();
		content.getChildren().add(display);
	}

	public void showBattleChooserView(final Player current, List<Player> defendingPlayers, final Hex hex){
		VBox display = new VBox();
		Label lbl = new Label("Choose Your Opponent:");
		display.getChildren().add(lbl);
		for(int i=0;i<defendingPlayers.size();i++){
			final Player plyer = defendingPlayers.get(i);
			
			Button btn = new Button(plyer.getName());
			display.getChildren().add(btn);
			btn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent arg0) {
					KNTAppFactory.getPopupPresenter().showBattlePopup(); // Show the popup
					KNTAppFactory.getBattlePresenter().triggerBattle(current, plyer, hex);
				}
			});
		}
		display.getStyleClass().add("largeSpacing");
		display.setAlignment(Pos.CENTER);
		content.getChildren().clear();
		content.getChildren().add(display);
	}
	
	
	public void showHexDetailsView(HexDetailsView view) {
		content.getChildren().clear();
		content.getChildren().add(view);
	}

	public void showThingDetailsView(ThingDetailsView view) {
		content.getChildren().clear();
		content.getChildren().add(view);
	}
	
	public void showArmyDetailsView(ArmyDetailsView view) {
		content.getChildren().clear();
		content.getChildren().add(view);
	}
	
	public void clearDetailsView(){
		content.getChildren().clear();
	}
}
