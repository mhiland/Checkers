package Game;

import java.util.ArrayList;
import java.util.List;

import org.freedesktop.cairo.Context;
import org.gnome.gtk.DrawingArea;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Widget;

import Visuals.Background;
import Visuals.BlackToken;
import Visuals.RedToken;
import Visuals.Tokens;
import viewer.Viewer;


/**
 * Create Checkers Game
 * @author mdh
 *
 */
public class Game {
	protected static DrawingArea canvas;
	private static Context context;
	private static Game myGame;
	private GameArea gameArea;
	private Background background;
	private Tokens tok;
	private static Game game;
	protected static List<Tokens> rTokens;
	protected static List<Tokens> bTokens;
//	protected int[] rStart = new int[]{1,3,5,7,8,10,12,14,17,19,21,23};
//	protected int[] bStart = new int[]{40,42,44,46,49,51,53,55,56,58,60,62};
	protected int[] rStart = new int[]{1,3,5,7,8,10,12,14,17,19,23,49,33};//testing
	protected int[] bStart = new int[]{28,40,42,44,46,53,56,58,60,62};//testing

	/**
	 * Explicit Constructor 
	 */
	protected Game() {}

	/**
	 * Construct a new plot with the internal viewer
	 * @param title Application Title
	 */
	public Game(String title) {
		myGame = this;
		Viewer viewer = new Viewer(title); 
		setCanvas(viewer.getArea());
		setupGame();
	}


	public void setupGame() {
		canvas.setTooltipText("Player Ones Move");
		rTokens = new ArrayList<Tokens>();
		bTokens = new ArrayList<Tokens>();
		for (int i : rStart){
			tok = new RedToken(i);
			rTokens.add(tok);
		}
		for (int i : bStart){
			tok = new BlackToken(i);
			bTokens.add(tok);
		}
	}

	/**
	 * Create a Canvas for the GTKDrawingArea
	 */
	private void setCanvas(DrawingArea area) {
		Game.canvas = area;
		gameArea = new GameArea();
		gameArea.setWidth(canvas.getAllocatedWidth());
		gameArea.setHeight(canvas.getAllocatedHeight());
		background = new Background();
	}

	/**
	 * Commits the Game to the GTK DrawingArea
	 */
	public void show(){
		canvas.connect(new Widget.Draw() {
		
			public boolean onDraw(Widget source, Context cr) {
				try {
					drawGame(cr);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}	
		}
				);
		Gtk.main();
	}

	/**
	 * Draws Plot to a given Context
	 * @param cr
	 */
	private void drawGame(Context cr){
		Game.setContext(cr);
		gameArea.setGameArea(canvas);
		background.setBackground();
		for (Tokens t:rTokens)
			t.draw();
		for (Tokens t:bTokens)
			t.draw();

		cr.stroke();
	}
	
	public void reDrawGame(){
		canvas.queueDraw();
	}

	// For use with Internal Viewer
	protected static Game getGame(){
		return myGame;
	}

	public static Context getContext() {
		return context;
	}

	private static void setContext(Context context) {
		Game.context = context;
	}

	public static void main(String[] args) throws Exception {
		game = new Game("Gnome Checkers");
		game.show();
	}

}
