/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.game.services.GameService;
import com.model.Board;
import com.model.game.Game;
import com.view.BoardView;

/**
 *
 * @author kurtis
 */
public class BoardPresenter {
    private final BoardView view;
    // Required Presenters
//    private HexDetailsPresenter detailsPresenter;
    private SidePanePresenter sidePanePresenter;
    
    // Usually BoardService, but ok for our purposes. We will see in IT2
    private GameService svc;
    
    private int lastHexSelected = -1;
    
    public BoardPresenter(BoardView view, SidePanePresenter sidePanePresenter) {
	this.view = view;
	this.view.setPresenter(this);
//	this.detailsPresenter = details;
	this.sidePanePresenter = sidePanePresenter;
	
	// Set initial model (usually uses a service.
	svc = GameService.getInstance();
	Game game = svc.getGame();
	
	view.setBoard(game.getBoard());
    }
    
    public BoardView getView() {
	return view;
    }
    
    // UI Logic here.

    public void handleHexClick(int selected) {
	// Only need service to fetch board.
	// TODO create GameService#selectHex(...)?
	Board b = svc.getGame().getBoard();
	
	// undo last selection.
	if (lastHexSelected > -1) {
	    b.getHexes().get(lastHexSelected).setSelected(false);
	}
	lastHexSelected = selected;
	b.getHexes().get(selected).setSelected(true);
	
	// Update BoardView
	view.setBoard(b);
	
	// Show the HexDetails in sidepane
	sidePanePresenter.showHexDetailsFor(b.getHexes().get(selected));
//	detailsPresenter.showHex(b.getHexes().get(selected));
    }
}
