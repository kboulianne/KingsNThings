package com.main;

import com.model.Die;
import com.presenter.ArmyDetailsPresenter;
import com.presenter.BattlePresenter;
import com.presenter.DicePresenter;
import com.presenter.GamePresenter;
import com.presenter.BoardPresenter;
import com.presenter.HexDetailsPresenter;
import com.presenter.PlayerInfoPresenter;
import com.presenter.PopupPresenter;
import com.presenter.SidePanePresenter;
import com.presenter.ThingDetailsPresenter;
import com.view.ArmyDetailsView;
import com.view.BattleView;
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
 * Presenters and views are created once and reused.
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
	private static final ArmyDetailsPresenter armyDetailsPresenter;
	private static final PlayerInfoPresenter playerInfoPresenter;
	private static final PopupPresenter popupPresenter;
	private static final BattlePresenter battlePresenter;

	static {
		// Create all presenters then set their dependencies. This way we avoid Circular Dependency problem.
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
		
		// Done initializing
		gamePresenter.setupSubViews();
	}

	private KNTAppFactory() {
	}

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
	
	private static PopupPresenter createPopupPresenter() {
		PopupView view = new PopupView();
		PopupPresenter presenter = new PopupPresenter(view);

		return presenter;
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

}
