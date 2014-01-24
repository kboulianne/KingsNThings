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

/**
 *
 * @author kurtis
 */
public class KingsNThingsAppFactory {
    private GamePresenter mainPresenter;
    private DicePresenter dicePresenter;
    private SidePanePresenter sidePanePresenter;
    private BoardPresenter boardPresenter;
    private HexDetailsPresenter hexDetailsPresenter;
    private ThingDetailsPresenter thingDetailsPresenter;
    private PlayerInfoPresenter playerInfoPresenter;
    private PopupPresenter popupPresenter;
    
    // TODO refactor into a better factory? Or is this sufficient?
    public GamePresenter getMainPresenter() {
	if (mainPresenter == null) {
	    GameView view = new GameView();
	    mainPresenter = new GamePresenter(
		    view,
		    getDicePresenter(),
		    getSidePanePresenter(),
		    getBoardPresenter(),
		    getPlayerInfoPresenter(),
		    getPopupPresenter()
	    );
	}
	
	return mainPresenter;
    }
    
    public DicePresenter getDicePresenter() {
	/// Create the presenter if needed.
	if (dicePresenter == null) {
	    DiceView view = new DiceView();
	    dicePresenter = new DicePresenter(view, mainPresenter);
	}
	
	return dicePresenter;
    }
    
    public SidePanePresenter getSidePanePresenter() {
	if (sidePanePresenter == null) {
	    SidePaneView view = new SidePaneView();
	    sidePanePresenter = new SidePanePresenter(view);
	    
	    // Avoids infinite recursion
	    sidePanePresenter.setHexDetailsPresenter(getHexDetailsPresenter());
	    sidePanePresenter.setThingDetailsPresenter(getThingDetailsPresenter());
	}
	
	return sidePanePresenter;
    }
    
    public BoardPresenter getBoardPresenter() {
	if (boardPresenter == null) {
	    BoardView view = new BoardView();
	    boardPresenter = new BoardPresenter(
		    view,
		    getSidePanePresenter()
	    );
	}
	
	return boardPresenter;
    }
    
    public HexDetailsPresenter getHexDetailsPresenter() {
	if (hexDetailsPresenter == null) {
	    HexDetailsView view = new HexDetailsView();
	    hexDetailsPresenter = new HexDetailsPresenter(view, getSidePanePresenter());
	}
	
	return hexDetailsPresenter;
    }
    
    public ThingDetailsPresenter getThingDetailsPresenter() {
        if (thingDetailsPresenter == null) {
            ThingDetailsView view = new ThingDetailsView();
	    CreatureDetailsView cView = new CreatureDetailsView();
            thingDetailsPresenter = new ThingDetailsPresenter(view, cView);
        }
        
        return thingDetailsPresenter;
    }
    
    public PlayerInfoPresenter getPlayerInfoPresenter() {
	if (playerInfoPresenter == null) {
	    PlayerInfoView view = new PlayerInfoView();
	    playerInfoPresenter = new PlayerInfoPresenter(
		    view,
		    getSidePanePresenter()
	    );
	    
	    // Avoids infinite recursion
	    playerInfoPresenter.setGamePresenter(getMainPresenter());
	}
	
	return playerInfoPresenter;
    }
    
    public PopupPresenter getPopupPresenter() {
	if (popupPresenter == null) {
	    PopupView view = new PopupView();
	    popupPresenter = new PopupPresenter(view);
	}
	
	return popupPresenter;
    }
}
