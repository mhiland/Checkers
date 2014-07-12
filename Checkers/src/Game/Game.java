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
	private static GameArea gameArea;
	private static Background background;
	private static Tokens tok;
	private static Game game;
	protected static int bKings = 0;
	protected static List<Tokens> rTokens;
	protected static List<Tokens> bTokens;
	protected static boolean endOfGame = false;
	protected static int[] rCrown = new int[]{56,58,60,62};
	protected static int[] bCrown = new int[]{1,3,5,7,};
	protected static int[] rStart = new int[]{1,3,5,7,8,10,12,14,17,19,21,23};
	protected static int[] bStart = new int[]{40,42,44,46,49,51,53,55,56,58,60,62};
	protected static int[] validPositions = new int[]
			{1,3,5,7,
			8,10,12,14,
			17,19,21,23,
			24,26,28,30,
			33,35,37,39,
			40,42,44,46,
			49,51,53,55,
			56,58,60,62};
//	protected int[] rStart = new int[]{1,3,5,7,8,10,12,14,17,19,23,49,33};//testing
//	protected int[] bStart = new int[]{28,40,42,44,46,53,56,58,60,62};//testing
//	protected int[] rStart = new int[]{8,14,23,26,53,37};//testing
//	protected int[] bStart = new int[]{10,12,39};//testing
	/**
	 * Explicit Constructor 
	 */
	public Game() {}

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
	protected void setCanvas(DrawingArea area) {
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

	public boolean blackToken(int i){
		for (Tokens t: bTokens)
			if(i == t.getPos())
				return true;
		return false;
	}
	
	public boolean redToken(int i){
		for (Tokens t: rTokens)
			if(i == t.getPos())
				return true;
		return false;
	}
	
	public boolean isValid(int val){
		for (int i : validPositions){
			if (i == val){
				return true;
			}
		}
		return false;
	}
	

	public static void main(String[] args) throws Exception {
		game = new Game("Gnome Checkers");
		game.show();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tok == null) ? 0 : tok.hashCode());
		return result;
	}

	@SuppressWarnings("static-access")
	@Override
	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Game other = (Game) obj;
		if (tok == null) {
			if (other.tok != null)
				return false;
		} else if (!(tok.getPos()==other.tok.getPos()))
			return false;
		return true;
	}
	
	
}
