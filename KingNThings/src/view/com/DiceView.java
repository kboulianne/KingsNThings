/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.Die;
import com.presenter.DicePresenter;
import com.presenter.GamePresenter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/** Displays both dice and the roll button.
 *
 * @author kurtis
 */
public class DiceView extends HBox {
    
    private DicePresenter presenter;
    
    // The model(s)
    private Die die1;
    private Die die2;
    
    private ImageView die1Iv;
    private ImageView die2Iv;
    
    public DiceView(Die die1, Die die2) {
	this.die1 = die1;
	this.die2 = die2;
	
	buildView();
    }
    
    public void setPresenter(final DicePresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    protected void buildView() {
	// View initializations
	setId("topRBox");
	AnchorPane.setRightAnchor(this, 0.0);
	AnchorPane.setTopAnchor(this, 0.0);
	
	// First die
	die1Iv = new ImageView();
	die1Iv.setFitWidth(48);
	die1Iv.setPreserveRatio(true);
	
	// Second die
	die2Iv = new ImageView();
	die2Iv.setFitWidth(48);
	die2Iv.setPreserveRatio(true);
	
	// Roll button
	Button roll = new Button("Roll");
	roll.setOnAction(new EventHandler<ActionEvent>() {

	    @Override
	    public void handle(ActionEvent t) {
		presenter.roll();
	    }
	});
	
	
	// Add all controls
	getChildren().addAll(die1Iv, die2Iv, roll);
    }
    
    public void setDice(Die d1, Die d2) {
	die1Iv.imageProperty().set(d1.getImage());
	die2Iv.imageProperty().set(d2.getImage());
    }
}