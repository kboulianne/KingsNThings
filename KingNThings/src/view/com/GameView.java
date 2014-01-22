/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.presenter.GamePresenter;
import com.view.model.GameViewModel;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author kurtis
 */
public class GameView extends AnchorPane {
    private GameViewModel model;
    private GamePresenter presenter;
    
    // Class-level controls needing exposure outside buildView()
    // private Button roll;
    
    public GameView(final GameViewModel model) {
        this.model = model;
        buildView();
    }
    
    public void setPresenter(final GamePresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    protected void buildView() {
        
    }
}
