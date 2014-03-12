package com.presenter;

import com.view.LobbyView;

public class LobbyPresenter {
	private final LobbyView view;
	
	public LobbyPresenter(final LobbyView view) {
		this.view = view;
	}
	
	/**
	 * Gets the view managed by this presenter.
	 * @return The view
	 */
	public final LobbyView getView() {
		return view;
	}
	
	public void handleRefreshButton() {
		System.out.println("Refresh");
	}

	public void handleHostButton() {
		System.out.println("Host");
	}

	public void handleJoinEvent() {
		System.out.println("Join");		
	}

	public void handleWindowShown() {
		System.out.println("Fetching GameRooms.");		
	}
}
