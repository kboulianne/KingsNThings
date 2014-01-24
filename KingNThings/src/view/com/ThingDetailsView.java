/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.Thing;
import com.presenter.ThingDetailsPresenter;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/** When a thing is clicked, display this in SidePaneView using
 * SidePaneView#showThingDetailsView
 *
 * @author kurtis
 */
public class ThingDetailsView extends StackPane {
    
    private ThingDetailsPresenter presenter;
    
    public ThingDetailsView() {
        buildView();
    }
    
    public void setPresenter(final ThingDetailsPresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    protected void buildView() {
        getChildren().add(new Label("This is ThingDetailsView"));
    }
    
    public void setThing(Thing thing) {
        
    }
}
