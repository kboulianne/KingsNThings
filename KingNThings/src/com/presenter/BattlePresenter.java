package com.presenter;

import com.view.BattleView;

public class BattlePresenter {
	private BattleView view;
	
	public BattlePresenter(BattleView v){
		view = v;
		view.setPresenter(this);
	}

	public BattleView getView() {
		return view;
	}
}
