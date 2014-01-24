/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.view;

import com.model.Board;
import com.model.Hex;
import com.presenter.BoardPresenter;
import com.presenter.Util;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author kurtis
 */
public class BoardView extends Canvas {
    
    private BoardPresenter presenter;
    
    // TODO Duplicated in SidePane. Create HexView?
    static final double HEX_WIDTH = 100.0; 
    static final double HEX_HEIGHT = HEX_WIDTH *0.8;
    double[][] hexCenterPoints;
    static double[][] choosenMapping;
    // OK Here this is UI stuff.
    static final double[][] MAPPING_37_TILES = new double[][]{{4.0,7.0},{4.0,5.0},{5.0,6.0},{5.0,8.0},{4.0,9.0},
			{3.0,8.0},{3.0,6.0},{3.0,4.0},{4.0,3.0},{5.0,4.0},{6.0,5.0},{6.0,7.0},{6.0,9.0},
			{5.0,10.0},{4.0,11.0},{3.0,10.0},{2.0,9.0},{2.0,7.0},{2.0,5.0},{2.0,3.0},{3.0,2.0},
			{4.0,1.0},{5.0,2.0},{6.0,3.0},{7.0,4.0},{7.0,6.0},{7.0,8.0},{7.0,10.0},{6.0,11.0},
			{5.0,12.0},{4.0,13.0},{3.0,12.0},{2.0,11.0},{1.0,10.0},{1.0,8.0},{1.0,6.0},{1.0,4.0}
	};
    static final double[][] MAPPING_19_TILES = new double[][]{{3.0,5.0},{3.0,3.0},{4.0,4.0},
			{4.0,6.0},{3.0,7.0},{2.0,6.0},{2.0,4.0},{2.0,2.0},{3.0,1.0},{4.0,2.0},{5.0,3.0},
			{5.0,5.0},{5.0,7.0},{4.0,8.0},{3.0,9.0},{2.0,8.0},{1.0,7.0},{1.0,5.0},{1.0,3.0}
	};
    static final Image START_IMAGE = new Image("view/com/assets/pics/tiles/start.png");
    
    public BoardView() {
	// FIXME Hardcoded stuff. is it needed?
	super(1280 * 0.5 - 10, HEX_HEIGHT * 7.2);
	buildView();
	addHandlers();
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
	
	choosenMapping = MAPPING_37_TILES;
	hexCenterPoints = new double[choosenMapping.length][2];
	for(int i = 0; i<choosenMapping.length;i++){
		double xOffset = choosenMapping[i][0]*0.75*HEX_WIDTH-40.0;
		double yOffset = choosenMapping[i][1]*0.5*HEX_HEIGHT-30.0;
		hexCenterPoints[i][0]=xOffset+(HEX_WIDTH*0.5);
		hexCenterPoints[i][1]=yOffset+(HEX_HEIGHT*0.5);
//		hexes.get(i).paint(null);
	}
    }

    private void addHandlers() {
	setOnMouseClicked(new EventHandler<MouseEvent>() {

	    @Override
	    public void handle(MouseEvent event) {
		double clickPtX = event.getX();
		double clickPtY = event.getY();
		for(int i = 0; i<hexCenterPoints.length;i++){
		    if(Util.distanceBtwTwoPts(
			    clickPtX, clickPtY,
			    hexCenterPoints[i][0], hexCenterPoints[i][1]) < HEX_WIDTH*0.30) {
			
			presenter.handleHexClick(i);
			break;
		    }
		}
//		presenter.handleHexClick(event.getX(), event.getY());
	    }
	});
    }
    
    /**
     * Paints the Background Image on the canvas.
     */
    private void paintBackgroundImage() {
	GraphicsContext gc = getGraphicsContext2D();
	gc.clearRect(0, 0,getWidth(), getHeight());	
	Image imgBg = new Image("view/com/assets/pics/background.jpg");
	gc.drawImage(imgBg, 0,0,getWidth(), getHeight());
    }
    
    /**
     * Paints the specified Hex on the canvas.
     * @param hex The hex to paint.
     */
    private void paintHex(final Hex hex) {
	GraphicsContext gc = getGraphicsContext2D();
	double height = HEX_HEIGHT;
//		double choosenMapping[][] = GameScreen.getChoosenMapping();

	double xOffset = choosenMapping[hex.getId()][0]*0.75*HEX_WIDTH-40.0; //40 move whole grid
	double yOffset = choosenMapping[hex.getId()][1]*0.5*height-30.0; //30 moves whole grid
	//gc.fillOval(xOffset, yOffset, xOffset+200, yOffset+200);

	//outer polygon
	gc.setFill(Color.BLACK);
	gc.fillPolygon(new double[]{(xOffset+(HEX_WIDTH*0.25)), (xOffset+(HEX_WIDTH*0.75)), (xOffset+HEX_WIDTH), 	
								 xOffset+(HEX_WIDTH*0.75), xOffset+(HEX_WIDTH*0.25), xOffset},
				   new double[]{yOffset, yOffset, yOffset+(height*0.5), 		
								yOffset+height, yOffset+height, yOffset+(height*0.5)}, 6);
	//inner polygon
	double gap=HEX_WIDTH*0.05;
	double temp_width = HEX_WIDTH;
	xOffset+=gap;
	yOffset+=gap;
	height-=(gap*2);//
	temp_width-=(gap*2);
	if (hex.isHighlighted()){
	    gc.setFill(Color.LIGHTBLUE);
	}
	else if (hex.isSelected()){
	    gc.setFill(Color.WHITESMOKE);
	}
	else {
	    gc.setFill(hex.getColor());
	}
	gc.fillPolygon(new double[]{(xOffset+(temp_width*0.25)), (xOffset+(temp_width*0.75)), (xOffset+temp_width), 	
								xOffset+(temp_width*0.75), xOffset+(temp_width*0.25), xOffset},
				   new double[]{yOffset, yOffset, yOffset+(height*0.5), 		
								yOffset+height, yOffset+height, yOffset+(height*0.5)}, 6);
	//image
	gap=temp_width*0.05;
	double imageAdjust=4.0;
	if(hex.isStartPosition())
	    gc.drawImage(START_IMAGE, xOffset+gap+(imageAdjust/2), yOffset+gap, temp_width-(gap*2.0)-imageAdjust, height-(gap*2.0));
	else
	    gc.drawImage(hex.getImage(), xOffset+gap+(imageAdjust/2), yOffset+gap, temp_width-(gap*2.0)-imageAdjust, height-(gap*2.0));
    }
    
    /**
     * Sets the board instance that the UI must display.
     * @param board The board to display.
     */
    public void setBoard(Board board) {
	List<Hex> hexes = board.getHexes();
	
	for (Hex h : hexes) {
	    paintHex(h);
	}
    }
}
