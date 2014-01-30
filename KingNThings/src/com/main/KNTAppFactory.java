/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main;

import com.presenter.DicePresenter;
import com.presenter.GamePresenter;
import com.presenter.BoardPresenter;
import com.presenter.HexDetailsPresenter;
import com.presenter.PlayerInfoPresenter;
import com.presenter.PopupPresenter;
import com.presenter.SidePanePresenter;
import com.presenter.ThingDetailsPresenter;
import com.view.BoardView;
import com.view.CreatureDetailsView;
import com.view.HexDetailsView;
import com.view.DiceView;
import com.view.GameView;
import com.view.PlayerInfoView;
import com.view.PopupView;
import com.view.SidePaneView;
import com.view.ThingDetailsView;

/** Presenters and views are created once and reused.
 *
 * @author kurtis
 */
public class KNTAppFactory {

    private static final GamePresenter gamePresenter;
    private static final DicePresenter dicePresenter;
    private static final SidePanePresenter sidePanePresenter;
    private static final BoardPresenter boardPresenter;
    private static final HexDetailsPresenter hexDetailsPresenter;
    private static final ThingDetailsPresenter thingDetailsPresenter;
    private static final PlayerInfoPresenter playerInfoPresenter;
    private static final PopupPresenter popupPresenter;
    
    static {
    	// Create all presenters then set their dependencies. This way we avoid Circular Dependency problem.
    	gamePresenter = createMainPresenter();
		dicePresenter = createDicePresenter();
		sidePanePresenter = createSidePanePresenter();
		boardPresenter = createBoardPresenter();
		hexDetailsPresenter = createHexDetailsPresenter();
		thingDetailsPresenter = createThingDetailsPresenter();
		playerInfoPresenter = createPlayerInfoPresenter();
		popupPresenter = createPopupPresenter();
		
		// Set dependencies
		gamePresenter.setDependencies(dicePresenter, sidePanePresenter, boardPresenter, playerInfoPresenter, popupPresenter);
		dicePresenter.setDependencies(gamePresenter);
		sidePanePresenter.setDependencies(hexDetailsPresenter, thingDetailsPresenter, gamePresenter);
		boardPresenter.setDependencies(sidePanePresenter);
		hexDetailsPresenter.setDependencies(sidePanePresenter);
		playerInfoPresenter.setDependencies(sidePanePresenter, gamePresenter);
    		
    }
		
    private KNTAppFactory() {}
	
	private static GamePresenter createMainPresenter() {
		GameView view = new GameView();
		GamePresenter presenter = new GamePresenter(view);
		
		return presenter;
	}
	
	private static DicePresenter createDicePresenter() {
		DiceView view = new DiceView();
		DicePresenter presenter = new DicePresenter(view);
		
		return presenter;
	}
	
	private static SidePanePresenter createSidePanePresenter() {
		SidePaneView view = new SidePaneView();
		SidePanePresenter presenter = new SidePanePresenter(view);
		
		return presenter;
	}
	
	private static BoardPresenter createBoardPresenter() {
		BoardView view = new BoardView();
		BoardPresenter presenter = new BoardPresenter(view);
		
		return presenter;
	}
	
	private static HexDetailsPresenter createHexDetailsPresenter() {
		HexDetailsView view = new HexDetailsView();
		HexDetailsPresenter presenter = new HexDetailsPresenter(view);
		
		return presenter;
	}
	
	private static ThingDetailsPresenter createThingDetailsPresenter() {
		ThingDetailsView tView = new ThingDetailsView();
		CreatureDetailsView cView = new CreatureDetailsView();
		ThingDetailsPresenter presenter = new ThingDetailsPresenter(tView, cView);
		
		return presenter;
	}
	
	private static PlayerInfoPresenter createPlayerInfoPresenter() {
		PlayerInfoView view = new PlayerInfoView();
		PlayerInfoPresenter presenter = new PlayerInfoPresenter(view);
		
		return presenter;
	}
	
	private static PopupPresenter createPopupPresenter() {
		PopupView view = new PopupView();
		PopupPresenter presenter = new PopupPresenter(view);
		
		return presenter;
	}

	public static ThingDetailsPresenter getThingdetailspresenter() {
		return thingDetailsPresenter;
	}

	public static GamePresenter getGamePresenter() {
		return gamePresenter;
	}

	public static DicePresenter getDicepresenter() {
		return dicePresenter;
	}

	public static SidePanePresenter getSidepanepresenter() {
		return sidePanePresenter;
	}

	public static BoardPresenter getBoardpresenter() {
		return boardPresenter;
	}

	public static HexDetailsPresenter getHexdetailspresenter() {
		return hexDetailsPresenter;
	}

	public static PlayerInfoPresenter getPlayerinfopresenter() {
		return playerInfoPresenter;
	}

	public static PopupPresenter getPopuppresenter() {
		return popupPresenter;
	}

}
