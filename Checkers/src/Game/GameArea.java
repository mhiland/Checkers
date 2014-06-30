package Game;


import org.freedesktop.cairo.Context;
import org.gnome.gtk.DrawingArea;

/**
 * Defines the plotting dimensions for all plot elements. <br>
 * Dimensions are in pixels.
 * @author mdh
 *
 */
public class GameArea extends Game {
	protected static double margin  = 0.14;
	protected static double leftMargin;
	protected static double topMargin;
	protected static double rightMargin;
	protected static double bottomMargin;
	protected static double xmin = 0;
	protected static double xmax = 1;
	protected static double ymin = 0;
	protected static double ymax = 1;
	protected static double[] x;
	protected static double[] y;
	protected static double plotWidth;
	protected static double plotHeight;
	protected static int width;   //in pixels
	protected static int height; //in pixels
	protected static double xInterval;
	protected static double yInterval;
	protected static double xScale;
	protected static double yScale;
	protected static double xOffSet;
	protected static double yOffSet; 
	protected static GameArea myPlotArea;
	
	
	/**
	 * Constructor <br>
	 * saves class instance
	 */
	protected GameArea(){
		myPlotArea = this;
	}
	
	/**
	 * Defines the width, height and margins of the plotting area.
	 * @param newplot 
	 * @param canvas
	 */
	protected void setGameArea(DrawingArea canvas){
		leftMargin = width * margin;
		topMargin = height * margin; 
		rightMargin = width*(1.-1.*margin);
		bottomMargin = height*(1.-1.*margin);
		plotWidth=(rightMargin - leftMargin);
		plotHeight=(bottomMargin - topMargin);
	}




	/**
	 * Set Colour
	 * @param lc
	 * <br>0: White<br>1: Black <br>2: Red<br>3: Green<br>4: Blue<br>5: Yellow
	 * <br>6: Grey <br>7: Purple
	 */
	public void setColour(int lc){
		Context cr = Game.getContext();
		int total = 8; 
		if ( lc          == 0)cr.setSource(1.0, 1.0, 1.0, 1.0); //white
		if ((lc % total) == 1)cr.setSource(0.0, 0.0, 0.0, 1.0); //black
		if ((lc % total) == 2)cr.setSource(1.0, 0.0, 0.0, 1.0); //red
		if ((lc % total) == 3)cr.setSource(0.0, 1.0, 0.0, 1.0); //green
		if ((lc % total) == 4)cr.setSource(0.0, 0.0, 1.0, 1.0); //blue
		if ((lc % total) == 5)cr.setSource(1.0, 1.0, 0.0, 1.0); //yellow
		if ((lc % total) == 6)cr.setSource(0.5, 0.5, 0.5, 1.0); //grey
		if ((lc % total) == 7)cr.setSource(0.5, 0.0, 0.5, 1.0); //purple
	}

	/**
	 * Set Width of Line <br>
	 * default: 1 for line plots
	 * @param size
	 */
	public void setLineWidth(double size){
		Context cr = Game.getContext();
		cr.setLineWidth(size);
	}
	protected void setWidth(int width) {
		GameArea.width= width;
	}

	protected void setHeight(int height) {
		GameArea.height = height;
	}
	
	protected double getWidth() {
		return width;
	}

	protected double getHeight() {
		return height;
	}
	protected double getPlotWidth() {
		return plotWidth;
	}

	protected double getPlotHeight() {
		return plotHeight;
	}
	
	protected double getMargin(){
		return margin;
	}
	
	public void setMargin(double value){
		margin = value;
	}
	
	protected double getTopMargin(){
		return topMargin;
	}
	
	protected double getBottomMargin(){
		return bottomMargin;
	}
	
	protected double getLeftMargin(){
		return leftMargin;
	}
	
	protected double getRightMargin(){
		return rightMargin;
	}
	
	protected double getxScale(){
		return xScale;
	}
	
	protected double getyScale(){
		return yScale;
	}
	protected double getxInterval(){
		return xInterval;
	}
	
	protected double getyInterval(){
		return yInterval;
	}
	protected double getxOffSet(){
		return xOffSet;
	}
	
	protected double getyOffSet(){
		return yOffSet;
	}
	
	protected double getxmin(){
		return xmin;
	}
	protected double getymin(){
		return ymin;
	}
	
	protected double getxmax(){
		return xmax;
	}
	
	protected double getymax(){
		return ymax;
	}
}
