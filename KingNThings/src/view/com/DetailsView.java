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
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
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
    
    private ImageView hexImage;
    private Label nameLbl;
    private Label testLbl;
    private Label ownerLbl;
    
    public DetailsView() {
	buildView();
    }
    
    protected void buildView() {
	setAlignment(Pos.CENTER);
	setId("detailsBox");
	setMinHeight(HEX_HEIGHT * 7);
	
	// Define content. (FROM paintHexInDetails
	hexImage = new ImageView();
	// FIXME Hardcoded
	hexImage.setFitWidth(300);
	hexImage.setPreserveRatio(true);
	hexImage.setSmooth(true);
	hexImage.setCache(true);
	
	VBox contentBox = new VBox();
	contentBox.getStyleClass().add("block");
	contentBox.setAlignment(Pos.CENTER);
	
	// Name Label
	nameLbl = new Label();
	// Test
	testLbl = new Label();
	// owner
	ownerLbl = new Label();
	
	contentBox.getChildren().addAll(hexImage, nameLbl, ownerLbl, testLbl);
	
	StackPane sp = new StackPane();
	// I think having image here was a bug since added in contentBox
	sp.getChildren().addAll(/*hexImage,*/ contentBox);
	getChildren().add(sp);
    }
    
    public void setPresenter(final DetailsPresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    public void setHex(Hex hex) {
	// update ui here.
	
	if (hex != null) {
	    // Set the Hex Name
	    nameLbl.setText("Type: " + hex.getType().name());
	    // Set the new image.
	    hexImage.setImage(hex.getImage());
	    // Set the owner if it exists
	    if (hex.getOwner() != null) {
		ownerLbl.setText("Owner: " + hex.getOwner().getName());
	    }
	    else {
		ownerLbl.setText("Owner: Not Owned");
	    }
	    
	    // Test label
	    String test = "";
	    for (int i: hex.getJoiningHexes()){
		    test+=i+", "; 
	    }
	    testLbl.setText("removeLater: id=" + hex.getId() + " joins with:"+test);
	}
    }
}
