/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.Hex;
import com.presenter.DetailsPresenter;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author kurtis
 */
public class DetailsView extends VBox {
    
    // FIXME: Duplicate
    static final double HEX_WIDTH = 100.0; 
    static final double HEX_HEIGHT = HEX_WIDTH *0.8;
    
    private DetailsPresenter presenter;
    
    public DetailsView() {
	buildView();
    }
    
    protected void buildView() {
	setAlignment(Pos.CENTER);
	setId("detailsBox");
	setMinHeight(HEX_HEIGHT * 7);
	
	// Define content.
	getChildren().add(new Label("This is DetailsView"));
    }
    
    public void setPresenter(final DetailsPresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    public void setHex(Hex h) {
	// update ui here.
    }
}
