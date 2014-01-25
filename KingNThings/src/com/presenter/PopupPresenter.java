/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.presenter;

import com.model.Player;
import com.model.Thing;
import com.view.popups.PlayerPopup;
import com.view.PopupView;
import com.view.ThingEvent;
import com.view.popups.CupPopup;
import java.util.List;
import javafx.event.EventHandler;

/**
 *
 * @author kurtis
 */
public class PopupPresenter {
    private PopupView view;
    
    // All the views (Pane classes) this popup can display.
    //private PlayerPopup playerPopup;
   // private CupPopup cupPopup;
    
    public PopupPresenter(PopupView view) {
		this.view = view;
		this.view.setPresenter(this);
    }
  

    public PopupView getView() {
    	return view;
    }

    public void dismissPopup() {
    	view.dismiss();
    }

    public void showPlayerPopup(Player p) {
    	view.show(new PlayerPopup(p));
    }
    
    public void showCupPopup(List<Thing> things, String title, EventHandler<ThingEvent> event) {

	view.show(new CupPopup(things, event), title);
    }
}
