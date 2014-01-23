/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view.com;

import com.model.Board;
import com.presenter.BoardPresenter;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import static view.com.GameScreen.playingArea;

/**
 *
 * @author kurtis
 */
public class BoardView extends Canvas {
    
    private BoardPresenter presenter;
    
    // TODO Duplicated in SidePane. Create HexView?
    static final double HEX_WIDTH = 100.0; 
    static final double HEX_HEIGHT = HEX_WIDTH *0.8;
    
    public BoardView() {
	// FIXME Hardcoded stuff. is it needed?
	super(1280 * 0.5 - 10, HEX_HEIGHT * 7.2);
	buildView();
    }
    
    public void setPresenter(final BoardPresenter presenter) {
        if (presenter == null)
            throw new NullPointerException("Presenter cannot be null");
        
        if (this.presenter != null)
            throw new IllegalStateException("The presenter was already set.");
        
        this.presenter = presenter;
    }
    
    protected void buildView() {
	setId("playingArea");
	getStyleClass().add("border");
	paintBackgroundImage();
    }

    private void paintBackgroundImage() {
	GraphicsContext gc = getGraphicsContext2D();
	gc.clearRect(0, 0,getWidth(), getHeight());	
	Image imgBg = new Image("view/com/assets/pics/background.jpg");
	gc.drawImage(imgBg, 0,0,getWidth(), getHeight());
    }
    
    public void setBoard(Board board) {
	
    }
}
