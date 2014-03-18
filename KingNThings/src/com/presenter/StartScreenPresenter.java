package com.presenter;

import com.main.KNTAppFactory;
import com.model.Player;
import com.view.StartScreenView;

public class StartScreenPresenter {

	private StartScreenView view;
	
	
	public StartScreenPresenter(StartScreenView view) {
		this.view = view;
	}
	
	public StartScreenView getView() {
		return view;
	}
	
	public void handlePlay(String name) {
		// Delegate to lobby
		Player p = new Player(name);
		
		KNTAppFactory.getLobbyPresenter().show(p);
	}
}

