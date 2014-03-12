package com.view;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.FlowPaneBuilder;

public class LobbyView extends BorderPane {

	private Button refreshBtn;
	private Button hostBtn;
	private Button joinBtn;
	
	public LobbyView() {
		buildView();
	}
	
	protected void buildView() {
		// Top
		refreshBtn = ButtonBuilder.create()
			.text("Refresh")
		.build();
		
		FlowPane topPane = FlowPaneBuilder.create()
				.children(refreshBtn)
		.build();
	}
}
