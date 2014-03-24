package com.main;

import com.client.KNTClient2;
import com.game.phase.GamePlay;
import com.model.Die;
import com.presenter.ArmyDetailsPresenter;
import com.presenter.BattlePresenter;
import com.presenter.DicePresenter;
import com.presenter.GamePresenter;
import com.presenter.BoardPresenter;
import com.presenter.GameRoomPresenter;
import com.presenter.HexDetailsPresenter;
import com.presenter.LobbyPresenter;
import com.presenter.PlayerInfoPresenter;
import com.presenter.PopupPresenter;
import com.presenter.SidePanePresenter;
import com.presenter.SpecialCharacterPresenter;
import com.presenter.StartScreenPresenter;
import com.presenter.ThingDetailsPresenter;
import com.view.ArmyDetailsView;
import com.view.BattleView;
import com.view.BoardView;
import com.view.GameRoomView;
import com.view.HexDetailsView;
import com.view.DiceView;
import com.view.GameView;
import com.view.LobbyView;
import com.view.PlayerInfoView;
import com.view.PopupView;
import com.view.SidePaneView;
import com.view.SpecialCharacterView;
import com.view.StartScreenView;
import com.view.ThingDetailsView;

/**
 * Presenters and views are created once and reused.
 *
 * @author kurtis
 */
public class KNTAppFactory {

	private static final StartScreenPresenter startScreenPresenter;
	private static final LobbyPresenter lobbyPresenter;
	private static final GameRoomPresenter gameRoomPresenter;
	private static final GamePresenter gamePresenter;
	private static final DicePresenter dicePresenter;
	private static final SidePanePresenter sidePanePresenter;
	private static final BoardPresenter boardPresenter;
	private static final HexDetailsPresenter hexDetailsPresenter;
	private static final ThingDetailsPresenter thingDetailsPresenter;
	private static final ArmyDetailsPresenter armyDetailsPresenter;
	private static final PlayerInfoPresenter playerInfoPresenter;
	private static final PopupPresenter popupPresenter;
	private static final BattlePresenter battlePresenter;
	private static final SpecialCharacterPresenter specialCharacterPresenter; 

	// Game logic
	private static GamePlay gamePlay;
	
	// One per application? Maybe two in the future, one for Notifications and one For requests
    private static final KNTClient2 CLIENT; 
	
	static {
		CLIENT = new KNTClient2("localhost", 6868);
		
    		
    		// Create all presenters then set their dependencies. This way we avoid Circular Dependency problem.
    		startScreenPresenter = createStartScreenPresenter();
		lobbyPresenter = createLobbyPresenter();
		gameRoomPresenter = createGameRoomPresenter();
		gamePresenter = createMainPresenter();
		dicePresenter = createDicePresenter();
		sidePanePresenter = createSidePanePresenter();
		boardPresenter = createBoardPresenter();
		hexDetailsPresenter = createHexDetailsPresenter();
		thingDetailsPresenter = createThingDetailsPresenter();
		armyDetailsPresenter = createArmyDetailsPresenter();
		playerInfoPresenter = createPlayerInfoPresenter();
		popupPresenter = createPopupPresenter();
		battlePresenter = createBattlePresenter();
		specialCharacterPresenter = createSpecialCharacterPresenter();
		// Done initializing
		gamePresenter.setupSubViews();
	}

	private KNTAppFactory() {
	}

	private static StartScreenPresenter createStartScreenPresenter() {
		StartScreenView view = new StartScreenView();
		StartScreenPresenter presenter = new StartScreenPresenter(view, CLIENT.getPlayerService());
		
		return presenter;
	}
	
	private static LobbyPresenter createLobbyPresenter() {
		LobbyView view = new LobbyView();
		LobbyPresenter presenter = new LobbyPresenter(view, CLIENT.getGameRoomService());
		
		return presenter;
	}
	
	private static GameRoomPresenter createGameRoomPresenter() {
		GameRoomView view = new GameRoomView();
		GameRoomPresenter presenter = new GameRoomPresenter(view, CLIENT.getGameRoomService());
		
		return presenter;
	}
	
	private static GamePresenter createMainPresenter() {
		GameView view = new GameView();
		GamePresenter presenter = new GamePresenter(view, CLIENT.getGameService());

		return presenter;
	}

	private static DicePresenter createDicePresenter() {
		DiceView view = new DiceView();
		DicePresenter presenter = new DicePresenter(view, CLIENT.getGameService());

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
		//CreatureDetailsView cView = new CreatureDetailsView();
		ThingDetailsPresenter presenter = new ThingDetailsPresenter(tView);//, cView);

		return presenter;
	}
	
	private static ArmyDetailsPresenter createArmyDetailsPresenter() {
		ArmyDetailsView view = new ArmyDetailsView();
		ArmyDetailsPresenter presenter = new ArmyDetailsPresenter(view);

		return presenter;
	}

	private static PlayerInfoPresenter createPlayerInfoPresenter() {
		PlayerInfoView view = new PlayerInfoView();
		PlayerInfoPresenter presenter = new PlayerInfoPresenter(view);

		return presenter;
	}

	private static BattlePresenter createBattlePresenter() {
		// Independent DiceViews 
		DiceView dv1 = new DiceView();
		DiceView dv2 = new DiceView();
		DicePresenter dp1 = new DicePresenter(dv1, new Die(), new Die());
		DicePresenter dp2 = new DicePresenter(dv2, new Die(), new Die());
		
		
		BattleView view = new BattleView(dv1, dv2);
		BattlePresenter presenter = new BattlePresenter(view, dp1, dp2);

		return presenter;
	}
	
	private static SpecialCharacterPresenter createSpecialCharacterPresenter() {
		SpecialCharacterView view = new SpecialCharacterView();
		SpecialCharacterPresenter presenter = new SpecialCharacterPresenter(view);
		return presenter;
	}
	
	private static PopupPresenter createPopupPresenter() {
		PopupView view = new PopupView();
		PopupPresenter presenter = new PopupPresenter(view);

		return presenter;
	}

	public static final StartScreenPresenter getStartScreenPresenter() {
		return startScreenPresenter;
	}
	
	public static final LobbyPresenter getLobbyPresenter() {
		return lobbyPresenter;
	}
	
	public static final GameRoomPresenter getGameRoomPresenter() {
		return gameRoomPresenter;
	}
	
	public static ThingDetailsPresenter getThingDetailsPresenter() {
		return thingDetailsPresenter;
	}

	public static GamePresenter getGamePresenter() {
		return gamePresenter;
	}

	public static DicePresenter getDicePresenter() {
		return dicePresenter;
	}

	public static SidePanePresenter getSidePanePresenter() {
		return sidePanePresenter;
	}

	public static BoardPresenter getBoardPresenter() {
		return boardPresenter;
	}

	public static HexDetailsPresenter getHexDetailsPresenter() {
		return hexDetailsPresenter;
	}

	public static PlayerInfoPresenter getPlayerInfoPresenter() {
		return playerInfoPresenter;
	}

	public static PopupPresenter getPopupPresenter() {
		return popupPresenter;
	}

	public static ArmyDetailsPresenter getArmyDetailsPresenter() {
		return armyDetailsPresenter;
	}

	public static BattlePresenter getBattlePresenter() {
		return battlePresenter;
	}

	public static SpecialCharacterPresenter getSpecialCharacterPresenter() {
		return specialCharacterPresenter;
	}
	
	public static GamePlay getGamePlay() {
		// Created when first called.
		if (gamePlay == null)
			gamePlay = new GamePlay();
		
		return gamePlay;
	}
}
