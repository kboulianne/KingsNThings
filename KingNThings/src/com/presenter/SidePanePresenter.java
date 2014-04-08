package com.presenter;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import com.main.KNTAppFactory;
import com.model.Cup;
import com.model.Game;
import com.model.Hex;
import com.model.Player;
import com.model.Thing;
import com.util.Util;
import com.view.SidePaneView;
import com.view.ThingRecruitmentView;

/**
 * Handles Opponent view, and any <ViewName>DetailView that can be displayed in the pane.
 * 
 * @author kurtis
 */
public class SidePanePresenter {

	/** The view managed by this presenter. */
	private final SidePaneView view;

	private int freeRecruits = 0;
	private int paidRecValue = 0;
	
	public SidePanePresenter(SidePaneView view) {
		this.view = view;
	}

	/**
	 * Gets the view managed by this presenter.
	 * @return 
	 */
	public SidePaneView getView() {
		return view;
	}

	/**
	 * Shows the details for the Hex selected in the BoardPresenter.
	 * @param h  The hex to display.
	 */
	void showHexDetailsFor(Hex h) {
		view.showHexDetailsView(KNTAppFactory.getHexDetailsPresenter().getView());

		// make the presenter update the view
		KNTAppFactory.getHexDetailsPresenter().showHex(h);	
	}

	/**
	 * Shows the details for the Thing selected in the player rack, ArmyOrMisc etc...
	 * @param t The Thing to display.
	 */
	public void showThingDetailsFor(Thing t) {
		view.showThingDetailsView(KNTAppFactory.getThingDetailsPresenter().getViewFor(t));

		// Make the presenter update the UI
		KNTAppFactory.getThingDetailsPresenter().showThing(t);
	}

	/**
	 * Shows the PlayerInfo as a popup for the specified player.
	 * @param player The player information to display in the popup.
	 */
	public void showOpponentInfo(Player player) {
		KNTAppFactory.getGamePresenter().showPlayerInfoPopup(player);
	}

	public void showThingRecruitment(
			final int freeRecruits, 
			int paidRecruits) {
		
		final ThingRecruitmentView tView = new ThingRecruitmentView();
		this.freeRecruits = freeRecruits;
		this.paidRecValue = paidRecruits;
		tView.setFreeRecruit(freeRecruits);
		tView.setPaidRecruit(paidRecruits);

		
//		tView.getPlaceRecruitsButton().setOnAction(new EventHandler<ActionEvent>()	{
//			public void handle(ActionEvent arg)	{
//				handlePlaceRecruit();
//			}
//		});
		
		view.setThingRecruitment(tView);
	}
	
	
	/**
	 * Dismisses the Opponent Info popup being displayed.
	 */
	public void dismissOpponentInfo() {
		KNTAppFactory.getGamePresenter().dismissPopup();
	}

	// TODO: Make view as private instance
	public void handleBuyRecruits(ThingRecruitmentView view) {
		Game game = KNTAppFactory.getGamePresenter().getLocalInstance();
		
		Player current = game.getCurrentPlayer();
		
		if (current.getGold() >= 5 && paidRecValue < 5) {
			Util.playClickSound();
			current.removeGold(5);
			KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(current);
			view.setPaidRecruit(++ paidRecValue);
		}
	}

	public void handlePlaceRecruit() {
		Game game = KNTAppFactory.getGamePresenter().getLocalInstance();
		
		Util.playClickSound();
		int total = 0;
		List<Thing> listOfThings = game.getLastSelectedThingsOfCurrentPlayerBlock();
		Player player = game.getCurrentPlayer();
		Cup cup = game.getCup();
		
		// Return selected items to the cup
		for(Thing t: listOfThings)	{
			total++;
			player.removeThing(t);
			cup.addThing(t);
		}
		
		
		total = (int)Math.ceil(total/2.0);
		total += (freeRecruits + paidRecValue);
		
		// Draw recruits from the cup
		for(int i = 0; i < total; i++)	{
			Thing c = cup.getRandomThing();
			cup.removeThing(c);
			player.addThing(c);
		}
		
		KNTAppFactory.getPlayerInfoPresenter().getView().setPlayer(player);
		
		
		view.clearDetailsView();
		view.showArbitraryView("Choose things from rack and\n"
				   + "    place them on the board", Game.CROWN_IMAGE);
		
		KNTAppFactory.getBoardPresenter().getView().setDisable(false);
		KNTAppFactory.getBoardPresenter().getView().addPlacementHandler();
		KNTAppFactory.getDicePresenter().getView().getEndTurnBtn().setDisable(false);
		KNTAppFactory.getPlayerInfoPresenter().getView().setRackDefaultHandler(player);
	}
}
