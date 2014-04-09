package com.presenter;

import com.main.KNTAppFactory;
import com.main.NetworkedMain;
import com.model.Player;
import com.server.services.IPlayerService;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.view.StartScreenView;

public class StartScreenPresenter {

	private StartScreenView view;
	private final IPlayerService playerService;
	
	public StartScreenPresenter(StartScreenView view, IPlayerService service) {
		this.view = view;
		
		playerService = service;
	}
	
	public StartScreenView getView() {
		return view;
	}
	
	public void handlePlay(String name) {
		// Send name to server.
		try {
			Player p = playerService.connect(name);
			NetworkedMain.setPlayer(p);
			
			KNTAppFactory.getLobbyPresenter().show();
		} catch (JSONRPC2Error e) {
			e.printStackTrace();
		}		
	}
}

