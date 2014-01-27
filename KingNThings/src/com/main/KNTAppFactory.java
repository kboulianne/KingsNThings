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

    private final GamePresenter mainPresenter;
    private final DicePresenter dicePresenter;
    private final SidePanePresenter sidePanePresenter;
    private final BoardPresenter boardPresenter;
    private final HexDetailsPresenter hexDetailsPresenter;
    private final ThingDetailsPresenter thingDetailsPresenter;
    private final PlayerInfoPresenter playerInfoPresenter;
    private final PopupPresenter popupPresenter;
    
	public KNTAppFactory() {
		// Create all presenters then set their dependencies. This way we avoid Circular Dependency problem.
		this.mainPresenter = createMainPresenter();
		this.dicePresenter = createDicePresenter();
		this.sidePanePresenter = createSidePanePresenter();
		this.boardPresenter = createBoardPresenter();
		this.hexDetailsPresenter = createHexDetailsPresenter();
		this.thingDetailsPresenter = createThingDetailsPresenter();
		this.playerInfoPresenter = createPlayerInfoPresenter();
		this.popupPresenter = createPopupPresenter();
		
		// Set dependencies
		mainPresenter.setDependencies(dicePresenter, sidePanePresenter, boardPresenter, playerInfoPresenter, popupPresenter);
		dicePresenter.setDependencies(mainPresenter);
		sidePanePresenter.setDependencies(hexDetailsPresenter, thingDetailsPresenter, mainPresenter);
		boardPresenter.setDependencies(sidePanePresenter);
		hexDetailsPresenter.setDependencies(sidePanePresenter);
		playerInfoPresenter.setDependencies(sidePanePresenter, mainPresenter);
	}
	
	private GamePresenter createMainPresenter() {
		GameView view = new GameView();
		GamePresenter presenter = new GamePresenter(view);
		
		return presenter;
	}
	
	private DicePresenter createDicePresenter() {
		DiceView view = new DiceView();
		DicePresenter presenter = new DicePresenter(view);
		
		return presenter;
	}
	
	private SidePanePresenter createSidePanePresenter() {
		SidePaneView view = new SidePaneView();
		SidePanePresenter presenter = new SidePanePresenter(view);
		
		return presenter;
	}
	
	private BoardPresenter createBoardPresenter() {
		BoardView view = new BoardView();
		BoardPresenter presenter = new BoardPresenter(view);
		
		return presenter;
	}
	
	private HexDetailsPresenter createHexDetailsPresenter() {
		HexDetailsView view = new HexDetailsView();
		HexDetailsPresenter presenter = new HexDetailsPresenter(view);
		
		return presenter;
	}
	
	private ThingDetailsPresenter createThingDetailsPresenter() {
		ThingDetailsView tView = new ThingDetailsView();
		CreatureDetailsView cView = new CreatureDetailsView();
		ThingDetailsPresenter presenter = new ThingDetailsPresenter(tView, cView);
		
		return presenter;
	}
	
	private PlayerInfoPresenter createPlayerInfoPresenter() {
		PlayerInfoView view = new PlayerInfoView();
		PlayerInfoPresenter presenter = new PlayerInfoPresenter(view);
		
		return presenter;
	}
	
	private PopupPresenter createPopupPresenter() {
		PopupView view = new PopupView();
		PopupPresenter presenter = new PopupPresenter(view);
		
		return presenter;
	}
	
    public GamePresenter getMainPresenter() {
		return mainPresenter;
    }
    
//    public DicePresenter getDicePresenter() {
//		return dicePresenter;
//    }
//    
//    public SidePanePresenter getSidePanePresenter() {
//		return sidePanePresenter;
//    }
//    
//    public BoardPresenter getBoardPresenter() {
//		return boardPresenter;
//    }
//    
//    public HexDetailsPresenter getHexDetailsPresenter() {
//		return hexDetailsPresenter;
//    }
//    
//    public ThingDetailsPresenter getThingDetailsPresenter() {
//        return thingDetailsPresenter;
//    }
//    
//    public PlayerInfoPresenter getPlayerInfoPresenter() {
//		return playerInfoPresenter;
//    }
//    
//    public PopupPresenter getPopupPresenter() {
//		return popupPresenter;
//    }

}
